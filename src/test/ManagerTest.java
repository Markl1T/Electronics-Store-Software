package test;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import model.Manager;
import model.Sector;
import model.Cashier;
import java.util.List;
import java.util.ArrayList;

class ManagerTest {
    @Test
    void testGetSectorListBoundary() throws Exception {
        java.time.LocalDate dob = java.time.LocalDate.of(1990, 1, 1);
        Manager manager = new Manager("user1", "pass", "John Doe", "123", "john@example.com", dob, 5000);
        ArrayList<Sector> allSectors = new ArrayList<>();
        // No sectors assigned
        try (MockedStatic<dao.FileHandler> mocked = Mockito.mockStatic(dao.FileHandler.class)) {
            mocked.when(() -> dao.FileHandler.readFile(anyString())).thenReturn(allSectors);
            List<Sector> sectors = manager.getSectorList();
            assertEquals(0, sectors.size());
        }
        // One sector assigned
        Sector sector1 = Mockito.mock(Sector.class);
        Mockito.when(sector1.getManager()).thenReturn(manager);
        Mockito.when(sector1.getName()).thenReturn("S1");
        allSectors.add(sector1);
        try (MockedStatic<dao.FileHandler> mocked = Mockito.mockStatic(dao.FileHandler.class)) {
            mocked.when(() -> dao.FileHandler.readFile(anyString())).thenReturn(allSectors);
            List<Sector> sectors = manager.getSectorList();
            assertEquals(1, sectors.size());
            assertEquals("S1", sectors.get(0).getName());
        }
    }

    @Test
    void testGetSectorListEquivalence() throws Exception {
        java.time.LocalDate dob = java.time.LocalDate.of(1991, 2, 2);
        Manager manager = new Manager("user2", "pass", "Jane Doe", "456", "jane@example.com", dob, 6000);
        ArrayList<Sector> allSectors = new ArrayList<>();
        // Valid manager with sectors
        Sector sector1 = Mockito.mock(Sector.class);
        Mockito.when(sector1.getManager()).thenReturn(manager);
        Mockito.when(sector1.getName()).thenReturn("S1");
        allSectors.add(sector1);
        // Invalid manager (no sectors)
        Manager otherManager = new Manager("other", "pass", "Other", "000", "other@example.com", dob, 6000);
        Sector sector2 = Mockito.mock(Sector.class);
        Mockito.when(sector2.getManager()).thenReturn(otherManager);
        Mockito.when(sector2.getName()).thenReturn("S2");
        allSectors.add(sector2);
        try (MockedStatic<dao.FileHandler> mocked = Mockito.mockStatic(dao.FileHandler.class)) {
            mocked.when(() -> dao.FileHandler.readFile(anyString())).thenReturn(allSectors);
            List<Sector> sectors = manager.getSectorList();
            assertEquals(1, sectors.size());
            assertEquals("S1", sectors.get(0).getName());
        }
    }

    @Test
    void testGetCashierListCoverage() throws Exception {
        java.time.LocalDate dob = java.time.LocalDate.of(1992, 3, 3);
        Manager manager = new Manager("user3", "pass", "Jim Doe", "789", "jim@example.com", dob, 7000);
        // Setup sectors
        Sector sector1 = Mockito.mock(Sector.class);
        Mockito.when(sector1.getManager()).thenReturn(manager);
        Mockito.when(sector1.getName()).thenReturn("S1");
        ArrayList<Sector> allSectors = new ArrayList<>();
        allSectors.add(sector1);
        // Setup cashiers
        ArrayList<Cashier> allCashiers = new ArrayList<>();
        Cashier cashier1 = Mockito.mock(Cashier.class);
        Mockito.when(cashier1.getSector()).thenReturn(sector1);
        Mockito.when(cashier1.getSector().getName()).thenReturn("S1");
        allCashiers.add(cashier1);
        // Mock FileHandler.readFile for SECTOR and CASHIER
        try (MockedStatic<dao.FileHandler> mocked = Mockito.mockStatic(dao.FileHandler.class)) {
            mocked.when(() -> dao.FileHandler.readFile(dao.FileHandler.SECTOR)).thenReturn(allSectors);
            mocked.when(() -> dao.FileHandler.readFile(dao.FileHandler.CASHIER)).thenReturn(allCashiers);
            List<Cashier> cashiers = manager.getCashierList();
            assertEquals(1, cashiers.size());
        }
        // No cashiers assigned
        try (MockedStatic<dao.FileHandler> mocked = Mockito.mockStatic(dao.FileHandler.class)) {
            mocked.when(() -> dao.FileHandler.readFile(dao.FileHandler.SECTOR)).thenReturn(allSectors);
            mocked.when(() -> dao.FileHandler.readFile(dao.FileHandler.CASHIER)).thenReturn(new ArrayList<>());
            List<Cashier> cashiers = manager.getCashierList();
            assertEquals(0, cashiers.size());
        }
    }

    @Test
    void testManagerConstructorAndGetters() throws Exception {
        java.time.LocalDate dob = java.time.LocalDate.of(1990, 1, 1);
        Manager manager = new Manager("user4", "pass", "John", "000", "john@example.com", dob, 5000);
        assertEquals("user4", manager.getUsername());
        assertEquals("pass", manager.getPassword());
        assertEquals("John", manager.getName());
        assertEquals("000", manager.getPhoneNumber());
        assertEquals("john@example.com", manager.getEmail());
        assertEquals(dob, manager.getDateOfBirth());
        assertEquals(5000, manager.getCurrentSalary());
    }

    @Test
    void testManagerAccessFlags() throws Exception {
        Manager manager = new Manager("user5", "pass", "Jane", "111", "jane@example.com", java.time.LocalDate.of(1995, 5, 5), 5000);
        assertTrue(manager.getCanAccessStock());
        assertTrue(manager.getCanAccessItems());
        assertTrue(manager.getCanAccessCategories());
        assertTrue(manager.getCanAccessCashiers());
        assertTrue(manager.getCanAccessSuppliers());
        manager.setCanAccessStock(false);
        manager.setCanAccessItems(false);
        manager.setCanAccessCategories(false);
        manager.setCanAccessCashiers(false);
        manager.setCanAccessSuppliers(false);
        assertFalse(manager.getCanAccessStock());
        assertFalse(manager.getCanAccessItems());
        assertFalse(manager.getCanAccessCategories());
        assertFalse(manager.getCanAccessCashiers());
        assertFalse(manager.getCanAccessSuppliers());
    }
    
    @Test
    void testToString() throws Exception {
        Manager manager = new Manager("user6", "pass", "Alice", "222", "alice@example.com", java.time.LocalDate.of(1992, 2, 2), 6000);
        assertEquals("Alice", manager.toString());
    }
}