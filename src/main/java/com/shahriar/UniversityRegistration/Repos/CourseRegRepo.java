package com.shahriar.UniversityRegistration.Repos;

import com.shahriar.UniversityRegistration.Entities.Course;
import com.shahriar.UniversityRegistration.Entities.CourseReg;
import com.shahriar.UniversityRegistration.Entities.StudentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.http.converter.json.GsonBuilderUtils;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRegRepo extends JpaRepository<CourseReg, Long> {

    @Query("SELECT c FROM Course c WHERE c.studentID = :providedStudentID")
    List<Course> findByStudentID(@Param("providedStudentID") String providedStudentID);
    CourseReg findByStudentId(String studentId);
}
