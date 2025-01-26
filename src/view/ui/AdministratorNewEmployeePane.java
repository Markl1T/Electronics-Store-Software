package view.ui;

import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import model.Sector;

public class AdministratorNewEmployeePane extends GridPane{
	private final ComboBox<String> roleComboBox = new ComboBox<>();
	private final TextField usernameField = new TextField();
	private final TextField passwordField = new TextField();
	private final TextField nameField = new TextField();
	private final TextField phoneNumberField = new TextField();
	private final TextField emailField = new TextField();
	private final DatePicker birthdatePicker = new DatePicker();
	private final TextField salaryField = new TextField();
	private final ComboBox<Sector> sectorComboBox = new ComboBox<>();
	private final Button registerButton = new Button("Register");
	
	public AdministratorNewEmployeePane() {
		setView();
	}
	
	private void setView() {
		setAlignment(Pos.CENTER);
		setVgap(7);
		setHgap(10);
		
		roleComboBox.getItems().addAll("Cashier", "Manager");
		
		addRow(0, new Label("Select Role: "), roleComboBox);
		addRow(1, new Label("Username: "), usernameField);
		addRow(2, new Label("Password: "), passwordField);
		addRow(3, new Label("Name: "), nameField);
		addRow(4, new Label("Phone Number: "), phoneNumberField);
		addRow(5, new Label("Email: "), emailField);
		addRow(6, new Label("Birthdate"), birthdatePicker);
		addRow(7, new Label("Salary: "), salaryField);
		
		sectorComboBox.setValue(null);
		addRow(8, new Label("Sector (cashiers): "), sectorComboBox);
		
		registerButton.setStyle("-fx-font-size: 18px");
		add(registerButton, 0, 9, 2, 1);
		GridPane.setHalignment(registerButton, HPos.CENTER);
	}
	
	public ComboBox<String> getRoleComboBox() {
		return roleComboBox;
	}
	public TextField getUsernameField() {
		return usernameField;
	}
	public TextField getPasswordField() {
		return passwordField;
	}
	public TextField getNameField() {
		return nameField;
	}
	public TextField getPhoneNumberField() {
		return phoneNumberField;
	}
	public TextField getEmailField() {
		return emailField;
	}
	public DatePicker getBirthdatePicker() {
		return birthdatePicker;
	}
	public TextField getSalaryField() {
		return salaryField;
	}
	public ComboBox<Sector> getSectorComboBox() {
		return sectorComboBox;
	}
	public Button getRegisterButton() {
		return registerButton;
	}
	
}
