package com.news.cms.service;

import com.news.cms.entity.Comment;
import com.news.cms.entity.enums.CommentStatus;
import com.news.cms.repository.CommentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {

    private final CommentRepository commentRepository;

    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public List<Comment> getAll() {
        return commentRepository.findAll();
    }

    public List<Comment> getByArticleId(Long articleId) {
        return commentRepository.findByArticleId(articleId);
    }

    public List<Comment> getPending() {
        return commentRepository.findByStatus(CommentStatus.PENDING);
    }

    public Comment getById(Long id) {
        return commentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Comment not found: " + id));
    }

    public Comment create(Comment comment) {
        comment.setStatus(CommentStatus.PENDING);
        return commentRepository.save(comment);
    }

    public Comment approve(Long id) {
        Comment comment = getById(id);
        comment.setStatus(CommentStatus.APPROVED);
        return commentRepository.save(comment);
    }

    public Comment reject(Long id) {
        Comment comment = getById(id);
        comment.setStatus(CommentStatus.REJECTED);
        return commentRepository.save(comment);
    }

    public void delete(Long id) {
        commentRepository.deleteById(id);
    }
}