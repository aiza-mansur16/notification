package com.example.notification.service;

import com.example.notification.model.EmailInfoDto;
import com.example.notification.utils.model.exception.EmailNotSentException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
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

  @CircuitBreaker(name = "EmailService")
  @Retry(name = "RetrySendEmail")
  @Override
  public void sendNotification(EmailInfoDto notificationInfoDto) {
    if (log.isDebugEnabled()) {
      log.debug("Sending email notification to: {}", notificationInfoDto.recipientEmail());
    }
    try {
      var mailMessage = new SimpleMailMessage();
      mailMessage.setFrom(senderEmail);
      mailMessage.setTo(notificationInfoDto.recipientEmail());
      mailMessage.setSubject(notificationInfoDto.subject());
      mailMessage.setText(notificationInfoDto.message());

      mailSender.send(mailMessage);

      if (log.isDebugEnabled()) {
        log.debug("Email notification sent successfully to: {}", notificationInfoDto.recipientEmail());
      }

    } catch (MailException e) {
      throw e;
    } catch (Exception e) {
      if (log.isWarnEnabled()) {
        log.warn("Error sending email notification to: {}", notificationInfoDto.recipientEmail());
      }
      throw new EmailNotSentException("Error sending email notification to: "
          + notificationInfoDto.recipientEmail(), e);
    }
  }
}
