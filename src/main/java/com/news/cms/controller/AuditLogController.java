package com.news.cms.controller;

import com.news.cms.entity.AuditLog;
import com.news.cms.service.AuditLogService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/audit-logs")
public class AuditLogController {

    private final AuditLogService auditLogService;

    public AuditLogController(AuditLogService auditLogService) {
        this.auditLogService = auditLogService;
    }

    @GetMapping
    public List<AuditLog> getAll() {
        return auditLogService.getAllLogs();
    }

    @GetMapping("/count")
    public ResponseEntity<Long> getCount() {
        return ResponseEntity.ok(auditLogService.getLogCount());
    }

    @GetMapping("/user/{userId}")
    public List<AuditLog> getByUser(@PathVariable Long userId) {
        return auditLogService.getLogsByUser(userId);
    }
}