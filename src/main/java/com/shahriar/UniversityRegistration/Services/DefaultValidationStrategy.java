package com.shahriar.UniversityRegistration.Services;

import com.shahriar.UniversityRegistration.Exception.DuplicateCredentials;
import com.shahriar.UniversityRegistration.Repos.AccountRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DefaultValidationStrategy implements ValidationStrategy {

    @Autowired
    private AccountRepo accountRepo;

    public DefaultValidationStrategy(AccountRepo accountRepo) {
        this.accountRepo = accountRepo;
    }

    @Override
    public void validateEmail(String studentGmail) {

        if (studentGmail == null || studentGmail.isEmpty()) {
            throw new IllegalArgumentException("Email can't be null or empty");
        }
        else if (accountRepo.findByStudentGmail(studentGmail) != null) {
            throw new DuplicateCredentials("Email exists already");
        }
    }

    @Override
    public void validateStudentId(String studentID) {

        if (studentID == null || studentID.isEmpty()) {
            throw new IllegalArgumentException("Student ID can't be null or empty");
        }
        else if (accountRepo.findByStudentID(studentID) != null) {
            throw new DuplicateCredentials("Student ID exists already");
        }
    }

}
