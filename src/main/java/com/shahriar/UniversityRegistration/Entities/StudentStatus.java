package com.shahriar.UniversityRegistration.Entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class StudentStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @OneToMany(mappedBy = "studentStatus", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Course> courses;


    @OneToMany(mappedBy = "studentStatus", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Course> shortCourses;

    @OneToMany(mappedBy = "studentStatus", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Course> backlogCourses;

//    public boolean isOpenCredit() {
//        return openCredit;
//    }

    private double completedCredit;
    private boolean openCredit;

    @OneToOne
    @JoinColumn(name = "student_id", referencedColumnName = "studentID") // Join using studentID
    @JsonBackReference
    private Student student;
}
