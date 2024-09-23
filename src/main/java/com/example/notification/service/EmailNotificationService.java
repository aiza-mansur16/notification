package com.example.notification.service;

import com.example.notification.model.EmailInfoDto;
import com.example.notification.utils.model.EmailNotSentException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class EmailNotificationService implements Notification<EmailInfoDto> {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String senderEmail;

    public EmailNotificationService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public void sendNotification(EmailInfoDto notificationInfoDto) {
        log.info("Sending email notification to: {}", notificationInfoDto.recipientEmail());
        try {
            var mailMessage = new SimpleMailMessage();
            mailMessage.setFrom(senderEmail);
            mailMessage.setTo(notificationInfoDto.recipientEmail());
            mailMessage.setSubject(notificationInfoDto.subject());
            mailMessage.setText(notificationInfoDto.message());

            mailSender.send(mailMessage);

            log.info("Email notification sent successfully to: {}", notificationInfoDto.recipientEmail());

        } catch (Exception e) {
            log.warn("Error sending email notification to: {}", notificationInfoDto.recipientEmail());
            throw new EmailNotSentException("Error sending email notification to: "+ notificationInfoDto.recipientEmail());
        }
    }
}
