package test;


import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import dao.FileHandler;
import model.Bill;
import model.Cashier;
import model.Item;
import model.Manager;
import model.Sector;
import model.Supplier;
import model.Stock;
import model.User;

class FileHandlerTest {

    // -------------------- addStock --------------------
    @Test
    void testAddStock_updatesItemQuantityAndAppendsStock() throws Exception {
        Item mockItem = mock(Item.class);
        when(mockItem.getName()).thenReturn("Laptop");

        ArrayList<Item> mockItemList = new ArrayList<>();
        mockItemList.add(mockItem);

        try (MockedStatic<FileHandler> mocked = mockStatic(FileHandler.class)) {
            mocked.when(() -> FileHandler.readFile(FileHandler.ITEM)).thenReturn(mockItemList);
            mocked.when(() -> FileHandler.updateFile(anyString(), any())).thenAnswer(i -> null);
            mocked.when(() -> FileHandler.appendFile(anyString(), any())).thenAnswer(i -> null);

            FileHandler.addStock(mockItem, 10, 500.0);

            verify(mockItem).changeQuantity(10);
            mocked.verify(() -> FileHandler.updateFile(FileHandler.ITEM, mockItemList));
            mocked.verify(() -> FileHandler.appendFile(eq(FileHandler.STOCK), any(Stock.class)));
        }
    }

    // -------------------- updateItem --------------------
    @Test
    void testUpdateItem_updatesPriceAndSupplier() throws Exception {
        Supplier newSupplier = mock(Supplier.class);
        Item originalItem = mock(Item.class);
        when(originalItem.getName()).thenReturn("Laptop");

        ArrayList<Item> mockItemList = new ArrayList<>();
        mockItemList.add(originalItem);

        Item updatedItem = mock(Item.class);
        when(updatedItem.getName()).thenReturn("Laptop");
        when(updatedItem.getSellingPrice()).thenReturn(1200.0);
        when(updatedItem.getSupplier()).thenReturn(newSupplier);

        try (MockedStatic<FileHandler> mocked = mockStatic(FileHandler.class)) {
            mocked.when(() -> FileHandler.readFile(FileHandler.ITEM)).thenReturn(mockItemList);
            mocked.when(() -> FileHandler.updateFile(anyString(), any())).thenAnswer(i -> null);

            FileHandler.updateItem(updatedItem);

            verify(originalItem).setSellingPrice(1200.0);
            verify(originalItem).setSupplier(newSupplier);
            mocked.verify(() -> FileHandler.updateFile(FileHandler.ITEM, mockItemList));
        }
    }

    // -------------------- updateSupplier --------------------
    @Test
    void testUpdateSupplier_updatesPhoneAndEmail() throws Exception {
        Supplier originalSupplier = mock(Supplier.class);
        when(originalSupplier.getName()).thenReturn("SupplierA");

        Supplier updatedSupplier = mock(Supplier.class);
        when(updatedSupplier.getName()).thenReturn("SupplierA");
        when(updatedSupplier.getPhoneNumber()).thenReturn("123456789");
        when(updatedSupplier.getEmail()).thenReturn("test@example.com");

        ArrayList<Supplier> supplierList = new ArrayList<>();
        supplierList.add(originalSupplier);

        try (MockedStatic<FileHandler> mocked = mockStatic(FileHandler.class)) {
            mocked.when(() -> FileHandler.readFile(FileHandler.SUPPLIER)).thenReturn(supplierList);
            mocked.when(() -> FileHandler.updateFile(anyString(), any())).thenAnswer(i -> null);

            FileHandler.updateSupplier(updatedSupplier);

            verify(originalSupplier).setPhoneNumber("123456789");
            verify(originalSupplier).setEmail("test@example.com");
            mocked.verify(() -> FileHandler.updateFile(FileHandler.SUPPLIER, supplierList));
        }
    }

    // -------------------- updateCashier --------------------
    @Test
    void testUpdateCashier_updatesFields() throws Exception {
        Sector mockSector = mock(Sector.class);
        Cashier original = mock(Cashier.class);
        when(original.getUsername()).thenReturn("cashier1");

        Cashier updated = mock(Cashier.class);
        when(updated.getUsername()).thenReturn("cashier1");
        when(updated.getPassword()).thenReturn("pass");
        when(updated.getPhoneNumber()).thenReturn("111");
        when(updated.getEmail()).thenReturn("cashier@example.com");
        when(updated.getSector()).thenReturn(mockSector);
        when(updated.getCurrentSalary()).thenReturn(1000.0);
        when(updated.getCanAccessToday()).thenReturn(true);
        when(updated.getCanCreateBill()).thenReturn(true);

        ArrayList<Cashier> cashierList = new ArrayList<>();
        cashierList.add(original);

        try (MockedStatic<FileHandler> mocked = mockStatic(FileHandler.class)) {
            mocked.when(() -> FileHandler.readFile(FileHandler.CASHIER)).thenReturn(cashierList);
            mocked.when(() -> FileHandler.updateFile(anyString(), any())).thenAnswer(i -> null);

            FileHandler.updateCashier(updated);

            verify(original).setPassword("pass");
            verify(original).setPhoneNumber("111");
            verify(original).setEmail("cashier@example.com");
            verify(original).setSector(mockSector);
            verify(original).setCurrentSalary(1000.0);
            verify(original).setCanAccessToday(true);
            verify(original).setCanCreateBill(true);

            mocked.verify(() -> FileHandler.updateFile(FileHandler.CASHIER, cashierList));
        }
    }

    // -------------------- updateSector --------------------
    @Test
    void testUpdateSector_updatesManager() throws Exception {
        Manager mockManager = mock(Manager.class);
        Sector original = mock(Sector.class);
        when(original.getName()).thenReturn("First Floor");

        Sector updated = mock(Sector.class);
        when(updated.getName()).thenReturn("First Floor");
        when(updated.getManager()).thenReturn(mockManager);

        ArrayList<Sector> sectorList = new ArrayList<>();
        sectorList.add(original);

        try (MockedStatic<FileHandler> mocked = mockStatic(FileHandler.class)) {
            mocked.when(() -> FileHandler.readFile(FileHandler.SECTOR)).thenReturn(sectorList);
            mocked.when(() -> FileHandler.updateFile(anyString(), any())).thenAnswer(i -> null);

            FileHandler.updateSector(updated);

            verify(original).setManager(mockManager);
            mocked.verify(() -> FileHandler.updateFile(FileHandler.SECTOR, sectorList));
        }
    }

    // -------------------- updateManager --------------------
    @Test
    void testUpdateManager_updatesAllFields() throws Exception {
        Manager original = mock(Manager.class);
        when(original.getUsername()).thenReturn("manager1");

        Manager updated = mock(Manager.class);
        when(updated.getUsername()).thenReturn("manager1");
        when(updated.getPassword()).thenReturn("pass");
        when(updated.getPhoneNumber()).thenReturn("111");
        when(updated.getEmail()).thenReturn("manager@example.com");
        when(updated.getCurrentSalary()).thenReturn(2000.0);
        when(updated.getCanAccessStock()).thenReturn(true);
        when(updated.getCanAccessItems()).thenReturn(true);
        when(updated.getCanAccessCategories()).thenReturn(true);
        when(updated.getCanAccessCashiers()).thenReturn(true);
        when(updated.getCanAccessSuppliers()).thenReturn(true);

        ArrayList<Manager> managerList = new ArrayList<>();
        managerList.add(original);

        try (MockedStatic<FileHandler> mocked = mockStatic(FileHandler.class)) {
            mocked.when(() -> FileHandler.readFile(FileHandler.MANAGER)).thenReturn(managerList);
            mocked.when(() -> FileHandler.updateFile(anyString(), any())).thenAnswer(i -> null);

            FileHandler.updateManager(updated);

            verify(original).setPassword("pass");
            verify(original).setPhoneNumber("111");
            verify(original).setEmail("manager@example.com");
            verify(original).setCurrentSalary(2000.0);
            verify(original).setCanAccessStock(true);
            verify(original).setCanAccessItems(true);
            verify(original).setCanAccessCategories(true);
            verify(original).setCanAccessCashiers(true);
            verify(original).setCanAccessSuppliers(true);

            mocked.verify(() -> FileHandler.updateFile(FileHandler.MANAGER, managerList));
        }
    }

    // -------------------- deleteUser --------------------
    @Test
    void testDeleteUser_removesCashierOrManager() throws Exception {
        Cashier cashier = mock(Cashier.class);
        when(cashier.getUsername()).thenReturn("c1");

        ArrayList<Cashier> cashierList = new ArrayList<>();
        cashierList.add(cashier);

        try (MockedStatic<FileHandler> mocked = mockStatic(FileHandler.class)) {
            mocked.when(() -> FileHandler.readFile(FileHandler.CASHIER)).thenReturn(cashierList);
            mocked.when(() -> FileHandler.updateFile(anyString(), any())).thenAnswer(i -> null);

            FileHandler.deleteUser(cashier);

            // List should have removed the cashier (verified via updateFile call)
            mocked.verify(() -> FileHandler.updateFile(FileHandler.CASHIER, cashierList));
        }
    }

    // -------------------- deleteSupplier --------------------
    @Test
    void testDeleteSupplier_removesSupplier() throws Exception {
        Supplier supplier = mock(Supplier.class);
        when(supplier.getName()).thenReturn("S1");

        ArrayList<Supplier> list = new ArrayList<>();
        list.add(supplier);

        try (MockedStatic<FileHandler> mocked = mockStatic(FileHandler.class)) {
            mocked.when(() -> FileHandler.readFile(FileHandler.SUPPLIER)).thenReturn(list);
            mocked.when(() -> FileHandler.updateFile(anyString(), any())).thenAnswer(i -> null);

            FileHandler.delete(supplier);

            mocked.verify(() -> FileHandler.updateFile(FileHandler.SUPPLIER, list));
        }
    }
}