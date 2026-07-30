package ru.osaulenko.dto.response;

import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class UserResponseDto {
    private UUID userId;
    private String name;
    private HairColorDto hairColor;
    private boolean male;
    private List<UUID> friends;
    private List<UUID> accounts;
}
