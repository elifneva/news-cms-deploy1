package com.news.cms.controller;

import com.news.cms.service.PersistentLoginService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/persistent-logins")
public class PersistentLoginController {

    private final PersistentLoginService persistentLoginService;

    public PersistentLoginController(PersistentLoginService persistentLoginService) {
        this.persistentLoginService = persistentLoginService;
    }

    /**
     * Dashboard'daki "Aktif Oturumlar" veya "Beni Hatırla Kayıtları" sayısını döner.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> getCount() {
        return ResponseEntity.ok(persistentLoginService.getPersistentLoginCount());
    }

    /**
     * Belirli bir kullanıcının tüm "beni hatırla" oturumlarını siler (Logout gibi düşünülebilir).
     */
    @DeleteMapping("/user/{username}")
    public ResponseEntity<Void> deleteByUser(@PathVariable String username) {
        persistentLoginService.deleteByUsername(username);
        return ResponseEntity.noContent().build();
    }
}