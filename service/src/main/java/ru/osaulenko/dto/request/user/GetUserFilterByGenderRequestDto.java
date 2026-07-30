package ru.osaulenko.dto.request.user;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class GetUserFilterByGenderRequestDto {
    private boolean male;
}
