package view.ui;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToolBar;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.DoubleStringConverter;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.Category;
import model.Item;
import model.Sector;
import model.Supplier;

public class ManagerItemsPane extends VBox {
	private TextField searchItemField = new TextField();
	private TextField searchSupplierField = new TextField();
	private TableView<Item> itemTable = new TableView<>();
	private TableColumn<Item, String> nameColumn = new TableColumn<>("Name");
	private TableColumn<Item, Category> categoryColumn = new TableColumn<>("Category");
	private TableColumn<Item, Sector> sectorColumn = new TableColumn<>("Sector");
	private TableColumn<Item, Supplier> supplierColumn = new TableColumn<>("Supplier");
	private TableColumn<Item, Integer> quantityColumn = new TableColumn<>("Quantity");
	private TableColumn<Item, Double> sellingPriceColumn = new TableColumn<>("Selling Price");

	private Button updateButton = new Button("Update Item");

	@SuppressWarnings("deprecation")
	public ManagerItemsPane() {
		itemTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		itemTable.setPlaceholder(new Label("No Item Data"));
		itemTable.setEditable(true);

		itemTable.getColumns().add(nameColumn);
		itemTable.getColumns().add(categoryColumn);
		itemTable.getColumns().add(sectorColumn);
		itemTable.getColumns().add(supplierColumn);
		itemTable.getColumns().add(quantityColumn);
		sellingPriceColumn.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
		itemTable.getColumns().add(sellingPriceColumn);
		HBox searchFields = new HBox(4);
		searchFields.getChildren().addAll(
               new Label("Search Item by Name: "), searchItemField,
               new Label("Search Item by Supplier: "), searchSupplierField);

		updateButton.getStyleClass().add("update-button");
		
		ToolBar searchToolBar = new ToolBar(searchFields);
		this.getChildren().add(searchToolBar);
		this.getChildren().add(itemTable); 
        this.getChildren().add(new ToolBar(updateButton));
	}

	public TextField getSearchItemField() {
		return searchItemField;
	}

	public TextField getSearchSupplierField() {
		return searchSupplierField;
	}

	public TableView<Item> getItemTable() {
		return itemTable;
	}

	public TableColumn<Item, String> getNameColumn() {
		return nameColumn;
	}

	public TableColumn<Item, Category> getCategoryColumn() {
		return categoryColumn;
	}

	public TableColumn<Item, Sector> getSectorColumn() {
		return sectorColumn;
	}

	public TableColumn<Item, Supplier> getSupplierColumn() {
		return supplierColumn;
	}

	public TableColumn<Item, Integer> getQuantityColumn() {
		return quantityColumn;
	}

	public TableColumn<Item, Double> getSellingPriceColumn() {
		return sellingPriceColumn;
	}

	public Button getUpdateButton() {
		return updateButton;
	}

}