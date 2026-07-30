package ru.osaulenko.mapper.dto;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.osaulenko.domain.AccountTransactionDomain;
import ru.osaulenko.domain.TransactionTypeDomain;
import ru.osaulenko.dto.response.TransactionResponseDto;
import ru.osaulenko.dto.response.TransactionTypeDto;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TransactionDtoMapper {
    @Mapping(source = "money.value", target = "amount")
    TransactionResponseDto toDto(AccountTransactionDomain domain);
    TransactionTypeDomain toDomainTransactionType(TransactionTypeDto typeDto);
    List<TransactionResponseDto> toDtoList(List<AccountTransactionDomain> domainList);
}
