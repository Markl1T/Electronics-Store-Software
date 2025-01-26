package view.ui;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.TilePane;

public class AdministratorHomePane extends BorderPane {

	private final Button newEmployeeBtn = new HomeButton("New Employee");
	private final Button newSectorBtn = new HomeButton("New Sector");
	private final Button cashierBtn = new HomeButton("Cashiers");
	private final Button managerBtn = new HomeButton("Managers");
	private final Button sectorBtn = new HomeButton("Sectors");
	private final Button statsBtn = new HomeButton("Stats");
	private final Button changePasswordBtn = new HomeButton("Change Password");
	private final Button logoutBtn = new HomeButton("Log Out");
	private final Label welcomeLbl = new Label();

	public AdministratorHomePane() {
		TilePane homePane = new TilePane();
		homePane.getChildren().addAll(newEmployeeBtn, newSectorBtn, cashierBtn, managerBtn, sectorBtn, statsBtn,
				changePasswordBtn, logoutBtn);
		homePane.setPadding(new Insets(25));
		homePane.setHgap(50);
		homePane.setVgap(50);
		welcomeLbl.setPadding(new Insets(20));
		welcomeLbl.setStyle("-fx-font-weight: bold;" + "-fx-font-size: 40px");
		setTop(welcomeLbl);
		setCenter(homePane);
	}

	public Button getNewEmployeeBtn() {
		return newEmployeeBtn;
	}

	public Button getNewSectorBtn() {
		return newSectorBtn;
	}

	public Button getCashierBtn() {
		return cashierBtn;
	}

	public Button getManagerBtn() {
		return managerBtn;
	}

	public Button getSectorBtn() {
		return sectorBtn;
	}

	public Button getStatsBtn() {
		return statsBtn;
	}

	public Button getChangePasswordBtn() {
		return changePasswordBtn;
	}

	public Button getLogoutBtn() {
		return logoutBtn;
	}

	public Label getWelcomeLbl() {
		return welcomeLbl;
	}

}
