package ru.osaulenko.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AccountDomain {
    private UUID accountId;
    private String accountNumber;
    private UUID userId;
    private AccountMoneyDomain balance;
    private List<UUID> transactions = new ArrayList<>();

    public void subtractBalance(AccountMoneyDomain money) {
        if (balance.getValue().compareTo(money.getValue()) < 0) {
            throw new RuntimeException("not enough money");
        }

        balance = new AccountMoneyDomain(balance.getValue().subtract(money.getValue()));
    }

    public void addBalance(AccountMoneyDomain money) {
        balance = new AccountMoneyDomain(balance.getValue().add(money.getValue()));
    }
}
