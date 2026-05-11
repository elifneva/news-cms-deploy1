package com.news.cms.config;
import java.util.Set;

import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.news.cms.entity.Role;
import com.news.cms.entity.User;
import com.news.cms.repository.RoleRepository;
import com.news.cms.repository.UserRepository;

@Component
public class DataInitializer implements CommandLineRunner {

	private final RoleRepository roleRepository;
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	public DataInitializer(RoleRepository roleRepository, UserRepository userRepository, PasswordEncoder passwordEncoder) {
		this.roleRepository = roleRepository;
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public void run(String... args) {
		Role adminRole = roleRepository.findByName("ADMIN")
				.orElseGet(() -> roleRepository.save(Role.builder().name("ADMIN").build()));
		Role userRole = roleRepository.findByName("USER")
				.orElseGet(() -> roleRepository.save(Role.builder().name("USER").build()));

		if (!userRepository.existsByUsername("admin")) {
			User admin = User.builder()
					.username("admin")
					.email("admin@newscms.local")
					.passwordHash(passwordEncoder.encode("Admin123!"))
					.firstName("System")
					.lastName("Admin")
					.enabled(true)
					.roles(Set.of(adminRole, userRole))
					.build();
			userRepository.save(admin);
		}
	}
}