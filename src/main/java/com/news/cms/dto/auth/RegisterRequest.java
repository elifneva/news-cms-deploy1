package com.news.cms.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterRequest(
		@NotBlank @Size(min = 3, max = 64) String username,
		@NotBlank @Email @Size(max = 255) String email,
		@NotBlank @Size(min = 6, max = 72) String password,
		@Size(max = 100) String firstName,
		@Size(max = 100) String lastName
) {
}
