//package com.shahriar.UniversityRegistration.Learnings;
//
//
//import com.shahriar.UniversityRegistration.Entities.Student;
//import com.shahriar.UniversityRegistration.Entities.StudentStatus;
//import com.shahriar.UniversityRegistration.Repos.AccountRepo;
//import com.shahriar.UniversityRegistration.Repos.StudentStatusRepo;
//import com.shahriar.UniversityRegistration.Services.ValidationStrategy;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.ArrayList;
//
//@Service
//public class OneToOneProblem {
//
//    @Autowired
//    private AccountRepo accountRepo;
//
//    @Autowired
//    private StudentStatusRepo statusRepo;
//
//    public Student createAccount(String name,
//                                 String studentGmail,
//                                 String studentID,
//                                 String relatedBatch,
//                                 String password,
//                                 ValidationStrategy strategy) {
//
//        try {
//
//            strategy.validate(studentGmail, studentID);
//
//            Student savedAccount = accountRepo.save(
//                    Student.builder()
//                            .name(name)
//                            .studentGmail(studentGmail)
//                            .studentID(studentID)
//                            .relatedBatch(relatedBatch)
//                            .password(password)
//                            .build()
//            );
//
//            StudentStatus studentStatus = StudentStatus.builder()
//                    .completedCredit(0.0)
//                    .openCredit(false)
//                    .courses(new ArrayList<>())
//                    .shortCourses(new ArrayList<>())
//                    .backlogCourses(new ArrayList<>())
//                    .student(savedAccount)   /// Have to set the db saved "savedEntity" not the "account"
//                    .build();
//
//            //Saving the owning side ensures auto saving in the inverse(Student) side
//            //Otherwise use CASCADE ALL in the inverse(Student) side
//            StudentStatus savedStudentStatus = statusRepo.save(studentStatus);
//
//
//            // For returning the response with associated entity;
//            // not necessary for storing in DB
//            savedAccount.setStudentStatus(studentStatus);
//
//            return savedAccount;
//
//        }
//        catch (IllegalArgumentException ie) {
//            System.out.println("Validation error: " + ie.getMessage());
//            throw ie;
//        }
//        catch (Exception e) {
//            System.out.println("Unexpected error occurred: " + e.getMessage());
//            throw new RuntimeException("Error creating account", e);
//        }
//    }
//}
