package com.news.cms.service;

import com.news.cms.entity.Tag;
import com.news.cms.repository.TagRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagService {

    private final TagRepository tagRepository;

    public TagService(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    public List<Tag> getAll() {
        return tagRepository.findAll();
    }

    public Tag getById(Long id) {
        return tagRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tag not found: " + id));
    }

    public Tag getBySlug(String slug) {
        return tagRepository.findBySlug(slug)
                .orElseThrow(() -> new RuntimeException("Tag not found: " + slug));
    }

    public Tag create(Tag tag) {
        if (tagRepository.existsBySlug(tag.getSlug())) {
            throw new RuntimeException("Slug already in use: " + tag.getSlug());
        }
        if (tagRepository.existsByName(tag.getName())) {
            throw new RuntimeException("Tag name already in use: " + tag.getName());
        }
        return tagRepository.save(tag);
    }

    public Tag update(Long id, Tag updated) {
        Tag existing = getById(id);
        existing.setName(updated.getName());
        existing.setSlug(updated.getSlug());
        return tagRepository.save(existing);
    }

    public void delete(Long id) {
        tagRepository.deleteById(id);
    }
}