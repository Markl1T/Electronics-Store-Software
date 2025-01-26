package view;


import controller.CashierController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import model.Cashier;
import view.ui.CashierHomePane;
import view.ui.CashierNewBillPane;
import view.ui.CashierTodayPane;
import view.ui.ExceptionLabel;


public class CashierView extends BorderPane{
	private final MenuItem newBillMenu = new MenuItem("New Bill");
	private final MenuItem todayMenu = new MenuItem("Today");
	private final MenuItem homeMenu = new MenuItem("Home");
	private final MenuItem logoutMenu = new MenuItem("Logout");
	private Cashier cashier;
	
	private CashierNewBillPane newBillPane = new CashierNewBillPane();
	private CashierTodayPane todayPane = new CashierTodayPane();
	private CashierHomePane homePane = new CashierHomePane();
	
	private ExceptionLabel exceptionLabel = new ExceptionLabel();
	
	public CashierView(Cashier cashier) {
		this.cashier = cashier;
		this.setStyle("-fx-background-color: lightblue;");
		showMenu();
		exceptionLabel.setVisible(false);
		exceptionLabel.setPadding(new Insets(4));
		setBottom(exceptionLabel);
		BorderPane.setAlignment(exceptionLabel, Pos.CENTER);
		
		showHomeView();
	}
	
	private void showMenu() {
		Menu newMenu = new Menu("New");
		newMenu.getItems().add(newBillMenu);
		
		Menu viewsMenu = new Menu("Views");
		viewsMenu.getItems().addAll(homeMenu, todayMenu);
		
		Menu exitMenu = new Menu("Exit");
		exitMenu.getItems().add(logoutMenu);
		
		setTop(new MenuBar(newMenu, viewsMenu, exitMenu));
		CashierController.handleMenu(this);
	}
	
	public void showHomeView() {
		this.homePane = new CashierHomePane();
		setCenter(homePane);
		exceptionLabel.setText("");
		CashierController.handleHome(this);
	}
	
	public void showTodayView() {
		this.todayPane = new CashierTodayPane();
		setCenter(todayPane);
		exceptionLabel.setText("");
		CashierController.handleToday(this);
	}
	
	public void showNewBillView() {
		this.newBillPane = new CashierNewBillPane();
		setCenter(newBillPane);
		exceptionLabel.setText("");
		CashierController.handleNewBill(this);		
	}

	public MenuItem getNewBillMenu() {
		return newBillMenu;
	}

	public MenuItem getTodayMenu() {
		return todayMenu;
	}

	public MenuItem getLogoutMenu() {
		return logoutMenu;
	}

	public Cashier getCashier() {
		return cashier;
	}

	public CashierNewBillPane getNewBillPane() {
		return newBillPane;
	}

	public CashierTodayPane getTodayPane() {
		return todayPane;
	}

	public ExceptionLabel getExceptionLabel() {
		return exceptionLabel;
	}

	public MenuItem getHomeMenu() {
		return homeMenu;
	}

	public CashierHomePane getHomePane() {
		return homePane;
	}
	
	
}
