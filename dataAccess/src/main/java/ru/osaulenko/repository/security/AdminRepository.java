package ru.osaulenko.repository.security;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.osaulenko.entities.security.BankAdmin;

import java.util.UUID;

@Repository
public interface AdminRepository extends JpaRepository<BankAdmin, UUID> {
}
