package com.shahriar.UniversityRegistration.Repos;

import com.shahriar.UniversityRegistration.Entities.LogStatus;
import com.shahriar.UniversityRegistration.Entities.StudentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentStatusRepo extends JpaRepository<StudentStatus, Long> {

//    SELECT s.name
//    FROM student s
//    JOIN student_status ss ON s.studentID = ss.student_id
//    WHERE ss.student_id = 'CUET1234';
    @Query("SELECT ss FROM StudentStatus ss WHERE ss.student.studentID = :providedStudentID")
    StudentStatus findByStudentID(@Param("providedStudentID") String passedStudentID);

    List<StudentStatus> findByOpenCredit(boolean b);
}
