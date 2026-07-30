package ru.osaulenko.mapper.persistence;

import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.osaulenko.domain.UserDomain;
import ru.osaulenko.entities.Account;
import ru.osaulenko.entities.User;
import ru.osaulenko.repository.UserRepository;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "friends", expression = "java(toFriendIds(entity.getFriends()))")
    @Mapping(target = "accounts", expression = "java(toAccountIds(entity.getAccounts()))")
    UserDomain toDomain(User entity);

    @Mapping(target = "friends", ignore = true)
    @Mapping(target = "accounts", ignore = true)
    User toEntity(UserDomain domain);

    List<UserDomain> toDomainList(List<User> entityList);


    default List<UUID> toFriendIds(List<User> friends) {
        if (friends == null)
            throw new IllegalArgumentException("friends must be not null");
        return friends.stream().map(User::getUserId).collect(Collectors.toCollection(LinkedList::new));
    }

    default List<UUID> toAccountIds(List<Account> accounts) {
        if (accounts == null)
            throw new IllegalArgumentException("accounts must be not null");
        return accounts.stream().map(Account::getAccountId).collect(Collectors.toCollection(LinkedList::new));
    }
}
