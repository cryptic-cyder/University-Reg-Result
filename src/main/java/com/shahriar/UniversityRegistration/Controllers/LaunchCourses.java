package com.shahriar.UniversityRegistration.Controllers;

import com.shahriar.UniversityRegistration.Entities.CourseDTO;
import com.shahriar.UniversityRegistration.Services.LaunchCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class LaunchCourses {

    @Autowired
    private LaunchCourseService launchCourseService;

    @PostMapping("/public/launchCourse")
    public ResponseEntity<?> createAcc(@RequestBody List<CourseDTO> courses) {

        launchCourseService.addCourse(courses);
        return ResponseEntity.ok("Successfully added the courses");

    }
}
