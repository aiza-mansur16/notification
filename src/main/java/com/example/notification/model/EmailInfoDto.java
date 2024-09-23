package com.example.notification.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record EmailInfoDto(
        @Schema(description = "recipient email")
        @NotNull(message = "recipient email cannot be null")
        @NotBlank(message = "recipient email cannot be emptu")
        @Email
        @JsonProperty("recipientEmail")
        String recipientEmail,

        @Schema(description = "subject of mail")
        @NotNull(message = "recipient subject cannot be null")
        @NotBlank(message = "recipient subject cannot be empty")
        @JsonProperty("subject")
        String subject,

        @Schema(description = "message of mail")
        @NotNull(message = "message cannot be null")
        @NotBlank(message = "message cannot be empty")
        @JsonProperty("message")
        String message
) {
}
