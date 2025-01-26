package view.ui;

import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.VBox;
import model.Bill;

public class CashierTodayPane extends VBox{
	private final TextField todayTotalField = new TextField();
	
	private TableView<Bill> billTable = new TableView<>();
	
	private TableColumn<Bill, String> billNumberColumn = new TableColumn<>("Bill Number");
	private TableColumn<Bill, String> dateTimeColumn = new TableColumn<>("Date and time");
	private TableColumn<Bill, Integer> quantityColumn = new TableColumn<>("Number of items sold");
	private TableColumn<Bill, Double> priceColumn = new TableColumn<>("Total price");
	
	
	@SuppressWarnings("deprecation")
	public CashierTodayPane() {
    	billTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

		billTable.setPlaceholder(new Label("No Bill Data"));
		
		billTable.getColumns().add(billNumberColumn);
		billTable.getColumns().add(dateTimeColumn);
		billTable.getColumns().add(quantityColumn);
		billTable.getColumns().add(priceColumn);
		
		getChildren().add(new ToolBar(new Label("Today's Total: "), todayTotalField));
		getChildren().add(billTable);
	}
	
	public TextField getTodayTotalField() {
		return todayTotalField;
	}

	public TableView<Bill> getBillTable() {
		return billTable;
	}

	public TableColumn<Bill, String> getBillNumberColumn() {
		return billNumberColumn;
	}

	public TableColumn<Bill, String> getDateTimeColumn() {
		return dateTimeColumn;
	}

	public TableColumn<Bill, Integer> getQuantityColumn() {
		return quantityColumn;
	}

	public TableColumn<Bill, Double> getPriceColumn() {
		return priceColumn;
	}

	
}
	
