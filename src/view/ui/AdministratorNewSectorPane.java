package view.ui;

import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import model.Manager;

public class AdministratorNewSectorPane extends GridPane{
	private final TextField nameField = new TextField();
	private final ComboBox<Manager> managerComboBox = new ComboBox<>();
	private final Button createButton = new Button("Create");
	
	public AdministratorNewSectorPane() {
		setView();
	}
	
	private void setView() {
		setAlignment(Pos.CENTER);
		setVgap(7);
		setHgap(10);
		
		addRow(0, new Label("Sector Name: "), nameField);
		addRow(1, new Label("Manager: "), managerComboBox);
		
		createButton.setStyle("-fx-font-size: 18px");
		add(createButton, 0, 2, 2, 1);
		GridPane.setHalignment(createButton, HPos.CENTER);
		
	}

	public TextField getNameField() {
		return nameField;
	}

	public ComboBox<Manager> getManagerComboBox() {
		return managerComboBox;
	}

	public Button getCreateButton() {
		return createButton;
	}
	
}
