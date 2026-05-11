package com.news.cms.controller;

import com.news.cms.entity.PasswordResetToken;
import com.news.cms.service.PasswordResetTokenService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/password-reset")
public class PasswordResetTokenController {

    private final PasswordResetTokenService tokenService;

    public PasswordResetTokenController(PasswordResetTokenService tokenService) {
        this.tokenService = tokenService;
    }

    // Dashboard'daki sayaç için toplam talep sayısı
    @GetMapping("/count")
    public ResponseEntity<Long> getCount() {
        return ResponseEntity.ok(tokenService.getTokenCount());
    }

    // Belirli bir token'ın detaylarını ve geçerliliğini kontrol etmek için
    @GetMapping("/{token}")
    public ResponseEntity<?> getTokenDetails(@PathVariable String token) {
        Optional<PasswordResetToken> resetToken = tokenService.getByToken(token);
        
        if (resetToken.isPresent()) {
            boolean expired = tokenService.isTokenExpired(resetToken.get());
            // Token bulunduysa detayları ve süresinin dolup dolmadığını dön
            return ResponseEntity.ok(new TokenStatusResponse(resetToken.get(), expired));
        }
        
        return ResponseEntity.notFound().build();
    }

    // İç yardımcı sınıf (Daha detaylı bilgi dönmek istersen)
    private static class TokenStatusResponse {
        public PasswordResetToken tokenData;
        public boolean isExpired;

        public TokenStatusResponse(PasswordResetToken tokenData, boolean isExpired) {
            this.tokenData = tokenData;
            this.isExpired = isExpired;
        }
    }
}