package com.news.cms.controller;

import com.news.cms.entity.Category;
import com.news.cms.repository.CategoryRepository;
import com.news.cms.service.CategoryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/categories")
public class CategoryController {

    private final CategoryService categoryService;
    private final CategoryRepository categoryRepository;

    public CategoryController(CategoryService categoryService, CategoryRepository categoryRepository) {
        this.categoryService = categoryService;
        this.categoryRepository = categoryRepository;
    }

    // 1. DÜZENLEME SAYFASINI AÇAN METOT
    @GetMapping("/edit/{id}")
    public String editCategory(@PathVariable("id") Long id, Model model) {
        // FindById küçük harf 'categoryRepository' ile çağrılır
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Geçersiz kategori ID: " + id));
        
        model.addAttribute("category", category);
        return "admin/edit-category";
    }

    // 2. GÜNCELLEMEYİ KAYDEDEN METOT
    @PostMapping("/update/{id}")
    public String updateCategory(@PathVariable("id") Long id, @ModelAttribute("category") Category category) { 
        Category existingCategory = categoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Kategori bulunamadı: " + id));

        category.setId(id);
        category.setCreatedAt(existingCategory.getCreatedAt()); 
        
        categoryRepository.save(category); 
        return "redirect:/admin/dashboard";
    }

    // 3. SİLME İŞLEMİ
    @GetMapping("/delete/{id}")
    public String deleteCategory(@PathVariable("id") Long id) {
        categoryService.delete(id);
        return "redirect:/admin/dashboard";
    }
}