package com.example.notification.utils.model.exception;

public class EmailNotSentException extends RuntimeException {

  public EmailNotSentException(String message) {
    super(message);
  }

  public EmailNotSentException(String message, Throwable cause) {
    super(message, cause);
  }
}
