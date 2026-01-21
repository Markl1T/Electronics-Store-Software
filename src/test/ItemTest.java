package test;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.Category;
import model.InvalidPriceException;
import model.InvalidQuantityException;
import model.Item;
import model.Manager;
import model.Sector;
import model.Supplier;

class ItemTest {

    private Sector sampleSector;
    private Category sampleCategory;
    private Supplier sampleSupplier;

    @BeforeEach
    void setup() throws Exception {
        Manager sampleManager;
        sampleManager = new Manager(
                "manager1",
                "1234",
                "Manager 1",
                "123456789",
                "manager1@electronicsstore.com",
                LocalDate.of(1990, 1, 1),
                1000
        );

        sampleSector = new Sector("Electronics", sampleManager);
        sampleCategory = new Category("Phones", sampleSector, 5);
        sampleSupplier = new Supplier("BestSupplier", "1234567890", "supplier@example.com");
    }

    // Constructor Tests

    @Test
    void testConstructorValidPrice() throws Exception {
        Item item = new Item("Phone", sampleCategory, sampleSupplier, 100.0);

        assertEquals("Phone", item.getName());
        assertEquals(sampleCategory, item.getCategory());
        assertEquals(sampleSector, item.getSector());
        assertEquals(sampleSupplier, item.getSupplier());
        assertEquals(100.0, item.getSellingPrice());
        assertEquals(0, item.getQuantity());
    }

    @Test
    void testConstructorInvalidPrice() {
        assertThrows(InvalidPriceException.class,
                () -> new Item("Phone", sampleCategory, sampleSupplier, 0.0));

        assertThrows(InvalidPriceException.class,
                () -> new Item("Phone", sampleCategory, sampleSupplier, -50.0));
    }

    // Selling Price Tests

    @Test
    void testSetSellingPriceValid() throws Exception {
        Item item = new Item("Phone", sampleCategory, sampleSupplier, 100.0);
        item.setSellingPrice(150.0);
        assertEquals(150.0, item.getSellingPrice());
    }

    @Test
    void testSetSellingPriceInvalid() throws Exception {
        Item item = new Item("Phone", sampleCategory, sampleSupplier, 100.0);

        assertThrows(InvalidPriceException.class,
                () -> item.setSellingPrice(-50.0));
    }

    // Quantity Tests

    @Test
    void testChangeQuantityValid() throws Exception {
        Item item = new Item("Phone", sampleCategory, sampleSupplier, 100.0);

        item.changeQuantity(10);
        assertEquals(10, item.getQuantity());

        item.changeQuantity(-5);
        assertEquals(5, item.getQuantity());
    }

    @Test
    void testChangeQuantityInvalid() throws Exception {
        Item item = new Item("Phone", sampleCategory, sampleSupplier, 100.0);
        item.changeQuantity(5);

        assertThrows(InvalidQuantityException.class,
                () -> item.changeQuantity(-10));
    }

    // Stock Tests

    @Test
    void testCheckStockNoStock() throws Exception {
        Item item = new Item("Phone", sampleCategory, sampleSupplier, 100.0);
        assertEquals("No Stock", item.checkStock());
    }

    @Test
    void testCheckStockWithStock() throws Exception {
        Item item = new Item("Phone", sampleCategory, sampleSupplier, 100.0);
        item.changeQuantity(5);
        assertEquals("5", item.checkStock());
    }

    // Supplier Tests

    @Test
    void testSetSupplier() throws Exception {
        Item item = new Item("Phone", sampleCategory, sampleSupplier, 100.0);
        Supplier newSupplier = new Supplier("NewSupplier", "0987654321", "new@example.com");

        item.setSupplier(newSupplier);
        assertEquals(newSupplier, item.getSupplier());
    }
}
