package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.testfx.framework.junit5.ApplicationTest;

import controller.AdministratorController;
import dao.FileHandler;
import javafx.stage.Stage;
import model.Administrator;
import model.Cashier;
import view.AdministratorView;

public class AdministratorFxTest extends ApplicationTest {

    protected AdministratorView view;
    protected Administrator administrator;

    @Override
    public void start(Stage stage) {
        administrator = mock(Administrator.class);
        view = new AdministratorView(administrator);
        stage.setScene(view.getScene());
        stage.show();
    }
    
    @Test
    void clickingCashierMenu() {
        AdministratorView spyView = spy(view);

        AdministratorController.handleMenu(spyView);

        clickOn(spyView.getCashierMenu());

        verify(spyView).showCashiersView();
    }
	
	@Test
    void registerWithEmptyFields_shouldShowErrorMessage() {
        AdministratorController.handleNewEmployee(view);

        clickOn(view.getNewEmployeePane().getRegisterButton());

        assertTrue(view.getExceptionLabel().isVisible());
        assertEquals("All fields must be filled.", view.getExceptionLabel().getText());
    }
	
    @Test
    void registerCashier_shouldCallRegisterEmployeeAndNavigate() throws Exception {

        try (MockedStatic<FileHandler> fh = mockStatic(FileHandler.class)) {

            fh.when(() -> FileHandler.readFile(FileHandler.SECTOR))
              .thenReturn(new ArrayList<>());

            AdministratorController.handleNewEmployee(view);

            // Fill fields
            view.getNewEmployeePane().getRoleComboBox().setValue("Cashier");
            view.getNewEmployeePane().getUsernameField().setText("user");
            view.getNewEmployeePane().getPasswordField().setText("pass");
            view.getNewEmployeePane().getNameField().setText("John");
            view.getNewEmployeePane().getPhoneNumberField().setText("123");
            view.getNewEmployeePane().getEmailField().setText("a@b.com");
            view.getNewEmployeePane().getBirthdatePicker().setValue(LocalDate.of(2000,1,1));
            view.getNewEmployeePane().getSalaryField().setText("5000");

            clickOn(view.getNewEmployeePane().getRegisterButton());

            fh.verify(() ->
                FileHandler.registerEmployee(
                    eq("Cashier"), any(), any(), any(), any(),
                    any(), any(), anyDouble(), any()
                )
            );
        }
    }
    
    @Test
    void typingInSearchCashier_shouldFilterTable() throws Exception {
        try (MockedStatic<FileHandler> fh = mockStatic(FileHandler.class)) {

            Cashier c1 = mock(Cashier.class);
            when(c1.getName()).thenReturn("Alice");

            Cashier c2 = mock(Cashier.class);
            when(c2.getName()).thenReturn("Bob");

            fh.when(() -> FileHandler.readFile(FileHandler.CASHIER))
              .thenReturn(new ArrayList<>(List.of(c1, c2)));

            AdministratorController.handleCashiers(view);

            clickOn(view.getCashierPane().getSearchCashierField()).write("Ali");

            assertEquals(1,
                view.getCashierPane().getCashierTable().getItems().size()
            );
        }
    }
    
    @Test
    void statsWithInvalidDates_shouldShowError() {
        AdministratorController.handleStats(view);

        view.getStatsPane().getStatsDateSelector()
            .getStartDatePicker().setValue(LocalDate.now());

        view.getStatsPane().getStatsDateSelector()
            .getEndDatePicker().setValue(LocalDate.now().minusDays(1));

        clickOn(view.getStatsPane().getStatsDateSelector().getEnterButton());

        assertTrue(view.getExceptionLabel().isVisible());
        assertEquals("Start Date must be before End Date.",
                     view.getExceptionLabel().getText());
    }
}
