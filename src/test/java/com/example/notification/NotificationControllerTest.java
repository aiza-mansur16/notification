package com.example.notification;

import com.example.notification.model.EmailInfoDto;
import com.example.notification.service.EmailNotificationService;
import com.example.notification.utils.model.EmailNotSentException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class NotificationControllerTest {

    @LocalServerPort
    int port = 0;

    @Autowired
    TestRestTemplate testRestTemplate;

    @MockBean
    private EmailNotificationService emailNotificationService;

    @Test
    void sendEmailSuccess() {
        var emailInfoDto = new EmailInfoDto(
                "john.doe@gmail.com",
                "Budget Exceed Alert",
                "Your expenses have exceeded the budget limit.");
        var response = testRestTemplate.exchange(
                "http://localhost:" + port + "/api/notifications/mail",
                HttpMethod.POST,
                new HttpEntity<>(emailInfoDto),
                Void.class
        );
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    void sendEmailFailure() {
        doThrow(new EmailNotSentException("Failed to send email."))
                .when(emailNotificationService).sendNotification(any(EmailInfoDto.class));
        try {
            testRestTemplate.exchange(
                    "http://localhost:" + port + "/api/notifications/mail",
                    HttpMethod.POST,
                    new HttpEntity<>(new EmailInfoDto(
                            "john.doe@gmail.com",
                            "Budget Exceed Alert",
                            "Your expenses have exceeded the budget limit."
                    )),
                    Void.class
            );
        } catch (Exception e) {
            assertEquals("Failed to send email.", e.getMessage());
        }
        verify(emailNotificationService).sendNotification(any());
    }
}
