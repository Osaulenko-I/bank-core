package ru.osaulenko.service.security;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import ru.osaulenko.entities.security.BankClient;
import ru.osaulenko.entities.security.BankUser;

import java.util.UUID;

@Component
public class SecurityUtils {
    public String getUsername() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || !auth.isAuthenticated()) {
            throw new AccessDeniedException("not authenticated");
        }
        return auth.getName();
    }

    public BankUser getBankUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || !auth.isAuthenticated()) {
            throw new AccessDeniedException("not authenticated");
        }

        return (BankUser) auth.getPrincipal();
    }

    public UUID getUUID() {
        BankUser bankUser = getBankUser();

        if (bankUser instanceof BankClient client) {
            return client.getId();
        }

        throw new AccessDeniedException("only client have uuid");
    }

    public boolean isAdmin() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth != null && auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
    }

    public boolean isClient() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth != null && auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_CLIENT"));
    }
}