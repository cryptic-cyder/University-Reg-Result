package com.shahriar.UniversityRegistration.Controllers;

import com.shahriar.UniversityRegistration.Entities.Student;
import com.shahriar.UniversityRegistration.Repos.AccountRepo;
import com.shahriar.UniversityRegistration.Services.AccountService;
import com.shahriar.UniversityRegistration.Services.DefaultValidationStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountController {

    @Autowired
    private AccountService accountService;
    @Autowired
    private AccountRepo accountRepo;

    @PostMapping("/public/reg")
    public ResponseEntity<?> createAcc(@RequestParam("name") String name,
                          @RequestParam("studentGmail") String studentGmail,
                          @RequestParam("studentID") String studentID,
                          @RequestParam("relatedBatch") String relatedBatch,
                          @RequestParam("password") String password
    ) {
        try {

            Student student = accountService.createAccount(name,
                    studentGmail,
                    studentID,
                    relatedBatch,
                    password,
                    new DefaultValidationStrategy(accountRepo)
            );

            return new ResponseEntity<>(student, HttpStatus.OK);

        }
        catch (IllegalArgumentException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
        catch (Exception ex) {
            return new ResponseEntity<>("Error creating student account", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
