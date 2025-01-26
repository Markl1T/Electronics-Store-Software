package view.ui;
import java.time.LocalDate;
import javafx.util.converter.DoubleStringConverter;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToolBar;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.VBox;
import model.Manager;
public class AdministratorManagersPane extends VBox{
	
	private TextField searchManagerField = new TextField();
	private TableView<Manager> managerTable = new TableView<>();
	
	private TableColumn<Manager, String> nameColumn = new TableColumn<>("Name");
	private TableColumn<Manager, String> usernameColumn = new TableColumn<>("Username");
	private TableColumn<Manager, String> passwordColumn = new TableColumn<>("Password");
	private TableColumn<Manager, String> phoneNumberColumn = new TableColumn<>("Phone Number");
	private TableColumn<Manager, String> emailColumn = new TableColumn<>("Email");
	private TableColumn<Manager, LocalDate> dateOfBirthColumn = new TableColumn<>("Date Of Birth");
	private TableColumn<Manager, Double> salaryColumn = new TableColumn<>("Salary");
	private TableColumn<Manager, Boolean> canAccessStockColumn = new TableColumn<>("Can Access Stock");
	private TableColumn<Manager, Boolean> canAccessItemsColumn = new TableColumn<>("Can Access Items");
	private TableColumn<Manager, Boolean> canAccessCategoriesColumn = new TableColumn<>("Can Access Categories");
	private TableColumn<Manager, Boolean> canAccessCashiersColumn = new TableColumn<>("Can Access Cashiers");
	private TableColumn<Manager, Boolean> canAccessSuppliersColumn = new TableColumn<>("Can Access Suppliers");
	
	private Button updateButton = new Button("Update Profile");
	private Button payoutButton = new Button("Payout Salaries");
	private Button deleteButton = new Button("Delete Profile");
	
	@SuppressWarnings("deprecation")
	public AdministratorManagersPane() {
		managerTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		managerTable.setPlaceholder(new Label("No Manager Data"));
		managerTable.setEditable(true);
		
		managerTable.getColumns().add(nameColumn);
		managerTable.getColumns().add(usernameColumn);
		
		passwordColumn.setCellFactory(TextFieldTableCell.forTableColumn());
		managerTable.getColumns().add(passwordColumn);
		
		phoneNumberColumn.setCellFactory(TextFieldTableCell.forTableColumn());
		managerTable.getColumns().add(phoneNumberColumn);
		
		emailColumn.setCellFactory(TextFieldTableCell.forTableColumn());
		managerTable.getColumns().add(emailColumn);
		
		managerTable.getColumns().add(dateOfBirthColumn);
		
		salaryColumn.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
		managerTable.getColumns().add(salaryColumn);

		managerTable.getColumns().add(canAccessStockColumn);
		managerTable.getColumns().add(canAccessItemsColumn);
		managerTable.getColumns().add(canAccessCategoriesColumn);
		managerTable.getColumns().add(canAccessCashiersColumn);
		managerTable.getColumns().add(canAccessSuppliersColumn);
		
		updateButton.getStyleClass().add("update-button");
		deleteButton.getStyleClass().add("delete-button");
		payoutButton.getStyleClass().add("payout-button");
		
		this.getChildren().add(new ToolBar(new Label("Search Manager by Name: "), searchManagerField));
		this.getChildren().add(managerTable);
		this.getChildren().add(new ToolBar(updateButton, deleteButton, payoutButton));
	}
	public TextField getSearchManagerField() {
		return searchManagerField;
	}
	public TableView<Manager> getManagerTable() {
		return managerTable;
	}
	public TableColumn<Manager, String> getNameColumn() {
		return nameColumn;
	}
	public TableColumn<Manager, String> getUsernameColumn() {
		return usernameColumn;
	}
	public TableColumn<Manager, String> getPasswordColumn() {
		return passwordColumn;
	}
	public TableColumn<Manager, String> getPhoneNumberColumn() {
		return phoneNumberColumn;
	}
	public TableColumn<Manager, String> getEmailColumn() {
		return emailColumn;
	}
	public TableColumn<Manager, LocalDate> getDateOfBirthColumn() {
		return dateOfBirthColumn;
	}
	public TableColumn<Manager, Double> getSalaryColumn() {
		return salaryColumn;
	}
	public TableColumn<Manager, Boolean> getCanAccessStockColumn() {
		return canAccessStockColumn;
	}
	public TableColumn<Manager, Boolean> getCanAccessItemsColumn() {
		return canAccessItemsColumn;
	}
	public TableColumn<Manager, Boolean> getCanAccessCategoriesColumn() {
		return canAccessCategoriesColumn;
	}
	public TableColumn<Manager, Boolean> getCanAccessCashiersColumn() {
		return canAccessCashiersColumn;
	}
	public TableColumn<Manager, Boolean> getCanAccessSuppliersColumn() {
		return canAccessSuppliersColumn;
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



