package com.news.cms.repository;

import com.news.cms.entity.Article;
import com.news.cms.entity.enums.ArticleStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {
    List<Article> findByStatus(ArticleStatus status);
    List<Article> findByAuthorId(Long authorId);
    List<Article> findByCategoryId(Long categoryId);
    Optional<Article> findBySlug(String slug);
    boolean existsBySlug(String slug);
}