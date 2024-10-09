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
@Table(name = "student", indexes = @Index(columnList = "studentID"))
public class Student {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String studentGmail;

    @Column(unique = true)
    private String studentID;
    private String relatedBatch;

}
