package com.example.notification.controller;

import com.example.notification.model.EmailInfoDto;
import com.example.notification.service.EmailNotificationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/notifications")
@Validated
public class NotificationController {

    private final EmailNotificationService emailNotificationService;

    public NotificationController(EmailNotificationService emailNotificationService) {
        this.emailNotificationService = emailNotificationService;
    }

    @Operation(
            summary = "Send email notification",
            description = "Sends an email notification to the specified recipient",
            responses = {
                    @ApiResponse(
                            responseCode = "204",
                            description = "Mail sent successfully"
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Invalid request"
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Internal server error"
                    )
            }
    )
    @PostMapping(value = "/mail",
            produces = "application/json",
            consumes = "application/json"
    )
    public ResponseEntity<Void> sendMailNotification(@RequestBody @Valid EmailInfoDto emailInfoDto) {
        emailNotificationService.sendNotification(emailInfoDto);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
