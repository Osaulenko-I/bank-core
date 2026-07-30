package ru.osaulenko.mapper.persistence;

import org.mapstruct.Mapper;
import ru.osaulenko.domain.AccountMoneyDomain;
import ru.osaulenko.entities.AccountMoney;

@Mapper(componentModel = "spring")
public interface AccountMoneyMapper {
    default AccountMoneyDomain toDomain(AccountMoney entity) {
        return new AccountMoneyDomain(entity.getValue());
    }

    default AccountMoney toEntity(AccountMoneyDomain domain) {
        return new AccountMoney(domain.getValue());
    }
}
