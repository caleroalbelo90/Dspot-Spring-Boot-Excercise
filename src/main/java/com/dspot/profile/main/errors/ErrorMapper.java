package com.dspot.profile.main.errors;

public interface ErrorMapper<T extends Exception> {
    CustomError mapToCustomError(T ex);
}