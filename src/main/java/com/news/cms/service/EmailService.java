package com.news.cms.service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public boolean sendPasswordResetEmail(String toEmail, String resetLink) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(toEmail);
            message.setSubject("Şifre Sıfırlama - özavsrNews");
            message.setText("Merhaba,\n\nŞifrenizi sıfırlamak için aşağıdaki linke tıklayın:\n\n"
                    + resetLink
                    + "\n\nBu link 30 dakika geçerlidir.\n\nEğer bu isteği siz yapmadıysanız, bu emaili görmezden gelin.");
            mailSender.send(message);
            return true;
        } catch (Exception e) {
            // Hata durumunda loglanabilir veya sessizce false dönebilir
            System.err.println("Email gönderim hatası: " + e.getMessage());
            return false;
        }
    }
}