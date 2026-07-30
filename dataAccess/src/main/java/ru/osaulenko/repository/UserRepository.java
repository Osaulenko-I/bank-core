package ru.osaulenko.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.osaulenko.entities.HairColor;
import ru.osaulenko.entities.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    @Query("SELECT u FROM User u LEFT JOIN FETCH u.friends WHERE u.userId = :userId")
    Optional<User> findByIdWithFriends(@Param("userId") UUID uuid);
}
