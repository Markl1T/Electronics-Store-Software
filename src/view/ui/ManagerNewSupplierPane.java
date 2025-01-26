package view.ui;

import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
public class ManagerNewSupplierPane extends GridPane{
	
	private final TextField nameField = new TextField();
	private final TextField phoneNumberField = new TextField();
	private final TextField emailField = new TextField();
	private final Button addButton = new Button ("Add Supplier");
	
	
	public ManagerNewSupplierPane() {
		setView();
	}
	
	private void setView() {
		setAlignment(Pos.CENTER);
		setVgap(7);
		setHgap(10);
		
		addRow(0, new Label("Supplier Name: "), nameField);
		addRow(1, new Label("Phone Number: "), phoneNumberField);
		addRow(2, new Label("Email: "), emailField);
		addButton.setStyle("-fx-font-size: 18px");
		add(addButton, 0, 3, 2, 1);
		GridPane.setHalignment(addButton, HPos.CENTER);
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
   public Button getAddButton() {
       return addButton;
   }
}

