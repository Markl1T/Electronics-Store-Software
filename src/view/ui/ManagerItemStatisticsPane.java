package view.ui;

import java.time.LocalDate;

import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.VBox;
import model.Item;

public class ManagerItemStatisticsPane extends VBox{
	
	private TextField searchItemField = new TextField();
	private DateSelectionPane dateSelection = new DateSelectionPane();
	private TableView<Item> itemTable = new TableView<>();
	private TableColumn<Item, String> nameColumn = new TableColumn<>("Name");
	private TableColumn<Item, LocalDate> startDateColumn = new TableColumn<>("Start Date");
	private TableColumn<Item, LocalDate> endDateColumn = new TableColumn<>("End Date");
	private TableColumn<Item, Integer> numberOfItemsSoldColumn = new TableColumn<>("Number Of Items Sold");
	private TableColumn<Item, Integer> numberOfItemsPurchasedColumn = new TableColumn<>("Number Of Items Purchased");
	
	@SuppressWarnings("deprecation")
	public ManagerItemStatisticsPane() {
    	itemTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		itemTable.setPlaceholder(new Label("No Item Data"));
		itemTable.getColumns().add(nameColumn);
		itemTable.getColumns().add(startDateColumn);
		itemTable.getColumns().add(endDateColumn);
		itemTable.getColumns().add(numberOfItemsSoldColumn);
		itemTable.getColumns().add(numberOfItemsPurchasedColumn);
		this.getChildren().add(new ToolBar(new Label("Search Item: "), searchItemField, dateSelection));
		this.getChildren().add(itemTable);
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

	public TableColumn<Item, Integer> getNumberOfItemsSoldColumn() {
		return numberOfItemsSoldColumn;
	}

	public TableColumn<Item, Integer> getNumberOfItemsPurchasedColumn() {
		return numberOfItemsPurchasedColumn;
	}

	public DateSelectionPane getDateSelection() {
		return dateSelection;
	}

	public TableColumn<Item, LocalDate> getStartDateColumn() {
		return startDateColumn;
	}

	public TableColumn<Item, LocalDate> getEndDateColumn() {
		return endDateColumn;
	}
	
}
