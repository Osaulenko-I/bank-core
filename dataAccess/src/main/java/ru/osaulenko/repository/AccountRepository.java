    package ru.osaulenko.repository;

    import org.springframework.data.jpa.repository.JpaRepository;
    import org.springframework.data.jpa.repository.Query;
    import org.springframework.data.repository.query.Param;
    import org.springframework.stereotype.Repository;
    import ru.osaulenko.entities.Account;
    import ru.osaulenko.entities.TransactionType;

    import java.util.List;
    import java.util.Optional;
    import java.util.UUID;

    @Repository
    public interface AccountRepository extends JpaRepository<Account, UUID> {
        List<Account> findByUser_UserId(UUID userId);

        @Query("SELECT a FROM Account a LEFT JOIN FETCH a.transactions WHERE a.accountId = :accountId")
        Optional<Account> findByAccountId(@Param("accountId") UUID accountId);

        @Query("SELECT DISTINCT a FROM Account a LEFT JOIN FETCH a.transactions")
        List<Account> findAllWithTransactions();

        @Query("SELECT DISTINCT a FROM Account a LEFT JOIN FETCH a.transactions WHERE a.user.userId = :userId")
        List<Account> findWithTransactions_UserId(@Param("userId") UUID userId);
    }
