package ru.osaulenko.service.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.osaulenko.repository.security.BankUserRepository;

@Service
@RequiredArgsConstructor
public class BankUserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {
    private final BankUserRepository bankUserRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return bankUserRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));
    }
}
