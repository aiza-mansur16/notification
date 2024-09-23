package com.example.notification.utils.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.util.List;

@Getter
@Setter
public class Error {
        @Schema(description = "status code of error")
        private HttpStatus statusCode;

        @Schema(description = "error messages")
        private List<String> messages;
}
