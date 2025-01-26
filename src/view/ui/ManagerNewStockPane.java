package view.ui;

import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import model.Item;

public class ManagerNewStockPane extends GridPane {
	private final ComboBox<Item> itemComboBox = new ComboBox<>();
   private final TextField stockQuantityField = new TextField();
   private final TextField purchasePriceField = new TextField();
   private final Button createButton = new Button("Add Stock");
  
   public ManagerNewStockPane() {
       setView();
   }
  
   private void setView() {
       setAlignment(Pos.CENTER);
       setVgap(7);
       setHgap(10);
      
       addRow(0, new Label("Item: "), itemComboBox);
       addRow(1, new Label("Stock Quantity: "), stockQuantityField);         
       addRow(2, new Label("Purchase Price: "), purchasePriceField);
       createButton.setStyle("-fx-font-size: 18px");
       add(createButton, 0, 4, 2, 1);
       GridPane.setHalignment(createButton, HPos.CENTER);
   }
   public ComboBox<Item> getItemComboBox() {
       return itemComboBox;
   }
   public TextField getStockQuantityField() {
       return stockQuantityField;
   }
   public TextField getPurchasePriceField() {
       return purchasePriceField;
   }
   public Button getCreateButton() {
       return createButton;
   }
}
