package com.news.cms.service;

import com.news.cms.entity.LoginAttempt;
import com.news.cms.repository.LoginAttemptRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class LoginAttemptService {

    private final LoginAttemptRepository loginAttemptRepository;

    public LoginAttemptService(LoginAttemptRepository loginAttemptRepository) {
        this.loginAttemptRepository = loginAttemptRepository;
    }

    public List<LoginAttempt> getAllAttempts() {
        return loginAttemptRepository.findAll();
    }

    // Dashboard istatistiği için toplam giriş denemesi sayısı
    public long getAttemptCount() {
        return loginAttemptRepository.count();
    }

    public LoginAttempt saveAttempt(LoginAttempt attempt) {
        return loginAttemptRepository.save(attempt);
    }

    public List<LoginAttempt> getFailedAttempts() {
        return loginAttemptRepository.findBySuccessfulFalse();
    }
}