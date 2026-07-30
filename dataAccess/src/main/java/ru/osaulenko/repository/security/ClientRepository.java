package ru.osaulenko.repository.security;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.osaulenko.entities.HairColor;
import ru.osaulenko.entities.security.BankClient;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ClientRepository extends JpaRepository<BankClient, UUID> {
    Optional<BankClient> findByUserProfile_UserId(UUID userId);

    List<BankClient> findByUserProfile_Male(boolean male);

    List<BankClient> findByUserProfile_HairColor(HairColor hairColor);

    List<BankClient> findAll();
}
