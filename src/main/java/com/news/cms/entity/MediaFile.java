package com.news.cms.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Table(name = "media_files")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MediaFile {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "article_id")
	private Article article;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "uploaded_by_id", nullable = false)
	private User uploadedBy;

	@Column(name = "original_name", nullable = false, length = 255)
	private String originalName;

	@Column(name = "stored_path", nullable = false, length = 500)
	private String storedPath;

	@Column(name = "mime_type", length = 120)
	private String mimeType;

	@Column(name = "byte_size")
	private Long byteSize;

	@Column(name = "created_at", nullable = false)
	private Instant createdAt;

	@PrePersist
	void onCreate() {
		createdAt = Instant.now();
	}
}
