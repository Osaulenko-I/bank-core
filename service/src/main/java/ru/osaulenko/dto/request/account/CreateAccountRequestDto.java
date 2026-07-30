package ru.osaulenko.dto.request.account;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class CreateAccountRequestDto {
    @NotBlank
    private String accountNumber;
    @NotNull
    private UUID userId;
}
