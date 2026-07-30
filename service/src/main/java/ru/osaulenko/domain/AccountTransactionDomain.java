package ru.osaulenko.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class AccountTransactionDomain {
    private UUID transactionId;
    private UUID accountId;
    private TransactionTypeDomain type;
    private AccountMoneyDomain money;
}
