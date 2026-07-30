package ru.osaulenko.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.osaulenko.domain.AccountDomain;
import ru.osaulenko.domain.AccountMoneyDomain;
import ru.osaulenko.domain.AccountTransactionDomain;
import ru.osaulenko.domain.TransactionTypeDomain;
import ru.osaulenko.dto.request.account.*;
import ru.osaulenko.dto.response.AccountResponseDto;
import ru.osaulenko.dto.response.TransactionResponseDto;
import ru.osaulenko.dto.response.TransactionTypeDto;
import ru.osaulenko.entities.*;
import ru.osaulenko.mapper.dto.AccountDtoMapper;
import ru.osaulenko.mapper.dto.TransactionDtoMapper;
import ru.osaulenko.mapper.persistence.AccountMapper;
import ru.osaulenko.mapper.persistence.AccountMoneyMapper;
import ru.osaulenko.mapper.persistence.AccountTransactionMapper;
import ru.osaulenko.repository.AccountRepository;
import ru.osaulenko.repository.TransactionRepository;
import ru.osaulenko.repository.security.ClientRepository;
import ru.osaulenko.service.security.SecurityUtils;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;
    private final ClientRepository clientRepository;

    private final SecurityUtils securityUtils;

    private final AccountMapper accountMapper;
    private final AccountDtoMapper accountDtoMapper;
    private final AccountMoneyMapper accountMoneyMapper;
    private final AccountTransactionMapper transactionMapper;
    private final TransactionDtoMapper transactionDtoMapper;

    private final BigDecimal OWN_COMMISSION = new BigDecimal("0.00");
    private final BigDecimal FRIEND_COMMISSION = new BigDecimal("0.03");
    private final BigDecimal OTHER_COMMISSION = new BigDecimal("0.10");

    @Transactional
    public AccountResponseDto replenish(ReplenishRequestDto requestDto) {
        Account account = accountRepository.findByAccountId(requestDto.getAccountId())
                .orElseThrow(() -> new RuntimeException("Account not found"));

        validateAccountAccess(account);

        AccountDomain domain = accountMapper.toDomain(account);

        AccountMoneyDomain moneyDomain = new AccountMoneyDomain(requestDto.getAmount());

        domain.addBalance(moneyDomain);

        Account updated = accountMapper.toEntity(domain);
        accountRepository.save(updated);

        AccountTransaction transaction = new AccountTransaction();
        transaction.setAccount(updated);
        transaction.setType(TransactionType.REPLENISH);
        transaction.setMoney(accountMoneyMapper.toEntity(moneyDomain));

        transactionRepository.save(transaction);

        return accountDtoMapper.toDto(domain);
    }

    @Transactional
    public AccountResponseDto debiting(DebitingRequestDto requestDto) {
        Account account = accountRepository.findByAccountId(requestDto.getAccountId())
                .orElseThrow(() -> new RuntimeException("Account not found"));

        validateAccountAccess(account);

        AccountDomain domain = accountMapper.toDomain(account);

        AccountMoneyDomain moneyDomain = new AccountMoneyDomain(requestDto.getAmount());

        domain.subtractBalance(moneyDomain);

        Account updated = accountMapper.toEntity(domain);
        accountRepository.save(updated);

        AccountTransaction transaction = new AccountTransaction();
        transaction.setAccount(updated);
        transaction.setType(TransactionType.DEBITING);
        transaction.setMoney(accountMoneyMapper.toEntity(moneyDomain));

        transactionRepository.save(transaction);

        return accountDtoMapper.toDto(domain);
    }

    @Transactional(readOnly = true)
    public AccountResponseDto viewBalance(ViewBalanceRequestDto requestDto) {
        Account account = accountRepository.findByAccountId(requestDto.getAccountId())
                .orElseThrow(() -> new RuntimeException("Account not found"));

        validateAccountAccess(account);

        AccountDomain domain = accountMapper.toDomain(account);
        return accountDtoMapper.toDto(domain);
    }

    @Transactional
    public AccountResponseDto transfer(TransferRequestDto requestDto) {
        Account accountFrom = accountRepository.findByAccountId(requestDto.getFromId())
                .orElseThrow(() -> new RuntimeException("AccountFrom not found"));
        Account accountTo = accountRepository.findByAccountId(requestDto.getToId())
                .orElseThrow(() -> new RuntimeException("AccountTo not found"));
        User userFrom = clientRepository.findByUserProfile_UserId(accountFrom.getUser().getUserId())
                .orElseThrow(() -> new RuntimeException("UserFrom not found"))
                .userProfile;
        User userTo = clientRepository.findByUserProfile_UserId(accountTo.getUser().getUserId())
                .orElseThrow(() -> new RuntimeException("UserTo not found"))
                .userProfile;

        validateAccountAccess(accountFrom);

        BigDecimal debitingWithCommission;

        if (userFrom.getFriends().contains(userTo)) {
            debitingWithCommission = requestDto.getAmount().multiply(BigDecimal.ONE.add(FRIEND_COMMISSION));
        } else if(userFrom.getUserId().equals(userTo.getUserId())) {
            debitingWithCommission = requestDto.getAmount().multiply(BigDecimal.ONE.add(OWN_COMMISSION));
        } else {
            debitingWithCommission = requestDto.getAmount().multiply(BigDecimal.ONE.add(OTHER_COMMISSION));
        }
        AccountMoneyDomain debitingDomain = new AccountMoneyDomain(debitingWithCommission);

        AccountDomain domainFrom = accountMapper.toDomain(accountFrom);
        AccountDomain domainTo = accountMapper.toDomain(accountTo);

        domainFrom.subtractBalance(debitingDomain);
        domainTo.addBalance(new AccountMoneyDomain(requestDto.getAmount()));

        AccountTransaction transactionFrom = new AccountTransaction();
        transactionFrom.setMoney(accountMoneyMapper.toEntity(debitingDomain));
        transactionFrom.setType(TransactionType.TRANSFER);
        transactionFrom.setAccount(accountFrom);

        AccountTransaction transactionTo = new AccountTransaction();
        transactionTo.setMoney(accountMoneyMapper.toEntity(new AccountMoneyDomain(requestDto.getAmount())));
        transactionTo.setType(TransactionType.REPLENISH);
        transactionTo.setAccount(accountTo);

        accountRepository.save(accountMapper.toEntity(domainFrom));
        accountRepository.save(accountMapper.toEntity(domainTo));
        transactionRepository.save(transactionFrom);
        transactionRepository.save(transactionTo);

        return accountDtoMapper.toDto(domainFrom);
    }

    @Transactional(readOnly = true)
    public AccountResponseDto getAccount(GetAccountByIdRequestDto getAccountByIdRequestDto) {
        UUID accountId = getAccountByIdRequestDto.getAccountId();

        Account account = accountRepository
                .findByAccountId(accountId).orElseThrow(() -> new RuntimeException("account not found"));

        validateAccountAccess(account);

        AccountDomain accountDomain = accountMapper.toDomain(account);

        return accountDtoMapper.toDto(accountDomain);
    }

    @Transactional(readOnly = true)
    public List<TransactionResponseDto> getTransactions(GetTransactionsRequestDto requestDto) {
        Account account = accountRepository.findByAccountId(
                requestDto.getAccountId()).orElseThrow(() -> new RuntimeException("Account not found"));

        validateAccountAccess(account);

        List<AccountTransaction> transactions = transactionRepository.findByFilters(
                account.getAccountId(),
                transactionMapper.toEntityType(
                        (TransactionTypeDomain.fromString(requestDto.getTypeDto()))));
        return transactionDtoMapper.toDtoList(transactionMapper.toDomainList(transactions));
    }


    @Transactional(readOnly = true)
    public List<AccountResponseDto> getAllAccountsFilterById(GetAccountsRequestDto requestDto) {
        if (!securityUtils.isAdmin()) {
            throw new AccessDeniedException("only admin can view other account");
        }

        List<AccountDomain> accountDomainList = accountMapper.toDomainList(
                accountRepository.findWithTransactions_UserId(requestDto.getUserId()));
        return accountDtoMapper.toDtoList(accountDomainList);
    }

    @Transactional
    public AccountResponseDto create(CreateAccountRequestDto requestDto) {
        if (!securityUtils.isAdmin()) {
            throw new AccessDeniedException("only admin can crate account");
        }

        User user = clientRepository.findByUserProfile_UserId(requestDto.getUserId())
                .orElseThrow(() -> new RuntimeException("user not found")).userProfile;

        AccountDomain domain = new AccountDomain();
        domain.setAccountNumber(requestDto.getAccountNumber());
        domain.setBalance(new AccountMoneyDomain(BigDecimal.ZERO));
        domain.setUserId(user.getUserId());

        Account saved = accountRepository.save(accountMapper.toEntity(domain));

        return accountDtoMapper.toDto(accountMapper.toDomain(saved));
    }

    @Transactional(readOnly = true)
    public List<AccountResponseDto> getAllAccounts() {
        if (!securityUtils.isAdmin()) {
            throw new AccessDeniedException("only admin can view accounts");
        }

        List<AccountDomain> accountDomainList = accountMapper.toDomainList(accountRepository.findAllWithTransactions());
        return accountDtoMapper.toDtoList(accountDomainList);
    }

    private void validateAccountAccess(Account account) {
        if (securityUtils.isAdmin()) {
            return;
        }
        UUID userClientId = securityUtils.getUUID();

        UUID userId = clientRepository.findById(userClientId)
                .orElseThrow(() -> new RuntimeException("client not found"))
                .userProfile.getUserId();


        if (!userId.equals(account.getUser().getUserId())) {
            throw new AccessDeniedException("Access denied");
        }
    }
}
