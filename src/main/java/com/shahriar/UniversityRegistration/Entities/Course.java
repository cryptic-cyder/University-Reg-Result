package com.shahriar.UniversityRegistration.Entities;


import com.shahriar.UniversityRegistration.Repos.StudentStatusRepo;
import jakarta.persistence.*;
import lombok.*;

@Entity

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String relatedBatch;
    private double credit;

    private String grade;
    private double gpa;
    private int CTMarks;

    @Enumerated(EnumType.STRING)
    private LogStatus backlogOpen;

    @Enumerated(EnumType.STRING)
    private LogStatus shortOpen;

    private String studentID;

    @ManyToOne
    @JoinColumn(name = "pk_of_student_status", nullable = false)
    private StudentStatus studentStatus;

    @ManyToOne
    @JoinColumn(name = "courseReg_id", nullable = true)
    private CourseReg courseReg;

}
