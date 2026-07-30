package ru.osaulenko.domain;

public enum TransactionTypeDomain {
    DEBITING("debiting"),
    REPLENISH("replenish"),
    TRANSFER("transfer");

    private final String type;

    private TransactionTypeDomain(String type) {
        this.type = type;
    }

    public static TransactionTypeDomain fromString(String type) {
        for (TransactionTypeDomain c : values()) {
            if (c.type.equals(type))
                return c;
        }

        throw new IllegalArgumentException("transaction type not found");
    }
}
