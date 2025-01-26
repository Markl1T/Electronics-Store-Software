package view.ui;

import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.TilePane;
import javafx.geometry.Insets;
import javafx.scene.control.Button;

public class CashierHomePane extends BorderPane {
	private final Button newBillBtn = new HomeButton("New Bill");
	private final Button todayBtn = new HomeButton("Today");
	private final Button logoutBtn = new HomeButton("Log Out");
	private final Label welcomeLbl = new Label();
	
	public CashierHomePane() {
		TilePane homePane = new TilePane();
		homePane.getChildren().addAll(newBillBtn, todayBtn, logoutBtn);
		homePane.setPadding(new Insets(25));
		homePane.setHgap(50);
		homePane.setVgap(50);
		welcomeLbl.setPadding(new Insets(20));
		welcomeLbl.setStyle("-fx-font-weight: bold;" + "-fx-font-size: 40px");
		setTop(welcomeLbl);
		setCenter(homePane);
	}

	public Button getNewBillBtn() {
		return newBillBtn;
	}

	public Button getTodayBtn() {
		return todayBtn;
	}

	public Button getLogoutBtn() {
		return logoutBtn;
	}

	public Label getWelcomeLbl() {
		return welcomeLbl;
	}
	
}
