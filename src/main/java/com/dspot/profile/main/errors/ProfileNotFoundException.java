package com.dspot.profile.main.errors;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class ProfileNotFoundException extends ResponseStatusException {
    public ProfileNotFoundException(Long profileId) {
        super(HttpStatus.NOT_FOUND, "Profile with id " + profileId + " does not exist");
    }
}