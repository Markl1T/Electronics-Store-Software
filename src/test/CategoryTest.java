package test;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import model.Category;
import model.Item;
import model.Sector;
import java.util.ArrayList;

class CategoryTest {
    
	@Test
    void testSetMinQuantityBoundary() throws Exception {
        Category category = new Category("Electronics", null, 0);
        assertEquals(0, category.getMinQuantity());
        category = new Category("Electronics", null, 1);
        assertEquals(1, category.getMinQuantity());
        category = new Category("Electronics", null, 1000);
        assertEquals(1000, category.getMinQuantity());
    }

    @Test
    void testSetMinQuantityInvalid() {
        Exception exception = assertThrows(Exception.class, () -> {
            new Category("Electronics", null, -1);
        });
        assertTrue(exception.getMessage().contains("Minimum quantity can't be less than 0"));
    }

    @Test
    void testGetTotalQuantityBoundary() throws Exception {
        Sector sector = new Sector("S1", null);
        Category category = new Category("Electronics", sector, 0);
        ArrayList<Item> items = new ArrayList<>();
        // 0 items
        try (MockedStatic<dao.FileHandler> mocked = Mockito.mockStatic(dao.FileHandler.class)) {
            mocked.when(() -> dao.FileHandler.readFile(anyString())).thenReturn(items);
            assertEquals(0, category.getTotalQuantity());
        }
        // 1 item
        Item item1 = Mockito.mock(Item.class);
        Mockito.when(item1.getCategory()).thenReturn(category);
        Mockito.when(item1.getQuantity()).thenReturn(5);
        items.add(item1);
        try (MockedStatic<dao.FileHandler> mocked = Mockito.mockStatic(dao.FileHandler.class)) {
            mocked.when(() -> dao.FileHandler.readFile(anyString())).thenReturn(items);
            assertEquals(5, category.getTotalQuantity());
        }
        // Many items
        Item item2 = Mockito.mock(Item.class);
        Mockito.when(item2.getCategory()).thenReturn(category);
        Mockito.when(item2.getQuantity()).thenReturn(10);
        items.add(item2);
        try (MockedStatic<dao.FileHandler> mocked = Mockito.mockStatic(dao.FileHandler.class)) {
            mocked.when(() -> dao.FileHandler.readFile(anyString())).thenReturn(items);
            assertEquals(15, category.getTotalQuantity());
        }
    }

    @Test
    void testCheckLowQuantityBranches() throws Exception {
        Sector sector = new Sector("S1", null);
        Category category = new Category("Electronics", sector, 5);
        ArrayList<Item> items = new ArrayList<>();
        // getTotalQuantity < minQuantity
        try (MockedStatic<dao.FileHandler> mocked = Mockito.mockStatic(dao.FileHandler.class)) {
            mocked.when(() -> dao.FileHandler.readFile(anyString())).thenReturn(items);
            assertEquals("Low quantity", category.checkLowQuantity());
        }
        // getTotalQuantity >= minQuantity
        Item item1 = Mockito.mock(Item.class);
        Mockito.when(item1.getCategory()).thenReturn(category);
        Mockito.when(item1.getQuantity()).thenReturn(5);
        items.add(item1);
        try (MockedStatic<dao.FileHandler> mocked = Mockito.mockStatic(dao.FileHandler.class)) {
            mocked.when(() -> dao.FileHandler.readFile(anyString())).thenReturn(items);
            assertEquals("", category.checkLowQuantity());
        }
    }

    @Test
    void testSetMinQuantity() throws Exception {
        Category category = new Category("Electronics", null, 7);
        assertEquals(7, category.getMinQuantity());
    }
    @Test
    void testGetNameAndToString() throws Exception {
        Category category = new Category("Electronics", null, 3);
        assertEquals("Electronics", category.getName());
        assertEquals("Electronics", category.toString());
    }
    @Test
    void testGetSector() throws Exception {
        Category category = new Category("Electronics", null, 3);
        assertNull(category.getSector());
    }
}