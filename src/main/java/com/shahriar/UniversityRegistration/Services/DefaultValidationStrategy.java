package com.shahriar.UniversityRegistration.Services;

import com.shahriar.UniversityRegistration.Entities.Student;
import com.shahriar.UniversityRegistration.Repos.AccountRepo;
import org.springframework.beans.factory.annotation.Autowired;

public class DefaultValidationStrategy implements ValidationStrategy {

    @Autowired
    private AccountRepo accountRepo;

    public DefaultValidationStrategy(AccountRepo accountRepo) {
        this.accountRepo = accountRepo;
    }

    @Override
    public void validate(String studentGmail, String studentID) {

        if (studentGmail == null || studentGmail.isEmpty())
            throw new IllegalArgumentException("Email can't be null or empty");

        else if (accountRepo.findByStudentGmail(studentGmail) != null)
            throw new IllegalArgumentException("Email exists already");


        if (studentID == null || studentID.isEmpty())
            throw new IllegalArgumentException("Student ID can't be null or empty");

        else if (accountRepo.findByStudentID(studentID) != null)
            throw new IllegalArgumentException("Student ID exists already");

    }

}
