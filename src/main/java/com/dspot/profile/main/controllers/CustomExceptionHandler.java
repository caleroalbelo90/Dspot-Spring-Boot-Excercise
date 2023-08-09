package com.dspot.profile.main.controllers;

import com.dspot.profile.main.errors.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CustomExceptionHandler {

    @Autowired
    private ProfileNotFoundExceptionMapper profileNotFoundExceptionMapper;

    @Autowired
    private InvalidInputExceptionMapper invalidInputExceptionMapper;

    @ExceptionHandler(ProfileNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public CustomError handleProfileNotFoundException(ProfileNotFoundException ex) {
        return profileNotFoundExceptionMapper.mapToCustomError(ex);
    }

    @ExceptionHandler(InvalidInputException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public CustomError handleInvalidInputException(InvalidInputException ex) {
        return invalidInputExceptionMapper.mapToCustomError(ex);
    }
}