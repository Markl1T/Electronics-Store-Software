package view;

import controller.AdministratorController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import model.Administrator;
import view.ui.AdministratorNewEmployeePane;
import view.ui.AdministratorNewSectorPane;
import view.ui.AdministratorChangePasswordPane;
import view.ui.AdministratorSectorsPane;
import view.ui.AdministratorStatsPane;
import view.ui.AdministratorCashiersPane;
import view.ui.AdministratorHomePane;
import view.ui.AdministratorManagersPane;
import view.ui.ExceptionLabel;
import view.ui.TitlePane;

public class AdministratorView extends BorderPane{
	
	private Administrator administrator;
	
	private final MenuItem newEmployeeMenu = new MenuItem("New Employee");
	private final MenuItem newSectorMenu = new MenuItem("New Sector");
	private final MenuItem cashierMenu = new MenuItem("Cashiers");
	private final MenuItem managerMenu = new MenuItem("Managers");
	private final MenuItem sectorMenu = new MenuItem("Sectors");
	private final MenuItem statsMenu = new MenuItem("Stats");
	private final MenuItem logoutMenu = new MenuItem("Logout");
	private final MenuItem changePasswordMenu = new MenuItem("Change Password");
	private final MenuItem homeMenu = new MenuItem("Home");
	
	private TitlePane title = new TitlePane("Administrator");
	private VBox topPane = new VBox();
	private AdministratorNewEmployeePane newEmployeePane = new AdministratorNewEmployeePane();
	private AdministratorNewSectorPane newSectorPane = new AdministratorNewSectorPane();
	private AdministratorCashiersPane cashierPane = new AdministratorCashiersPane();
	private AdministratorManagersPane managerPane = new AdministratorManagersPane();
	private AdministratorSectorsPane sectorPane = new AdministratorSectorsPane();
	private AdministratorStatsPane statsPane = new AdministratorStatsPane();
	private AdministratorChangePasswordPane changePasswordPane = new AdministratorChangePasswordPane();
	private AdministratorHomePane homePane = new AdministratorHomePane();
	
	private ExceptionLabel exceptionLabel = new ExceptionLabel();
	
	

	public AdministratorView(Administrator administrator) {
		this.administrator = administrator;
		this.setStyle("-fx-background-color: lightblue;");
		exceptionLabel.setVisible(false);
		exceptionLabel.setPadding(new Insets(4));
		showMenu();
		showHomeView();
	}
	
	private void showMenu() {
		Menu newMenu = new Menu("New");
		newMenu.getItems().addAll(newEmployeeMenu, newSectorMenu);
		
		Menu viewsMenu = new Menu("Views");
		viewsMenu.getItems().addAll(homeMenu, cashierMenu, managerMenu, sectorMenu, statsMenu, changePasswordMenu);
		
		Menu exitMenu = new Menu("Exit");
		exitMenu.getItems().add(logoutMenu);

		topPane.getChildren().addAll(new MenuBar(newMenu, viewsMenu, exitMenu), title);
		setTop(topPane);
		setBottom(exceptionLabel);
		BorderPane.setAlignment(exceptionLabel, Pos.CENTER);
		
		AdministratorController.handleMenu(this);
	}
	
	public void showHomeView() {
		title.setTitleLbl(administrator.getName());
		this.homePane = new AdministratorHomePane();
		setCenter(homePane);
		exceptionLabel.setText("");
		AdministratorController.handleHome(this);
	}
	
	public void showNewEmployeeView() {
		title.setTitleLbl("New Employee");
		this.newEmployeePane = new AdministratorNewEmployeePane();
		setCenter(newEmployeePane);
		exceptionLabel.setText("");
		AdministratorController.handleNewEmployee(this);
	}
	
	public void showNewSectorView() {
		title.setTitleLbl("New Sector");
		this.newSectorPane = new AdministratorNewSectorPane();
		setCenter(newSectorPane);
		exceptionLabel.setText("");
		AdministratorController.handleNewSector(this);
	}
	
	public void showCashiersView() {
		title.setTitleLbl("Cashiers");
		this.cashierPane = new AdministratorCashiersPane();
		setCenter(cashierPane);
		exceptionLabel.setText("");
		AdministratorController.handleCashiers(this);
	}
	
	public void showManagersView() {
		title.setTitleLbl("Managers");
		this.managerPane = new AdministratorManagersPane();
		setCenter(managerPane);
		exceptionLabel.setText("");
		AdministratorController.handleManagers(this);
	}
	
	public void showSectorsView() {
		title.setTitleLbl("Sectors");
		this.sectorPane = new AdministratorSectorsPane();
		setCenter(sectorPane);
		exceptionLabel.setText("");
		AdministratorController.handleSectors(this);
	}
	
	public void showStatsView() {
		title.setTitleLbl("Statistics");
		this.statsPane = new AdministratorStatsPane();
		setCenter(statsPane);
		exceptionLabel.setText("");
		AdministratorController.handleStats(this);
	}
	
	public void showChangePasswordView() {
		title.setTitleLbl("Change Password");
		this.changePasswordPane = new AdministratorChangePasswordPane();
		setCenter(changePasswordPane);
		exceptionLabel.setText("");
		AdministratorController.handleProfile(this);
	}
	
	public Administrator getAdministrator() {
		return administrator;
	}
	
	public MenuItem getNewEmployeeMenu() {
		return newEmployeeMenu;
	}

	public MenuItem getNewSectorMenu() {
		return newSectorMenu;
	}

	public MenuItem getCashierMenu() {
		return cashierMenu;
	}
	
	public MenuItem getManagerMenu() {
		return managerMenu;
	}
	
	public MenuItem getSectorMenu() {
		return sectorMenu;
	}

	public MenuItem getStatsMenu() {
		return statsMenu;
	}
	
	public MenuItem getChangePasswordMenu() {
		return changePasswordMenu;
	}
	public MenuItem getLogoutMenu() {
		return logoutMenu;
	}

	public AdministratorNewEmployeePane getNewEmployeePane() {
		return newEmployeePane;
	}

	public AdministratorNewSectorPane getNewSectorPane() {
		return newSectorPane;
	}
	
	public AdministratorStatsPane getStatsPane() {
		return statsPane;
	}
	
	public AdministratorCashiersPane getCashierPane() {
		return cashierPane;
	}
	
	public AdministratorManagersPane getManagerPane() {
		return managerPane;
	}
	public AdministratorSectorsPane getSectorPane() {
		return sectorPane;
	}

	public AdministratorChangePasswordPane getChangePasswordPane() {
		return changePasswordPane;
	}

	public ExceptionLabel getExceptionLabel() {
		return exceptionLabel;
	}

	public MenuItem getHomeMenu() {
		return homeMenu;
	}

	public AdministratorHomePane getHomePane() {
		return homePane;
	}

	public VBox getTopPane() {
		return topPane;
	}

	public void setTopPane(VBox topPane) {
		this.topPane = topPane;
	}

	public TitlePane getTitle() {
		return title;
	}
	
	
}
