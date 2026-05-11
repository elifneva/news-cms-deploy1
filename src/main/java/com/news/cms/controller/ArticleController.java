package com.news.cms.controller;

import com.news.cms.entity.Article;
import com.news.cms.service.ArticleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/articles")
public class ArticleController {

    private final ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping
    public List<Article> getAll() {
        return articleService.getAll();
    }

    @GetMapping("/published")
    public List<Article> getPublished() {
        return articleService.getPublished();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Article> getById(@PathVariable Long id) {
        return ResponseEntity.ok(articleService.getById(id));
    }

    @GetMapping("/slug/{slug}")
    public ResponseEntity<Article> getBySlug(@PathVariable String slug) {
        return ResponseEntity.ok(articleService.getBySlug(slug));
    }

    @PostMapping
    public ResponseEntity<Article> create(@RequestBody Article article) {
        return ResponseEntity.status(HttpStatus.CREATED).body(articleService.create(article));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Article> update(@PathVariable Long id, @RequestBody Article article) {
        return ResponseEntity.ok(articleService.update(id, article));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        articleService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/publish")
    public ResponseEntity<Article> publish(@PathVariable Long id) {
        return ResponseEntity.ok(articleService.publish(id));
    }

    @PatchMapping("/{id}/archive")
    public ResponseEntity<Article> archive(@PathVariable Long id) {
        return ResponseEntity.ok(articleService.archive(id));
    }
}