package ru.osaulenko.mapper.dto;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.osaulenko.domain.AccountDomain;
import ru.osaulenko.dto.response.AccountResponseDto;

import java.util.List;

@Mapper(componentModel = "spring", uses = {TransactionDtoMapper.class})
public interface AccountDtoMapper {
    @Mapping(source = "balance.value", target = "balance")
    AccountResponseDto toDto(AccountDomain domain);

    List<AccountResponseDto> toDtoList(List<AccountDomain> domainList);
}
