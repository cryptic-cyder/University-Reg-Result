//    private List<Course> findOpenCreditStudents() {
//
//        List<StudentStatus> openCreditStudentStatus = statusRepo.findByOpenCredit(true);
//        throwNoResourceException(openCreditStudentStatus, "No open credit student status is found...");
//
//        //        return openCreditStudentStatus.stream()
////                .map(s ->
////                        courseRepo.findByPkOfStudentStatus(s.getId())
////                ).collect(Collectors.toList());
//
//        List<Course> openCreditStudents = new ArrayList<>();
//
//        for (StudentStatus s : openCreditStudentStatus) {
//
//            Long id = s.getId();
//
//            Course course = courseRepo.findByPkOfStudentStatus(id, LogStatus.AVAILABLE);
//
//            if (course != null)
//                openCreditStudents.add(course);
//        }
//
//        return openCreditStudents;
//    }

package com.shahriar.UniversityRegistration.Services;

import com.shahriar.UniversityRegistration.Entities.*;
import com.shahriar.UniversityRegistration.Repos.AccountRepo;
import com.shahriar.UniversityRegistration.Repos.CourseRegRepo;
import com.shahriar.UniversityRegistration.Repos.CourseRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LaunchCourseService {


    @Autowired
    private AssignCourses assignCourses;


    public void addCourse(List<CourseDTO> DTOs) {

        assignCourses.assignCourseToBatch(DTOs);
        assignCourses.assignCourseToIrregular(DTOs);

    }

}
