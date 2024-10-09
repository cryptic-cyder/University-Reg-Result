package com.shahriar.UniversityRegistration.Entities;


import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Data
@Getter
@Setter
public class Course {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String relatedBatch;
    private String grade;
    private double gpa;
    private int CTMarks;
    private boolean backlogOpen;
    private boolean shortOpen;
    private double credit;
    private boolean registered;

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private StudentStatus student;

    @ManyToOne
    @JoinColumn(name = "courseReg_id", nullable = false)
    private CourseReg courseReg;
}
