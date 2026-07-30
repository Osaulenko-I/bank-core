package ru.osaulenko.dto.request.account;

import lombok.Data;
import ru.osaulenko.dto.response.TransactionTypeDto;

import java.util.UUID;

@Data
public class GetTransactionsRequestDto {
    private final String typeDto;
    private final UUID accountId;
}
