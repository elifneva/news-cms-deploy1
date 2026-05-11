package com.news.cms.controller;

import com.news.cms.entity.Comment;
import com.news.cms.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping
    public List<Comment> getAll() {
        return commentService.getAll();
    }

    @GetMapping("/article/{articleId}")
    public List<Comment> getByArticle(@PathVariable Long articleId) {
        return commentService.getByArticleId(articleId);
    }

    @GetMapping("/pending")
    public List<Comment> getPending() {
        return commentService.getPending();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Comment> getById(@PathVariable Long id) {
        return ResponseEntity.ok(commentService.getById(id));
    }

    @PostMapping
    public ResponseEntity<Comment> create(@RequestBody Comment comment) {
        return ResponseEntity.status(HttpStatus.CREATED).body(commentService.create(comment));
    }

    @PatchMapping("/{id}/approve")
    public ResponseEntity<Comment> approve(@PathVariable Long id) {
        return ResponseEntity.ok(commentService.approve(id));
    }

    @PatchMapping("/{id}/reject")
    public ResponseEntity<Comment> reject(@PathVariable Long id) {
        return ResponseEntity.ok(commentService.reject(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        commentService.delete(id);
        return ResponseEntity.noContent().build();
    }
}