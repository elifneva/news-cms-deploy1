package com.news.cms.repository;

import com.news.cms.entity.PersistentLogin;
import com.news.cms.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PersistentLoginRepository extends JpaRepository<PersistentLogin, Long> {

    // Bir kullanıcıya ait tüm aktif "beni hatırla" oturumlarını bulmak için
    List<PersistentLogin> findByUser(User user);

    // Seri numarasına göre (Spring Security için kritik) oturum bulma
    Optional<PersistentLogin> findBySeries(String series);

    // Belirli bir kullanıcı adıyla ilişkili kayıtları bulmak istersen (User entity üzerinden)
    List<PersistentLogin> findByUser_Username(String username);
}