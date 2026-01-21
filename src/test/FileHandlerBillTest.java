package test;

import static org.mockito.Mockito.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import dao.FileHandler;
import model.Bill;
import model.Cashier;
import model.Item;

class FileHandlerBillTest {

    @Test
    void testSaveBill_updatesItemQuantitiesAndAppendsBill() throws Exception {
        // Arrange
        Item item1 = mock(Item.class);
        Item item2 = mock(Item.class);

        when(item1.getName()).thenReturn("Laptop");
        when(item2.getName()).thenReturn("Mouse");

        ArrayList<Item> allItemsList = new ArrayList<>();
        allItemsList.add(item1);
        allItemsList.add(item2);

        Bill mockBill = mock(Bill.class);
        Cashier mockCashier = mock(Cashier.class);

        when(mockBill.getItemList()).thenReturn(new ArrayList<>() {{
            add(item1);
            add(item2);
        }});
        when(mockBill.getQuantityList()).thenReturn(new ArrayList<>() {{
            add(5);   // Laptop quantity sold
            add(2);   // Mouse quantity sold
        }});
        when(mockBill.getCashier()).thenReturn(mockCashier);
        when(mockBill.getBillNumber()).thenReturn("B001");
        when(mockBill.getPriceList()).thenReturn(new ArrayList<>());
        when(mockBill.getTotalPrice()).thenReturn(1000.0);
        when(mockBill.getFormattedDate()).thenReturn("2026-01-21");

        try (MockedStatic<FileHandler> mocked = mockStatic(FileHandler.class)) {
            // Mock FileHandler methods
            mocked.when(() -> FileHandler.readFile(FileHandler.ITEM)).thenReturn(allItemsList);
            mocked.when(() -> FileHandler.updateFile(anyString(), any())).thenAnswer(i -> null);
            mocked.when(() -> FileHandler.appendFile(anyString(), any())).thenAnswer(i -> null);
            mocked.when(() -> FileHandler.printBill(any(Bill.class))).thenAnswer(i -> null);

            // Act
            FileHandler.saveBill(mockBill);

            // Assert
            // Verify quantities are updated
            verify(item1).changeQuantity(-5);
            verify(item2).changeQuantity(-2);

            // Verify file update and append
            mocked.verify(() -> FileHandler.updateFile(FileHandler.ITEM, allItemsList));
            mocked.verify(() -> FileHandler.appendFile(FileHandler.BILL, mockBill));
            mocked.verify(() -> FileHandler.printBill(mockBill));
        }
    }

    @Test
    void testPrintBill_callsPrintWriter() throws Exception {
        // Arrange
        Bill mockBill = mock(Bill.class);
        Cashier mockCashier = mock(Cashier.class);

        when(mockBill.getBillNumber()).thenReturn("B001");
        when(mockBill.getCashier()).thenReturn(mockCashier);
        when(mockCashier.getName()).thenReturn("CashierName");
        when(mockBill.getFormattedDate()).thenReturn("2026-01-21");
        when(mockBill.getItemList()).thenReturn(new ArrayList<>());
        when(mockBill.getPriceList()).thenReturn(new ArrayList<>());
        when(mockBill.getQuantityList()).thenReturn(new ArrayList<>());
        when(mockBill.getTotalPrice()).thenReturn(1000.0);

        try (MockedStatic<FileHandler> mocked = mockStatic(FileHandler.class)) {
            mocked.when(() -> FileHandler.printBill(any(Bill.class))).thenCallRealMethod();

            // Act
            FileHandler.printBill(mockBill);

            // Since PrintWriter writes to file, we are only testing that no exceptions occur
        }
    }
}
