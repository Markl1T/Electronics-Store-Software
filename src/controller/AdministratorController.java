package controller;

import main.Main;
import view.AdministratorView;
import view.LoginView;
import model.Administrator;
import model.Cashier;
import model.Manager;
import model.Salary;
import model.Sector;
import view.ui.AdministratorCashiersPane;
import view.ui.AdministratorManagersPane;
import view.ui.AdministratorNewEmployeePane;
import view.ui.AdministratorNewSectorPane;
import view.ui.AdministratorSectorsPane;
import view.ui.AdministratorStatsPane;
import java.time.LocalDate;
import java.util.ArrayList;
import dao.FileHandler;
import dao.StatisticsDAO;
import javafx.animation.PauseTransition;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.cell.ChoiceBoxTableCell;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.util.Duration;

public class AdministratorController {

	public static void handleMenu(AdministratorView pane) {
		pane.getNewEmployeeMenu().setOnAction(e -> pane.showNewEmployeeView());
		pane.getNewSectorMenu().setOnAction(e -> pane.showNewSectorView());
		pane.getHomeMenu().setOnAction(e -> pane.showHomeView());
		pane.getCashierMenu().setOnAction(e -> pane.showCashiersView());
		pane.getManagerMenu().setOnAction(e -> pane.showManagersView());
		pane.getSectorMenu().setOnAction(e -> pane.showSectorsView());
		pane.getStatsMenu().setOnAction(e -> pane.showStatsView());
		pane.getChangePasswordMenu().setOnAction(e -> pane.showChangePasswordView());
		pane.getLogoutMenu().setOnAction(e -> Main.displayOnScene(new LoginView()));
	}
	
	public static void handleHome(AdministratorView pane) {
		pane.getHomePane().getWelcomeLbl().setText("Welcome, Administrator");
		pane.getHomePane().getNewEmployeeBtn().setOnAction(e -> pane.showNewEmployeeView());
        pane.getHomePane().getNewSectorBtn().setOnAction(e -> pane.showNewSectorView());
        pane.getHomePane().getCashierBtn().setOnAction(e -> pane.showCashiersView());
        pane.getHomePane().getManagerBtn().setOnAction(e -> pane.showManagersView());
        pane.getHomePane().getSectorBtn().setOnAction(e -> pane.showSectorsView());
        pane.getHomePane().getStatsBtn().setOnAction(e -> pane.showStatsView());
        pane.getHomePane().getChangePasswordBtn().setOnAction(e -> pane.showChangePasswordView());
        pane.getHomePane().getLogoutBtn().setOnAction(e -> Main.displayOnScene(new LoginView()));
	}

	public static void handleNewEmployee(AdministratorView pane) {
		AdministratorNewEmployeePane newEmployeePane = pane.getNewEmployeePane();
		try {
			ArrayList<Sector> sectorList = FileHandler.readFile(FileHandler.SECTOR);
			newEmployeePane.getSectorComboBox().getItems().addAll(sectorList);
		} catch (Exception ex) {
			showException(ex.getMessage(), pane);
		}

		newEmployeePane.getRoleComboBox().setOnAction(e -> newEmployeePane.getSectorComboBox()
				.setDisable(newEmployeePane.getRoleComboBox().getValue().equals("Manager")));

		newEmployeePane.getRegisterButton().setOnAction(e -> {
			try {
				String role = newEmployeePane.getRoleComboBox().getValue();
				String username = newEmployeePane.getUsernameField().getText();
				String password = newEmployeePane.getPasswordField().getText();
				String name = newEmployeePane.getNameField().getText();
				String phoneNumber = newEmployeePane.getPhoneNumberField().getText();
				String email = newEmployeePane.getEmailField().getText();
				LocalDate birthdate = newEmployeePane.getBirthdatePicker().getValue();
				double salary = Double.parseDouble(newEmployeePane.getSalaryField().getText());
				Sector sector = newEmployeePane.getSectorComboBox().getValue();
				FileHandler.registerEmployee(role, username, password, name, phoneNumber, email, birthdate, salary,
						sector);
				if (role.equals("Cashier")) {
					pane.showCashiersView();
				} else {
					pane.showManagersView();
				}
			} catch (Exception ex) {
				showException(ex.getMessage(), pane);
			}
		});
	}

	public static void handleNewSector(AdministratorView pane) {
		AdministratorNewSectorPane newSectorPane = pane.getNewSectorPane();
		try {
			ArrayList<Manager> managerList = FileHandler.readFile(FileHandler.MANAGER);
			newSectorPane.getManagerComboBox().getItems().addAll(managerList);
		} catch (Exception ex) {
			showException(ex.getMessage(), pane);
		}

		newSectorPane.getCreateButton().setOnAction(e -> {
			try {
				String name = newSectorPane.getNameField().getText();
				Manager manager = newSectorPane.getManagerComboBox().getValue();
				Sector sector = new Sector(name, manager);
				FileHandler.appendFile(FileHandler.SECTOR, sector);
				pane.showSectorsView();
			} catch (Exception ex) {
				showException(ex.getMessage(), pane);
			}
		});
	}

	public static void handleCashiers(AdministratorView pane) {
		AdministratorCashiersPane cashierPane = pane.getCashierPane();

		try {
			// Fill cashier table with data
			ArrayList<Cashier> cashierList = FileHandler.readFile(FileHandler.CASHIER);
			ObservableList<Cashier> cashierData = FXCollections.observableArrayList(cashierList);
			cashierPane.getCashierTable().setItems(cashierData);

			cashierPane.getNameColumn()
					.setCellValueFactory(cashierEvent -> new SimpleStringProperty(cashierEvent.getValue().getName()));
			cashierPane.getUsernameColumn().setCellValueFactory(
					cashierEvent -> new SimpleStringProperty(cashierEvent.getValue().getUsername()));
			cashierPane.getPasswordColumn().setCellValueFactory(
					cashierEvent -> new SimpleStringProperty(cashierEvent.getValue().getPassword()));
			cashierPane.getPhoneNumberColumn().setCellValueFactory(
					cashierEvent -> new SimpleStringProperty(cashierEvent.getValue().getPhoneNumber()));
			cashierPane.getEmailColumn()
					.setCellValueFactory(cashierEvent -> new SimpleStringProperty(cashierEvent.getValue().getEmail()));
			cashierPane.getDateOfBirthColumn().setCellValueFactory(
					cashierEvent -> new SimpleObjectProperty<LocalDate>(cashierEvent.getValue().getDateOfBirth()));
			cashierPane.getSalaryColumn().setCellValueFactory(
					cashierEvent -> new SimpleDoubleProperty(cashierEvent.getValue().getCurrentSalary()).asObject());

			ArrayList<Sector> sectorList = FileHandler.readFile(FileHandler.SECTOR);
			ObservableList<Sector> sectorData = FXCollections.observableArrayList(sectorList);
			cashierPane.getSectorColumn().setCellFactory(ComboBoxTableCell.forTableColumn(sectorData));
			cashierPane.getSectorColumn().setCellValueFactory(
					cashierEvent -> new SimpleObjectProperty<Sector>(cashierEvent.getValue().getSector()));

			ObservableList<Boolean> booleanData = FXCollections.observableArrayList(true, false);
			cashierPane.getCanCreateBillColumn().setCellValueFactory(
					cashierEvent -> new SimpleBooleanProperty(cashierEvent.getValue().getCanCreateBill()));
			cashierPane.getCanCreateBillColumn().setCellFactory(ChoiceBoxTableCell.forTableColumn(booleanData));

			cashierPane.getCanAccessTodayColumn().setCellValueFactory(
					cashierEvent -> new SimpleBooleanProperty(cashierEvent.getValue().getCanAccessToday()));
			cashierPane.getCanAccessTodayColumn().setCellFactory(ChoiceBoxTableCell.forTableColumn(booleanData));

			// Edit configurations for cashier table columns
			cashierPane.getPasswordColumn().setOnEditCommit(e -> e.getRowValue().setPassword(e.getNewValue()));
			cashierPane.getPhoneNumberColumn().setOnEditCommit(e -> e.getRowValue().setPhoneNumber(e.getNewValue()));
			cashierPane.getEmailColumn().setOnEditCommit(e -> {
				try {
					e.getRowValue().setEmail(e.getNewValue());
				} catch (Exception ex) {
					showException(ex.getMessage(), pane);
				}
			});
			cashierPane.getSalaryColumn().setOnEditCommit(e -> {
				try {
					e.getRowValue().setCurrentSalary(e.getNewValue());
				} catch (Exception ex) {
					showException(ex.getMessage(), pane);
				}
			});
			cashierPane.getSectorColumn().setOnEditCommit(e -> e.getRowValue().setSector(e.getNewValue()));
			cashierPane.getCanCreateBillColumn()
					.setOnEditCommit(e -> e.getRowValue().setCanCreateBill(e.getNewValue()));
			cashierPane.getCanAccessTodayColumn()
					.setOnEditCommit(e -> e.getRowValue().setCanAccessToday(e.getNewValue()));

			cashierPane.getUpdateButton().setOnAction(e -> {
				try {
					int index = pane.getCashierPane().getCashierTable().getSelectionModel().getSelectedIndex();
					Cashier cashier = pane.getCashierPane().getCashierTable().getItems().get(index);
					FileHandler.updateCashier(cashier);
					pane.showCashiersView();
				} catch (Exception ex) {
					showException(ex.getMessage(), pane);
				}
			});
			cashierPane.getDeleteButton().setOnAction(e -> {
				try {
					int index = pane.getCashierPane().getCashierTable().getSelectionModel().getSelectedIndex();
					Cashier c = pane.getCashierPane().getCashierTable().getItems().get(index);
					FileHandler.deleteUser(c);
					pane.showCashiersView();
				} catch (Exception ex) {
					showException(ex.getMessage(), pane);
				}
			});
			cashierPane.getSearchCashierField().setOnKeyReleased(e -> searchCashier(pane));

			// Payout Selected Cashiers
			cashierPane.getPayoutButton().setOnAction(e -> {
				try {
					ObservableList<Cashier> payoutList = cashierPane.getCashierTable().getItems();
					for (Cashier c : payoutList) {
						Salary newSalary = new Salary(c, c.getCurrentSalary());
						FileHandler.appendFile(FileHandler.SALARY, newSalary);
					}
				} catch (Exception ex) {
					showException(ex.getMessage(), pane);
				}
			});
		} catch (Exception ex) {
			showException(ex.getMessage(), pane);
		}

	}

	public static void handleManagers(AdministratorView pane) {
		AdministratorManagersPane managerPane = pane.getManagerPane();

		try {
			// Fill manager table with data
			ArrayList<Manager> managerList = FileHandler.readFile(FileHandler.MANAGER);
			ObservableList<Manager> managerData = FXCollections.observableArrayList(managerList);
			managerPane.getManagerTable().setItems(managerData);

			managerPane.getNameColumn()
					.setCellValueFactory(managerEvent -> new SimpleStringProperty(managerEvent.getValue().getName()));
			managerPane.getUsernameColumn().setCellValueFactory(
					managerEvent -> new SimpleStringProperty(managerEvent.getValue().getUsername()));
			managerPane.getPasswordColumn().setCellValueFactory(
					managerEvent -> new SimpleStringProperty(managerEvent.getValue().getPassword()));
			managerPane.getPhoneNumberColumn().setCellValueFactory(
					managerEvent -> new SimpleStringProperty(managerEvent.getValue().getPhoneNumber()));
			managerPane.getEmailColumn()
					.setCellValueFactory(managerEvent -> new SimpleStringProperty(managerEvent.getValue().getEmail()));
			managerPane.getDateOfBirthColumn().setCellValueFactory(
					managerEvent -> new SimpleObjectProperty<LocalDate>(managerEvent.getValue().getDateOfBirth()));
			managerPane.getSalaryColumn().setCellValueFactory(
					managerEvent -> new SimpleDoubleProperty(managerEvent.getValue().getCurrentSalary()).asObject());

			ObservableList<Boolean> booleanData = FXCollections.observableArrayList(true, false);
			managerPane.getCanAccessStockColumn().setCellValueFactory(
					managerEvent -> new SimpleBooleanProperty(managerEvent.getValue().getCanAccessStock()));
			managerPane.getCanAccessStockColumn().setCellFactory(ChoiceBoxTableCell.forTableColumn(booleanData));

			managerPane.getCanAccessItemsColumn().setCellValueFactory(
					managerEvent -> new SimpleBooleanProperty(managerEvent.getValue().getCanAccessItems()));
			managerPane.getCanAccessItemsColumn().setCellFactory(ChoiceBoxTableCell.forTableColumn(booleanData));

			managerPane.getCanAccessCategoriesColumn().setCellValueFactory(
					managerEvent -> new SimpleBooleanProperty(managerEvent.getValue().getCanAccessCategories()));
			managerPane.getCanAccessCategoriesColumn().setCellFactory(ChoiceBoxTableCell.forTableColumn(booleanData));

			managerPane.getCanAccessCashiersColumn().setCellValueFactory(
					managerEvent -> new SimpleBooleanProperty(managerEvent.getValue().getCanAccessCashiers()));
			managerPane.getCanAccessCashiersColumn().setCellFactory(ChoiceBoxTableCell.forTableColumn(booleanData));

			managerPane.getCanAccessSuppliersColumn().setCellValueFactory(
					managerEvent -> new SimpleBooleanProperty(managerEvent.getValue().getCanAccessSuppliers()));
			managerPane.getCanAccessSuppliersColumn().setCellFactory(ChoiceBoxTableCell.forTableColumn(booleanData));

			// Edit configurations for manager table columns
			managerPane.getPasswordColumn().setOnEditCommit(e -> e.getRowValue().setPassword(e.getNewValue()));
			managerPane.getPhoneNumberColumn().setOnEditCommit(e -> e.getRowValue().setPhoneNumber(e.getNewValue()));
			managerPane.getEmailColumn().setOnEditCommit(e -> {
				try {
					e.getRowValue().setEmail(e.getNewValue());
				} catch (Exception ex) {
					showException(ex.getMessage(), pane);
				}
			});
			managerPane.getSalaryColumn().setOnEditCommit(e -> {
				try {
					e.getRowValue().setCurrentSalary(e.getNewValue());
				} catch (Exception ex) {
					showException(ex.getMessage(), pane);
				}
			});

			managerPane.getCanAccessStockColumn()
					.setOnEditCommit(e -> e.getRowValue().setCanAccessStock(e.getNewValue()));
			managerPane.getCanAccessItemsColumn()
					.setOnEditCommit(e -> e.getRowValue().setCanAccessItems(e.getNewValue()));
			managerPane.getCanAccessCategoriesColumn()
					.setOnEditCommit(e -> e.getRowValue().setCanAccessCategories(e.getNewValue()));
			managerPane.getCanAccessCashiersColumn()
					.setOnEditCommit(e -> e.getRowValue().setCanAccessCashiers(e.getNewValue()));
			managerPane.getCanAccessSuppliersColumn()
					.setOnEditCommit(e -> e.getRowValue().setCanAccessSuppliers(e.getNewValue()));

			// Button actions
			managerPane.getUpdateButton().setOnAction(e -> {
				try {
					int index = pane.getManagerPane().getManagerTable().getSelectionModel().getSelectedIndex();
					Manager manager = pane.getManagerPane().getManagerTable().getItems().get(index);
					FileHandler.updateManager(manager);
					pane.showManagersView();
				} catch (Exception ex) {
					showException(ex.getMessage(), pane);
				}
			});
			managerPane.getDeleteButton().setOnAction(e -> {
				try {
					int index = pane.getManagerPane().getManagerTable().getSelectionModel().getSelectedIndex();
					Manager m = pane.getManagerPane().getManagerTable().getItems().get(index);
					FileHandler.deleteUser(m);
					pane.showManagersView();
				} catch (Exception ex) {
					showException(ex.getMessage(), pane);
				}
			});
			managerPane.getSearchManagerField().setOnKeyReleased(e -> searchManager(pane));
			managerPane.getPayoutButton().setOnAction(e -> {
				try {
					ObservableList<Manager> payoutList = managerPane.getManagerTable().getItems();
					for (Manager m : payoutList) {
						Salary newSalary = new Salary(m, m.getCurrentSalary());
						FileHandler.appendFile(FileHandler.SALARY, newSalary);
					}
				} catch (Exception ex) {
					showException(ex.getMessage(), pane);
				}
			});
		} catch (Exception ex) {
			showException(ex.getMessage(), pane);
		}
	}

	public static void handleSectors(AdministratorView pane) {
		AdministratorSectorsPane sectorPane = pane.getSectorPane();

		// Fill Sector Table with Data
		try {
			ArrayList<Sector> sectorList = FileHandler.readFile(FileHandler.SECTOR);
			ObservableList<Sector> sectorData = FXCollections.observableArrayList(sectorList);
			sectorPane.getSectorTable().setItems(sectorData);

			sectorPane.getNameColumn().setCellValueFactory(e -> new SimpleStringProperty(e.getValue().getName()));

			sectorPane.getManagerColumn()
					.setCellValueFactory(e -> new SimpleObjectProperty<Manager>(e.getValue().getManager()));
			ArrayList<Manager> managerList = FileHandler.readFile(FileHandler.MANAGER);
			ObservableList<Manager> managerData = FXCollections.observableArrayList(managerList);
			sectorPane.getManagerColumn().setCellFactory(ComboBoxTableCell.forTableColumn(managerData));
			sectorPane.getManagerColumn().setOnEditCommit(e -> e.getRowValue().setManager(e.getNewValue()));

			sectorPane.getUpdateButton().setOnAction(e -> {
				try {
					int index = pane.getSectorPane().getSectorTable().getSelectionModel().getSelectedIndex();
					Sector sector = pane.getSectorPane().getSectorTable().getItems().get(index);
					FileHandler.updateSector(sector);
				} catch (Exception ex) {
					showException(ex.getMessage(), pane);
				}
			});
		} catch (Exception ex) {
			showException(ex.getMessage(), pane);
		}
	}

	public static void handleStats(AdministratorView pane) {
		AdministratorStatsPane statsPane = pane.getStatsPane();

		statsPane.getStatsDateSelector().getEnterButton().setOnAction(e -> {
			LocalDate startDate = statsPane.getStatsDateSelector().getStartDatePicker().getValue();
			LocalDate endDate = statsPane.getStatsDateSelector().getEndDatePicker().getValue();
			try {
				statsPane.getItemCostsField().setText(StatisticsDAO.getItemCosts(startDate, endDate) + "");
				statsPane.getStaffCostsField().setText(StatisticsDAO.getStaffCosts(startDate, endDate) + "");
				statsPane.getTotalCostsField().setText(StatisticsDAO.getTotalCosts(startDate, endDate) + "");
				statsPane.getTotalRevenueField().setText(StatisticsDAO.getTotalRevenue(startDate, endDate) + "");
				statsPane.getTotalProfitsField().setText(StatisticsDAO.getTotalProfits(startDate, endDate) + "");
			} catch (Exception ex) {
				showException(ex.getMessage(), pane);
			}
		});

	}

	public static void handleProfile(AdministratorView pane) {
		pane.getChangePasswordPane().getChangePasswordButton().setOnAction(e -> {
			try {
				String newPassword = pane.getChangePasswordPane().getPasswordField().getText();
				ArrayList<Administrator> admin = FileHandler.readFile(FileHandler.ADMINISTRATOR);
				admin.get(0).setPassword(newPassword);
				FileHandler.updateFile(FileHandler.ADMINISTRATOR, admin);
			} catch (Exception ex) {
				showException(ex.getMessage(), pane);
			}
		});
	}

	private static void searchCashier(AdministratorView pane) {
		String pattern = pane.getCashierPane().getSearchCashierField().getText().toLowerCase();
		try {
			ArrayList<Cashier> cashierList = FileHandler.readFile(FileHandler.CASHIER);
			ArrayList<Cashier> matchingCashierList = new ArrayList<>();
			for (Cashier c : cashierList) {
				if (c.getName().toLowerCase().matches("(?i).*" + pattern + ".*"))
					matchingCashierList.add(c);
			}
			ObservableList<Cashier> cashierData = FXCollections.observableArrayList(matchingCashierList);
			pane.getCashierPane().getCashierTable().setItems(cashierData);
		} catch (Exception ex) {
			showException(ex.getMessage(), pane);
		}
	}

	private static void searchManager(AdministratorView pane) {
		String pattern = pane.getManagerPane().getSearchManagerField().getText().toLowerCase();
		try {
			ArrayList<Manager> managerList = FileHandler.readFile(FileHandler.MANAGER);
			ArrayList<Manager> matchingManagerList = new ArrayList<>();
			for (Manager m : managerList) {
				if (m.getName().toLowerCase().matches("(?i).*" + pattern + ".*")) {
					matchingManagerList.add(m);
				}
			}
			ObservableList<Manager> managerData = FXCollections.observableArrayList(matchingManagerList);
			pane.getManagerPane().getManagerTable().setItems(managerData);
		} catch (Exception ex) {
			showException(ex.getMessage(), pane);
		}
	}

	private static void showException(String exceptionMessage, AdministratorView pane) {
		pane.getExceptionLabel().setText(exceptionMessage);
		pane.getExceptionLabel().setVisible(true);

		PauseTransition pause = new PauseTransition(Duration.seconds(5));

		pause.setOnFinished(event -> pane.getExceptionLabel().setVisible(false));
		pause.play();
	}
}
