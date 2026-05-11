package com.news.cms.repository;

import com.news.cms.entity.News;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Repository
public interface NewsRepository extends JpaRepository<News, Long> {

    List<News> findByTitleContainingIgnoreCase(String title);

    List<News> findByAuthor(String author);

    List<News> findByTitleContainingIgnoreCaseOrContentContainingIgnoreCase(String title, String content);
   
    Page<News> findByCategory(com.news.cms.entity.Category category, Pageable pageable);
    List<News> findTop3ByCategoryAndIdNot(com.news.cms.entity.Category category, Long id);
    List<News> findByTitleContainingIgnoreCaseOrContentContainingIgnoreCaseOrCategoryNameContainingIgnoreCase(
    	    String title, String content, String categoryName);
}