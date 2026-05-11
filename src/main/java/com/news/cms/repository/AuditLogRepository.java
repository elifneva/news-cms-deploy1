package com.news.cms.repository;

import com.news.cms.entity.AuditLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface AuditLogRepository extends JpaRepository<AuditLog, Long> {
    // Belirli bir kullanıcıya ait işlem kayıtlarını getirmek için
    List<AuditLog> findByUserId(Long userId);
    
    // Belirli bir işlem türüne (örn: "DELETE") göre filtrelemek için
    List<AuditLog> findByAction(String action);
}