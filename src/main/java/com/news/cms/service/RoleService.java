package com.news.cms.service;

import com.news.cms.entity.Role;
import com.news.cms.repository.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {

    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    // Dashboard istatistiği için toplam rol sayısı
    public long getRoleCount() {
        return roleRepository.count();
    }

    public Role createRole(Role role) {
        return roleRepository.save(role);
    }

    public Role getByName(String name) {
        return roleRepository.findByName(name)
                .orElseThrow(() -> new RuntimeException("Role not found: " + name));
    }
}