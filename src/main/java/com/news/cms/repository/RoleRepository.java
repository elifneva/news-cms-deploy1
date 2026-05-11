package com.news.cms.repository;

import com.news.cms.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    // Rol ismine göre (örn: ROLE_ADMIN) bulmak için
    Optional<Role> findByName(String name);
}