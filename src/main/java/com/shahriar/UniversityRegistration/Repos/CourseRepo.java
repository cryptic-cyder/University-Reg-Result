package com.shahriar.UniversityRegistration.Repos;

import com.shahriar.UniversityRegistration.Entities.Course;
import com.shahriar.UniversityRegistration.Entities.LogStatus;
import com.shahriar.UniversityRegistration.Entities.StudentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepo extends JpaRepository<Course, Long> {
    Course findByName(String courseName);

    @Query("SELECT c FROM Course c WHERE c.backlogOpen = :backlogStatus AND c.name = :courseName")
    List<Course> findCoursesByIrregularStatusAndName(
            @Param("backlogStatus") LogStatus backlogStatus,
            @Param("courseName") String courseName
    );

    @Query("SELECT c FROM Course c WHERE c.studentStatus.id = :pk AND c.backlogOpen = :status")
    Course findByPkOfStudentStatus(@Param("pk") Long statusPK,
                                   @Param("status") LogStatus logStatus
    );

//    This part of the query applies the WHERE condition, which ensures that only the Course entities
//    provided in the courses list will be updated.
//    c IN :courses means that only the Course entities in the
//    provided list (passed as a parameter to the method) will be affected by the update.
//    The list is passed as the courses parameter in the method.
   @Modifying
    @Query("UPDATE Course c SET c.backlogOpen = :logStatus, c.shortOpen = :logStatus WHERE c IN :courses")
    void updateCourseLogStatus(@Param("courses") List<Course> courses,
                               @Param("logStatus") LogStatus logStatus
    );


}
