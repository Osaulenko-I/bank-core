package ru.osaulenko.dto.request.account;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class ViewBalanceRequestDto {
    @NotNull
    private UUID accountId;
}
