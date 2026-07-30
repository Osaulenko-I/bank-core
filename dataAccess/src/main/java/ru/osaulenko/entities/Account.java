package ru.osaulenko.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID accountId;

    @Column(name = "account_number", nullable = false)
    private String accountNumber;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Embedded
    private AccountMoney balance;

    @OneToMany(mappedBy = "account", fetch = FetchType.LAZY)
    private List<AccountTransaction> transactions = new ArrayList<>();
}
