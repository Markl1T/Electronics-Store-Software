package view.ui;

import java.time.LocalDate;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import model.Cashier;

public class ManagerCashierStatisticsPane extends BorderPane {

	private final GridPane statisticsPane = new GridPane();
	private final ToolBar selectionBar = new ToolBar();
    private final TextField cashierTF = new TextField();
    private final DateSelectionPane dateSelection = new DateSelectionPane();
    private final HBox selectCashierPane = new HBox(new Label("Select Cashier: "), cashierTF);
    
    private TableView<Cashier> cashierTable = new TableView<>();
	private TableColumn<Cashier, String> nameColumn = new TableColumn<>("Name");
	private TableColumn<Cashier, String> usernameColumn = new TableColumn<>("Username");
	private TableColumn<Cashier, LocalDate> startDateColumn = new TableColumn<>("Start Date");
	private TableColumn<Cashier, LocalDate> endDateColumn = new TableColumn<>("End Date");
	private TableColumn<Cashier, Integer> numberOfBillsColumn = new TableColumn<>("Number of Bills");
	private TableColumn<Cashier, Integer> numberOfItemsSoldColumn = new TableColumn<>("Number of Items Sold");
	private TableColumn<Cashier, Double> totalRevenueGeneratedColumn = new TableColumn<>("Total Revenue Generated");
	
    public ManagerCashierStatisticsPane() {
        setView();
    }
    
    @SuppressWarnings("deprecation")
	private void setView() {
    	cashierTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    	selectionBar.getItems().addAll(selectCashierPane, dateSelection);
    	setTop(selectionBar);
    	dateSelection.getEnterButton().setText("Show Statistics");
    	selectCashierPane.setPadding(new Insets(4));
	    selectCashierPane.setAlignment(Pos.CENTER);
    	statisticsPane.setPadding(new Insets(4));
        statisticsPane.setAlignment(Pos.CENTER);;
        statisticsPane.setHgap(4);
        statisticsPane.setVgap(4);
        
        cashierTable.setPlaceholder(new Label("No Cashier Data"));
		
		cashierTable.getColumns().add(nameColumn);
		cashierTable.getColumns().add(usernameColumn);
		cashierTable.getColumns().add(startDateColumn);
		cashierTable.getColumns().add(endDateColumn);
		cashierTable.getColumns().add(numberOfBillsColumn);
		cashierTable.getColumns().add(numberOfItemsSoldColumn);
		cashierTable.getColumns().add(totalRevenueGeneratedColumn);
		
        setCenter(cashierTable);
    }

    public TextField getCashierTF() {
        return cashierTF;
    }

    public DateSelectionPane getDateSelectionPane() {
        return dateSelection;
    }

	public GridPane getStatisticsPane() {
		return statisticsPane;
	}

	public ToolBar getSelectionBar() {
		return selectionBar;
	}

	public DateSelectionPane getDateSelection() {
		return dateSelection;
	}

	public TableView<Cashier> getCashierTable() {
		return cashierTable;
	}

	public TableColumn<Cashier, String> getNameColumn() {
		return nameColumn;
	}

	public TableColumn<Cashier, String> getUsernameColumn() {
		return usernameColumn;
	}

	public TableColumn<Cashier, LocalDate> getStartDateColumn() {
		return startDateColumn;
	}

	public void setStartDateColumn(TableColumn<Cashier, LocalDate> startDateColumn) {
		this.startDateColumn = startDateColumn;
	}

	public TableColumn<Cashier, LocalDate> getEndDateColumn() {
		return endDateColumn;
	}

	public void setEndDateColumn(TableColumn<Cashier, LocalDate> endDateColumn) {
		this.endDateColumn = endDateColumn;
	}

	public TableColumn<Cashier, Integer> getNumberOfBillsColumn() {
		return numberOfBillsColumn;
	}

	public void setNumberOfBillsColumn(TableColumn<Cashier, Integer> numberOfBillsColumn) {
		this.numberOfBillsColumn = numberOfBillsColumn;
	}

	public TableColumn<Cashier, Integer> getNumberOfItemsSoldColumn() {
		return numberOfItemsSoldColumn;
	}

	public void setNumberOfItemsSoldColumn(TableColumn<Cashier, Integer> numberOfItemsSoldColumn) {
		this.numberOfItemsSoldColumn = numberOfItemsSoldColumn;
	}

	public TableColumn<Cashier, Double> getTotalRevenueGeneratedColumn() {
		return totalRevenueGeneratedColumn;
	}

	public void setTotalRevenueGeneratedColumn(TableColumn<Cashier, Double> totalRevenueGeneratedColumn) {
		this.totalRevenueGeneratedColumn = totalRevenueGeneratedColumn;
	}

	public HBox getSelectCashierPane() {
		return selectCashierPane;
	}
    
}
