package com.news.cms.controller;

import com.news.cms.entity.News;
import com.news.cms.entity.Tag;
import com.news.cms.repository.CategoryRepository;
import com.news.cms.repository.NewsRepository;
import com.news.cms.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/admin/news")
public class NewsController {

    @Autowired
    private NewsRepository newsRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private TagRepository tagRepository;

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("news", new News());
        model.addAttribute("categories", categoryRepository.findAll());
        model.addAttribute("tags", tagRepository.findAll());
        return "admin/edit-news";
    }
    @GetMapping("/edit/{id}")
    public String editNewsForm(@PathVariable("id") Long id, Model model) {
        newsRepository.findById(id).ifPresent(news -> {
            model.addAttribute("news", news);
        });
        model.addAttribute("categories", categoryRepository.findAll());
        model.addAttribute("tags", tagRepository.findAll());
        return "admin/edit-news";
    }

    @GetMapping("/view/{id}")
    public String viewNews(@PathVariable("id") Long id, Model model) {
        newsRepository.findById(id).ifPresent(news -> {
            model.addAttribute("news", news);
        });
        return "news-detail";
    }

    @PostMapping("/save")
    public String saveNews(@ModelAttribute("news") News news,
                           @RequestParam(value = "categoryId", required = false) Long categoryId,
                           @RequestParam(value = "tagIds", required = false) List<Long> tagIds) {
        if (news.getId() != null) {
            newsRepository.findById(news.getId()).ifPresent(old -> {
                news.setCreatedAt(old.getCreatedAt());
                if (news.getImageUrl() == null || news.getImageUrl().isEmpty()) {
                    news.setImageUrl(old.getImageUrl());
                }
            });
        }
        if (categoryId != null) {
            categoryRepository.findById(categoryId).ifPresent(news::setCategory);
        }
        if (tagIds != null) {
            Set<Tag> selectedTags = new HashSet<>(tagRepository.findAllById(tagIds));
            news.setTags(selectedTags);
        }
        newsRepository.save(news);
        return "redirect:/admin/dashboard";
    }

    @GetMapping("/delete/{id}")
    public String deleteNews(@PathVariable("id") Long id) {
        newsRepository.deleteById(id);
        return "redirect:/admin/dashboard";
    }
}