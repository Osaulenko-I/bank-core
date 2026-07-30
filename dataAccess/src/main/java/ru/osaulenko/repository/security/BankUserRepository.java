package ru.osaulenko.repository.security;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.osaulenko.entities.security.BankUser;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface BankUserRepository extends JpaRepository<BankUser, UUID> {
    Optional<BankUser> findByUsername(String username);
    boolean existsByUsername(String username);
}
