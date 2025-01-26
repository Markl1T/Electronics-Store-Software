package view;

import controller.LoginController;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import view.ui.ExceptionLabel;

public class LoginView extends StackPane {
	private GridPane innerPane = new GridPane();
	private TextField usernameField = new TextField();
	private PasswordField passwordField = new PasswordField();
	private Button loginButton = new Button("Log In");
	private Label electronicsStoreSoftware = new Label("ELECTRONICS STORE SOFTWARE");

	private ExceptionLabel exceptionLabel = new ExceptionLabel();

	public LoginView() {
		setView();
		LoginController.handle(this);
	}

	private void setView() {
		setStyle("-fx-background-image: url('/images/electronicstorelogin.jpeg');" 
				+ "-fx-background-size: cover;"
				+ "-fx-background-position: center;");
		
		electronicsStoreSoftware.setFont(Font.font("Roboto", 24));//
		electronicsStoreSoftware.setAlignment(Pos.CENTER);
		electronicsStoreSoftware.getStyleClass().add("electronics-store");

		innerPane.setStyle("-fx-background-color: white; -fx-border-color: steelblue; -fx-border-width: 3;");
		innerPane.setAlignment(Pos.CENTER);
		innerPane.setPadding(new Insets(8));
		innerPane.setHgap(10);
		innerPane.setVgap(10);

		innerPane.setPrefSize(300, 200);
		innerPane.setMinSize(USE_PREF_SIZE, USE_PREF_SIZE);
		innerPane.setMaxSize(400, 250);

		innerPane.add(new Label("Username: "), 0, 0);
		innerPane.add(usernameField, 1, 0);
		innerPane.add(new Label("Password: "), 0, 1);
		innerPane.add(passwordField, 1, 1);
		innerPane.add(loginButton, 0, 2, 2, 1);
		loginButton.setStyle("-fx-font-size: 18px");
		GridPane.setHalignment(loginButton, HPos.CENTER);

		innerPane.add(exceptionLabel, 0, 3, 2, 1);
		GridPane.setHalignment(exceptionLabel, HPos.CENTER);

		VBox vbox = new VBox(20, electronicsStoreSoftware, innerPane);
		vbox.setAlignment(Pos.CENTER);

		getChildren().add(vbox);
		
		StackPane.setMargin(innerPane, new Insets(50));
	}

	public GridPane getInnerPane() {
		return innerPane;
	}

	public TextField getUsernameField() {
		return usernameField;
	}

	public PasswordField getPasswordField() {
		return passwordField;
	}

	public Button getLoginButton() {
		return loginButton;
	}

	public ExceptionLabel getExceptionLabel() {
		return exceptionLabel;
	}

}
