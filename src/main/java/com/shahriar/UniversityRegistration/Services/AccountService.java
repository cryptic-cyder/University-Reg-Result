package com.shahriar.UniversityRegistration.Services;

import com.shahriar.UniversityRegistration.Exception.NotFoundException;
import com.shahriar.UniversityRegistration.Entities.CourseDTO;
import com.shahriar.UniversityRegistration.Entities.Student;
import com.shahriar.UniversityRegistration.Entities.StudentStatus;
import com.shahriar.UniversityRegistration.Repos.AccountRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class AccountService {

    @Autowired
    private AccountRepo accountRepo;

    @Autowired
    private DefaultValidationStrategy validation;

    public Student createAccount(String name,
                                 String studentGmail,
                                 String studentID,
                                 String relatedBatch,
                                 String password,
                                 ValidationStrategy strategy) {

            validation.validateEmail(studentGmail);
            validation.validateStudentId(studentID);

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

    public List<Student> getStudentByBatch(List<CourseDTO> DTOs) {

        String relatedBatch = DTOs.get(0).getRelatedBatch();

        List<Student> batchStudents = accountRepo.findByRelatedBatch(relatedBatch);

        if (batchStudents.isEmpty()) {
            throw new NotFoundException("No irregular student is present in batch "+relatedBatch);
        }

        return batchStudents;
    }

}
