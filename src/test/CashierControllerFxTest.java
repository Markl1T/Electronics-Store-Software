package test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.*;

import java.time.LocalDate;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.testfx.framework.junit5.ApplicationTest;

import controller.CashierController;
import dao.FileHandler;
import dao.StatisticsDAO;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import model.Bill;
import model.Cashier;
import model.Item;
import model.Sector;
import view.CashierView;
import view.ui.CashierNewBillPane;
import view.ui.CashierTodayPane;

public class CashierControllerFxTest extends ApplicationTest {

    private CashierView view;
    private Cashier cashier;

    @Override
    public void start(Stage stage) {
        // Mock the Cashier object
        cashier = mock(Cashier.class);
        when(cashier.getCanCreateBill()).thenReturn(true);
        when(cashier.getCanAccessToday()).thenReturn(true);
        when(cashier.getSector()).thenReturn(mock(Sector.class));

        // Spy on CashierView
        view = spy(new CashierView(cashier));
    }

    @BeforeEach
    void resetView() {
        // Reset spied view between tests
        reset(view);
    }

    @Test
    void handleMenu_shouldEnableMenusAndSetActions() {
        CashierController.handleMenu(view);

        // New Bill menu should be enabled
        assertTrue(view.getNewBillMenu().isDisable() == false);

        // Today menu should be enabled
        assertTrue(view.getTodayMenu().isDisable() == false);

        // Clicking menus should trigger showNewBillView / showTodayView
        view.getNewBillMenu().fire();
        verify(view).showNewBillView(any(CashierNewBillPane.class));

        view.getTodayMenu().fire();
        verify(view).showTodayView(any(CashierTodayPane.class));
    }

    @Test
    void handleNewBill_shouldFilterItemsBySectorAndUpdateTable() throws Exception {
        CashierNewBillPane newBillPane = spy(view.getNewBillPane());

        // Mock items
        Item item1 = mock(Item.class);
        Item item2 = mock(Item.class);

        Sector sector = mock(Sector.class);
        when(cashier.getSector()).thenReturn(sector);
        when(item1.getSector()).thenReturn(sector);
        when(item2.getSector()).thenReturn(mock(Sector.class)); // different sector

        ArrayList<Item> items = new ArrayList<>();
        items.add(item1);
        items.add(item2);

        try (MockedStatic<FileHandler> fh = mockStatic(FileHandler.class)) {
            fh.when(() -> FileHandler.readFile(FileHandler.ITEM)).thenReturn(items);

            CashierController.handleNewBill(view);

            // Table should only contain item1 (matching sector)
            assertTrue(newBillPane.getItemTable().getItems().contains(item1));
            assertFalse(newBillPane.getItemTable().getItems().contains(item2));
        }
    }

    @Test
    void handleNewBill_addItemUpdatesTotal() throws Exception {
        CashierNewBillPane newBillPane = spy(view.getNewBillPane());
        Item item = mock(Item.class);
        when(item.getName()).thenReturn("TestItem");
        when(item.getSellingPrice()).thenReturn(50.0);

        // Put item in table
        newBillPane.getItemTable().getItems().add(item);

        // Set quantity to add
        newBillPane.getSelectQuantityField().setText("2");

        // Spy FileHandler
        try (MockedStatic<FileHandler> fh = mockStatic(FileHandler.class)) {
            CashierController.handleNewBill(view);

            // Simulate clicking Add button
            newBillPane.getItemTable().getSelectionModel().select(0);
            newBillPane.getAddButton().fire();

            // Total should update correctly
            assertEquals("100.0", newBillPane.getTotalField().getText());
        }
    }

    @Test
    void handleToday_shouldPopulateTableAndTotal() throws Exception {
        CashierTodayPane todayPane = spy(view.getTodayPane());

        Bill b1 = mock(Bill.class);
        when(b1.getBillNumber()).thenReturn("B001");
        when(b1.getFormattedDate()).thenReturn("2026-01-21");
        when(b1.getTotalQuantity()).thenReturn(5);
        when(b1.getTotalPrice()).thenReturn(100.0);

        ArrayList<Bill> bills = new ArrayList<>();
        bills.add(b1);

        try (MockedStatic<StatisticsDAO> dao = mockStatic(StatisticsDAO.class)) {
            dao.when(() -> StatisticsDAO.getTodayBillsByCashier(cashier)).thenReturn(bills);
            dao.when(() -> StatisticsDAO.getTodayTotalByCashier(cashier)).thenReturn(100.0);

            CashierController.handleToday(view);

            // Table should contain bill
            assertTrue(todayPane.getBillTable().getItems().contains(b1));

            // Total field should show correct total
            assertEquals("100.0", todayPane.getTodayTotalField().getText());
        }
    }

    @Test
    void searchItem_shouldFilterItemsCorrectly() throws Exception {
        CashierNewBillPane newBillPane = spy(view.getNewBillPane());

        Item item1 = mock(Item.class);
        Item item2 = mock(Item.class);

        when(item1.getName()).thenReturn("Apple");
        when(item2.getName()).thenReturn("Banana");

        ArrayList<Item> items = new ArrayList<>();
        items.add(item1);
        items.add(item2);

        try (MockedStatic<FileHandler> fh = mockStatic(FileHandler.class)) {
            fh.when(() -> FileHandler.readFile(FileHandler.ITEM)).thenReturn(items);

            // Set search text
            newBillPane.getSearchItemField().setText("App");

            // Call searchItem indirectly
            CashierController.handleNewBill(view);

            // Table should contain only item1
            assertTrue(newBillPane.getItemTable().getItems().contains(item1));
            assertFalse(newBillPane.getItemTable().getItems().contains(item2));
        }
    }
}