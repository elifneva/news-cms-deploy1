package com.news.cms.entity;

import com.news.cms.entity.enums.ArticleStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "articles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Article {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, length = 300)
	private String title;

	@Column(nullable = false, unique = true, length = 320)
	private String slug;

	@Column(length = 600)
	private String summary;

	@Column(nullable = false, columnDefinition = "LONGTEXT")
	private String body;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 24)
	@Builder.Default
	private ArticleStatus status = ArticleStatus.DRAFT;

	@Column(name = "featured_image_url", length = 500)
	private String featuredImageUrl;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "author_id", nullable = false)
	private User author;

	@ManyToOne(fetch = FetchType.LAZY, optional = true)
	@JoinColumn(name = "category_id", nullable = true)
	private Category category;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(
			name = "article_tags",
			joinColumns = @JoinColumn(name = "article_id", nullable = false),
			inverseJoinColumns = @JoinColumn(name = "tag_id", nullable = false))
	@Builder.Default
	private Set<Tag> tags = new HashSet<>();

	@Column(name = "published_at")
	private Instant publishedAt;

	@Column(name = "is_active", nullable = false)
	@Builder.Default
	private boolean active = true;

	@Column(name = "created_at", nullable = false, updatable = false)
	private Instant createdAt;

	@Column(name = "updated_at", nullable = false)
	private Instant updatedAt;

	@PrePersist
	void onCreate() {
		Instant now = Instant.now();
		createdAt = now;
		updatedAt = now;
	}

	@PreUpdate
	void onUpdate() {
		updatedAt = Instant.now();
	}
}
