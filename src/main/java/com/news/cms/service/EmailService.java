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

    public void sendPasswordResetEmail(String toEmail, String resetLink) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("Şifre Sıfırlama - News CMS");
        message.setText("Merhaba,\n\nŞifrenizi sıfırlamak için aşağıdaki linke tıklayın:\n\n"
                + resetLink
                + "\n\nBu link 30 dakika geçerlidir.\n\nEğer bu isteği siz yapmadıysanız, bu emaili görmezden gelin.");
        mailSender.send(message);
    }
}