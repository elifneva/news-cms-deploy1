package com.news.cms.service;

import com.news.cms.entity.PersistentLogin;
import com.news.cms.repository.PersistentLoginRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PersistentLoginService {

    private final PersistentLoginRepository persistentLoginRepository;

    public PersistentLoginService(PersistentLoginRepository persistentLoginRepository) {
        this.persistentLoginRepository = persistentLoginRepository;
    }

    // Dashboard için toplam kayıt sayısı
    public long getPersistentLoginCount() {
        return persistentLoginRepository.count();
    }

    @Transactional
    public void deleteByUsername(String username) {
        // findByUser_Username artık String (username) kabul ediyor
        List<PersistentLogin> logins = persistentLoginRepository.findByUser_Username(username);
        if (!logins.isEmpty()) {
            persistentLoginRepository.deleteAll(logins);
        }
    }
}