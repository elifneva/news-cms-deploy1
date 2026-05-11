package com.news.cms.repository;
import com.news.cms.entity.LoginAttempt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface LoginAttemptRepository extends JpaRepository<LoginAttempt, Long> {
    List<LoginAttempt> findByUsername(String username);
    List<LoginAttempt> findBySuccessfulFalse();
    List<LoginAttempt> findByIpAddress(String ipAddress);
}