package test;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import dao.FileHandler;
import model.Category;
import model.Item;
import model.Sector;

public class SectorTest {

    @Test
    void testGetCategoryList_filtersBySector() throws Exception {
        // Arrange
        Sector firstFloor = new Sector("Fisrt Floor", null);
        Sector secondFloor = new Sector("Second Floor", null);

        Category c1 = mock(Category.class);
        when(c1.getSector()).thenReturn(firstFloor);

        Category c2 = mock(Category.class);
        when(c2.getSector()).thenReturn(secondFloor);

        ArrayList<Category> mockCategories = new ArrayList<>();
        mockCategories.add(c1);
        mockCategories.add(c2);

        try (MockedStatic<FileHandler> mockedFileHandler = mockStatic(FileHandler.class)) {
            mockedFileHandler
                .when(() -> FileHandler.readFile(FileHandler.CATEGORY))
                .thenReturn(mockCategories);

            // Act
            ArrayList<Category> result = firstFloor.getCategoryList();

            // Assert
            assertEquals(1, result.size());
            assertTrue(result.contains(c1));
            assertFalse(result.contains(c2));
        }
    }

    @Test
    void testGetItemList_filtersBySector() throws Exception {
        // Arrange
        Sector firstFloor = new Sector("First Floor", null);
        Sector secondFloor = new Sector("Second Floor", null);

        Item i1 = mock(Item.class);
        when(i1.getSector()).thenReturn(firstFloor);

        Item i2 = mock(Item.class);
        when(i2.getSector()).thenReturn(secondFloor);

        ArrayList<Item> mockItems = new ArrayList<>();
        mockItems.add(i1);
        mockItems.add(i2);

        try (MockedStatic<FileHandler> mockedFileHandler = mockStatic(FileHandler.class)) {
            mockedFileHandler
                .when(() -> FileHandler.readFile(FileHandler.ITEM))
                .thenReturn(mockItems);

            // Act
            ArrayList<Item> result = firstFloor.getItemList();

            // Assert
            assertEquals(1, result.size());
            assertTrue(result.contains(i1));
            assertFalse(result.contains(i2));
        }
    }
}