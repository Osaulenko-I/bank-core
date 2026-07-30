package ru.osaulenko.mapper.dto;

import org.mapstruct.Mapper;
import ru.osaulenko.domain.UserDomain;
import ru.osaulenko.dto.response.UserResponseDto;
import ru.osaulenko.entities.User;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserDtoMapper {
    UserResponseDto toDto(UserDomain domain);

    List<UserResponseDto> toDtoList(List<UserDomain> domainList);
}
