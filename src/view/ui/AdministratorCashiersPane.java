package view.ui;

import java.time.LocalDate;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToolBar;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.VBox;
import model.Cashier;
import model.Sector;
import javafx.util.converter.DoubleStringConverter;

public class AdministratorCashiersPane extends VBox {
	private TextField searchCashierField = new TextField();
	
	private TableView<Cashier> cashierTable = new TableView<>();
	
	private TableColumn<Cashier, String> nameColumn = new TableColumn<>("Name");
	private TableColumn<Cashier, String> usernameColumn = new TableColumn<>("Username");
	private TableColumn<Cashier, String> passwordColumn = new TableColumn<>("Password");
	private TableColumn<Cashier, String> phoneNumberColumn = new TableColumn<>("Phone Number");
	private TableColumn<Cashier, String> emailColumn = new TableColumn<>("Email");
	private TableColumn<Cashier, LocalDate> dateOfBirthColumn = new TableColumn<>("Date of Birth");
	private TableColumn<Cashier, Double> salaryColumn = new TableColumn<>("Current Salary");
	private TableColumn<Cashier, Sector> sectorColumn = new TableColumn<>("Sector");
	private TableColumn<Cashier, Boolean> canCreateBillColumn = new TableColumn<>("Can Create Bill");
	private TableColumn<Cashier, Boolean> canAccessTodayColumn = new TableColumn<>("Can Access Today");
	
	private Button updateButton = new Button("Update Profile");
	private Button payoutButton = new Button("Payout Salaries");
	private Button deleteButton = new Button("Delete Profile");
	
	@SuppressWarnings("deprecation")
	public AdministratorCashiersPane () {
		cashierTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		cashierTable.setPlaceholder(new Label("No Cashier Data"));
		cashierTable.setEditable(true);
		
		cashierTable.getColumns().add(nameColumn);
		cashierTable.getColumns().add(usernameColumn);
		
		passwordColumn.setCellFactory(TextFieldTableCell.forTableColumn());
		cashierTable.getColumns().add(passwordColumn);
		
		phoneNumberColumn.setCellFactory(TextFieldTableCell.forTableColumn());
		cashierTable.getColumns().add(phoneNumberColumn);
		
		emailColumn.setCellFactory(TextFieldTableCell.forTableColumn());
		cashierTable.getColumns().add(emailColumn);
		
		cashierTable.getColumns().add(dateOfBirthColumn);
		
		salaryColumn.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
		cashierTable.getColumns().add(salaryColumn);
		

		cashierTable.getColumns().add(sectorColumn);
		cashierTable.getColumns().add(canCreateBillColumn);
		cashierTable.getColumns().add(canAccessTodayColumn);
		
		updateButton.getStyleClass().add("update-button");
		deleteButton.getStyleClass().add("delete-button");
		payoutButton.getStyleClass().add("payout-button");
		
	
		this.getChildren().add(new ToolBar(new Label("Search Cashier by Name: "), searchCashierField));
		this.getChildren().add(cashierTable);
		this.getChildren().add(new ToolBar(updateButton, deleteButton, payoutButton));
	}

	public TextField getSearchCashierField() {
		return searchCashierField;
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

	public TableColumn<Cashier, String> getPasswordColumn() {
		return passwordColumn;
	}

	public TableColumn<Cashier, String> getPhoneNumberColumn() {
		return phoneNumberColumn;
	}

	public TableColumn<Cashier, String> getEmailColumn() {
		return emailColumn;
	}

	public TableColumn<Cashier, LocalDate> getDateOfBirthColumn() {
		return dateOfBirthColumn;
	}

	public TableColumn<Cashier, Double> getSalaryColumn() {
		return salaryColumn;
	}

	public TableColumn<Cashier, Sector> getSectorColumn() {
		return sectorColumn;
	}

	public TableColumn<Cashier, Boolean> getCanCreateBillColumn() {
		return canCreateBillColumn;
	}

	public TableColumn<Cashier, Boolean> getCanAccessTodayColumn() {
		return canAccessTodayColumn;
	}

	public Button getUpdateButton() {
		return updateButton;
	}

	public Button getPayoutButton() {
		return payoutButton;
	}
	
	public Button getDeleteButton() {
		return deleteButton;
	}
}
