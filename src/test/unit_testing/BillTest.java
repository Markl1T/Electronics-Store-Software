package test.unit_testing;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;

class BillTest {

	private Cashier cashier;
    private Bill bill;

    @BeforeEach
    void setUp() {
        cashier = mock(Cashier.class);
        bill = new Bill(cashier);
    }
    
    // add item
    @Test
    void addItemWhenQuantityIsValid() throws Exception {
        Item item = mock(Item.class);

        when(item.getQuantity()).thenReturn(10);
        when(item.getSellingPrice()).thenReturn(5.0);

        bill.addItem(item, 3);

        assertEquals(1, bill.getItemList().size());
        assertEquals(1, bill.getPriceList().size());
        assertEquals(1, bill.getQuantityList().size());

        assertEquals(item, bill.getItemList().get(0));
        assertEquals(5.0, bill.getPriceList().get(0));
        assertEquals(3, bill.getQuantityList().get(0));
        
    }
    
    @Test
    void addItemAndIncreaseTotalPriceCorrectly() throws Exception {
        Item item = mock(Item.class);

        when(item.getQuantity()).thenReturn(20);
        when(item.getSellingPrice()).thenReturn(2.5);

        bill.addItem(item, 4);

        assertEquals(10.0, bill.getTotalPrice(), 0.0001);
    }
    
    @Test
    void addItemThrowExceptionWhenQuantityIsZero() {
        Item item = mock(Item.class);
        when(item.getQuantity()).thenReturn(5);

        Exception exception = assertThrows(
                InvalidQuantityException.class,
                () -> bill.addItem(item, 0)
        );

        assertEquals("Quantity should be greater than 0", exception.getMessage());
    }
    
    @Test
    void addItemThrowExceptionWhenQuantityIsNegative() {
        Item item = mock(Item.class);
        when(item.getQuantity()).thenReturn(5);

        Exception exception = assertThrows(
                InvalidQuantityException.class,
                () -> bill.addItem(item, -2)
        );

        assertEquals("Quantity should be greater than 0", exception.getMessage());
    }
    
    @Test
    void addItemThrowExceptionWhenQuantityExceedsAvailable() {
        Item item = mock(Item.class);
        when(item.getQuantity()).thenReturn(3);

        Exception exception = assertThrows(
                InvalidQuantityException.class,
                () -> bill.addItem(item, 5)
        );

        assertEquals(
                "Quantity can't be greater than the available quantity",
                exception.getMessage()
        );
    }
    
    // get total quantity
    @Test
    void getTotalQuantityReturnSumOfAllItemQuantities() throws Exception {
        Item item1 = mock(Item.class);
        Item item2 = mock(Item.class);

        when(item1.getQuantity()).thenReturn(10);
        when(item1.getSellingPrice()).thenReturn(2.0);

        when(item2.getQuantity()).thenReturn(20);
        when(item2.getSellingPrice()).thenReturn(3.0);

        bill.addItem(item1, 3);
        bill.addItem(item2, 7);

        assertEquals(10, bill.getTotalQuantity());
    }
    
    
}
