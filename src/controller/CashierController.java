package controller;

import java.util.ArrayList;

import dao.FileHandler;
import dao.StatisticsDAO;
import javafx.animation.PauseTransition;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.util.Duration;
import main.Main;
import model.Item;
import model.Bill;
import model.Cashier;
import model.Category;
import view.CashierView;
import view.LoginView;
import view.ui.CashierNewBillPane;
import view.ui.CashierTodayPane;

public class CashierController {
	public static void handleMenu(CashierView pane) {
		pane.getNewBillMenu().setDisable(!(pane.getCashier().getCanCreateBill()));
		pane.getNewBillMenu().setOnAction(e -> pane.showNewBillView());
		
		pane.getTodayMenu().setDisable(!(pane.getCashier().getCanAccessToday()));
		pane.getTodayMenu().setOnAction(e -> pane.showTodayView());
		
		pane.getHomeMenu().setOnAction(e -> pane.showHomeView());
		
		pane.getLogoutMenu().setOnAction(e -> Main.displayOnScene(new LoginView()));
	}
	
	public static void handleHome(CashierView pane) {
		Cashier cashier = pane.getCashier();
		pane.getHomePane().getWelcomeLbl().setText("Welcome, " + cashier.getName());
		pane.getHomePane().getNewBillBtn().setDisable(!(pane.getCashier().getCanCreateBill()));
		pane.getHomePane().getNewBillBtn().setOnAction(e -> pane.showNewBillView());
		pane.getHomePane().getTodayBtn().setDisable(!(pane.getCashier().getCanAccessToday()));
        pane.getHomePane().getTodayBtn().setOnAction(e -> pane.showTodayView());
        pane.getHomePane().getLogoutBtn().setOnAction(e -> Main.displayOnScene(new LoginView()));
	}
	
	public static void handleNewBill(CashierView pane) {
		Bill bill = new Bill(pane.getCashier());
		CashierNewBillPane newBillPane = pane.getNewBillPane();
		newBillPane.getBillNumberLabel().setText(bill.getBillNumber());
		try {
	        // Fill item table with data
	        ArrayList<Item> itemList = FileHandler.readFile(FileHandler.ITEM);
	        itemList.removeIf(i -> !(i.getSector().getName().equals(pane.getCashier().getSector().getName())));
	        ObservableList<Item> itemData = FXCollections.observableArrayList(itemList);
	        newBillPane.getItemTable().setItems(itemData);


	        newBillPane.getNameColumn().setCellValueFactory(i -> new SimpleStringProperty(i.getValue().getName()));
	        newBillPane.getCategoryColumn().setCellValueFactory(i -> new SimpleObjectProperty<Category>(i.getValue().getCategory()));
	        newBillPane.getPriceColumn().setCellValueFactory(i -> new SimpleDoubleProperty(i.getValue().getSellingPrice()).asObject());
	        newBillPane.getQuantityColumn().setCellValueFactory(i -> new SimpleIntegerProperty(i.getValue().getQuantity()).asObject());
	        newBillPane.getNoteColumn().setCellValueFactory(i -> new SimpleStringProperty(i.getValue().checkStock()));
	        
	
	        // Button actions
	        newBillPane.getSearchItemField().setOnKeyReleased(e -> searchItem(pane));
	        
	        newBillPane.getAddButton().setOnAction(e -> {
	        	pane.getExceptionLabel().setText("");
	        	try{
	        		int index = newBillPane.getItemTable().getSelectionModel().getSelectedIndex();
	        		if(index < 0 || newBillPane.getSelectQuantityField().getText().isEmpty()) 
	        			showException("Select an item and a quantity.", pane);
	        		Item item = newBillPane.getItemTable().getItems().get(index);
	        		int quantity = Integer.parseInt(newBillPane.getSelectQuantityField().getText());
	        		bill.addItem(item, quantity);
	        		newBillPane.getBillPane().getChildren().add(new Label("   Item Name: " + item.getName() + "\t Item Price: " + item.getSellingPrice() + "\t Quantity: " + quantity));
	        		newBillPane.getTotalField().setText(bill.getTotalPrice()+"");
	        	} catch(Exception ex) {
	        		showException(ex.getMessage(), pane);
	        	}
	        });
			
	        newBillPane.getCreateBillButton().setOnAction(e -> {try{
	        	FileHandler.saveBill(bill);
	        	pane.showNewBillView();
	        } catch(Exception ex) {
	        	showException(ex.getMessage(), pane);
	        	newBillPane.getBillPane().getChildren().remove(2, newBillPane.getBillPane().getChildren().size());
	        	newBillPane.getTotalField().setText("");
	        }});
	        
	    } catch (Exception ex) {
	    	showException(ex.getMessage(), pane);
	        newBillPane.getBillPane().getChildren().remove(2, newBillPane.getBillPane().getChildren().size());
	        newBillPane.getTotalField().setText("");
	    }
	}
	
	public static void handleToday(CashierView pane) {
		CashierTodayPane todayPane = pane.getTodayPane();
		
		try {
	        ArrayList<Bill> billList = StatisticsDAO.getTodayBillsByCashier(pane.getCashier());
	        ObservableList<Bill> billData = FXCollections.observableArrayList(billList);
	        todayPane.getBillTable().setItems(billData);
	        todayPane.getBillNumberColumn().setCellValueFactory(b -> new SimpleStringProperty(b.getValue().getBillNumber()));
	        todayPane.getDateTimeColumn().setCellValueFactory(b -> new SimpleStringProperty(b.getValue().getFormattedDate()));
	        todayPane.getQuantityColumn().setCellValueFactory(b -> new SimpleIntegerProperty(b.getValue().getTotalQuantity()).asObject());
	        todayPane.getPriceColumn().setCellValueFactory(b -> new SimpleDoubleProperty(b.getValue().getTotalPrice()).asObject());
	        todayPane.getTodayTotalField().setText(StatisticsDAO.getTodayTotalByCashier(pane.getCashier()) + "");  
	        
	    } catch (Exception ex) {
	    	showException(ex.getMessage(), pane);
	    }
	}
	
	private static void searchItem(CashierView pane) {
		CashierNewBillPane newBillPane = pane.getNewBillPane();
		
	    String pattern = newBillPane.getSearchItemField().getText().toLowerCase();
	    try {
	        ArrayList<Item> itemList = FileHandler.readFile(FileHandler.ITEM);
	        ArrayList<Item> matchingItemList = new ArrayList<>();
	        for (Item i : itemList) {
	            if (i.getName().toLowerCase().matches("(?i).*" + pattern + ".*")) {
	                matchingItemList.add(i);
	            }
	        }
	        ObservableList<Item> itemData = FXCollections.observableArrayList(matchingItemList);
	        newBillPane.getItemTable().setItems(itemData);
	    } catch (Exception ex) {
	    	showException(ex.getMessage(), pane);
	    }
	}
	
	private static void showException(String exceptionMessage, CashierView pane) {
        pane.getExceptionLabel().setText(exceptionMessage);
        pane.getExceptionLabel().setVisible(true);

        PauseTransition pause = new PauseTransition(Duration.seconds(5));
        
        pause.setOnFinished(event -> pane.getExceptionLabel().setVisible(false));
        pause.play();
    }
}
