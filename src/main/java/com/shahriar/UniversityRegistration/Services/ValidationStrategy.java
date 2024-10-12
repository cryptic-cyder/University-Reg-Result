package com.shahriar.UniversityRegistration.Services;

import com.shahriar.UniversityRegistration.Entities.Student;

public interface ValidationStrategy {
    void validate(String gmail, String id);
}
