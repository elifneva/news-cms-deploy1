package com.news.cms.service;

import com.news.cms.entity.Category;
import com.news.cms.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> getAll() {
        return categoryRepository.findAll();
    }

    public List<Category> getRootCategories() {
        return categoryRepository.findByParentIsNull();
    }

    public Category getById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found: " + id));
    }

    public Category getBySlug(String slug) {
        return categoryRepository.findBySlug(slug)
                .orElseThrow(() -> new RuntimeException("Category not found: " + slug));
    }

    public Category create(Category category) {
        if (categoryRepository.existsBySlug(category.getSlug())) {
            throw new RuntimeException("Slug already in use: " + category.getSlug());
        }
        return categoryRepository.save(category);
    }

    public Category update(Long id, Category updated) {
        Category existing = getById(id);
        existing.setName(updated.getName());
        existing.setSlug(updated.getSlug());
        existing.setDescription(updated.getDescription());
        existing.setParent(updated.getParent());
        existing.setSortOrder(updated.getSortOrder());
        existing.setActive(updated.isActive());
        return categoryRepository.save(existing);
    }

    public void delete(Long id) {
        categoryRepository.deleteById(id);
    }
}