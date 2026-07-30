package ru.osaulenko.mapper.persistence;


import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.osaulenko.domain.AccountTransactionDomain;
import ru.osaulenko.domain.TransactionTypeDomain;
import ru.osaulenko.entities.Account;
import ru.osaulenko.entities.AccountTransaction;
import ru.osaulenko.entities.TransactionType;

import java.util.List;
import java.util.UUID;

@Mapper(componentModel = "spring", uses = {AccountMoneyMapper.class})
public interface AccountTransactionMapper {

    @Mapping(source = "account.accountId", target = "accountId")
    @Mapping(source = "type", target = "type")
    AccountTransactionDomain toDomain(AccountTransaction entity);

    @Mapping(target = "account", expression = "java(uuidToAccount(domain.getAccountId()))")
    @Mapping(target = "type", source = "type")
    @Mapping(target = "transactionId", ignore = true)
    AccountTransaction toEntity(AccountTransactionDomain domain);

    List<AccountTransactionDomain> toDomainList(List<AccountTransaction> toDomainList);

    TransactionType toEntityType(TransactionTypeDomain transactionTypeDomain);


    default Account uuidToAccount(UUID id) {
        if (id == null)
            throw new IllegalArgumentException("id must be not null");
        Account account = new Account();
        account.setAccountId(id);
        return account;
    }
}
