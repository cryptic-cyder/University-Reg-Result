package com.shahriar.UniversityRegistration.Services;

import com.shahriar.UniversityRegistration.Entities.Course;
import com.shahriar.UniversityRegistration.Entities.Student;
import com.shahriar.UniversityRegistration.Entities.StudentStatus;
import com.shahriar.UniversityRegistration.Repos.AccountRepo;
import com.shahriar.UniversityRegistration.Repos.StudentStatusRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class AccountService {

    @Autowired
    private AccountRepo accountRepo;

    public Student createAccount(String name,
                                 String studentGmail,
                                 String studentID,
                                 String relatedBatch,
                                 String password,
                                 ValidationStrategy strategy) {

        try {

            strategy.validate(studentGmail, studentID);

            Student account = Student.builder()
                    .name(name)
                    .studentGmail(studentGmail)
                    .studentID(studentID)
                    .relatedBatch(relatedBatch)
                    .password(password)
                    .build();

            StudentStatus studentStatus = StudentStatus.builder()
                    .completedCredit(0.0)
                    .openCredit(false)
                    .courses(new ArrayList<>())
                    .shortCourses(new ArrayList<>())
                    .backlogCourses(new ArrayList<>())
                    .build();

            account.setStudentStatus(studentStatus);
            studentStatus.setStudent(account);

            return accountRepo.save(account);// Save the student, this will also save the studentStatus due to CascadeType.ALL
        }
        catch (IllegalArgumentException ie) {
            System.out.println("Validation error: " + ie.getMessage());
            throw ie;
        }
        catch (Exception e) {
            System.out.println("Unexpected error occurred: " + e.getMessage());
            throw new RuntimeException("Error creating account", e);
        }
    }
}
