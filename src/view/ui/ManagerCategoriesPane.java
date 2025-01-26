package view.ui;

import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.VBox;
import model.Category;
import model.Sector;

public class ManagerCategoriesPane extends VBox {

	private TextField searchCategoryField = new TextField();
	private TableView<Category> categoryTable = new TableView<>();
	private TableColumn<Category, String> nameColumn = new TableColumn<>("Name");
	private TableColumn<Category, Sector> sectorColumn = new TableColumn<>("Sector");
	private TableColumn<Category, Integer> minQuantityColumn = new TableColumn<>("Minimum Quantity");
	private TableColumn<Category, Integer> actualQuantityColumn = new TableColumn<>("Actual Quantity");
	private TableColumn<Category, String> noteColumn = new TableColumn<>("Note");

	@SuppressWarnings("deprecation")
	public ManagerCategoriesPane() {
    	categoryTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		categoryTable.setPlaceholder(new Label("No Category Data"));
		categoryTable.setEditable(true);
		categoryTable.getColumns().add(nameColumn);
		categoryTable.getColumns().add(sectorColumn);
		categoryTable.getColumns().add(minQuantityColumn);
		categoryTable.getColumns().add(actualQuantityColumn);
		categoryTable.getColumns().add(noteColumn);
		this.getChildren().add(new ToolBar(new Label("Search Category by Name: "), searchCategoryField));
		this.getChildren().add(categoryTable);
	}

	public TextField getSearchCategoryField() {
		return searchCategoryField;
	}

	public TableView<Category> getCategoryTable() {
		return categoryTable;
	}

	public TableColumn<Category, String> getNameColumn() {
		return nameColumn;
	}

	public TableColumn<Category, Sector> getSectorColumn() {
		return sectorColumn;
	}

	public TableColumn<Category, Integer> getMinQuantityColumn() {
		return minQuantityColumn;
	}

	public TableColumn<Category, Integer> getActualQuantityColumn() {
		return actualQuantityColumn;
	}

	public TableColumn<Category, String> getNoteColumn() {
		return noteColumn;
	}

}