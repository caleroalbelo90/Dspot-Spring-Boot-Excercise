package com.dspot.profile.main.errors;

import org.springframework.stereotype.Component;

@Component
public class ProfileNotFoundExceptionMapper implements ErrorMapper<ProfileNotFoundException> {
    @Override
    public CustomError mapToCustomError(ProfileNotFoundException ex) {
        return new CustomError("404", "Profile not found");
    }
}