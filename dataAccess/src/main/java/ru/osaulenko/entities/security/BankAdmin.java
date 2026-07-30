package ru.osaulenko.entities.security;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class BankAdmin extends BankUser{
    @Override
    public String getRole() {
        return "ROLE_ADMIN";
    }
}
