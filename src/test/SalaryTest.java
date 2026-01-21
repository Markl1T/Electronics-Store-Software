package test;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;

import model.InvalidSalaryException;
import model.Salary;
import model.User;

class SalaryTest {

    @Test
    void testSalaryConstructorValid() throws Exception {
        // Arrange
        User mockUser = mock(User.class);
        double amount = 2500.0;

        // Act
        Salary salary = new Salary(mockUser, amount);

        // Assert
        assertEquals(mockUser, salary.getEmployee());
        assertEquals(amount, salary.getAmount());
        assertEquals(LocalDate.now(), salary.getStartDate());
    }

    @Test
    void testSalaryConstructorZeroAmountBoundary() throws Exception {
        // Arrange
        User mockUser = mock(User.class);
        double amount = 0.0;

        // Act
        Salary salary = new Salary(mockUser, amount);

        // Assert
        assertEquals(mockUser, salary.getEmployee());
        assertEquals(0.0, salary.getAmount());
        assertEquals(LocalDate.now(), salary.getStartDate());
    }

    @Test
    void testSalaryConstructorNegativeAmountThrowsException() {
        // Arrange
        User mockUser = mock(User.class);
        double amount = -100.0;

        // Act & Assert
        InvalidSalaryException exception = assertThrows(
                InvalidSalaryException.class,
                () -> new Salary(mockUser, amount)
        );

        assertEquals("Salary cannot be less than 0", exception.getMessage());
    }

    @Test
    void testSalaryStartDateIsToday() throws Exception {
        // Arrange
        User mockUser = mock(User.class);

        // Act
        Salary salary = new Salary(mockUser, 1500.0);

        // Assert
        assertEquals(LocalDate.now(), salary.getStartDate());
    }
}
