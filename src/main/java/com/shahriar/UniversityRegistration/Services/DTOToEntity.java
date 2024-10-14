package com.shahriar.UniversityRegistration.Services;

import com.shahriar.UniversityRegistration.Entities.Course;
import com.shahriar.UniversityRegistration.Entities.CourseDTO;
import com.shahriar.UniversityRegistration.Entities.LogStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DTOToEntity {

    public List<Course> convertDTOToCourse(List<CourseDTO> DTOs, String studentID) {

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
}
