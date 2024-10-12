package com.shahriar.UniversityRegistration.Services;

import com.shahriar.UniversityRegistration.Entities.*;
import com.shahriar.UniversityRegistration.Repos.AccountRepo;
import com.shahriar.UniversityRegistration.Repos.CourseRegRepo;
import com.shahriar.UniversityRegistration.Repos.CourseRepo;
import com.shahriar.UniversityRegistration.Repos.StudentStatusRepo;
import org.apache.tomcat.util.net.jsse.JSSEUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
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


    public void addCourse(List<CourseDTO> DTOs) {

        addCoursesToRegularBatch(DTOs);
        addCoursesToLogAndOpenCredits(DTOs);

    }

    private void addCoursesToRegularBatch(List<CourseDTO> dtOs) {

        List<Student> studentsOfThatBatch = findingAllStudentsOfSpecificYear(dtOs);

        for (Student student : studentsOfThatBatch) {

            StudentStatus studentStatus = statusRepo.findByStudentID(student.getStudentID());
            throwNoResourceException(studentStatus, "No students status found for " + student.getName());

            List<Course> courses = convertingDTOlistIntoCourse(dtOs, student.getStudentID());

            oneToManyConnection(studentStatus, courses);

            manyToOneConnection(studentStatus, courses);

            statusRepo.save(studentStatus);
        }
    }

    private void addCoursesToLogAndOpenCredits(List<CourseDTO> dtOs) {

        for (CourseDTO dto : dtOs) { //Opening course for log and open-credit students

            String courseName = dto.getName();

            List<Course> courses = courseRepo.findCoursesByBacklogOpenStatusAndName(LogStatus.ACTIVE, courseName);

            List<Course> openCreditStudents = findOpenCreditStudents();

            courses.addAll(openCreditStudents);

            changingStatusOfLogAvailable(courses);
        }
    }

    private void changingStatusOfLogAvailable(List<Course> courses) {

        courses.forEach(course -> {

            course.setBacklogOpen(LogStatus.AVAILABLE);
            course.setShortOpen(LogStatus.AVAILABLE);
            courseRepo.save(course);

        });
    }

    private List<Course> findOpenCreditStudents() {

        List<StudentStatus> openCreditStudentStatus = statusRepo.findByOpenCredit(true);

//        return openCreditStudentStatus.stream()
//                .map(s ->
//                        courseRepo.findByPkOfStudentStatus(s.getId())
//                ).collect(Collectors.toList());

        List<Course> openCreditStudents = new ArrayList<>();

        for (StudentStatus s : openCreditStudentStatus) {

            Long id = s.getId();

            Course course = courseRepo.findByPkOfStudentStatus(id);

            openCreditStudents.add(course);
        }

        return openCreditStudents;
    }

    public List<Student> findingAllStudentsOfSpecificYear(List<CourseDTO> DTOs) {

        String relatedBatch = DTOs.get(0).getRelatedBatch();

        List<Student> studentListRelatedYear = accountRepo.findByRelatedBatch(relatedBatch);

        throwNoResourceException(studentListRelatedYear, "No students found for that batch: " + relatedBatch);

        return studentListRelatedYear;
    }

    public List<Student> findingAllStudentWithBacklog(List<CourseDTO> DTOs) {

        String relatedBatch = DTOs.get(0).getRelatedBatch();

        List<Student> studentListRelatedYear = accountRepo.findByRelatedBatch(relatedBatch);

        throwNoResourceException(studentListRelatedYear, "No students found for that batch: " + relatedBatch);

        return studentListRelatedYear;
    }


    public List<Course> convertingDTOlistIntoCourse(List<CourseDTO> DTOs, String studentID) {

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
            //course.setCourseReg();
        }
    }

    private void throwNoResourceException(List<Student> studentListRelatedYear, String message) {

        if (studentListRelatedYear == null || studentListRelatedYear.isEmpty())
            throw new NoSuchElementException(message);
    }

    private void throwNoResourceException(StudentStatus studentStatus, String message) {

        if (studentStatus == null)
            throw new NoSuchElementException(message);
    }
}
