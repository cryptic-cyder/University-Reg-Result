package com.shahriar.UniversityRegistration.Services;

import com.shahriar.UniversityRegistration.Entities.Student;
import com.shahriar.UniversityRegistration.Repos.AccountRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


class AccountServiceTest {

    @Mock
    private AccountRepo accountRepo;

    @InjectMocks
    private AccountService accountService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // Initialize the mocks
    }


    @Test
    public void shouldTestAccountCreation() {

        Student mockResult = new Student(1L,
                "Shahriar",
                "shahriarbadhon778@gmail.com",
                "1804020",
                "2018",
                "1234",
                false,
                false
        );

        when(accountRepo.save(mockResult)).thenReturn(mockResult);

        Student codeResult = accountService.createAccount(mockResult, new DefaultValidationStrategy(accountRepo));

        assertNotNull(codeResult);
        assertEquals(1L, codeResult.getId());
        assertEquals("Shahriar", codeResult.getName());
        assertEquals("shahriarbadhon778@gmail.com", codeResult.getStudentGmail());
        assertEquals("1804020", codeResult.getStudentID());
        assertEquals("2018", codeResult.getRelatedBatch());
        assertEquals("1234", codeResult.getPassword());

        verify(accountRepo, times(1)).save(codeResult);
    }

    @Test
    public void testCreateAccount_NullStudent() {

        Student mockResult = null;

        // Assert that IllegalArgumentException is thrown when null is passed
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> accountService.createAccount(mockResult, new DefaultValidationStrategy(accountRepo)));

        // Verify the exception message
        assertEquals("AccountController can't be null", exception.getMessage());

        // Verify that the save method was never called
        verify(accountRepo, never()).save(any(Student.class));
    }

    @Test
    public void testCreateAccount_verify_null_empty_existing_email() {

        Student mockResult = new Student(1L,
                "Shahriar",
                "shahriarbadhon778@gmail.com",
                "1804020",
                "2018",
                "1234",
                false,
                false
        );
        when(accountRepo.findByStudentGmail(mockResult.getStudentGmail())).thenReturn(true); // Assuming this method exists

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> accountService.createAccount(mockResult, new DefaultValidationStrategy(accountRepo)));

        assertEquals("Email exists already", exception.getMessage());

        verify(accountRepo, never()).save(any(Student.class));
    }

}