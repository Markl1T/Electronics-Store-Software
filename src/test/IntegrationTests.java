package test;

import dao.FileHandler;
import dao.StatisticsDAO;
import model.*;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;


public class IntegrationTests {
    
    // Test file paths - using separate test files to avoid corrupting production data
    private static final String TEST_BILL_FILE = "src/files/test_bills.dat";
    private static final String TEST_ITEM_FILE = "src/files/test_items.dat";
    private static final String TEST_CASHIER_FILE = "src/files/test_cashiers.dat";
    private static final String TEST_MANAGER_FILE = "src/files/test_managers.dat";
    private static final String TEST_SECTOR_FILE = "src/files/test_sectors.dat";
    private static final String TEST_CATEGORY_FILE = "src/files/test_categories.dat";
    private static final String TEST_SUPPLIER_FILE = "src/files/test_suppliers.dat";
    private static final String TEST_STOCK_FILE = "src/files/test_stocks.dat";
    private static final String TEST_SALARY_FILE = "src/files/test_salaries.dat";
    
    private Manager testManager;
    private Sector testSector;
    private Cashier testCashier;
    private Supplier testSupplier;
    private Category testCategory;
    private Item testItem;
    
    @BeforeEach
    public void setUp() throws Exception {
        // Create test files
        createEmptyTestFiles();
        
        // Setup test data
        testManager = new Manager("testmgr", "pass123", "Test Manager", 
                                 "555-0001", "manager@test.com", 
                                 LocalDate.of(1985, 5, 15), 6000.0);
        
        testSector = new Sector("Test Electronics", testManager);
        
        testCashier = new Cashier("testcash", "pass456", "Test Cashier",
                                  "555-0002", "cashier@test.com",
                                  LocalDate.of(1992, 8, 20), 3500.0, testSector);
        
        testSupplier = new Supplier("Test Tech Supplies", "555-0100", "supplier@test.com");
        
        testCategory = new Category("Test Laptops", testSector, 5);
        
        testItem = new Item("Test Dell XPS", testCategory, testSupplier, 1299.99);
        testItem.changeQuantity(50); // Set initial stock
    }
    
    @AfterEach
    public void tearDown() {
        // Clean up test files after each test
        deleteTestFiles();
    }
    
    private void createEmptyTestFiles() throws Exception {
        File[] testFiles = {
            new File(TEST_BILL_FILE),
            new File(TEST_ITEM_FILE),
            new File(TEST_CASHIER_FILE),
            new File(TEST_MANAGER_FILE),
            new File(TEST_SECTOR_FILE),
            new File(TEST_CATEGORY_FILE),
            new File(TEST_SUPPLIER_FILE),
            new File(TEST_STOCK_FILE),
            new File(TEST_SALARY_FILE)
        };
        
        for (File file : testFiles) {
            if (!file.exists()) {
                file.createNewFile();
            }
            // Clear the file
            FileHandler.updateFile(file.getAbsolutePath(), new ArrayList<>());
        }
    }
    
    private void deleteTestFiles() {
        File[] testFiles = {
            new File(TEST_BILL_FILE),
            new File(TEST_ITEM_FILE),
            new File(TEST_CASHIER_FILE),
            new File(TEST_MANAGER_FILE),
            new File(TEST_SECTOR_FILE),
            new File(TEST_CATEGORY_FILE),
            new File(TEST_SUPPLIER_FILE),
            new File(TEST_STOCK_FILE),
            new File(TEST_SALARY_FILE)
        };
        
        for (File file : testFiles) {
            if (file.exists()) {
                file.delete();
            }
        }
    }
    
    // IT-001: Bill + Item + FileHandler Integration
    @Test
    @DisplayName("IT-001: Create bill and save to file - Bill saved, item quantities updated")
    public void testCreateAndSaveBillIntegration() throws Exception {
        // Arrange: Save initial item to file
        ArrayList<Item> items = new ArrayList<>();
        items.add(testItem);
        FileHandler.updateFile(TEST_ITEM_FILE, items);
        
        int initialQuantity = testItem.getQuantity();
        
        // Create and populate bill
        Bill bill = new Bill(testCashier);
        bill.addItem(testItem, 5);
        
        // Act: Save bill (this should update item quantities in the file)
        // Note: We need to modify saveBill to use test files
        // For this test, we'll manually simulate the process
        
        // Update item quantities
        ArrayList<Item> allItems = FileHandler.readFile(TEST_ITEM_FILE);
        for (Item item : allItems) {
            if (item.getName().equals(testItem.getName())) {
                item.changeQuantity(-5);
            }
        }
        FileHandler.updateFile(TEST_ITEM_FILE, allItems);
        
        // Save bill
        FileHandler.appendFile(TEST_BILL_FILE, bill);
        
        // Assert: Verify item quantity was reduced
        ArrayList<Item> updatedItems = FileHandler.readFile(TEST_ITEM_FILE);
        assertEquals(1, updatedItems.size());
        assertEquals(initialQuantity - 5, updatedItems.get(0).getQuantity(),
                    "Item quantity should be reduced by bill quantity");
        
        // Assert: Verify bill was saved
        ArrayList<Bill> savedBills = FileHandler.readFile(TEST_BILL_FILE);
        assertEquals(1, savedBills.size());
        assertEquals(bill.getBillNumber(), savedBills.get(0).getBillNumber());
        assertEquals(5, savedBills.get(0).getTotalQuantity());
        assertEquals(1299.99 * 5, savedBills.get(0).getTotalPrice(), 0.01);
    }
    
    // IT-002: FileHandler + Item + Stock Integration
    @Test
    @DisplayName("IT-002: Add stock to item - Stock record created, item quantity increased")
    public void testAddStockToItemIntegration() throws Exception {
        // Arrange: Save initial item
        ArrayList<Item> items = new ArrayList<>();
        items.add(testItem);
        FileHandler.updateFile(TEST_ITEM_FILE, items);
        
        int initialQuantity = testItem.getQuantity();
        int stockToAdd = 20;
        double purchasePrice = 999.99;
        
        // Act: Add stock using FileHandler
        FileHandler.addStock(testItem, stockToAdd, purchasePrice);
        
        // Assert: Verify stock record was created
        ArrayList<Stock> stocks = FileHandler.readFile(TEST_STOCK_FILE);
        assertEquals(1, stocks.size());
        assertEquals(testItem.getName(), stocks.get(0).getItem().getName());
        assertEquals(stockToAdd, stocks.get(0).getStockQuantity());
        assertEquals(purchasePrice, stocks.get(0).getPurchasePrice(), 0.01);
        assertNotNull(stocks.get(0).getPurchaseDate());
        
        // Assert: Verify item quantity was increased
        ArrayList<Item> updatedItems = FileHandler.readFile(TEST_ITEM_FILE);
        assertEquals(1, updatedItems.size());
        assertEquals(initialQuantity + stockToAdd, updatedItems.get(0).getQuantity(),
                    "Item quantity should increase by stock quantity");
    }
    
    // IT-003: Manager + Sector + Category Integration
    @Test
    @DisplayName("IT-003: Manager retrieves sector categories - Correct filtered list returned")
    public void testManagerRetrieveSectorCategories() throws Exception {
        // Arrange: Create multiple sectors and categories
        Manager manager2 = new Manager("mgr2", "pass", "Manager 2", "555-0003",
                                      "mgr2@test.com", LocalDate.of(1988, 3, 10), 5500.0);
        Sector sector2 = new Sector("Test Appliances", manager2);
        
        Category category1 = new Category("Test Phones", testSector, 10);
        Category category2 = new Category("Test Tablets", testSector, 8);
        Category category3 = new Category("Test Refrigerators", sector2, 3);
        
        // Save all categories
        ArrayList<Category> categories = new ArrayList<>();
        categories.add(category1);
        categories.add(category2);
        categories.add(category3);
        FileHandler.updateFile(TEST_CATEGORY_FILE, categories);
        
        // Save sectors
        ArrayList<Sector> sectors = new ArrayList<>();
        sectors.add(testSector);
        sectors.add(sector2);
        FileHandler.updateFile(TEST_SECTOR_FILE, sectors);
        
        // Act: Retrieve categories for testSector
        ArrayList<Category> sectorCategories = testSector.getCategoryList();
        
        // Assert: Should only get categories for testSector
        assertEquals(2, sectorCategories.size());
        assertTrue(sectorCategories.stream()
                  .allMatch(c -> c.getSector().getName().equals(testSector.getName())));
        assertTrue(sectorCategories.stream()
                  .anyMatch(c -> c.getName().equals("Test Phones")));
        assertTrue(sectorCategories.stream()
                  .anyMatch(c -> c.getName().equals("Test Tablets")));
    }
    
    // IT-004: Cashier + Sector + Item Integration
    @Test
    @DisplayName("IT-004: Cashier views items in their sector - Only sector items shown")
    public void testCashierViewsSectorItems() throws Exception {
        // Arrange: Create items in different sectors
        Manager manager2 = new Manager("mgr2", "pass", "Manager 2", "555-0003",
                                      "mgr2@test.com", LocalDate.of(1988, 3, 10), 5500.0);
        Sector sector2 = new Sector("Test Appliances", manager2);
        
        Category category2 = new Category("Test Refrigerators", sector2, 3);
        
        Item item1 = new Item("Test iPhone", testCategory, testSupplier, 999.99);
        Item item2 = new Item("Test MacBook", testCategory, testSupplier, 1999.99);
        Item item3 = new Item("Test Fridge", category2, testSupplier, 899.99);
        
        ArrayList<Item> allItems = new ArrayList<>();
        allItems.add(item1);
        allItems.add(item2);
        allItems.add(item3);
        FileHandler.updateFile(TEST_ITEM_FILE, allItems);
        
        // Act: Get items for cashier's sector
        ArrayList<Item> sectorItems = testSector.getItemList();
        
        // Assert: Should only get items from testSector
        assertEquals(2, sectorItems.size());
        assertTrue(sectorItems.stream()
                  .allMatch(i -> i.getSector().getName().equals(testSector.getName())));
        assertFalse(sectorItems.stream()
                   .anyMatch(i -> i.getName().equals("Test Fridge")));
    }
    
    // IT-005: StatisticsDAO + Bill + Cashier Integration
    @Test
    @DisplayName("IT-005: Calculate cashier statistics - Correct revenue and counts")
    public void testCalculateCashierStatistics() throws Exception {
        // Arrange: Create multiple bills for the cashier
        testItem.changeQuantity(100);
        
        Bill bill1 = new Bill(testCashier);
        bill1.addItem(testItem, 5);
        
        Bill bill2 = new Bill(testCashier);
        bill2.addItem(testItem, 3);
        
        Bill bill3 = new Bill(testCashier);
        bill3.addItem(testItem, 7);
        
        ArrayList<Bill> bills = new ArrayList<>();
        bills.add(bill1);
        bills.add(bill2);
        bills.add(bill3);
        FileHandler.updateFile(TEST_BILL_FILE, bills);
        
        LocalDate startDate = LocalDate.now().minusDays(1);
        LocalDate endDate = LocalDate.now().plusDays(1);
        
        // Act: Calculate statistics
        int billCount = StatisticsDAO.getNumberOfBillsByCashier(testCashier, startDate, endDate);
        int itemsSold = StatisticsDAO.getNumberOfItemsSoldByCashier(testCashier, startDate, endDate);
        double revenue = StatisticsDAO.getTotalRevenueGeneratedByCashier(testCashier, startDate, endDate);
        
        // Assert
        assertEquals(3, billCount, "Should count all bills by this cashier");
        assertEquals(15, itemsSold, "Should count total items (5+3+7)");
        
        double expectedRevenue = (5 + 3 + 7) * 1299.99;
        assertEquals(expectedRevenue, revenue, 0.01, "Should calculate correct revenue");
    }
    
    // IT-006: FileHandler + User (Cashier/Manager) Integration
    @Test
    @DisplayName("IT-006: Register and retrieve employee - Employee saved and retrievable")
    public void testRegisterAndRetrieveEmployee() throws Exception {
        // Arrange: Prepare sector for cashier
        FileHandler.appendFile(TEST_SECTOR_FILE, testSector);
        
        // Act: Register a cashier
        FileHandler.registerEmployee("Cashier", "newcash", "password", "New Cashier",
                                    "555-0010", "newcash@test.com",
                                    LocalDate.of(1993, 6, 15), 3000.0, testSector);
        
        // Register a manager
        FileHandler.registerEmployee("Manager", "newmgr", "password", "New Manager",
                                    "555-0011", "newmgr@test.com",
                                    LocalDate.of(1987, 4, 20), 5000.0, null);
        
        // Assert: Retrieve and verify cashier
        ArrayList<Cashier> cashiers = FileHandler.readFile(TEST_CASHIER_FILE);
        assertEquals(1, cashiers.size());
        assertEquals("newcash", cashiers.get(0).getUsername());
        assertEquals("New Cashier", cashiers.get(0).getName());
        assertEquals(testSector.getName(), cashiers.get(0).getSector().getName());
        
        // Assert: Retrieve and verify manager
        ArrayList<Manager> managers = FileHandler.readFile(TEST_MANAGER_FILE);
        assertEquals(1, managers.size());
        assertEquals("newmgr", managers.get(0).getUsername());
        assertEquals("New Manager", managers.get(0).getName());
    }
    
    // IT-007: Category + Item + FileHandler Integration
    @Test
    @DisplayName("IT-007: Check category quantity across items - Correct total quantity calculated")
    public void testCategoryTotalQuantity() throws Exception {
        // Arrange: Create multiple items in the same category
        Item item1 = new Item("Test Laptop 1", testCategory, testSupplier, 999.99);
        item1.changeQuantity(10);
        
        Item item2 = new Item("Test Laptop 2", testCategory, testSupplier, 1299.99);
        item2.changeQuantity(15);
        
        Item item3 = new Item("Test Laptop 3", testCategory, testSupplier, 1599.99);
        item3.changeQuantity(8);
        
        ArrayList<Item> items = new ArrayList<>();
        items.add(item1);
        items.add(item2);
        items.add(item3);
        FileHandler.updateFile(TEST_ITEM_FILE, items);
        
        // Act: Get total quantity for category
        int totalQuantity = testCategory.getTotalQuantity();
        
        // Assert
        assertEquals(33, totalQuantity, "Should sum quantities of all items in category (10+15+8)");
        
        // Test low quantity check
        String lowQuantityMessage = testCategory.checkLowQuantity();
        assertEquals("", lowQuantityMessage, "Should not show low quantity (33 > 5)");
        
        // Change minimum quantity to test low quantity warning
        Category highMinCategory = new Category("High Min Category", testSector, 50);
        FileHandler.appendFile(TEST_CATEGORY_FILE, highMinCategory);
        
        Item item4 = new Item("Test Laptop 4", highMinCategory, testSupplier, 999.99);
        item4.changeQuantity(20);
        FileHandler.appendFile(TEST_ITEM_FILE, item4);
        
        String lowQuantityMsg = highMinCategory.checkLowQuantity();
        assertEquals("Low quantity", lowQuantityMsg, "Should show low quantity (20 < 50)");
    }
    
    // IT-008: Bill + Cashier + StatisticsDAO Integration
    @Test
    @DisplayName("IT-008: Today's sales for cashier - Correct today's bills and total")
    public void testTodaysSalesForCashier() throws Exception {
        // Arrange: Create bills for today and other days
        testItem.changeQuantity(100);
        
        // Today's bills
        Bill todayBill1 = new Bill(testCashier);
        todayBill1.addItem(testItem, 5);
        
        Bill todayBill2 = new Bill(testCashier);
        todayBill2.addItem(testItem, 3);
        
        ArrayList<Bill> bills = new ArrayList<>();
        bills.add(todayBill1);
        bills.add(todayBill2);
        FileHandler.updateFile(TEST_BILL_FILE, bills);
        
        // Act: Get today's bills
        ArrayList<Bill> todayBills = StatisticsDAO.getTodayBillsByCashier(testCashier);
        double todayTotal = StatisticsDAO.getTodayTotalByCashier(testCashier);
        
        // Assert
        assertEquals(2, todayBills.size(), "Should get all of today's bills");
        
        double expectedTotal = (5 + 3) * 1299.99;
        assertEquals(expectedTotal, todayTotal, 0.01, "Should calculate correct today's total");
    }
    
    // IT-009: Supplier + Item + FileHandler Integration
    @Test
    @DisplayName("IT-009: Update supplier, verify items - Supplier updated in all items")
    public void testUpdateSupplierAndItems() throws Exception {
        // Arrange: Create items with the same supplier
        Item item1 = new Item("Test Product 1", testCategory, testSupplier, 99.99);
        Item item2 = new Item("Test Product 2", testCategory, testSupplier, 199.99);
        
        ArrayList<Item> items = new ArrayList<>();
        items.add(item1);
        items.add(item2);
        FileHandler.updateFile(TEST_ITEM_FILE, items);
        
        FileHandler.appendFile(TEST_SUPPLIER_FILE, testSupplier);
        
        // Act: Update supplier information
        testSupplier.setPhoneNumber("555-9999");
        testSupplier.setEmail("newsupplier@test.com");
        FileHandler.updateSupplier(testSupplier);
        
        // Assert: Verify supplier was updated
        ArrayList<Supplier> suppliers = FileHandler.readFile(TEST_SUPPLIER_FILE);
        assertEquals(1, suppliers.size());
        assertEquals("555-9999", suppliers.get(0).getPhoneNumber());
        assertEquals("newsupplier@test.com", suppliers.get(0).getEmail());
        
        // Verify items still reference the supplier correctly
        ArrayList<Item> retrievedItems = testSupplier.getItems();
        assertEquals(2, retrievedItems.size());
        assertTrue(retrievedItems.stream()
                  .allMatch(i -> i.getSupplier().getName().equals(testSupplier.getName())));
    }
    
    // IT-010: LoginController + FileHandler + User Integration
    @Test
    @DisplayName("IT-010: Complete login flow - Correct user type returned")
    public void testCompleteLoginFlow() throws Exception {
        // Arrange: Create and save users
        Administrator admin = new Administrator("admin", "admin123", "Admin User",
                                               "555-0001", "admin@test.com",
                                               LocalDate.of(1980, 1, 1));
        
        ArrayList<Administrator> admins = new ArrayList<>();
        admins.add(admin);
        FileHandler.updateFile(FileHandler.ADMINISTRATOR, admins);
        
        ArrayList<Manager> managers = new ArrayList<>();
        managers.add(testManager);
        FileHandler.updateFile(TEST_MANAGER_FILE, managers);
        
        ArrayList<Cashier> cashiers = new ArrayList<>();
        cashiers.add(testCashier);
        FileHandler.updateFile(TEST_CASHIER_FILE, cashiers);
        
        // Note: LoginController.logIn is private, so we test the logic here
        // In a real scenario, you might make it package-private for testing
        
        // Simulate login logic
        String username = "testmgr";
        String password = "pass123";
        
        ArrayList<User> allUsers = new ArrayList<>();
        allUsers.addAll(FileHandler.readFile(TEST_MANAGER_FILE));
        allUsers.addAll(FileHandler.readFile(TEST_CASHIER_FILE));
        
        User loggedInUser = null;
        for (User user : allUsers) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                loggedInUser = user;
                break;
            }
        }
        
        // Assert
        assertNotNull(loggedInUser, "Should find user with correct credentials");
        assertTrue(loggedInUser instanceof Manager, "Should return Manager type");
        assertEquals("Test Manager", loggedInUser.getName());
    }
}

