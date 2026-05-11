package com.news.cms.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

/**
 * "Beni hatırla" için kalıcı oturum kaydı (series/token).
 */
@Entity
@Table(name = "persistent_logins")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PersistentLogin {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	@Column(nullable = false, unique = true, length = 64)
	private String series;

	@Column(nullable = false, length = 128)
	private String token;

	@Column(name = "last_used_at", nullable = false)
	private Instant lastUsedAt;

	@PrePersist
	@PreUpdate
	void touch() {
		if (lastUsedAt == null) {
			lastUsedAt = Instant.now();
		}
	}
}
