package com.news.cms.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Table(name = "login_attempts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginAttempt {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, length = 255)
	private String username;

	@Column(name = "ip_address", length = 64)
	private String ipAddress;

	@Column(nullable = false)
	private boolean successful;

	@Column(name = "attempted_at", nullable = false)
	private Instant attemptedAt;

	@PrePersist
	void onCreate() {
		if (attemptedAt == null) {
			attemptedAt = Instant.now();
		}
	}
}
