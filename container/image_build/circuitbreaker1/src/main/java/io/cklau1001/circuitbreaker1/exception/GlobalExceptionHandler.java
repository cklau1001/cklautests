package io.cklau1001.circuitbreaker1.exception;

import io.cklau1001.circuitbreaker1.model.CustomErrorCode;
import io.cklau1001.circuitbreaker1.model.CustomErrorResponse;
import feign.FeignException;
import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.net.ConnectException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({CallNotPermittedException.class})
    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    public ResponseEntity<?> handleServiceUnavailableEception(Exception e) {

        CustomErrorResponse ea = new CustomErrorResponse(
                CustomErrorCode.INTERNAL_ERROR,
                e.getMessage()
        );

        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(ea);

    }

    @ExceptionHandler({ConnectException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<?> handleConnectEception(Exception e) {

        CustomErrorResponse ea = new CustomErrorResponse(
                CustomErrorCode.INTERNAL_ERROR,
                e.getMessage()
        );

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ea);

    }

    @ExceptionHandler({FeignException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<?> handleFeignEception(Exception e) {

        CustomErrorResponse ea = new CustomErrorResponse(
                CustomErrorCode.NOT_FOUND,
                e.getMessage()
        );

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ea);

    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleMiscEception(Exception e) {

        CustomErrorResponse ea = new CustomErrorResponse(
                CustomErrorCode.INTERNAL_ERROR,
                "MISC error: " + e.getMessage()
        );

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ea);

    }

}
