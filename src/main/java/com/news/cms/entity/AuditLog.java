package com.news.cms.entity;

import com.news.cms.entity.enums.AuditAction;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Table(name = "audit_logs")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuditLog {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 32)
	private AuditAction action;

	@Column(name = "entity_type", length = 80)
	private String entityType;

	@Column(name = "entity_id")
	private Long entityId;

	@Column(columnDefinition = "TEXT")
	private String details;

	@Column(name = "ip_address", length = 64)
	private String ipAddress;

	@Column(name = "created_at", nullable = false)
	private Instant createdAt;

	@PrePersist
	void onCreate() {
		createdAt = Instant.now();
	}
}
