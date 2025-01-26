package view.ui;


import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import model.Category;
import model.Item;

public class CashierNewBillPane extends HBox{
	private VBox itemPane = new VBox();
	private TextField searchItemField = new TextField();
	
	private TableView<Item> itemTable = new TableView<>();
	
	private TableColumn<Item, String> nameColumn = new TableColumn<>("Item Name");
	private TableColumn<Item, Category> categoryColumn = new TableColumn<>("Category");
	private TableColumn<Item, Integer> quantityColumn = new TableColumn<>("Quantity");
	private TableColumn<Item, Double> priceColumn = new TableColumn<>("Price");
	private TableColumn<Item, String> noteColumn = new TableColumn<>("Note");
	
	
	private TextField selectQuantityField = new TextField();
	private Button addButton = new Button("Add to Bill");
	
	private VBox billPane = new VBox();
	private Label billNumberLabel = new Label();
	private TextField totalField = new TextField();
	
	private Button createBillButton = new Button("Save Bill");
	@SuppressWarnings("deprecation")
	public CashierNewBillPane() {
		itemTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		VBox.setVgrow(itemTable, Priority.ALWAYS); 
        VBox.setVgrow(itemPane, Priority.ALWAYS);  
        VBox.setVgrow(billPane, Priority.ALWAYS);
		itemTable.setPlaceholder(new Label("No Item Data"));
		itemTable.setEditable(true);
		
		itemTable.getColumns().add(nameColumn);
		itemTable.getColumns().add(categoryColumn);
		itemTable.getColumns().add(priceColumn);
		itemTable.getColumns().add(quantityColumn);
		itemTable.getColumns().add(noteColumn);
		
		itemPane.getChildren().add(new ToolBar(new Label("Search by Item Name:"), searchItemField));
		itemPane.getChildren().add(itemTable);
		itemPane.getChildren().add(new ToolBar(new Label("Select Quantity:"), selectQuantityField, addButton));
		
		billPane.getChildren().add(new ToolBar(new Label("Bill Number: "), billNumberLabel));
		billPane.getChildren().add(new ToolBar(new Label("Current Total: "), totalField, createBillButton));

		HBox.setHgrow(itemPane, Priority.ALWAYS);  // Allow itemPane to expand
        HBox.setHgrow(billPane, Priority.ALWAYS);
		getChildren().addAll(itemPane, billPane);
	}
	public VBox getItemPane() {
		return itemPane;
	}
	public TextField getSearchItemField() {
		return searchItemField;
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
	public TableColumn<Item, Integer> getQuantityColumn() {
		return quantityColumn;
	}
	public TableColumn<Item, Double> getPriceColumn() {
		return priceColumn;
	}
	public TableColumn<Item, String> getNoteColumn() {
		return noteColumn;
	}
	public TextField getSelectQuantityField() {
		return selectQuantityField;
	}
	public Button getAddButton() {
		return addButton;
	}
	
	public VBox getBillPane() {
		return billPane;
	}
	public Label getBillNumberLabel() {
		return billNumberLabel;
	}
	public TextField getTotalField() {
		return totalField;
	}
	public Button getCreateBillButton() {
		return createBillButton;
	}
	
	
}
