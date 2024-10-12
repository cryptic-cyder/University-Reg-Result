package com.shahriar.UniversityRegistration.Repos;

import com.shahriar.UniversityRegistration.Entities.Course;
import com.shahriar.UniversityRegistration.Entities.LogStatus;
import com.shahriar.UniversityRegistration.Entities.StudentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepo extends JpaRepository<Course, Long> {
    Course findByName(String courseName);

    @Query("SELECT c FROM Course c WHERE c.backlogOpen = :backlogStatus AND c.name = :courseName")
    List<Course> findCoursesByBacklogOpenStatusAndName(
            @Param("backlogStatus") LogStatus backlogStatus,
            @Param("courseName") String courseName
    );

    @Query("SELECT c FROM Course c WHERE c.studentStatus.id = :pk")
    Course findByPkOfStudentStatus(@Param("pk") Long statusPK);

}
