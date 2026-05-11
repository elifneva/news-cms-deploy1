package com.news.cms.controller;

import com.news.cms.service.PasswordResetService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
        String baseUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
        boolean sent = passwordResetService.sendResetEmail(email, baseUrl);
        if (sent) {
            model.addAttribute("message", "Şifre sıfırlama linki email adresinize gönderildi.");
        } else {
            model.addAttribute("error", "Bu email adresi sistemde kayıtlı değil.");
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