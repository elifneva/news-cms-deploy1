package com.news.cms.controller;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.stereotype.Controller;
import com.news.cms.entity.Role;
import com.news.cms.service.RoleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
public class RoleController {

    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping
    public List<Role> getAll() {
        return roleService.getAllRoles();
    }

    @GetMapping("/count")
    public ResponseEntity<Long> getCount() {
        return ResponseEntity.ok(roleService.getRoleCount());
    }

    @PostMapping
    public ResponseEntity<Role> create(@RequestBody Role role) {
        return ResponseEntity.ok(roleService.createRole(role));
    }
    @PostMapping("/admin/roles/create")
    public String createRoleFromDashboard(@RequestParam String name, RedirectAttributes redirectAttributes) {
        Role role = new Role();
        role.setName(name.toUpperCase());
        roleService.createRole(role);
        redirectAttributes.addFlashAttribute("success", "Rol oluşturuldu.");
        return "redirect:/admin/dashboard";
    }
}