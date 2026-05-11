package com.news.cms.repository;

import com.news.cms.entity.Comment;
import com.news.cms.entity.enums.CommentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByArticleId(Long articleId);
    List<Comment> findByStatus(CommentStatus status);
    List<Comment> findByUserId(Long userId);
    List<Comment> findByNewsId(Long newsId);
    List<Comment> findByNewsIdAndStatus(Long newsId, CommentStatus status);
}