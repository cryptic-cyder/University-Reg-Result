package com.shahriar.UniversityRegistration.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Data
@Getter
@Setter
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

