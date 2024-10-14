package com.shahriar.UniversityRegistration.Services;

import com.shahriar.UniversityRegistration.Entities.Student;

public interface ValidationStrategy {
    void validateEmail(String gmail);
    void validateStudentId(String studentId);

}
