package ru.osaulenko.mapper.persistence;

import org.mapstruct.Mapper;
import ru.osaulenko.domain.HairColorDomain;
import ru.osaulenko.entities.HairColor;

@Mapper(componentModel = "spring")
public interface HaircolorMapper {
    HairColor toEntity(HairColorDomain domain);
    HairColorDomain toDomain(HairColor entity);
}
