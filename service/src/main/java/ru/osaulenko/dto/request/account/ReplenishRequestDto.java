package ru.osaulenko.dto.request.account;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class ReplenishRequestDto {
    @NotNull
    private UUID accountId;
    @NotNull
    @Positive
    private BigDecimal amount;
}
