package test;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;


import model.InvalidPriceException;
import model.InvalidQuantityException;
import model.Item;
import model.Stock;

public class StockTest {

    @Test
    void testStockCreationWithValidValues() throws Exception {
    	
    	Item mockItem = mock(Item.class);
        // Act
        Stock stock = new Stock(mockItem, 10, 150.0);

        // Assert
        assertEquals(mockItem, stock.getItem());
        assertEquals(10, stock.getStockQuantity());
        assertEquals(150.0, stock.getPurchasePrice());
    }

    @Test
    void testStockQuantityLessThanOrEqualZeroThrowsException() {
        // Arrange
    	Item mockItem = mock(Item.class);

        // Act & Assert
        InvalidQuantityException exception = assertThrows(
                InvalidQuantityException.class,
                () -> new Stock(mockItem, 0, 100.0)
        );

        assertEquals("Stock quantity should be greater than 0", exception.getMessage());
    }

    @Test
    void testPurchasePriceLessThanOrEqualZeroThrowsException() {
        // Arrange
    	Item mockItem = mock(Item.class);

        // Act & Assert
        InvalidPriceException exception = assertThrows(
                InvalidPriceException.class,
                () -> new Stock(mockItem, 10, 0)
        );

        assertEquals("Stock purchase price should be greater than 0", exception.getMessage());
    }

    @Test
    void testPurchaseDateIsToday() throws Exception {
        // Arrange
    	Item mockItem = mock(Item.class);
        LocalDate today = LocalDate.now();

        // Act
        Stock stock = new Stock(mockItem, 5, 50.0);

        // Assert
        assertEquals(today, stock.getPurchaseDate());
    }
}