package ru.osaulenko.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class AccountMoney {
    @Column(scale = 2, precision = 10, nullable = false)
    @Getter
    private BigDecimal value;
}
