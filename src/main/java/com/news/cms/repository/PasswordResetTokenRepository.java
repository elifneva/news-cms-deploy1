package com.news.cms.repository;

import com.news.cms.entity.PasswordResetToken;
import com.news.cms.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {
    
    // Entity'deki 'tokenHash' alanı ile uyumlu hale getirildi
    Optional<PasswordResetToken> findByTokenHash(String tokenHash);
    
    // Kullanıcıya göre bulma (Zaten doğruydu)
    Optional<PasswordResetToken> findByUser(User user);
    
    // Silme işlemi de alan adıyla uyumlu olmalı
    void deleteByTokenHash(String tokenHash);
}