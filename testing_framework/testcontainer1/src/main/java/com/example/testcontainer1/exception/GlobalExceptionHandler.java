package com.example.testcontainer1.exception;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.RestClientException;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<AppErrorResponse> handleRecordNotFoundException(EntityNotFoundException ee) {
        AppErrorResponse errResponse = new AppErrorResponse(AppErrorResponse.ErrorCodeConstants.RECORD_NOT_FOUND,
                ee.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errResponse);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<AppErrorResponse> handleRuntimeException(RuntimeException re) {
        AppErrorResponse errResponse = new AppErrorResponse(AppErrorResponse.ErrorCodeConstants.SYSTEM_ERROR,
                re.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errResponse);
    }

    @ExceptionHandler(RestClientException.class)
    public ResponseEntity<AppErrorResponse> handleRestClientException(RestClientException reste) {
        AppErrorResponse errResponse = new AppErrorResponse(AppErrorResponse.ErrorCodeConstants.CONNECT_ERROR,
                reste.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errResponse);
    }

}
