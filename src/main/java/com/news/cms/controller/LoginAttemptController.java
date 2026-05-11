package com.news.cms.controller;

import com.news.cms.entity.LoginAttempt;
import com.news.cms.service.LoginAttemptService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/login-attempts")
public class LoginAttemptController {

    private final LoginAttemptService loginAttemptService;

    public LoginAttemptController(LoginAttemptService loginAttemptService) {
        this.loginAttemptService = loginAttemptService;
    }

    @GetMapping
    public List<LoginAttempt> getAll() {
        return loginAttemptService.getAllAttempts();
    }

    @GetMapping("/count")
    public ResponseEntity<Long> getCount() {
        return ResponseEntity.ok(loginAttemptService.getAttemptCount());
    }

    @GetMapping("/failed")
    public List<LoginAttempt> getFailed() {
        return loginAttemptService.getFailedAttempts();
    }
}