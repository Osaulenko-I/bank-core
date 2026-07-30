package ru.osaulenko.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.osaulenko.entities.AccountTransaction;
import ru.osaulenko.entities.TransactionType;

import java.util.List;
import java.util.UUID;

@Repository
public interface TransactionRepository extends JpaRepository<AccountTransaction, UUID> {
    @Query("SELECT t FROM AccountTransaction t WHERE " +
            "(:accountId IS NULL OR t.account.accountId = :accountId) AND " +
            "(:type IS NULL OR t.type = :type)")
    List<AccountTransaction> findByFilters(
            @Param("accountId") UUID accountId, @Param("type") TransactionType type);
}
