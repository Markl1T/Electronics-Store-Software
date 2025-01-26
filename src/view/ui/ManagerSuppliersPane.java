package view.ui;

import javafx.scene.layout.VBox;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToolBar;
import javafx.scene.control.cell.TextFieldTableCell;
import model.Supplier;

public class ManagerSuppliersPane extends VBox {
    private TextField searchSupplierField = new TextField();

    private TableView<Supplier> supplierTable = new TableView<>();

    private TableColumn<Supplier, String> nameColumn = new TableColumn<>("Name");
    private TableColumn<Supplier, String> phoneNumberColumn = new TableColumn<>("Phone Number");
    private TableColumn<Supplier, String> emailColumn = new TableColumn<>("Email");

    private Button updateButton = new Button("Update Profile");
    private Button deleteButton = new Button("Delete Profile");

    @SuppressWarnings("deprecation")
	public ManagerSuppliersPane() {
    	supplierTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        supplierTable.setPlaceholder(new Label("No Supplier Data"));
        supplierTable.setEditable(true);

        phoneNumberColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        emailColumn.setCellFactory(TextFieldTableCell.forTableColumn());

        supplierTable.getColumns().add(nameColumn);
        supplierTable.getColumns().add(phoneNumberColumn);
        supplierTable.getColumns().add(emailColumn);
        
        updateButton.getStyleClass().add("update-button");
		deleteButton.getStyleClass().add("delete-button");
        
        this.getChildren().add(new ToolBar(new Label("Search Supplier by Name: "), searchSupplierField));
        this.getChildren().add(supplierTable);
        this.getChildren().add(new ToolBar(updateButton, deleteButton));
    }

    public TextField getSearchSupplierField() {
        return searchSupplierField;
    }

    public TableView<Supplier> getSupplierTable() {
        return supplierTable;
    }

    public TableColumn<Supplier, String> getNameColumn() {
        return nameColumn;
    }

    public TableColumn<Supplier, String> getPhoneNumberColumn() {
        return phoneNumberColumn;
    }

    public TableColumn<Supplier, String> getEmailColumn() {
        return emailColumn;
    }
    
    public Button getUpdateButton() {
        return updateButton;
    }

    public Button getDeleteButton() {
        return deleteButton;
    }
}
