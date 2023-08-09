package com.dspot.profile.main.errors;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class ProfileNotFoundException extends ResponseStatusException {

    public ProfileNotFoundException(String message) {
        super(HttpStatus.NOT_FOUND, message);
    }
}