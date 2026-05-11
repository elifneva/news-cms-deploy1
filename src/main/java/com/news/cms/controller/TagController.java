package com.news.cms.controller;

import com.news.cms.entity.Tag;
import com.news.cms.service.TagService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tags")
public class TagController {

    private final TagService tagService;

    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @GetMapping
    public List<Tag> getAll() {
        return tagService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tag> getById(@PathVariable Long id) {
        return ResponseEntity.ok(tagService.getById(id));
    }

    @GetMapping("/slug/{slug}")
    public ResponseEntity<Tag> getBySlug(@PathVariable String slug) {
        return ResponseEntity.ok(tagService.getBySlug(slug));
    }

    @PostMapping
    public ResponseEntity<Tag> create(@RequestBody Tag tag) {
        return ResponseEntity.status(HttpStatus.CREATED).body(tagService.create(tag));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Tag> update(@PathVariable Long id, @RequestBody Tag tag) {
        return ResponseEntity.ok(tagService.update(id, tag));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        tagService.delete(id);
        return ResponseEntity.noContent().build();
    }
}