package view.ui;

import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class AdministratorChangePasswordPane extends GridPane{
	private TextField passwordField = new TextField();
	private Button changePasswordButton = new Button("Change");
	
	public AdministratorChangePasswordPane() {
		setView();
	}
	
	private void setView() {
		setAlignment(Pos.CENTER);
		setVgap(7);
		setHgap(10);
		
		addRow(0, new Label("Enter new password: "), passwordField);
		add(changePasswordButton, 0, 1, 2, 1);
		

		GridPane.setHalignment(changePasswordButton, HPos.CENTER);
		
	}
	public TextField getPasswordField() {
		return passwordField;
	}

	public Button getChangePasswordButton() {
		return changePasswordButton;
	}
	
	
}
