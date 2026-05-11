package com.news.cms.service;

import com.news.cms.entity.Article;
import com.news.cms.entity.User;
import com.news.cms.entity.enums.ArticleStatus;
import com.news.cms.repository.ArticleRepository;
import com.news.cms.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final UserRepository userRepository;

    public ArticleService(ArticleRepository articleRepository, UserRepository userRepository) {
        this.articleRepository = articleRepository;
        this.userRepository = userRepository;
    }

    public List<Article> getAll() {
        return articleRepository.findAll();
    }

    public List<Article> getPublished() {
        return articleRepository.findByStatus(ArticleStatus.PUBLISHED);
    }

    public Article getById(Long id) {
        return articleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Article not found: " + id));
    }

    public Article getBySlug(String slug) {
        return articleRepository.findBySlug(slug)
                .orElseThrow(() -> new RuntimeException("Article not found: " + slug));
    }

    public Article create(Article article) {
        if (articleRepository.existsBySlug(article.getSlug())) {
            throw new RuntimeException("Slug already in use: " + article.getSlug());
        }
        // Oturumu açık kullanıcıyı yazar olarak ata
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User author = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found: " + username));
        article.setAuthor(author);
        return articleRepository.save(article);
    }

    public Article update(Long id, Article updated) {
        Article existing = getById(id);
        existing.setTitle(updated.getTitle());
        existing.setSlug(updated.getSlug());
        existing.setSummary(updated.getSummary());
        existing.setBody(updated.getBody());
        existing.setStatus(updated.getStatus());
        existing.setFeaturedImageUrl(updated.getFeaturedImageUrl());
        existing.setCategory(updated.getCategory());
        existing.setTags(updated.getTags());
        return articleRepository.save(existing);
    }

    public void delete(Long id) {
        articleRepository.deleteById(id);
    }

    public Article publish(Long id) {
        Article article = getById(id);
        article.setStatus(ArticleStatus.PUBLISHED);
        article.setPublishedAt(Instant.now());
        return articleRepository.save(article);
    }

    public Article archive(Long id) {
        Article article = getById(id);
        article.setStatus(ArticleStatus.ARCHIVED);
        return articleRepository.save(article);
    }
}