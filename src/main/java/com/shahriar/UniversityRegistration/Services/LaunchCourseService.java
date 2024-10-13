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

import com.shahriar.UniversityRegistration.Config.NotFoundException;
import com.shahriar.UniversityRegistration.Entities.*;
import com.shahriar.UniversityRegistration.Repos.AccountRepo;
import com.shahriar.UniversityRegistration.Repos.CourseRegRepo;
import com.shahriar.UniversityRegistration.Repos.CourseRepo;
import com.shahriar.UniversityRegistration.Repos.StudentStatusRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class LaunchCourseService {

    @Autowired
    private AccountRepo accountRepo;

    @Autowired
    private StudentStatusRepo statusRepo;

    @Autowired
    private CourseRegRepo courseRegRepo;

    @Autowired
    private CourseRepo courseRepo;

    public StudentStatus getStudentStatus(String studentID) {

        StudentStatus status = statusRepo.findByStudentID(studentID);

        if (status == null) {
            throw new NotFoundException("No status found for student ID: " + studentID);
        }
        return status;
    }


    public void addCourse(List<CourseDTO> DTOs) {

        assignCourseToBatch(DTOs);
        assignCourseToIrregular(DTOs);

    }

    private void assignCourseToBatch(List<CourseDTO> DTOs) {

        List<Student> regularBatchStudent = getStudentByBatch(DTOs);

        for (Student student : regularBatchStudent) {

            try {

                StudentStatus status = getStudentStatus(student.getStudentID());

                List<Course> courses = convertingDTOListIntoCourse(DTOs, student.getStudentID());

                oneToManyConnection(status, courses);

                manyToOneConnection(status, courses);

                statusRepo.save(status);
            }
            catch (NotFoundException e) {

                System.out.println("\n\nSkipping student " + student.getName() + ": " + e.getMessage());
            }
        }
    }

    private void assignCourseToIrregular(List<CourseDTO> DTOs) {

        List<Course> log = new ArrayList<>();

        for (CourseDTO courseDTO : DTOs) {

            String courseName = courseDTO.getName();

            List<Course> specific = courseRepo.findCoursesByIrregularStatusAndName(LogStatus.ACTIVE, courseName);

            log.addAll(specific);
        }

       if(log.isEmpty())
           throw new NotFoundException("No irregular student is present...");

        changeLogStatus(log);

    }

    private void changeLogStatus(List<Course> courses) {

        courses.forEach(course -> {

            course.setBacklogOpen(LogStatus.AVAILABLE);
            course.setShortOpen(LogStatus.AVAILABLE);
            courseRepo.save(course);

        });
    }


    public List<Student> getStudentByBatch(List<CourseDTO> DTOs) {

        String relatedBatch = DTOs.get(0).getRelatedBatch();

        List<Student> batchStudents = accountRepo.findByRelatedBatch(relatedBatch);

        if (batchStudents.isEmpty()) {
            throw new NotFoundException("No irregular student is present in batch "+relatedBatch);
        }

        return batchStudents;
    }

    public List<Course> convertingDTOListIntoCourse(List<CourseDTO> DTOs, String studentID) {

        return DTOs.stream()
                .map(dto -> Course.builder()
                        .name(dto.getName())
                        .credit(dto.getCredit())
                        .relatedBatch(dto.getRelatedBatch())
                        .backlogOpen(LogStatus.UNAVAILABLE)
                        .shortOpen(LogStatus.UNAVAILABLE)
                        .grade("UNDEFINED")
                        .studentID(studentID)
                        .build()
                )
                .collect(Collectors.toList());
    }

    public void oneToManyConnection(StudentStatus studentStatus, List<Course> courses) {

        // Adding multiple course to one studentStatus---> One to many connection
        if (studentStatus.getCourses() != null)
            studentStatus.getCourses().addAll(courses);
        else
            studentStatus.setCourses(courses);
    }

    public void manyToOneConnection(StudentStatus studentStatus, List<Course> courses) {

        // Back connection----> For many to one
        for (Course course : courses) {
            course.setStudentStatus(studentStatus);
        }
    }

}
