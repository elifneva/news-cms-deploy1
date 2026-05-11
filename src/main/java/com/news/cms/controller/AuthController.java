package com.news.cms.controller;

import com.news.cms.dto.auth.AuthResponse;
import com.news.cms.dto.auth.LoginRequest;
import com.news.cms.dto.auth.RegisterRequest;
import com.news.cms.entity.Role;
import com.news.cms.entity.User;
import com.news.cms.repository.RoleRepository;
import com.news.cms.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationProvider; // Değişti
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    // GÖRSELDEKİ TAVSİYE: AuthenticationManager yerine Provider kullanıyoruz
    private final AuthenticationProvider authenticationProvider;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthController(AuthenticationProvider authenticationProvider,
                          UserRepository userRepository,
                          RoleRepository roleRepository,
                          PasswordEncoder passwordEncoder) {
        this.authenticationProvider = authenticationProvider;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request,
                                               HttpServletRequest httpRequest,
                                               HttpServletResponse httpResponse) {
        try {
            // 1. Kimlik bilgilerini içeren token'ı oluştur
            UsernamePasswordAuthenticationToken token = 
                    new UsernamePasswordAuthenticationToken(request.username(), request.password());

            // 2. AuthenticationProvider üzerinden doğrula
            Authentication auth = authenticationProvider.authenticate(token);

            // 3. SecurityContext'i güncelle
            SecurityContext context = SecurityContextHolder.createEmptyContext();
            context.setAuthentication(auth);
            SecurityContextHolder.setContext(context);

            // 4. Session'a kaydet (Stateful yapı için gerekli)
            new HttpSessionSecurityContextRepository()
                    .saveContext(context, httpRequest, httpResponse);

            return ResponseEntity.ok(new AuthResponse("Login successful", auth.getName()));

        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new AuthResponse("Invalid username or password", null));
        }
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid RegisterRequest request) {
    
        if (userRepository.existsByUsername(request.username())) {
            return ResponseEntity.badRequest()
                    .body(new AuthResponse("Username already taken", null));
        }

        if (userRepository.existsByEmail(request.email())) {
            return ResponseEntity.badRequest()
                    .body(new AuthResponse("Email already in use", null));
        }

        Role userRole = roleRepository.findByName("USER")
                .orElseThrow(() -> new RuntimeException("USER role not found"));

        User user = User.builder()
                .username(request.username())
                .email(request.email())
                .passwordHash(passwordEncoder.encode(request.password()))
                .firstName(request.firstName())
                .lastName(request.lastName())
                .enabled(true)
                .roles(Set.of(userRole))
                .build();

        userRepository.save(user);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new AuthResponse("Registration successful", request.username()));
    }

    @PostMapping("/logout")
    public ResponseEntity<AuthResponse> logout(HttpServletRequest request) {
        SecurityContextHolder.clearContext();
        var session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return ResponseEntity.ok(new AuthResponse("Logged out successfully", null));
    }
}