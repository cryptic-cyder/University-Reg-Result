package com.shahriar.UniversityRegistration.Services;


import com.shahriar.UniversityRegistration.Exception.NotFoundException;
import com.shahriar.UniversityRegistration.Entities.Course;
import com.shahriar.UniversityRegistration.Entities.StudentStatus;
import com.shahriar.UniversityRegistration.Repos.StudentStatusRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentStatusService {

    @Autowired
    private StudentStatusRepo statusRepo;

    public StudentStatus getStudentStatus(String studentID) {

        StudentStatus status = statusRepo.findByStudentID(studentID);

        if (status == null) {
            throw new NotFoundException("No status found for student ID: " + studentID);
        }
        return status;
    }

    public void createAssociation(StudentStatus studentStatus, List<Course> courses) {

        // Adding multiple course to one studentStatus---> One to many connection
        if (studentStatus.getCourses() != null)
            studentStatus.getCourses().addAll(courses);
        else
            studentStatus.setCourses(courses);


        // Back connection----> For many to one
        for (Course course : courses) {
            course.setStudentStatus(studentStatus);
        }
    }
}
