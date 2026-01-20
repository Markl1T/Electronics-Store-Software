package test.unit_testing;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.User;
import model.InvalidDateException;
import model.InvalidEmailException;
import model.InvalidSalaryException;

class UserTest {

    private User user;

    private static class TestUser extends User {
        protected TestUser(String username, String password, String name,
                           String phoneNumber, String email,
                           LocalDate dateOfBirth, double salary)
                throws InvalidSalaryException, InvalidEmailException, InvalidDateException {
            super(username, password, name, phoneNumber, email, dateOfBirth, salary);
        }
    }

    @BeforeEach
    void setup() throws InvalidSalaryException, InvalidEmailException, InvalidDateException {
        user = new TestUser(
                "testuser",
                "password123",
                "Test User",
                "123456789",
                "test@example.com",
                LocalDate.of(2000, 1, 1),
                1000
        );
    }

    // Constructor tests

    @Test
    void testConstructorValid() {
        assertEquals("testuser", user.getUsername());
        assertEquals("password123", user.getPassword());
        assertEquals("Test User", user.getName());
        assertEquals("123456789", user.getPhoneNumber());
        assertEquals("test@example.com", user.getEmail());
        assertEquals(LocalDate.of(2000, 1, 1), user.getDateOfBirth());
        assertEquals(1000, user.getCurrentSalary());
    }

    @Test
    void testConstructorInvalidEmail() {
        assertThrows(InvalidEmailException.class, () ->
            new TestUser(
                "user",
                "pass",
                "Name",
                "123",
                "invalidEmail",
                LocalDate.of(2000, 1, 1),
                100
            )
        );
    }

    @Test
    void testConstructorInvalidDate() {
        assertThrows(InvalidDateException.class, () ->
            new TestUser(
                "user",
                "pass",
                "Name",
                "123",
                "test@mail.com",
                LocalDate.now().plusDays(1),
                100
            )
        );
    }

    @Test
    void testConstructorInvalidSalary() {
        assertThrows(InvalidSalaryException.class, () ->
            new TestUser(
                "user",
                "pass",
                "Name",
                "123",
                "test@mail.com",
                LocalDate.of(2000, 1, 1),
                -10
            )
        );
    }

    // Email tests

    @Test
    void testValidEmailSimple() {
        assertDoesNotThrow(() -> user.setEmail("user@test.com"));
    }

    @Test
    void testValidEmailSubdomain() {
        assertDoesNotThrow(() -> user.setEmail("user@mail.test.com"));
    }

    @Test
    void testValidEmailShort() {
        assertDoesNotThrow(() -> user.setEmail("a@b.co"));
    }

    @Test
    void testInvalidEmailNoAtSymbol() {
        assertThrows(InvalidEmailException.class,
                () -> user.setEmail("usertest.com"));
    }

    @Test
    void testInvalidEmailMultipleAtSymbols() {
        assertThrows(InvalidEmailException.class,
                () -> user.setEmail("user@@test.com"));
    }

    @Test
    void testInvalidEmailNoDomain() {
        assertThrows(InvalidEmailException.class,
                () -> user.setEmail("user@test"));
    }

    @Test
    void testInvalidEmailNoUsername() {
        assertThrows(InvalidEmailException.class,
                () -> user.setEmail("@test.com"));
    }

    @Test
    void testInvalidEmailContainsSpaces() {
        assertThrows(InvalidEmailException.class,
                () -> user.setEmail("user test@test.com"));
    }

    @Test
    void testInvalidEmailNull() {
        assertThrows(InvalidEmailException.class,
                () -> user.setEmail(null));
    }

    // Date of birth tests

    @Test
    void testSetDateOfBirthValid() throws InvalidDateException {
        LocalDate date = LocalDate.of(1999, 5, 5);
        user.setDateOfBirth(date);
        assertEquals(date, user.getDateOfBirth());
    }

    @Test
    void testSetDateOfBirthInvalid() {
        assertThrows(InvalidDateException.class, () ->
            user.setDateOfBirth(LocalDate.now().plusDays(1))
        );
    }
    
    @Test
    void testDateOfBirthExactly18Valid() {
        LocalDate date = LocalDate.now().minusYears(18);
        assertDoesNotThrow(() -> user.setDateOfBirth(date));
    }

    @Test
    void testDateOfBirthUnder18Invalid() {
        LocalDate date = LocalDate.now().minusYears(18).plusDays(1);
        assertThrows(InvalidDateException.class,
                () -> user.setDateOfBirth(date));
    }
    
    @Test
    void testInvalidDateOfBirthNull() {
        assertThrows(InvalidDateException.class,
                () -> user.setDateOfBirth(null));
    }


    // Salary tests

    @Test
    void testSetSalaryValid() throws InvalidSalaryException {
        user.setCurrentSalary(2000);
        assertEquals(2000, user.getCurrentSalary());
    }

    @Test
    void testSetSalaryInvalid() {
        assertThrows(InvalidSalaryException.class, () ->
            user.setCurrentSalary(-1)
        );
    }

    // Simple setters

    @Test
    void testSetPassword() {
        user.setPassword("newpass");
        assertEquals("newpass", user.getPassword());
    }

    @Test
    void testSetPhoneNumber() {
        user.setPhoneNumber("999999");
        assertEquals("999999", user.getPhoneNumber());
    }
}
