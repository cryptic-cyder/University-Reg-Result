package com.shahriar.UniversityRegistration.Services;

import com.shahriar.UniversityRegistration.Entities.Course;
import com.shahriar.UniversityRegistration.Entities.CourseReg;
import com.shahriar.UniversityRegistration.Repos.CourseRegRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RegCoursesService {

    @Autowired
    private CourseRegRepo courseRegRepo;

    public void regCourse(List<Course> courses) {

        CourseReg courseReg = new CourseReg();

        courseReg.setStudentId("1804003");
        courseReg.setTransactionId("cxerweorucsank");

        courseReg.setCourses(courses);

        for (Course course : courses) {
            course.setCourseReg(courseReg);
        }

        courseRegRepo.save(courseReg);
    }

    public void getRegByAnyStudents(String studentId){

        //CourseReg courseReg = courseRegRepo.findByStudentId(studentId);
        //System.out.println(courseReg.getId()+" "+courseReg.getStudentId()+" "+courseReg.getTransactionId());
        List<Course> registeredCourse = courseRegRepo.findByStudentID(studentId);

        for(Course course:registeredCourse){
            System.out.println(course.getStudentID()+" "+course.getName());
        }
    }
}
