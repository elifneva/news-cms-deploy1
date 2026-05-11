package com.news.cms.entity;

import com.news.cms.entity.enums.CommentStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Table(name = "comments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Comment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY, optional = true)
	@JoinColumn(name = "article_id", nullable = true)
	private Article article;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "news_id")
	private News news;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;

	@Column(name = "guest_name", length = 120)
	private String guestName;

	@Column(name = "guest_email", length = 255)
	private String guestEmail;

	@Column(nullable = false, columnDefinition = "TEXT")
	private String body;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 24)
	@Builder.Default
	private CommentStatus status = CommentStatus.PENDING;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "parent_id")
	private Comment parent;

	@Column(name = "is_active", nullable = false)
	@Builder.Default
	private boolean active = true;

	@Column(name = "created_at", nullable = false)
	private Instant createdAt;

	@PrePersist
	void onCreate() {
		createdAt = Instant.now();
	}
}
