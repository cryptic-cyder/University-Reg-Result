package com.shahriar.UniversityRegistration.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class CourseReg {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String studentId;

    @OneToMany(mappedBy = "courseReg", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Course> courses;

    private String transactionId;
}

