package com.news.cms.controller;

import com.news.cms.entity.Category;
import com.news.cms.entity.Comment;
import com.news.cms.entity.User;
import com.news.cms.repository.*;
import com.news.cms.entity.enums.ArticleStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

@Controller
public class PageController {

    private final ArticleRepository articleRepository;
    private final CategoryRepository categoryRepository;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final AuditLogRepository auditLogRepository;
    private final LoginAttemptRepository loginAttemptRepository;
    private final MediaFileRepository mediaFileRepository;
    private final NewsRepository newsRepository;
    private final PasswordResetTokenRepository passwordResetTokenRepository;
    private final PersistentLoginRepository persistentLoginRepository;
    private final RoleRepository roleRepository;
    private final TagRepository tagRepository;
    private final PasswordEncoder passwordEncoder;

    public PageController(ArticleRepository articleRepository,
                          CategoryRepository categoryRepository,
                          CommentRepository commentRepository,
                          UserRepository userRepository,
                          AuditLogRepository auditLogRepository,
                          LoginAttemptRepository loginAttemptRepository,
                          MediaFileRepository mediaFileRepository,
                          NewsRepository newsRepository,
                          PasswordResetTokenRepository passwordResetTokenRepository,
                          PersistentLoginRepository persistentLoginRepository,
                          RoleRepository roleRepository,
                          TagRepository tagRepository,
                          PasswordEncoder passwordEncoder) {
        this.articleRepository = articleRepository;
        this.categoryRepository = categoryRepository;
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
        this.auditLogRepository = auditLogRepository;
        this.loginAttemptRepository = loginAttemptRepository;
        this.mediaFileRepository = mediaFileRepository;
        this.newsRepository = newsRepository;
        this.passwordResetTokenRepository = passwordResetTokenRepository;
        this.persistentLoginRepository = persistentLoginRepository;
        this.roleRepository = roleRepository;
        this.tagRepository = tagRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // ==========================================
    // GENEL SAYFALAR (INDEX, LOGIN, REGISTER)
    // ==========================================

    @GetMapping("/")
    public String index(@RequestParam(name = "page", defaultValue = "0") int page, Model model) {
        int pageSize = 6;
        var pageable = PageRequest.of(page, pageSize, Sort.by("createdAt").descending());
        var newsPage = newsRepository.findAll(pageable);
        model.addAttribute("newsList", newsPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", newsPage.getTotalPages());
        model.addAttribute("articles", articleRepository.findByStatus(ArticleStatus.PUBLISHED));
        model.addAttribute("categories", categoryRepository.findAll());
        return "index";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String showRegisterPage() {
        return "register";
    }

    @GetMapping("/search")
    public String search(@RequestParam("q") String q, Model model) {
        model.addAttribute("newsList", newsRepository
            .findByTitleContainingIgnoreCaseOrContentContainingIgnoreCaseOrCategoryNameContainingIgnoreCase(q, q, q));
        model.addAttribute("categories", categoryRepository.findAll());
        model.addAttribute("q", q);
        return "search";
    }

    // ==========================================
    // ADMIN DASHBOARD
    // ==========================================

    @GetMapping("/admin/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("articles", articleRepository.findAll());
        model.addAttribute("articleCount", articleRepository.count());
        model.addAttribute("categoryCount", categoryRepository.count());
        model.addAttribute("commentCount", commentRepository.count());
        model.addAttribute("userCount", userRepository.count());
        model.addAttribute("categories", categoryRepository.findAll());
        model.addAttribute("comments", commentRepository.findAll());
        model.addAttribute("users", userRepository.findAll());
        model.addAttribute("auditLogs", auditLogRepository.findAll());
        model.addAttribute("auditLogCount", auditLogRepository.count());
        model.addAttribute("loginAttempts", loginAttemptRepository.findAll());
        model.addAttribute("loginAttemptCount", loginAttemptRepository.count());
        model.addAttribute("mediaFiles", mediaFileRepository.findAll());
        model.addAttribute("mediaFileCount", mediaFileRepository.count());
        model.addAttribute("newsList", newsRepository.findAll());
        model.addAttribute("newsCount", newsRepository.count());
        model.addAttribute("passwordResetTokens", passwordResetTokenRepository.findAll());
        model.addAttribute("passwordResetTokenCount", passwordResetTokenRepository.count());
        model.addAttribute("persistentLogins", persistentLoginRepository.findAll());
        model.addAttribute("persistentLoginCount", persistentLoginRepository.count());
        model.addAttribute("roles", roleRepository.findAll());
        model.addAttribute("roleCount", roleRepository.count());
        model.addAttribute("tags", tagRepository.findAll());
        model.addAttribute("tagCount", tagRepository.count());
        return "admin/dashboard";
    }

    // ==========================================
    // ARTICLE (MAKALE) İŞLEMLERİ
    // ==========================================

    @GetMapping("/admin/articles/{id}/publish")
    public String publishArticle(@PathVariable("id") Long id) {
        articleRepository.findById(id).ifPresent(article -> {
            article.setStatus(ArticleStatus.PUBLISHED);
            article.setPublishedAt(Instant.now());
            articleRepository.save(article);
        });
        return "redirect:/admin/dashboard";
    }

    @GetMapping("/admin/articles/{id}/delete")
    public String deleteArticle(@PathVariable("id") Long id) {
        articleRepository.deleteById(id);
        return "redirect:/admin/dashboard";
    }

    @GetMapping("/admin/articles/{id}/edit")
    public String editArticleForm(@PathVariable("id") Long id, Model model) {
        articleRepository.findById(id).ifPresent(article -> {
            model.addAttribute("article", article);
            model.addAttribute("categories", categoryRepository.findAll());
        });
        return "admin/edit-article";
    }

    @PostMapping("/admin/articles/{id}/edit")
    public String editArticle(@PathVariable("id") Long id,
                               @RequestParam("title") String title,
                               @RequestParam("slug") String slug,
                               @RequestParam("summary") String summary,
                               @RequestParam("body") String body,
                               @RequestParam("categoryId") Long categoryId) {
        articleRepository.findById(id).ifPresent(article -> {
            article.setTitle(title);
            article.setSlug(slug);
            article.setSummary(summary);
            article.setBody(body);
            categoryRepository.findById(categoryId).ifPresent(article::setCategory);
            articleRepository.save(article);
        });
        return "redirect:/admin/dashboard";
    }

    // ==========================================
    // CATEGORY (KATEGORİ) İŞLEMLERİ
    // ==========================================

    @GetMapping("/admin/categories/{id}/delete")
    public String deleteCategory(@PathVariable("id") Long id) {
        categoryRepository.deleteById(id);
        return "redirect:/admin/dashboard";
    }

    @GetMapping("/admin/categories/add")
    public String addCategoryForm() {
        return "admin/add-category";
    }

    @PostMapping("/admin/categories/add")
    public String addCategory(@RequestParam("name") String name,
                              @RequestParam("slug") String slug) {
        Category cat = new Category();
        cat.setName(name);
        cat.setSlug(slug);
        categoryRepository.save(cat);
        return "redirect:/admin/dashboard";
    }

    @GetMapping("/kategori/{slug}")
    public String byCategory(@PathVariable("slug") String slug,
                              @RequestParam(name = "page", defaultValue = "0") int page,
                              Model model) {
        categoryRepository.findBySlug(slug).ifPresent(cat -> {
            int pageSize = 6;
            var pageable = PageRequest.of(page, pageSize, Sort.by("createdAt").descending());
            var newsPage = newsRepository.findByCategory(cat, pageable);
            model.addAttribute("newsList", newsPage.getContent());
            model.addAttribute("currentPage", page);
            model.addAttribute("totalPages", newsPage.getTotalPages());
            model.addAttribute("currentCategory", cat);
        });
        model.addAttribute("categories", categoryRepository.findAll());
        return "category";
    }

    // ==========================================
    // COMMENT (YORUM) İŞLEMLERİ
    // ==========================================

    @GetMapping("/admin/comments/{id}/delete")
    public String deleteComment(@PathVariable("id") Long id) {
        commentRepository.deleteById(id);
        return "redirect:/admin/dashboard";
    }

    @GetMapping("/admin/comments/{id}/approve")
    public String approveComment(@PathVariable("id") Long id) {
        commentRepository.findById(id).ifPresent(comment -> {
            comment.setStatus(com.news.cms.entity.enums.CommentStatus.APPROVED);
            commentRepository.save(comment);
        });
        return "redirect:/admin/dashboard";
    }

    @GetMapping("/admin/comments/{id}/reject")
    public String rejectComment(@PathVariable("id") Long id) {
        commentRepository.findById(id).ifPresent(comment -> {
            comment.setStatus(com.news.cms.entity.enums.CommentStatus.REJECTED);
            commentRepository.save(comment);
        });
        return "redirect:/admin/dashboard";
    }

    // ==========================================
    // USER (KULLANICI) İŞLEMLERİ
    // ==========================================

    @GetMapping("/admin/users/{id}/delete")
    public String deleteUser(@PathVariable("id") Long id) {
        userRepository.deleteById(id);
        return "redirect:/admin/dashboard";
    }

    @GetMapping("/admin/users/edit/{id}")
    public String editUserForm(@PathVariable("id") Long id, Model model) {
        userRepository.findById(id).ifPresent(user -> model.addAttribute("user", user));
        model.addAttribute("allRoles", roleRepository.findAll());
        return "admin/edit-user";
    }
    @PostMapping("/admin/users/update/{id}")
    public String updateExistingUser(@PathVariable("id") Long id,
                                     @RequestParam("username") String username,
                                     @RequestParam("email") String email,
                                     @RequestParam("firstName") String firstName,
                                     @RequestParam("lastName") String lastName,
                                     @RequestParam(value = "enabled", required = false) boolean enabled,
                                     @RequestParam(value = "roleIds", required = false) List<Long> roleIds) {
        userRepository.findById(id).ifPresent(user -> {
            user.setUsername(username);
            user.setEmail(email);
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setEnabled(enabled);
            if (roleIds != null) {
                Set<com.news.cms.entity.Role> roles = new HashSet<>(roleRepository.findAllById(roleIds));
                user.setRoles(roles);
            } else {
                user.getRoles().clear();
            }
            userRepository.save(user);
        });
        return "redirect:/admin/dashboard";
    }
    

    @PostMapping("/admin/users/create")
    public String createUser(@RequestParam("username") String username,
                             @RequestParam("email") String email,
                             @RequestParam("password") String password,
                             @RequestParam(value = "firstName", required = false) String firstName,
                             @RequestParam(value = "lastName", required = false) String lastName) {
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPasswordHash(passwordEncoder.encode(password));
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEnabled(true);
        userRepository.save(user);
        return "redirect:/admin/dashboard";
    }
    @GetMapping("/admin/users/{id}/enable")
    public String enableUser(@PathVariable Long id) {
        userRepository.findById(id).ifPresent(user -> {
            user.setEnabled(true);
            userRepository.save(user);
        });
        return "redirect:/admin/dashboard";
    }

    @GetMapping("/admin/users/{id}/disable")
    public String disableUser(@PathVariable Long id) {
        userRepository.findById(id).ifPresent(user -> {
            user.setEnabled(false);
            userRepository.save(user);
        });
        return "redirect:/admin/dashboard";
    }

    // ==========================================
    // SİSTEM GÜNLÜKLERİ VE DOSYA İŞLEMLERİ
    // ==========================================

    @GetMapping("/admin/auditlogs/{id}/delete")
    public String deleteAuditLog(@PathVariable("id") Long id) {
        auditLogRepository.deleteById(id);
        return "redirect:/admin/dashboard";
    }

    @GetMapping("/admin/loginattempts/{id}/delete")
    public String deleteLoginAttempt(@PathVariable("id") Long id) {
        loginAttemptRepository.deleteById(id);
        return "redirect:/admin/dashboard";
    }

    @GetMapping("/admin/mediafiles/{id}/delete")
    public String deleteMediaFile(@PathVariable("id") Long id) {
        mediaFileRepository.deleteById(id);
        return "redirect:/admin/dashboard";
    }

    // ==========================================
    // NEWS (HABER) İŞLEMLERİ
    // ==========================================

    @GetMapping("/news/{id}")
    public String publicViewNews(@PathVariable("id") Long id, Model model) {
        newsRepository.findById(id).ifPresent(news -> {
            model.addAttribute("news", news);
            if (news.getCategory() != null) {
                model.addAttribute("similarNews",
                    newsRepository.findTop3ByCategoryAndIdNot(news.getCategory(), id));
            }
            model.addAttribute("comments", 
                commentRepository.findByNewsIdAndStatus(id, com.news.cms.entity.enums.CommentStatus.APPROVED));
        });
        return "news-detail";
    }

    @PostMapping("/news/{id}/comment")
    public String addComment(@PathVariable("id") Long id,
                             @RequestParam("body") String body,
                             @RequestParam(value = "guestName", required = false) String guestName,
                             @RequestParam(value = "guestEmail", required = false) String guestEmail) {
        newsRepository.findById(id).ifPresent(news -> {
            Comment comment = new Comment();
            comment.setNews(news);
            comment.setBody(body);
            comment.setGuestName(guestName);
            comment.setGuestEmail(guestEmail);
            comment.setStatus(com.news.cms.entity.enums.CommentStatus.PENDING);
            commentRepository.save(comment);
        });
        return "redirect:/news/" + id + "?commented=true";
    }

    // ==========================================
    // DİĞER GÜVENLİK VE ETİKET İŞLEMLERİ
    // ==========================================

    @GetMapping("/admin/passwordresettokens/{id}/delete")
    public String deletePasswordResetToken(@PathVariable("id") Long id) {
        passwordResetTokenRepository.deleteById(id);
        return "redirect:/admin/dashboard";
    }

    @GetMapping("/admin/persistentlogins/{id}/delete")
    public String deletePersistentLogin(@PathVariable("id") Long id) {
        persistentLoginRepository.deleteById(id);
        return "redirect:/admin/dashboard";
    }

    @GetMapping("/admin/roles/{id}/delete")
    public String deleteRole(@PathVariable("id") Long id) {
        roleRepository.deleteById(id);
        return "redirect:/admin/dashboard";
    }

    @GetMapping("/admin/tags/{id}/delete")
    public String deleteTag(@PathVariable("id") Long id) {
        tagRepository.deleteById(id);
        return "redirect:/admin/dashboard";
    }

    @PostMapping("/admin/roles/create")
    public String createRole(@RequestParam("name") String name) {
        com.news.cms.entity.Role role = new com.news.cms.entity.Role();
        role.setName(name.toUpperCase());
        roleRepository.save(role);
        return "redirect:/admin/dashboard";
    }
}