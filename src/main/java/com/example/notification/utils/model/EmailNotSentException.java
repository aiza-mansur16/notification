package com.example.notification.utils.model;

public class EmailNotSentException extends RuntimeException {

    public EmailNotSentException(String message) {
        super(message);
    }
}
