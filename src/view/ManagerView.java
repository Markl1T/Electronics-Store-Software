package view;

import controller.ManagerController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import model.Manager;
import view.ui.ExceptionLabel;
import view.ui.ManagerCashierStatisticsPane;
import view.ui.ManagerCategoriesPane;
import view.ui.ManagerHomePane;
import view.ui.ManagerItemStatisticsPane;
import view.ui.ManagerItemsPane;
import view.ui.ManagerNewCategoryPane;
import view.ui.ManagerNewItemPane;
import view.ui.ManagerNewStockPane;
import view.ui.ManagerNewSupplierPane;
import view.ui.ManagerSuppliersPane;
import view.ui.TitlePane;

public class ManagerView extends BorderPane {

	private Manager manager;

	private final MenuItem newStockMenu = new MenuItem("New Stock");
	private final MenuItem newItemMenu = new MenuItem("New Item");
	private final MenuItem newCategoryMenu = new MenuItem("New Category");
	private final MenuItem newSupplierMenu = new MenuItem("New Supplier");
	private final MenuItem homeMenu = new MenuItem("Home");
	private final MenuItem cashierStatisticsMenu = new MenuItem("Cashier Statistics");
	private final MenuItem categoriesMenu = new MenuItem("Categories");
	private final MenuItem itemsMenu = new MenuItem("Items");
	private final MenuItem itemStatisticsMenu = new MenuItem("Item Statistics");
	private final MenuItem suppliersMenu = new MenuItem("Suppliers");
	private final MenuItem logoutMenu = new MenuItem("Log Out");
	private final Menu newMenu = new Menu("New");
	private final Menu viewsMenu = new Menu("Views");
	private final Menu exitMenu = new Menu("Exit");

	private TitlePane title = new TitlePane("Manager");
	private VBox topPane = new VBox();
	private ManagerNewCategoryPane newCategoryPane = new ManagerNewCategoryPane();
	private ManagerNewItemPane newItemPane = new ManagerNewItemPane();
	private ManagerNewStockPane newStockPane = new ManagerNewStockPane();
	private ManagerNewSupplierPane newSupplierPane = new ManagerNewSupplierPane();
	private ManagerHomePane homePane = new ManagerHomePane();

	private ManagerCashierStatisticsPane cashierStatisticsPane = new ManagerCashierStatisticsPane();
	private ManagerItemStatisticsPane itemStatisticsPane = new ManagerItemStatisticsPane();
	private ManagerSuppliersPane supplierPane = new ManagerSuppliersPane();
	private ManagerCategoriesPane categoryPane = new ManagerCategoriesPane();
	private ManagerItemsPane itemPane = new ManagerItemsPane();

	private ExceptionLabel exceptionLabel = new ExceptionLabel();

	public ManagerItemsPane getItemPane() {
		return itemPane;
	}

	public ManagerView(Manager manager) {
		this.manager = manager;
		this.setStyle("-fx-background-color: lightblue;");
		showMenu(manager);
		showHomeView();
		exceptionLabel.setVisible(false);
		exceptionLabel.setPadding(new Insets(4));
		setBottom(exceptionLabel);
		BorderPane.setAlignment(exceptionLabel, Pos.CENTER);
	}

	public void showMenu(Manager manager) {
		newMenu.getItems().addAll(newStockMenu, newItemMenu, newCategoryMenu, newSupplierMenu);
		viewsMenu.getItems().addAll(homeMenu, categoriesMenu, itemsMenu, suppliersMenu, itemStatisticsMenu,
				cashierStatisticsMenu);
		exitMenu.getItems().add(logoutMenu);
		topPane.getChildren().addAll(new MenuBar(newMenu, viewsMenu, exitMenu), title);
		setTop(topPane);
		ManagerController.controlMenu(this);
	}

	public void showHomeView() {
		title.setTitleLbl(manager.getName());
		this.homePane = new ManagerHomePane();
		setCenter(homePane);
		exceptionLabel.setText("");
		ManagerController.handleHome(this);
	}
	
	public void showNewCategoryView() {
		title.setTitleLbl("New Category");
		this.newCategoryPane = new ManagerNewCategoryPane();
		setCenter(newCategoryPane);
		exceptionLabel.setText("");
		ManagerController.handleNewCategory(this);
	}

	public void showNewItemView() {
		title.setTitleLbl("New Item");
		this.newItemPane = new ManagerNewItemPane();
		setCenter(newItemPane);
		exceptionLabel.setText("");
		ManagerController.handleNewItem(this);
	}

	public void showNewStockView() {
		title.setTitleLbl("New Stock");
		this.newStockPane = new ManagerNewStockPane();
		setCenter(newStockPane);
		exceptionLabel.setText("");
		ManagerController.handleNewStock(this);
	}

	public void showNewSupplierView() {
		title.setTitleLbl("New Supplier");
		this.newSupplierPane = new ManagerNewSupplierPane();
		setCenter(newSupplierPane);
		exceptionLabel.setText("");
		ManagerController.handleNewSupplier(this);

	}

	public void showCashierStatisticsView() {
		title.setTitleLbl("Cashier Statistics");
		this.cashierStatisticsPane = new ManagerCashierStatisticsPane();
		setCenter(cashierStatisticsPane);
		exceptionLabel.setText("");
		ManagerController.handleCashierStatistics(this);
	}

	public void showItemStatisticsView() {
		title.setTitleLbl("Item Statistics");
		this.itemStatisticsPane = new ManagerItemStatisticsPane();
		setCenter(itemStatisticsPane);
		exceptionLabel.setText("");
		ManagerController.handleItemStatistics(this);
	}

	public void showCategoriesView() {
		title.setTitleLbl("Categories");
		this.categoryPane = new ManagerCategoriesPane();
		setCenter(categoryPane);
		exceptionLabel.setText("");
		ManagerController.handleCategories(this);
	}

	public void showItemsView() {
		title.setTitleLbl("Items");
		this.itemPane = new ManagerItemsPane();
		setCenter(itemPane);
		exceptionLabel.setText("");
		ManagerController.handleItems(this);
	}

	public void showSuppliersView() {
		title.setTitleLbl("Suppliers");
		this.supplierPane = new ManagerSuppliersPane();
		setCenter(supplierPane);
		exceptionLabel.setText("");
		ManagerController.handleSuppliers(this);
	}

	public ManagerNewCategoryPane getNewCategoryPane() {
		return newCategoryPane;
	}

	public ManagerNewItemPane getNewItemPane() {
		return newItemPane;
	}

	public ManagerNewStockPane getNewStockPane() {
		return newStockPane;
	}

	public ManagerNewSupplierPane getNewSupplierPane() {
		return newSupplierPane;
	}

	public ManagerCashierStatisticsPane getCashierStatisticsPane() {
		return cashierStatisticsPane;
	}

	public ManagerItemStatisticsPane getItemStatisticsPane() {
		return itemStatisticsPane;
	}

	public ManagerSuppliersPane getSupplierPane() {
		return supplierPane;
	}

	public ManagerCategoriesPane getCategoryPane() {
		return categoryPane;
	}

	public MenuItem getCashierStatisticsMenu() {
		return cashierStatisticsMenu;
	}

	public MenuItem getNewStockMenu() {
		return newStockMenu;
	}

	public MenuItem getNewItemMenu() {
		return newItemMenu;
	}

	public MenuItem getNewCategoryMenu() {
		return newCategoryMenu;
	}

	public MenuItem getNewSupplierMenu() {
		return newSupplierMenu;
	}

	public Manager getManager() {
		return manager;
	}

	public MenuItem getCategoriesMenu() {
		return categoriesMenu;
	}

	public MenuItem getItemsMenu() {
		return itemsMenu;
	}

	public MenuItem getItemStatisticsMenu() {
		return itemStatisticsMenu;
	}

	public MenuItem getSuppliersMenu() {
		return suppliersMenu;
	}

	public MenuItem getLogoutMenu() {
		return logoutMenu;
	}

	public ExceptionLabel getExceptionLabel() {
		return exceptionLabel;
	}

	public MenuItem getHomeMenu() {
		return homeMenu;
	}
	
	public ManagerHomePane getHomePane() {
		return homePane;
	}

	public TitlePane getTitle() {
		return title;
	}

	public VBox getTopPane() {
		return topPane;
	}

}
