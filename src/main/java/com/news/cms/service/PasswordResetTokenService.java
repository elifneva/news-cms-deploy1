package com.news.cms.service;

import com.news.cms.entity.PasswordResetToken;
import com.news.cms.repository.PasswordResetTokenRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;

@Service
public class PasswordResetTokenService {

    private final PasswordResetTokenRepository tokenRepository;

    public PasswordResetTokenService(PasswordResetTokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    public long getTokenCount() {
        return tokenRepository.count();
    }

    public PasswordResetToken createToken(PasswordResetToken token) {
        return tokenRepository.save(token);
    }

    public boolean isTokenExpired(PasswordResetToken token) {
        // Entity'de Instant kullandığın için Instant.now() ile kıyaslıyoruz
        return token.getExpiresAt().isBefore(Instant.now());
    }

    public Optional<PasswordResetToken> getByToken(String token) {
        // HATA BURADAYDI: findByToken yerine findByTokenHash kullanmalısın
        return tokenRepository.findByTokenHash(token);
    }
}