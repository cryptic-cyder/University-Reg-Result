package com.shahriar.UniversityRegistration.Services;

import com.shahriar.UniversityRegistration.Exception.NotFoundException;
import com.shahriar.UniversityRegistration.Entities.*;
import com.shahriar.UniversityRegistration.Repos.CourseRepo;
import com.shahriar.UniversityRegistration.Repos.StudentStatusRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AssignCourses {

    @Autowired
    private DTOToEntity dtoToEntity;
    @Autowired
    private AccountService accountService;
    @Autowired
    private StudentStatusService studentStatusService;
    @Autowired
    private CourseRepo courseRepo;
    @Autowired
    private StudentStatusRepo statusRepo;


    public void assignCourseToBatch(List<CourseDTO> DTOs) {

        List<Student> regularBatchStudent = accountService.getStudentByBatch(DTOs);

        for (Student student : regularBatchStudent) {

            try {

                StudentStatus studentStatus = studentStatusService.getStudentStatus(student.getStudentID());

                List<Course> courses = dtoToEntity.convertDTOToCourse(DTOs, student.getStudentID());

                studentStatusService.createAssociation(studentStatus, courses);

                statusRepo.save(studentStatus);
            }
            catch (NotFoundException e) {

                System.out.println("\n\nSkipping student " + student.getName() + ": " + e.getMessage());
            }
        }
    }

    public void assignCourseToIrregular(List<CourseDTO> DTOs) {


        List<Course> log = new ArrayList<>();

        try {

            for (CourseDTO courseDTO : DTOs) {

                String courseName = courseDTO.getName();

                List<Course> specific = courseRepo.findCoursesByIrregularStatusAndName(LogStatus.ACTIVE, courseName);

                log.addAll(specific);
            }

            if (log.isEmpty())
                throw new NotFoundException("No irregular student is present...");
        }
        catch (NotFoundException e){
            System.out.println("Error: " + e.getMessage());
        }

        courseRepo.updateCourseLogStatus(log, LogStatus.AVAILABLE);
    }
}
