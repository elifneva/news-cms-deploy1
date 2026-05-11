package com.news.cms.repository;

import com.news.cms.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    
    // HATA BURADAYDI: findBySlug metodunu sadece bir kez tanımlamalıyız.
    Optional<Category> findBySlug(String slug);
    
    boolean existsBySlug(String slug);
    
    List<Category> findByParentIsNull();
}