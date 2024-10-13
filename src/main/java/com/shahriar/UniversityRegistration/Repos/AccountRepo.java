package com.shahriar.UniversityRegistration.Repos;

import com.shahriar.UniversityRegistration.Entities.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepo extends JpaRepository<Student, Long> {

    Student findByStudentGmail(String studentGmail);

    Student findByStudentID(String studentID);

    List<Student> findByRelatedBatch(String relatedBatch);
}
