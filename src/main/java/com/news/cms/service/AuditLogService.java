package com.news.cms.service;

import com.news.cms.entity.AuditLog;
import com.news.cms.repository.AuditLogRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class AuditLogService {

    private final AuditLogRepository auditLogRepository;

    public AuditLogService(AuditLogRepository auditLogRepository) {
        this.auditLogRepository = auditLogRepository;
    }

    public List<AuditLog> getAllLogs() {
        return auditLogRepository.findAll();
    }

    // Dashboard istatistiği için toplam log sayısı
    public long getLogCount() {
        return auditLogRepository.count();
    }

    public AuditLog saveLog(AuditLog log) {
        return auditLogRepository.save(log);
    }

    public List<AuditLog> getLogsByUser(Long userId) {
        return auditLogRepository.findByUserId(userId);
    }
}