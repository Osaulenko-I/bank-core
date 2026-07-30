package ru.osaulenko;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.osaulenko.entities.security.BankAdmin;
import ru.osaulenko.repository.security.AdminRepository;

@Component
@RequiredArgsConstructor
public class AdminInit implements CommandLineRunner {

    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        if (adminRepository.count() == 0) {
            BankAdmin bankAdmin = new BankAdmin();
            bankAdmin.setUsername("AdminDrun");
            bankAdmin.setPassword(passwordEncoder.encode("qwerty"));

            adminRepository.save(bankAdmin);
        }
    }
}
