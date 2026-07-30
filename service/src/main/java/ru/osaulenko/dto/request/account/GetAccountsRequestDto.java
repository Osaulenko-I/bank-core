package ru.osaulenko.dto.request.account;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class GetAccountsRequestDto {
    @NotNull
    private UUID userId;
}
