package com.shahriar.UniversityRegistration.Controllers;

import com.shahriar.UniversityRegistration.Entities.CourseDTO;
import com.shahriar.UniversityRegistration.Repos.CourseRegRepo;
import com.shahriar.UniversityRegistration.Services.LaunchCourseService;
import com.shahriar.UniversityRegistration.Services.RegCoursesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RegCoursesController {

    @Autowired
    private RegCoursesService regCoursesService;

    @PostMapping("/public/getRegCourses")
    public void cr() {

        regCoursesService.getRegByAnyStudents("1804003");
    }
}
