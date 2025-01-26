package controller;

import java.util.ArrayList;

import dao.DataAccessException;
import dao.FileHandler;
import javafx.animation.PauseTransition;
import javafx.util.Duration;
import main.Main;
import model.Administrator;
import model.Cashier;
import model.Manager;
import model.User;
import view.AdministratorView;
import view.CashierView;
import view.LoginView;
import view.ManagerView;

public class LoginController {

	public static void handle(LoginView pane) {
		pane.getLoginButton().setOnAction(e -> handleLoginButton(pane));
	}

	private static void handleLoginButton(LoginView pane) {
		try {
			FileHandler.seedAdministrator();

			String username = pane.getUsernameField().getText();
			String password = pane.getPasswordField().getText();

			User user = logIn(username, password);

			if (user instanceof Administrator)
				Main.displayOnScene(new AdministratorView((Administrator) user));
			else if (user instanceof Manager)
				Main.displayOnScene(new ManagerView((Manager) user));
			else if (user instanceof Cashier)
				Main.displayOnScene(new CashierView((Cashier) user));
		} catch (Exception ex) {
			pane.getExceptionLabel().setText(ex.getMessage());
			pane.getExceptionLabel().setVisible(true);

			PauseTransition pause = new PauseTransition(Duration.seconds(5));

			pause.setOnFinished(event -> pane.getExceptionLabel().setVisible(false));
			pause.play();
		}
	}

	// Helper method for login

	private static User logIn(String username, String password) throws DataAccessException {
		ArrayList<User> userList = new ArrayList<>();
		ArrayList<Manager> managerList = FileHandler.readFile(FileHandler.MANAGER);
		ArrayList<Cashier> cashierList = FileHandler.readFile(FileHandler.CASHIER);
		ArrayList<Administrator> administratorList = FileHandler.readFile(FileHandler.ADMINISTRATOR);

		userList.addAll(managerList);
		userList.addAll(cashierList);
		userList.addAll(administratorList);

		for (User user : userList) {
			if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
				return user;
			}
		}

		throw new DataAccessException("User Not Found");
	}
}
