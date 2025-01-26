package main;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import view.LoginView;


public class Main extends Application{
	private static Scene scene;
	private static Stage stage;
	
	@Override
	public void start(Stage primaryStage) {
		stage = primaryStage;
		scene = new Scene(new LoginView(), 1150, 600);
		displayOnScene(new LoginView());
		scene.getStylesheets().add(getClass().getResource("/view/ui/styles.css").toExternalForm());
		stage.setScene(scene);
		stage.setTitle("Electronics Store Software");
		stage.show();
	}
	
	public static void displayOnScene(Pane pane) {
		scene.setRoot(pane);
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
