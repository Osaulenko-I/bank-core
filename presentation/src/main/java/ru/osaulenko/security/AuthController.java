package ru.osaulenko.security;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor

public class AuthController {
    private final AuthenticationManager authenticationManager;

    @PostMapping("/login")
    @Operation(summary = "system login")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "logged in successfully"),
            @ApiResponse(responseCode = "401", description = "bad login")
    })
    public ResponseEntity<?> login(@RequestBody LoginRequest request, HttpServletRequest httpServletRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsername(),
                            request.getPassword()
                    )
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);

            HttpSession session = httpServletRequest.getSession(true);
            session.setAttribute(
                    HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
                    SecurityContextHolder.getContext()
            );

            return ResponseEntity.ok(Map.of(
                        "message", "Logged in successfully",
                        "username", authentication.getName(),
                        "role", authentication.getAuthorities().toString()
                    )
            );
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "invalid login or password"));
        }
    }

    @PostMapping("/loginout")
    @Operation(summary = "system logout")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "logout successfully"),
            @ApiResponse(responseCode = "401", description = "bad logout")
    })
    public ResponseEntity<?> logout(HttpServletRequest servletRequest) {
        SecurityContextHolder.clearContext();
        servletRequest.getSession().invalidate();
        return ResponseEntity.ok(Map.of("message", "logout successfully"));
    }
}
