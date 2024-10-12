package com.shahriar.UniversityRegistration.Entities;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class CourseDTO {

    private String name;
    private String relatedBatch;
    private double credit;

}
