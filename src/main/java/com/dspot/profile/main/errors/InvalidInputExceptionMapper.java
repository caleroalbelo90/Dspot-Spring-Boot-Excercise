package com.dspot.profile.main.errors;

import org.springframework.stereotype.Component;

@Component
public class InvalidInputExceptionMapper implements ErrorMapper<InvalidInputException> {
    @Override
    public CustomError mapToCustomError(InvalidInputException ex) {
        return new CustomError("400", "Invalid input: " + ex.getMessage());
    }
}