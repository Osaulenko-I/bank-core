package ru.osaulenko.domain;

import lombok.Getter;

import java.math.BigDecimal;

public class AccountMoneyDomain {
    public AccountMoneyDomain(BigDecimal value) {
        if (value.floatValue() < 0)
            throw new IllegalArgumentException("money can't be negative");

        this.value = value;
    }

    @Getter
    private BigDecimal value;
}
