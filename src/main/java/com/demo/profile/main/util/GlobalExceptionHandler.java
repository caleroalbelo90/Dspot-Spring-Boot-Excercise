package com.demo.profile.main.util;

import com.demo.profile.main.model.ApiError;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleGlobalException(Exception ex) {
        ApiError apiError = new ApiError();
        apiError.setMessage(ex.getMessage());

        // Get the status code of the original exception (if available).
        HttpStatusCode httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        if (ex instanceof org.springframework.web.server.ResponseStatusException) {
            httpStatus = ((org.springframework.web.server.ResponseStatusException) ex).getStatusCode();
        } else if (ex instanceof org.springframework.web.HttpRequestMethodNotSupportedException) {
            httpStatus = HttpStatus.METHOD_NOT_ALLOWED;
        }
        apiError.setStatus(httpStatus.value());

        return ResponseEntity.status(httpStatus).body(apiError);
    }

}