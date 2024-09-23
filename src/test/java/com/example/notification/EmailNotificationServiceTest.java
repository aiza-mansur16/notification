package com.example.notification;

import com.example.notification.model.EmailInfoDto;
import com.example.notification.service.EmailNotificationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mail.MailSendException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;


@SpringBootTest
@ActiveProfiles("test")
class EmailNotificationServiceTest {

    @Autowired
    private EmailNotificationService emailNotificationService;

    @MockBean
    private JavaMailSender mailSender;

    @Test
    void sendEmailSuccess() {
        var emailInfoDto = new EmailInfoDto("test@example.com", "Test Subject", "Test Message");
        emailNotificationService.sendNotification(emailInfoDto);
        verify(mailSender).send(any(SimpleMailMessage.class));
    }

    @Test
    void sendEmailFailure() {
        doThrow(new MailSendException("Mail server not available"))
                .when(mailSender).send(any(SimpleMailMessage.class));
        var emailInfoDto = new EmailInfoDto("test@example.com", "Test Subject", "Test Message");
        var exception = assertThrows(Exception.class, () -> emailNotificationService.sendNotification(emailInfoDto));
        assertEquals("Error sending email notification to: test@example.com", exception.getMessage());
        verify(mailSender).send(any(SimpleMailMessage.class));
    }
}
