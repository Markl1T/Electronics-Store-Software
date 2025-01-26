package controller;

import java.time.LocalDate;
import java.util.ArrayList;
import dao.FileHandler;
import dao.StatisticsDAO;
import javafx.animation.PauseTransition;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.util.Duration;
import main.Main;
import model.Cashier;
import model.Category;
import model.Item;
import model.Manager;
import model.Sector;

import model.Supplier;
import view.LoginView;
import view.ManagerView;
import view.ui.ManagerCashierStatisticsPane;
import view.ui.ManagerCategoriesPane;
import view.ui.ManagerItemStatisticsPane;
import view.ui.ManagerItemsPane;
import view.ui.ManagerNewCategoryPane;
import view.ui.ManagerNewItemPane;
import view.ui.ManagerNewStockPane;
import view.ui.ManagerNewSupplierPane;
import view.ui.ManagerSuppliersPane;

public class ManagerController {

	public static void controlMenu(ManagerView pane) {
		Manager manager = pane.getManager();

		pane.getHomeMenu().setOnAction(e -> pane.showHomeView());
		pane.getCashierStatisticsMenu().setDisable(!(manager.getCanAccessCashiers()));
		pane.getCashierStatisticsMenu().setOnAction(e -> pane.showCashierStatisticsView());

		if (!(manager.getCanAccessItems())) {
			pane.getItemStatisticsMenu().setDisable(true);
			pane.getItemsMenu().setDisable(true);
			pane.getNewItemMenu().setDisable(true);
		}
		pane.getNewItemMenu().setOnAction(e -> pane.showNewItemView());
		pane.getItemStatisticsMenu().setOnAction(e -> pane.showItemStatisticsView());
		pane.getItemsMenu().setOnAction(e -> pane.showItemsView());

		pane.getNewStockMenu().setDisable(!(manager.getCanAccessStock()));
		pane.getNewStockMenu().setOnAction(e -> pane.showNewStockView());

		if (!(manager.getCanAccessCategories())) {
			pane.getCategoriesMenu().setDisable(true);
			pane.getNewCategoryMenu().setDisable(true);
		}

		pane.getNewCategoryMenu().setOnAction(e -> pane.showNewCategoryView());
		pane.getCategoriesMenu().setOnAction(e -> pane.showCategoriesView());

		if (!(manager.getCanAccessSuppliers())) {
			pane.getSuppliersMenu().setDisable(true);
			pane.getNewSupplierMenu().setDisable(true);
		}
		pane.getNewSupplierMenu().setOnAction(e -> pane.showNewSupplierView());
		pane.getSuppliersMenu().setOnAction(e -> pane.showSuppliersView());
		pane.getLogoutMenu().setOnAction(e -> Main.displayOnScene(new LoginView()));
	}
	
	public static void handleHome(ManagerView pane) {
		Manager manager = pane.getManager();
		pane.getHomePane().getWelcomeLbl().setText("Welcome, " + manager.getName());
		pane.getHomePane().getNewStockBtn().setOnAction(e -> pane.showNewStockView());
        pane.getHomePane().getNewItemBtn().setOnAction(e -> pane.showNewItemView());
        pane.getHomePane().getNewCategoryBtn().setOnAction(e -> pane.showNewCategoryView());
        pane.getHomePane().getNewSupplierBtn().setOnAction(e -> pane.showNewSupplierView());
        pane.getHomePane().getCashierStatisticsBtn().setOnAction(e -> pane.showCashierStatisticsView());
        pane.getHomePane().getCategoriesBtn().setOnAction(e -> pane.showCategoriesView());
        pane.getHomePane().getItemsBtn().setOnAction(e -> pane.showItemsView());
        pane.getHomePane().getItemStatisticsBtn().setOnAction(e -> pane.showItemStatisticsView());
        pane.getHomePane().getSuppliersBtn().setOnAction(e -> pane.showSuppliersView());
        pane.getHomePane().getLogoutBtn().setOnAction(e -> Main.displayOnScene(new LoginView()));
        if (!(manager.getCanAccessItems())) {
			pane.getHomePane().getNewItemBtn().setDisable(true);
			pane.getHomePane().getItemStatisticsBtn().setDisable(true);
			pane.getHomePane().getItemsBtn().setDisable(true);
		}
        if (!(manager.getCanAccessCategories())) {
			pane.getHomePane().getNewCategoryBtn().setDisable(true);
			pane.getHomePane().getCategoriesBtn().setDisable(true);
		}
        if (!(manager.getCanAccessSuppliers())) {
			pane.getHomePane().getNewCategoryBtn().setDisable(true);
			pane.getHomePane().getCategoriesBtn().setDisable(true);
		}
        if (!(manager.getCanAccessStock())) {
			pane.getHomePane().getNewStockBtn().setDisable(true);
		}
        if (!(manager.getCanAccessCashiers())) {
			pane.getHomePane().getCashierStatisticsBtn().setDisable(true);
		}
        
        try {
			ManagerController.checkLowStockLevelsForSector(manager.getSectorList());
		} catch (Exception ex) {
			
		}
	}

	public static void handleNewCategory(ManagerView pane) {
		ManagerNewCategoryPane newCategoryPane = pane.getNewCategoryPane();
		try {
			ArrayList<Sector> managerSectorList = pane.getManager().getSectorList();
			newCategoryPane.getSectorComboBox().getItems().addAll(managerSectorList);
		} catch (Exception ex) {
			pane.getExceptionLabel().setText(ex.getMessage());
		}

		newCategoryPane.getCreateButton().setOnAction(e -> {
			try {
				String name = newCategoryPane.getNameField().getText();
				Sector sector = newCategoryPane.getSectorComboBox().getValue();
				int minQuantity = Integer.parseInt(newCategoryPane.getMinQuantityField().getText());
				Category newCategory = new Category(name, sector, minQuantity);
				FileHandler.appendFile(FileHandler.CATEGORY, newCategory);
				pane.showCategoriesView();
			} catch (NumberFormatException ex) {
				showException("Quantity should be an integer", pane);
			} catch (Exception ex) {
				showException(ex.getMessage(), pane);
			}
		});
	}

	public static void handleNewItem(ManagerView pane) {
		ManagerNewItemPane newItemPane = pane.getNewItemPane();
		newItemPane.getCategoryComboBox().getItems().clear();
		newItemPane.getCategoryComboBox().setDisable(true);

		try {
			ArrayList<Sector> managerSectorList = pane.getManager().getSectorList();
			newItemPane.getSectorComboBox().getItems().addAll(managerSectorList);

			ArrayList<Supplier> supplierList = FileHandler.readFile(FileHandler.SUPPLIER);
			newItemPane.getSupplierComboBox().getItems().addAll(supplierList);
		} catch (Exception ex) {
			showException(ex.getMessage(), pane);
		}
		newItemPane.getSectorComboBox().setOnAction(e -> {
			try {
				newItemPane.getCategoryComboBox().getItems().clear();
				newItemPane.getCategoryComboBox().setDisable(false);
				Sector selectedSector = newItemPane.getSectorComboBox().getValue();
				newItemPane.getCategoryComboBox().getItems().addAll(selectedSector.getCategoryList());
			} catch (Exception ex) {
				showException(ex.getMessage(), pane);
			}
		});

		newItemPane.getCreateButton().setOnAction(e -> {
			try {
				String name = newItemPane.getNameField().getText();
				Category category = newItemPane.getCategoryComboBox().getValue();
				Supplier supplier = newItemPane.getSupplierComboBox().getValue();
				Double sellingPrice = Double.parseDouble(newItemPane.getSellingPriceField().getText());
				if(name.isEmpty() || category == null || supplier == null) {
					throw new IllegalArgumentException("All fields must be filled");
				}
				Item newItem = new Item(name, category, supplier, sellingPrice);
				FileHandler.appendFile(FileHandler.ITEM, newItem);
				pane.showItemsView();
			} catch (NumberFormatException ex) {
				showException("Selling Price should be an integer or decimal number", pane);
			} catch (Exception ex) {
				showException(ex.getMessage(), pane);
			}
		});
	}

	public static void handleNewStock(ManagerView pane) {
		ManagerNewStockPane newStockPane = pane.getNewStockPane();

		try {
			ArrayList<Sector> sectorList = pane.getManager().getSectorList();
			ArrayList<Item> itemList = new ArrayList<>();
			for (Sector s : sectorList) {
				itemList.addAll(s.getItemList());
			}
			newStockPane.getItemComboBox().getItems().addAll(itemList);
		} catch (Exception ex) {
			showException(ex.getMessage(), pane);
		}

		newStockPane.getCreateButton().setOnAction(e -> {
			try {
				Item item = newStockPane.getItemComboBox().getValue();
				int quantity = Integer.parseInt(newStockPane.getStockQuantityField().getText());
				double purchasePrice = Double.parseDouble(newStockPane.getPurchasePriceField().getText());
				if(item == null || quantity == 0 || purchasePrice <= 0) {
					throw new IllegalArgumentException("All fields must be filled");
				}
				FileHandler.addStock(item, quantity, purchasePrice);
				pane.showItemsView();
			} catch (NumberFormatException ex) {
				showException("Stock quantity and price should be numbers", pane);
			} catch (Exception ex) {
				showException(ex.getMessage(), pane);
			}
		});
	}

	

	public static void handleNewSupplier(ManagerView pane) {
		ManagerNewSupplierPane newSupplierPane = pane.getNewSupplierPane();

		newSupplierPane.getAddButton().setOnAction(e -> {
			String name = newSupplierPane.getNameField().getText();
			String phoneNumber = newSupplierPane.getPhoneNumberField().getText();
			String email = newSupplierPane.getEmailField().getText();

			try {
				if(name.isEmpty() || phoneNumber.isEmpty() || email.isEmpty()) {
					throw new IllegalArgumentException("All fields must be filled");
				}
				Supplier newSupplier = new Supplier(name, phoneNumber, email);
				FileHandler.appendFile(FileHandler.SUPPLIER, newSupplier);
				pane.showSuppliersView();
			} catch (Exception ex) {
				showException(ex.getMessage(), pane);
			}
		});
	}

	public static void handleCategories(ManagerView pane) {
		ManagerCategoriesPane categoryPane = pane.getCategoryPane();
		try {
			ArrayList<Category> categoryList = FileHandler.readFile(FileHandler.CATEGORY);
			ObservableList<Category> categoryData = FXCollections.observableArrayList(categoryList);
			categoryPane.getCategoryTable().setItems(categoryData);
			
			categoryPane.getNameColumn().setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getName()));
			categoryPane.getSectorColumn()
					.setCellValueFactory(c -> new SimpleObjectProperty<Sector>(c.getValue().getSector()));
			categoryPane.getMinQuantityColumn()
					.setCellValueFactory(c -> new SimpleObjectProperty<Integer>(c.getValue().getMinQuantity()));
			categoryPane.getActualQuantityColumn().setCellValueFactory(c -> {
				SimpleObjectProperty<Integer> property;
				try {
					property = new SimpleObjectProperty<Integer>(c.getValue().getTotalQuantity());
				} catch (Exception ex) {
					property = new SimpleObjectProperty<Integer>(0);
				}
				return property;
			});

			categoryPane.getNoteColumn().setCellValueFactory(c -> {
				SimpleStringProperty property;
				try {
					property = new SimpleStringProperty(c.getValue().checkLowQuantity());
				} catch (Exception ex) {
					pane.getExceptionLabel().setText(ex.getMessage());
					property = new SimpleStringProperty();
				}
				return property;
			});

			categoryPane.getSearchCategoryField().setOnKeyReleased(e -> searchCategory(pane));
		} catch (Exception ex) {
			showException(ex.getMessage(), pane);
		}
	}

	private static void searchCategory(ManagerView pane) {
		String pattern = pane.getCategoryPane().getSearchCategoryField().getText().toLowerCase();
		try {
			ArrayList<Category> categoryList = FileHandler.readFile(FileHandler.CATEGORY);
			ArrayList<Category> matchingCategoryList = new ArrayList<>();
			for (Category c : categoryList) {
				if (c.getName().toLowerCase().matches("(?i).*" + pattern + ".*")) {
					matchingCategoryList.add(c);
				}
			}
			ObservableList<Category> categoryData = FXCollections.observableArrayList(matchingCategoryList);
			pane.getCategoryPane().getCategoryTable().setItems(categoryData);
		} catch (Exception ex) {
			showException(ex.getMessage(), pane);
		}
	}

	public static void handleSuppliers(ManagerView pane) {
		ManagerSuppliersPane supplierPane = pane.getSupplierPane();
		searchSupplier(pane);
		try {
			// Load supplier data
			ArrayList<Supplier> supplierList = FileHandler.readFile(FileHandler.SUPPLIER);
			ObservableList<Supplier> supplierData = FXCollections.observableArrayList(supplierList);
			supplierPane.getSupplierTable().setItems(supplierData);

			// Configure columns
			supplierPane.getNameColumn()
					.setCellValueFactory(supplier -> new SimpleStringProperty(supplier.getValue().getName()));
			supplierPane.getPhoneNumberColumn()
					.setCellValueFactory(supplier -> new SimpleStringProperty(supplier.getValue().getPhoneNumber()));
			supplierPane.getEmailColumn()
					.setCellValueFactory(supplier -> new SimpleStringProperty(supplier.getValue().getEmail()));

			// Configure column edits
			supplierPane.getPhoneNumberColumn().setOnEditCommit(e -> e.getRowValue().setPhoneNumber(e.getNewValue()));
			supplierPane.getEmailColumn().setOnEditCommit(e -> e.getRowValue().setEmail(e.getNewValue()));

			// Configure buttons
			supplierPane.getUpdateButton().setOnAction(e -> {try{
					int index = supplierPane.getSupplierTable().getSelectionModel().getSelectedIndex();
					Supplier supplier = pane.getSupplierPane().getSupplierTable().getItems().get(index);
					FileHandler.updateSupplier(supplier);
					pane.showSuppliersView();
				}catch (Exception ex) {
					showException(ex.getMessage(), pane);
				}
			});
			
			supplierPane.getDeleteButton().setOnAction(e -> {
				try {
					int index = supplierPane.getSupplierTable().getSelectionModel().getSelectedIndex();
					Supplier supplier = pane.getSupplierPane().getSupplierTable().getItems().get(index);
					FileHandler.delete(supplier);
					pane.showSuppliersView();
				} catch (Exception ex) {
					showException(ex.getMessage(), pane);
				}
			});
			supplierPane.getSearchSupplierField().setOnKeyReleased(e -> searchSupplier(pane));
			
		} catch (Exception ex) {
			showException(ex.getMessage(), pane);
		}

	}

	public static void handleItemStatistics(ManagerView pane) {
		ManagerItemStatisticsPane itemStatisticsPane = pane.getItemStatisticsPane();
		itemStatisticsPane.getDateSelection().getEnterButton().setOnAction(e -> {
			try {
				LocalDate startDate = itemStatisticsPane.getDateSelection().getStartDatePicker().getValue();
				LocalDate endDate = itemStatisticsPane.getDateSelection().getEndDatePicker().getValue();
				if (startDate == null || endDate == null) {
					throw new IllegalArgumentException("Both Start and End Date must be selected.");
				} else if (startDate.isAfter(endDate)) {
					throw new IllegalArgumentException("Start Date must be before End Date.");
				} else if (startDate.isAfter(LocalDate.now()) || endDate.isAfter(LocalDate.now())) {
					throw new IllegalArgumentException("Invalid Date");
				}
				itemStatisticsPane.getStartDateColumn()
						.setCellValueFactory(cashierEvent -> new SimpleObjectProperty<LocalDate>(startDate));
				itemStatisticsPane.getEndDateColumn()
						.setCellValueFactory(cashierEvent -> new SimpleObjectProperty<LocalDate>(endDate));
				itemStatisticsPane.getNumberOfItemsSoldColumn().setCellValueFactory(itemEvent -> {
					SimpleObjectProperty<Integer> numberOfItemsSold;
					try {
						numberOfItemsSold = new SimpleObjectProperty<Integer>(
								StatisticsDAO.getNumberOfItemsSold(itemEvent.getValue(), startDate, endDate));
					} catch (Exception ex) {
						numberOfItemsSold = new SimpleObjectProperty<Integer>(0);
					}
					return numberOfItemsSold;
				});

				itemStatisticsPane.getNumberOfItemsPurchasedColumn().setCellValueFactory(itemEvent -> {
					SimpleObjectProperty<Integer> numberOfItemsBought;
					try {
						numberOfItemsBought = new SimpleObjectProperty<Integer>(StatisticsDAO
								.getNumberOfItemsBought(itemEvent.getValue(), startDate, endDate));
					} catch (Exception ex) {
						numberOfItemsBought = new SimpleObjectProperty<Integer>(0);
					}
					return numberOfItemsBought;
				});
				searchItem(pane);
			} catch (Exception ex) {
				showException(ex.getMessage(), pane);
			}
		});
		try {
			itemStatisticsPane.getSearchItemField().setOnKeyReleased(e -> searchItem(pane));
			ArrayList<Item> itemList = new ArrayList<>();
			ArrayList<Sector> sectorList = pane.getManager().getSectorList();
			for (Sector s: sectorList) {
				itemList.addAll(s.getItemList());
			}
			ObservableList<Item> itemData = FXCollections.observableArrayList(itemList);
			itemStatisticsPane.getItemTable().setItems(itemData);
			itemStatisticsPane.getNameColumn().setCellValueFactory(i -> new SimpleStringProperty(i.getValue().getName()));
			searchItem(pane);
		} catch (Exception ex) {
			showException(ex.getMessage(), pane);
		}
	}

	public static void handleCashierStatistics(ManagerView pane) {
		ManagerCashierStatisticsPane cashierStatisticsPane = pane.getCashierStatisticsPane();
		searchCashier(pane);
		cashierStatisticsPane.getDateSelectionPane().getEnterButton().setOnAction(e -> {
			try {
				LocalDate startDate = cashierStatisticsPane.getDateSelectionPane().getStartDatePicker().getValue();
				LocalDate endDate = cashierStatisticsPane.getDateSelectionPane().getEndDatePicker().getValue();
				if (startDate == null || endDate == null) {
					throw new IllegalArgumentException("Both Start and End Date must be selected.");
				} else if (startDate.isAfter(endDate)) {
					throw new IllegalArgumentException("Start Date must be before End Date.");
				} else if (startDate.isAfter(LocalDate.now()) || endDate.isAfter(LocalDate.now())) {
					throw new IllegalArgumentException("Invalid Date");
				}
				cashierStatisticsPane.getStartDateColumn()
						.setCellValueFactory(cashierEvent -> new SimpleObjectProperty<LocalDate>(startDate));
				cashierStatisticsPane.getEndDateColumn()
						.setCellValueFactory(cashierEvent -> new SimpleObjectProperty<LocalDate>(endDate));
				cashierStatisticsPane.getNumberOfBillsColumn().setCellValueFactory(cashierEvent -> {
					SimpleObjectProperty<Integer> property;
					try {
						property = new SimpleObjectProperty<Integer>(
								StatisticsDAO.getNumberOfBillsByCashier(cashierEvent.getValue(), startDate, endDate));
					} catch (Exception ex) {
						property = new SimpleObjectProperty<Integer>(0);
					}
					return property;
				});

				cashierStatisticsPane.getNumberOfItemsSoldColumn().setCellValueFactory(cashierEvent -> {
					SimpleObjectProperty<Integer> property;
					try {
						property = new SimpleObjectProperty<Integer>(StatisticsDAO
								.getNumberOfItemsSoldByCashier(cashierEvent.getValue(), startDate, endDate));
					} catch (Exception ex) {
						property = new SimpleObjectProperty<Integer>(0);
					}
					return property;
				});
				cashierStatisticsPane.getTotalRevenueGeneratedColumn().setCellValueFactory(cashierEvent -> {
					SimpleObjectProperty<Double> property;
					try {
						property = new SimpleObjectProperty<Double>(StatisticsDAO
								.getTotalRevenueGeneratedByCashier(cashierEvent.getValue(), startDate, endDate));
					} catch (Exception ex) {
						property = new SimpleObjectProperty<Double>(0.0);
					}
					return property;
				});
				searchCashier(pane);
			} catch (Exception ex) {
				showException(ex.getMessage(), pane);
			}
		});
		try {
			ArrayList<Cashier> cashierList = pane.getManager().getCashierList();
			ObservableList<Cashier> cashierData = FXCollections.observableArrayList(cashierList);
			cashierStatisticsPane.getCashierTable().setItems(cashierData);
			cashierStatisticsPane.getNameColumn()
					.setCellValueFactory(cashierEvent -> new SimpleStringProperty(cashierEvent.getValue().getName()));
			cashierStatisticsPane.getUsernameColumn().setCellValueFactory(
					cashierEvent -> new SimpleStringProperty(cashierEvent.getValue().getUsername()));
			cashierStatisticsPane.getCashierTF().setOnKeyReleased(e -> searchCashier(pane));

		} catch (Exception ex) {
			showException(ex.getMessage(), pane);
		}
	}

	
	private static void searchCashier(ManagerView pane) {
		String pattern = pane.getCashierStatisticsPane().getCashierTF().getText().toLowerCase();
		try {
			ArrayList<Cashier> cashierList = pane.getManager().getCashierList();
			ArrayList<Cashier> matchingCashierList = new ArrayList<>();
			for (Cashier c : cashierList) {
				if (c.getName().toLowerCase().matches("(?i).*" + pattern + ".*"))
					matchingCashierList.add(c);
			}
			ObservableList<Cashier> cashierData = FXCollections.observableArrayList(matchingCashierList);
			pane.getCashierStatisticsPane().getCashierTable().setItems(cashierData);
		} catch (Exception ex) {
			showException(ex.getMessage(), pane);
		}
	}

	private static void searchSupplier(ManagerView pane) {
		String pattern = pane.getSupplierPane().getSearchSupplierField().getText().toLowerCase();
		try {
			ArrayList<Supplier> supplierList = FileHandler.readFile(FileHandler.SUPPLIER);
			ArrayList<Supplier> matchingSupplierList = new ArrayList<>();
			for (Supplier s : supplierList) {
				if (s.getName().toLowerCase().matches("(?i).*" + pattern + ".*")) {
					matchingSupplierList.add(s);
				}
			}
			ObservableList<Supplier> supplierData = FXCollections.observableArrayList(matchingSupplierList);
			pane.getSupplierPane().getSupplierTable().setItems(supplierData);
		} catch (Exception ex) {
			showException(ex.getMessage(), pane);
		}
	}
	
	public static void handleItems(ManagerView pane) {
		ManagerItemsPane itemPane = pane.getItemPane();
		try {
			ArrayList<Item> itemList = FileHandler.readFile(FileHandler.ITEM);
			ObservableList<Item> itemData = FXCollections.observableArrayList(itemList);
			itemPane.getItemTable().setItems(itemData);
			itemPane.getNameColumn().setCellValueFactory(i -> new SimpleStringProperty(i.getValue().getName()));
			itemPane.getCategoryColumn().setCellValueFactory(i -> new SimpleObjectProperty<Category>(i.getValue().getCategory()));
			itemPane.getSectorColumn().setCellValueFactory(i -> new SimpleObjectProperty<Sector>(i.getValue().getSector()));
			itemPane.getSupplierColumn().setCellValueFactory(i -> new SimpleObjectProperty<Supplier>(i.getValue().getSupplier()));
			itemPane.getQuantityColumn().setCellValueFactory(i -> new SimpleObjectProperty<Integer>(i.getValue().getQuantity()));
			itemPane.getSellingPriceColumn().setCellValueFactory(i -> new SimpleObjectProperty<Double>(i.getValue().getSellingPrice()));
			
			ArrayList<Supplier> supplierList = FileHandler.readFile(FileHandler.SUPPLIER);
			ObservableList<Supplier> supplierData = FXCollections.observableArrayList(supplierList);
			
			itemPane.getSupplierColumn().setCellFactory(ComboBoxTableCell.forTableColumn(supplierData));	
			itemPane.getSupplierColumn().setOnEditCommit(e -> e.getRowValue().setSupplier(e.getNewValue()));
			itemPane.getSellingPriceColumn().setOnEditCommit(
	                e -> {try { e.getRowValue().setSellingPrice(e.getNewValue());} catch (Exception ex) {
	                	pane.getExceptionLabel().setText(ex.getMessage());
	                }});		
			itemPane.getUpdateButton().setOnAction(e -> {
				try {
					int index = pane.getItemPane().getItemTable().getSelectionModel().getSelectedIndex();
					Item item = pane.getItemPane().getItemTable().getItems().get(index);
					FileHandler.updateItem(item);
					pane.showItemsView();
				} catch (Exception ex) {
					showException(ex.getMessage(), pane);
				}
			});
			itemPane.getSearchItemField().setOnKeyReleased(e -> searchItemByName(pane));
			itemPane.getSearchSupplierField().setOnKeyReleased(e -> searchItemBySupplier(pane));
		} catch (Exception ex) {
			showException(ex.getMessage(), pane);
		}
	}

	private static void searchItemByName(ManagerView pane) {
	    String pattern = pane.getItemPane().getSearchItemField().getText().toLowerCase();
	    try {
	        ArrayList<Item> itemList = FileHandler.readFile(FileHandler.ITEM);
	        ArrayList<Item> matchingItemList = new ArrayList<>();
	        for (Item i : itemList) {
	            if (i.getName().toLowerCase().matches("(?i).*" + pattern + ".*")) {
	                matchingItemList.add(i);
	            }
	        }
	        ObservableList<Item> itemData = FXCollections.observableArrayList(matchingItemList);
	        pane.getItemPane().getItemTable().setItems(itemData);
	    } catch (Exception ex) {
	    	showException(ex.getMessage(), pane);
	    }
	}
	
	private static void searchItem(ManagerView pane) {
	    String pattern = pane.getItemStatisticsPane().getSearchItemField().getText().toLowerCase();
	    try {
	        ArrayList<Item> itemList = FileHandler.readFile(FileHandler.ITEM);
	        ArrayList<Item> matchingItemList = new ArrayList<>();
	        for (Item i : itemList) {
	            if (i.getName().toLowerCase().matches("(?i).*" + pattern + ".*")) {
	                matchingItemList.add(i);
	            }
	        }
	        ObservableList<Item> itemData = FXCollections.observableArrayList(matchingItemList);
	        pane.getItemStatisticsPane().getItemTable().setItems(itemData);
	    } catch (Exception ex) {
	    	showException(ex.getMessage(), pane);
	    }
	}
	
	private static void searchItemBySupplier(ManagerView pane) {
		String pattern = pane.getItemPane().getSearchSupplierField().getText().toLowerCase();
	    try {
	        ArrayList<Item> itemList = FileHandler.readFile(FileHandler.ITEM);
	        ArrayList<Item> matchingItemList = new ArrayList<>();
	        for (Item i : itemList) {
	            if (i.getSupplier().getName().toLowerCase().matches("(?i).*" + pattern + ".*")) {
	                matchingItemList.add(i);
	            }
	        }
	        ObservableList<Item> itemData = FXCollections.observableArrayList(matchingItemList);
	        pane.getItemPane().getItemTable().setItems(itemData);
	    } catch (Exception ex) {
	    	showException(ex.getMessage(), pane);
	    }
	}
	
	private static void showException(String exceptionMessage, ManagerView pane) {
        pane.getExceptionLabel().setText(exceptionMessage);
        pane.getExceptionLabel().setVisible(true);

        PauseTransition pause = new PauseTransition(Duration.seconds(5));
        
        pause.setOnFinished(event -> pane.getExceptionLabel().setVisible(false));
        pause.play();
    }
	
	public static void checkLowStockLevelsForSector(ArrayList<Sector> sectorList) {
        try {
            ArrayList<Item> items = new ArrayList<Item>();
            
            for(Sector sector: sectorList) {
            	items.addAll(sector.getItemList());
            }
            
            for (Item item : items) {
                if (item.getQuantity() < item.getCategory().getMinQuantity()) {
                    showLowStockAlert(item);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void showLowStockAlert(Item item) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Low Stock Alert");
        alert.setHeaderText("Low Stock for " + item.getName());
        alert.setContentText("The stock quantity for " + item.getName() + " is below " + item.getCategory().getMinQuantity() + ". Please restock.");
        alert.showAndWait();
    }
}