package com.example.notification.utils.advice;

import com.example.notification.utils.model.Error;
import com.example.notification.utils.model.ResponseEnvelope;
import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;

@ControllerAdvice
@Component
public class RestControllerAdvice {

  @ExceptionHandler(CallNotPermittedException.class)
  public ResponseEntity<Object> handleCallNotPermittedException(CallNotPermittedException exception) {
    var error = new Error();
    error.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
    error.setMessages(List.of("External service is unavailable"));
    return new ResponseEntity<>(new ResponseEnvelope<>(null, error, null), HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<Object> handleGeneralException(Exception exception) {
    var error = new Error();
    error.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
    error.setMessages(List.of(exception.getMessage()));
    return new ResponseEntity<>(new ResponseEnvelope<>(null, error, null), HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
