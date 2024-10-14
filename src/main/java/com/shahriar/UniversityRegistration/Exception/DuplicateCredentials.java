package com.shahriar.UniversityRegistration.Exception;

public class DuplicateCredentials extends RuntimeException{

    public DuplicateCredentials(String message) {
        super(message);
    }
}
