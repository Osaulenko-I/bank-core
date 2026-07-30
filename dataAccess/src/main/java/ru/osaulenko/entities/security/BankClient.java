package ru.osaulenko.entities.security;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.osaulenko.entities.User;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class BankClient extends BankUser {
    @Override
    public String getRole() {
        return "ROLE_CLIENT";
    }

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_profile_id", referencedColumnName = "userId")
    public User userProfile;
}
