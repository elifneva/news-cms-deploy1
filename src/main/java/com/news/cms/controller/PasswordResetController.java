package com.news.cms.controller;

import com.news.cms.service.PasswordResetService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Controller
public class PasswordResetController {

    private final PasswordResetService passwordResetService;

    public PasswordResetController(PasswordResetService passwordResetService) {
        this.passwordResetService = passwordResetService;
    }

    @GetMapping("/forgot-password")
    public String forgotPasswordPage() {
        return "forgot-password";
    }

    @PostMapping("/forgot-password")
    public String handleForgotPassword(@RequestParam("email") String email,
                                        HttpServletRequest request,
                                        Model model) {
        // Daha güvenli bir baseUrl oluşturma (proxy arkasında da çalışır)
        String baseUrl = ServletUriComponentsBuilder.fromContextPath(request)
                .replacePath(null)
                .build()
                .toUriString();
                
        boolean success = passwordResetService.sendResetEmail(email, baseUrl);
        if (success) {
            model.addAttribute("message", "Şifre sıfırlama linki email adresinize gönderildi.");
        } else {
            // Hata sebebi e-posta bulunamaması mı yoksa gönderim hatası mı?
            if (!passwordResetService.userExists(email)) {
                model.addAttribute("error", "Bu email adresi sistemde kayıtlı değil.");
            } else {
                model.addAttribute("error", "E-posta gönderilirken bir hata oluştu. Lütfen sistem yöneticisiyle iletişime geçin veya e-posta ayarlarını kontrol edin.");
            }
        }
        return "forgot-password";
    }

    @GetMapping("/reset-password")
    public String resetPasswordPage(@RequestParam("token") String token, Model model) {
        model.addAttribute("token", token);
        return "reset-password";
    }

    @PostMapping("/reset-password")
    public String handleResetPassword(@RequestParam("token") String token,
                                       @RequestParam("password") String password,
                                       @RequestParam("confirmPassword") String confirmPassword,
                                       Model model) {
        if (!password.equals(confirmPassword)) {
            model.addAttribute("token", token);
            model.addAttribute("error", "Şifreler eşleşmiyor.");
            return "reset-password";
        }
        boolean success = passwordResetService.resetPassword(token, password);
        if (success) {
            return "redirect:/login?resetSuccess";
        } else {
            model.addAttribute("token", token);
            model.addAttribute("error", "Link geçersiz veya süresi dolmuş.");
            return "reset-password";
        }
    }
}