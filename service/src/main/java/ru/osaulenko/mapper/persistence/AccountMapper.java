package ru.osaulenko.mapper.persistence;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.osaulenko.domain.AccountDomain;
import ru.osaulenko.entities.Account;
import ru.osaulenko.entities.AccountTransaction;
import ru.osaulenko.entities.User;

import java.util.List;
import java.util.UUID;

@Mapper(componentModel = "spring", uses = {AccountMoneyMapper.class})
public interface AccountMapper {

    @Mapping(source = "user.userId", target = "userId")
    @Mapping(target = "transactions", expression = "java(toTransactionIds(entity.getTransactions()))")
    AccountDomain toDomain(Account entity);

    @Mapping(source = "userId", target = "user")
    @Mapping(target = "transactions", ignore = true)
    @Mapping(target = "accountId", source = "accountId")
    Account toEntity(AccountDomain domain);

    List<AccountDomain> toDomainList(List<Account> entities);

    default List<UUID> toTransactionIds(List<AccountTransaction> transactions) {
        if (transactions == null)
            throw new IllegalArgumentException("transaction must be not null");
        return transactions.stream().map(AccountTransaction::getTransactionId).toList();
    }

    default User uuidToUser(UUID id) {
        if (id == null)
            throw new IllegalArgumentException("id must be not null");
        User user = new User();
        user.setUserId(id);
        return user;
    }
}
