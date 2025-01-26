package view.ui;

import javafx.scene.layout.GridPane;

import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.Category; 
import model.Sector;   
import model.Supplier;

public class ManagerNewItemPane extends GridPane{
	private final TextField nameField = new TextField();
   private final ComboBox<Category> categoryComboBox = new ComboBox<>();
   private final ComboBox<Sector> sectorComboBox = new ComboBox<>();
   private final ComboBox<Supplier> supplierComboBox = new ComboBox<>();
   private final TextField sellingPriceField = new TextField();
   private final Button createButton = new Button("Add Item");
  
   public ManagerNewItemPane() {
       setView();
   }
  
   private void setView() {
   	 setAlignment(Pos.CENTER);
        setVgap(7);
        setHgap(10);
        addRow(0, new Label("Item Name: "), nameField);      
        addRow(1, new Label("Sector: "), sectorComboBox);
        addRow(2, new Label("Category: "), categoryComboBox);      
        addRow(3, new Label("Supplier: "), supplierComboBox);
        addRow(4, new Label("Selling Price: "), sellingPriceField);
        createButton.setStyle("-fx-font-size: 18px");
        add(createButton, 0, 6, 2, 1);
		GridPane.setHalignment(createButton, HPos.CENTER);
   }
  
   public TextField getNameField() {
       return nameField;
   }
   public ComboBox<Category> getCategoryComboBox() {
       return categoryComboBox;
   }
   public ComboBox<Sector> getSectorComboBox() {
       return sectorComboBox;
   }
   public ComboBox<Supplier> getSupplierComboBox() {
       return supplierComboBox;
   }

   public TextField getSellingPriceField() {
   	return sellingPriceField;
   }
  
   public Button getCreateButton() {
   	return createButton;
   }
}


