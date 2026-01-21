import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import src.model.Category;
import src.model.Item;

class CategoryTest {
    // Boundary Value Testing for setMinQuantity
    @Test
    void testSetMinQuantityBoundary() throws Exception {
        Category category = new Category("Electronics", null, 0);
        assertEquals(0, category.getMinQuantity());
        category = new Category("Electronics", null, 1);
        assertEquals(1, category.getMinQuantity());
        category = new Category("Electronics", null, 1000);
        assertEquals(1000, category.getMinQuantity());
    }

    // Equivalence Class Testing for setMinQuantity
    @Test
    void testSetMinQuantityInvalid() {
        Exception exception = assertThrows(Exception.class, () -> {
            new Category("Electronics", null, -1);
        });
        assertTrue(exception.getMessage().contains("Minimum quantity can't be less than 0"));
    }

    // Boundary Value Testing for getTotalQuantity
    @Test
    void testGetTotalQuantityBoundary() throws Exception {
        // Mocking FileHandler.readFile and Item is needed for real test
        // Here, just a placeholder for boundary values
        // Assume category with 0, 1, and max quantity items
        // ...existing code...
    }

    // Code Coverage Testing for checkLowQuantity
    @Test
    void testCheckLowQuantityBranches() throws Exception {
        Category category = new Category("Electronics", null, 5);
        // Simulate getTotalQuantity < minQuantity
        // ...existing code...
        // Simulate getTotalQuantity >= minQuantity
        // ...existing code...
    }

    // Original tests (adapted to constructor)
    @Test
    void testSetMinQuantity() throws Exception {
        Category category = new Category("Electronics", null, 7);
        assertEquals(7, category.getMinQuantity());
    }
}
