package ru.osaulenko.dto.response;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Data
public class AccountResponseDto {
    private UUID accountId;
    private UUID userId;
    private String accountNumber;
    private BigDecimal balance;
    private List<UUID> transactions;
}
