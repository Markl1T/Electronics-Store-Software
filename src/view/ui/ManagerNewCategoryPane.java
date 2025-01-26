package view.ui;

import javafx.scene.layout.GridPane;

import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.Sector;
public class ManagerNewCategoryPane extends GridPane {
	
	private final TextField nameField = new TextField();
	private final ComboBox<Sector> sectorComboBox = new ComboBox<>();
	private final TextField minQuantityField = new TextField();
	private final Button createButton = new Button("Create");
	
	public ManagerNewCategoryPane() {
		setView();
	}
	
	private void setView() {
		setAlignment(Pos.CENTER);
		setVgap(7);
		setHgap(10);
		
		addRow(0, new Label("Category Name: "), nameField);
		addRow(1, new Label("Sector: "), sectorComboBox);
		addRow(2, new Label("Minimum Quantity: "), minQuantityField);
		createButton.setStyle("-fx-font-size: 18px");
		add(createButton, 0, 3, 2, 1);
		GridPane.setHalignment(createButton, HPos.CENTER);
	}
	
	public TextField getNameField() {
		return nameField;
	}
	
	public ComboBox<Sector> getSectorComboBox(){
		return sectorComboBox;
	}
	
	public TextField getMinQuantityField() {
		return minQuantityField;
	}
	
	public Button getCreateButton() {
		return createButton;
	}
	
	
}

