package com.news.cms.service;

import com.news.cms.entity.News;
import com.news.cms.repository.NewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service // Spring'e bunun bir iş mantığı sınıfı olduğunu söyler
public class NewsService {

    @Autowired
    private NewsRepository newsRepository; // Repo'yu buraya bağladık

    // Tüm haberleri listeleme metodu
    public List<News> getAllNews() {
        return newsRepository.findAll();
    }

    // Yeni haber kaydetme metodu
    public News saveNews(News news) {
        return newsRepository.save(news);
    }
 // Sadece bir haberi ID'sine göre bulup getirme
    public News getNewsById(Long id) {
        return newsRepository.findById(id).orElse(null); 
    }

    // Haberi ID'sine göre veritabanından silme
    public void deleteNews(Long id) {
        newsRepository.deleteById(id);
    }
    public List<News> searchNewsByTitle(String title) {
        return newsRepository.findByTitleContainingIgnoreCase(title);
    }
}