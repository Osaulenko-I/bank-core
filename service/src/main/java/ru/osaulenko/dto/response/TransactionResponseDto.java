package ru.osaulenko.dto.response;

import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class TransactionResponseDto {
    private UUID transactionId;
    private UUID accountId;
    private TransactionTypeDto type;
    private BigDecimal amount;
}
