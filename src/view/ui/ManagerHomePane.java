package view.ui;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.TilePane;

public class ManagerHomePane extends BorderPane {

	private final Button newStockBtn = new HomeButton("New Stock");
	private final Button newItemBtn = new HomeButton("New Item");
	private final Button newCategoryBtn = new HomeButton("New Category");
	private final Button newSupplierBtn = new HomeButton("New Supplier");
	private final Button cashierStatisticsBtn = new HomeButton("Cashier Statistics");
	private final Button categoriesBtn = new HomeButton("Categories");
	private final Button itemsBtn = new HomeButton("Items");
	private final Button itemStatisticsBtn = new HomeButton("Item Statistics");
	private final Button suppliersBtn = new HomeButton("Suppliers");
	private final Button logoutBtn = new HomeButton("Log Out");
	private final Label welcomeLbl = new Label();

	public ManagerHomePane() {
		TilePane homePane = new TilePane();
		homePane.getChildren().addAll(newStockBtn, newItemBtn, newCategoryBtn, newSupplierBtn, cashierStatisticsBtn,
				categoriesBtn, itemsBtn, itemStatisticsBtn, suppliersBtn, logoutBtn);
		homePane.setPadding(new Insets(25));
		homePane.setHgap(50);
		homePane.setVgap(50);
		welcomeLbl.setPadding(new Insets(20));
		welcomeLbl.setStyle("-fx-font-weight: bold;"
				+ "-fx-font-size: 40px");
		setTop(welcomeLbl);
		setCenter(homePane);
	}

	public Button getNewStockBtn() {
		return newStockBtn;
	}

	public Button getNewItemBtn() {
		return newItemBtn;
	}

	public Button getNewCategoryBtn() {
		return newCategoryBtn;
	}

	public Button getNewSupplierBtn() {
		return newSupplierBtn;
	}

	public Button getCashierStatisticsBtn() {
		return cashierStatisticsBtn;
	}

	public Button getCategoriesBtn() {
		return categoriesBtn;
	}

	public Button getItemsBtn() {
		return itemsBtn;
	}

	public Button getItemStatisticsBtn() {
		return itemStatisticsBtn;
	}

	public Button getSuppliersBtn() {
		return suppliersBtn;
	}

	public Button getLogoutBtn() {
		return logoutBtn;
	}

	public Label getWelcomeLbl() {
		return welcomeLbl;
	}

	
}
