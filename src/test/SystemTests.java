package test;

import dao.FileHandler;
import dao.StatisticsDAO;
import model.*;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;

public class SystemTests {
    
    private static final String TEST_PREFIX = "src/files/test_";
    
    private Manager systemManager;
    private Sector systemSector;
    private Cashier systemCashier;
    private Supplier systemSupplier;
    private Category systemCategory;
    
    @BeforeAll
    public static void setUpClass() throws Exception {
        // Create test directory if it doesn't exist
        File testDir = new File("src/files");
        if (!testDir.exists()) {
            testDir.mkdirs();
        }
    }
    
    @BeforeEach
    public void setUp() throws Exception {
        // Clear all test files
        clearAllTestFiles();
        
        // Setup complete system data
        systemManager = new Manager("sysmgr", "pass", "System Manager",
                                   "555-1000", "sysmgr@test.com",
                                   LocalDate.of(1985, 5, 15), 6000.0);
        
        systemSector = new Sector("Electronics", systemManager);
        
        systemCashier = new Cashier("syscash", "pass", "System Cashier",
                                   "555-2000", "syscash@test.com",
                                   LocalDate.of(1992, 8, 20), 3500.0, systemSector);
        
        systemSupplier = new Supplier("Tech Supplies Inc", "555-3000", "supplier@tech.com");
        
        systemCategory = new Category("Laptops", systemSector, 5);
    }
    
    @AfterEach
    public void tearDown() {
        clearAllTestFiles();
    }
    
    private void clearAllTestFiles() {
        String[] fileTypes = {"bills", "items", "cashiers", "managers", "sectors",
                            "categories", "suppliers", "stocks", "salaries"};
        
        for (String fileType : fileTypes) {
            File file = new File(TEST_PREFIX + fileType + ".dat");
            if (file.exists()) {
                file.delete();
            }
        }
    }
    
    // ST-001: Complete Bill Creation Flow
    @Test
    @DisplayName("ST-001: Complete Bill Creation Flow")
    public void testCompleteBillCreationFlow() throws Exception {
        System.out.println("SYSTEM TEST 001: Complete Bill Creation Flow");
        
        // Step 1: Login as cashier (simulated)
        System.out.println("Step 1: Cashier login simulated");
        assertNotNull(systemCashier);
        assertTrue(systemCashier.getCanCreateBill());
        
        // Step 2: Navigate to New Bill (simulated by creating bill)
        System.out.println("Step 2: Navigate to New Bill");
        Bill bill = new Bill(systemCashier);
        assertNotNull(bill.getBillNumber());
        
        // Step 3: Setup and search for item
        System.out.println("Step 3: Search for item");
        Item laptop = new Item("Dell XPS 15", systemCategory, systemSupplier, 1499.99);
        laptop.changeQuantity(20);
        
        ArrayList<Item> items = new ArrayList<>();
        items.add(laptop);
        FileHandler.updateFile(TEST_PREFIX + "items.dat", items);
        
        // Simulate search
        ArrayList<Item> searchResults = FileHandler.readFile(TEST_PREFIX + "items.dat");
        assertEquals(1, searchResults.size());
        System.out.println("Found item: " + searchResults.get(0).getName());
        
        // Step 4: Add item to bill
        System.out.println("Step 4: Add item to bill");
        int quantityToPurchase = 3;
        bill.addItem(laptop, quantityToPurchase);
        assertEquals(1, bill.getItemList().size());
        assertEquals(quantityToPurchase, bill.getTotalQuantity());
        System.out.println("Added " + quantityToPurchase + " items to bill");
        
        // Step 5: Save bill
        System.out.println("Step 5: Save bill");
        int initialStock = laptop.getQuantity();
        FileHandler.saveBill(bill);
        
        // Verify bill was saved
        ArrayList<Bill> savedBills = FileHandler.readFile(TEST_PREFIX + "bills.dat");
        assertEquals(1, savedBills.size());
        System.out.println("Bill saved with number: " + savedBills.get(0).getBillNumber());
        
        // Verify item stock was reduced
        ArrayList<Item> updatedItems = FileHandler.readFile(TEST_PREFIX + "items.dat");
        assertEquals(initialStock - quantityToPurchase, updatedItems.get(0).getQuantity());
        System.out.println("Stock reduced from " + initialStock + " to " + 
                         updatedItems.get(0).getQuantity());
        
        // Verify bill file was created
        File billFile = new File("src/bills/" + bill.getBillNumber() + ".txt");
        assertTrue(billFile.exists(), "Physical bill file should be created");
        System.out.println("Physical bill file created");
        
    }
    
    // ST-002: Employee Registration Flow
    @Test
    @DisplayName("ST-002: Employee Registration Flow")
    public void testEmployeeRegistrationFlow() throws Exception {
        System.out.println("SYSTEM TEST 002: Employee Registration Flow");
        
        // Step 1: Login as admin (simulated)
        System.out.println("Step 1: Admin login simulated");
        Administrator admin = new Administrator("admin", "admin123", "Admin",
                                               "555-0000", "admin@test.com",
                                               LocalDate.now().minusYears(30));
        
        // Step 2: Navigate to New Employee (simulated)
        System.out.println("Step 2: Navigate to New Employee");
        
        // Step 3: Fill form and register cashier
        System.out.println("Step 3: Fill employee form");
        FileHandler.appendFile(TEST_PREFIX + "sectors.dat", systemSector);
        
        String role = "Cashier";
        String username = "newcashier";
        String password = "password123";
        String name = "New Cashier Employee";
        String phone = "555-4000";
        String email = "newcashier@test.com";
        LocalDate birthdate = LocalDate.of(1995, 3, 10);
        double salary = 3200.0;
        
        // Step 4: Register employee
        System.out.println("Step 4: Register employee");
        FileHandler.registerEmployee(role, username, password, name, phone,
                                    email, birthdate, salary, systemSector);
        
        // Step 5: Verify in employee list
        System.out.println("Step 5: Verify employee appears in list");
        ArrayList<Cashier> cashiers = FileHandler.readFile(TEST_PREFIX + "cashiers.dat");
        assertEquals(1, cashiers.size());
        
        Cashier newCashier = cashiers.get(0);
        assertEquals(username, newCashier.getUsername());
        assertEquals(name, newCashier.getName());
        assertEquals(email, newCashier.getEmail());
        assertEquals(salary, newCashier.getCurrentSalary(), 0.01);
        assertEquals(systemSector.getName(), newCashier.getSector().getName());
        
        System.out.println("Employee registered: " + newCashier.getName());
        System.out.println("Username: " + newCashier.getUsername());
        System.out.println("Salary: $" + newCashier.getCurrentSalary());
        
        // Test manager registration
        System.out.println("Registering manager...");
        FileHandler.registerEmployee("Manager", "newmgr", "pass", "New Manager",
                                    "555-5000", "newmgr@test.com",
                                    LocalDate.of(1988, 7, 20), 5500.0, null);
        
        ArrayList<Manager> managers = FileHandler.readFile(TEST_PREFIX + "managers.dat");
        assertEquals(1, managers.size());
        assertEquals("New Manager", managers.get(0).getName());
        System.out.println("Manager registered: " + managers.get(0).getName());
        
    }
    
    // ST-003: Stock Management Flow
    @Test
    @DisplayName("ST-003: Stock Management Flow")
    public void testStockManagementFlow() throws Exception {
        System.out.println("SYSTEM TEST 003: Stock Management Flow");
        
     // Step 1: Login as manager
        System.out.println("Step 1: Manager login simulated");
        assertTrue(systemManager.getCanAccessStock());
        
        // Step 2: Create item first
        System.out.println("Step 2: Create item to add stock to");
        Item laptop = new Item("HP Pavilion", systemCategory, systemSupplier, 899.99);
        laptop.changeQuantity(5); // Initial stock
        FileHandler.appendFile(TEST_PREFIX + "items.dat", laptop);
        
        int initialQuantity = laptop.getQuantity();
        System.out.println("Initial stock: " + initialQuantity);
        
        // Step 3: Navigate to New Stock
        System.out.println("Step 3: Navigate to New Stock");
        
        // Step 4: Enter quantity and price
        System.out.println("Step 4: Enter stock details");
        int stockQuantity = 15;
        double purchasePrice = 750.00;
        
        // Step 5: Add stock
        System.out.println("Step 5: Add stock");
        FileHandler.addStock(laptop, stockQuantity, purchasePrice);
        
        // Verify stock record created
        ArrayList<Stock> stocks = FileHandler.readFile(TEST_PREFIX + "stocks.dat");
        assertEquals(1, stocks.size());
        assertEquals(stockQuantity, stocks.get(0).getStockQuantity());
        assertEquals(purchasePrice, stocks.get(0).getPurchasePrice(), 0.01);
        System.out.println("Stock record created: " + stockQuantity + " units at $" + purchasePrice);
        
        // Verify item quantity updated
        ArrayList<Item> items = FileHandler.readFile(TEST_PREFIX + "items.dat");
        assertEquals(1, items.size());
        assertEquals(initialQuantity + stockQuantity, items.get(0).getQuantity());
        System.out.println("Item quantity updated to: " + items.get(0).getQuantity());
        
    }

    // ST-004: Statistics Generation Flow
    @Test
    @DisplayName("ST-004: Statistics Generation Flow")
    public void testStatisticsGenerationFlow() throws Exception {
        System.out.println("SYSTEM TEST 004: Statistics Generation Flow");
        
        // Step 1: Login as admin
        System.out.println("Step 1: Admin login simulated");
        
        // Setup test data
        Item laptop = new Item("MacBook Pro", systemCategory, systemSupplier, 2499.99);
        laptop.changeQuantity(50);
        FileHandler.appendFile(TEST_PREFIX + "items.dat", laptop);
        
        // Create bills
        Bill bill1 = new Bill(systemCashier);
        bill1.addItem(laptop, 2);
        
        Bill bill2 = new Bill(systemCashier);
        bill2.addItem(laptop, 3);
        
        FileHandler.saveBill(bill1);
        FileHandler.saveBill(bill2);
        
        // Create stock records
        Stock stock1 = new Stock(laptop, 20, 2000.00);
        FileHandler.appendFile(TEST_PREFIX + "stocks.dat", stock1);
        
        // Create salary records
        Salary salary = new Salary(systemCashier, 3500.0);
        FileHandler.appendFile(TEST_PREFIX + "salaries.dat", salary);
        
        // Step 2: Navigate to Stats
        System.out.println("Step 2: Navigate to Statistics");
        
        // Step 3: Select date range
        System.out.println("Step 3: Select date range");
        LocalDate startDate = LocalDate.now().minusDays(1);
        LocalDate endDate = LocalDate.now().plusDays(1);
        
        // Step 4: View statistics
        System.out.println("Step 4: Calculate statistics");
        double totalRevenue = StatisticsDAO.getTotalRevenue(startDate, endDate);
        double itemCosts = StatisticsDAO.getItemCosts(startDate, endDate);
        double staffCosts = StatisticsDAO.getStaffCosts(startDate, endDate);
        double totalCosts = StatisticsDAO.getTotalCosts(startDate, endDate);
        double totalProfits = StatisticsDAO.getTotalProfits(startDate, endDate);
        
        // Verify calculations
        double expectedRevenue = (2 + 3) * 2499.99;
        assertEquals(expectedRevenue, totalRevenue, 0.01);
        System.out.println("Total Revenue: $" + totalRevenue);
        
        double expectedItemCosts = 20 * 2000.00;
        assertEquals(expectedItemCosts, itemCosts, 0.01);
        System.out.println("Item Costs: $" + itemCosts);
        
        assertEquals(3500.0, staffCosts, 0.01);
        System.out.println("Staff Costs: $" + staffCosts);
        
        assertEquals(expectedItemCosts + 3500.0, totalCosts, 0.01);
        System.out.println("Total Costs: $" + totalCosts);
        
        assertEquals(expectedRevenue - totalCosts, totalProfits, 0.01);
        System.out.println("Total Profits: $" + totalProfits);
        
    }

    // ST-005: Sector Management Flow
    @Test
    @DisplayName("ST-005: Sector Management Flow")
    public void testSectorManagementFlow() throws Exception {
        System.out.println("SYSTEM TEST 005: Sector Management Flow");
        
        // Step 1: Login as admin and create sector
        System.out.println("Step 1: Create sector");
        FileHandler.appendFile(TEST_PREFIX + "sectors.dat", systemSector);
        
        // Step 2: Assign manager
        System.out.println("Step 2: Manager assigned to sector");
        assertEquals(systemManager.getName(), systemSector.getManager().getName());
        
        // Step 3: Add categories to sector
        System.out.println("Step 3: Add categories to sector");
        Category cat1 = new Category("Laptops", systemSector, 5);
        Category cat2 = new Category("Phones", systemSector, 10);
        
        FileHandler.appendFile(TEST_PREFIX + "categories.dat", cat1);
        FileHandler.appendFile(TEST_PREFIX + "categories.dat", cat2);
        
        // Step 4: Add items
        System.out.println("Step 4: Add items to categories");
        Item laptop = new Item("Dell XPS", cat1, systemSupplier, 1299.99);
        Item phone = new Item("iPhone", cat2, systemSupplier, 999.99);
        
        FileHandler.appendFile(TEST_PREFIX + "items.dat", laptop);
        FileHandler.appendFile(TEST_PREFIX + "items.dat", phone);
        
        // Step 5: Verify complete sector hierarchy
        System.out.println("Step 5: Verify sector hierarchy");
        ArrayList<Category> sectorCategories = systemSector.getCategoryList();
        assertEquals(2, sectorCategories.size());
        System.out.println("Categories in sector: " + sectorCategories.size());
        
        ArrayList<Item> sectorItems = systemSector.getItemList();
        assertEquals(2, sectorItems.size());
        System.out.println("Items in sector: " + sectorItems.size());
        
        assertTrue(sectorItems.stream()
                  .allMatch(i -> i.getSector().getName().equals(systemSector.getName())));
        
        System.out.println("Sector hierarchy created successfully");
    }

    // ST-006: Cashier Today View
    @Test
    @DisplayName("ST-006: Cashier Today View")
    public void testCashierTodayView() throws Exception {
        System.out.println("SYSTEM TEST 006: Cashier Today View");
        
        // Step 1: Login as cashier
        System.out.println("Step 1: Cashier login");
        assertTrue(systemCashier.getCanAccessToday());
        
        // Step 2: Create multiple bills
        System.out.println("Step 2: Create multiple bills");
        Item item = new Item("Test Item", systemCategory, systemSupplier, 99.99);
        item.changeQuantity(100);
        FileHandler.appendFile(TEST_PREFIX + "items.dat", item);
        
        Bill bill1 = new Bill(systemCashier);
        bill1.addItem(item, 5);
        
        Bill bill2 = new Bill(systemCashier);
        bill2.addItem(item, 3);
        
        Bill bill3 = new Bill(systemCashier);
        bill3.addItem(item, 7);
        
        FileHandler.saveBill(bill1);
        FileHandler.saveBill(bill2);
        FileHandler.saveBill(bill3);
        
        // Step 3: View Today tab
        System.out.println("Step 3: View today's bills");
        ArrayList<Bill> todayBills = StatisticsDAO.getTodayBillsByCashier(systemCashier);
        
        // Verify all today's bills shown
        assertEquals(3, todayBills.size());
        System.out.println("Today's bills count: " + todayBills.size());
        
        // Verify correct total
        double todayTotal = StatisticsDAO.getTodayTotalByCashier(systemCashier);
        double expectedTotal = (5 + 3 + 7) * 99.99;
        assertEquals(expectedTotal, todayTotal, 0.01);
        System.out.println("Today's total: $" + todayTotal);
        
        // Verify all bills are from today
        assertTrue(todayBills.stream()
                  .allMatch(b -> b.getDate().toLocalDate().equals(LocalDate.now())));
        System.out.println("All bills are from today: verified");
        
    }

    // ST-007: Manager Permissions Test
    @Test
    @DisplayName("ST-007: Manager Permissions Test")
    public void testManagerPermissions() throws Exception {
        System.out.println("SYSTEM TEST 007: Manager Permissions Test");
        
        // Step 1: Create manager with limited permissions
        System.out.println("Step 1: Create manager with limited permissions");
        Manager limitedManager = new Manager("limitedmgr", "pass", "Limited Manager",
                                            "555-6000", "limited@test.com",
                                            LocalDate.of(1990, 1, 1), 5000.0);
        
        // Disable certain permissions
        limitedManager.setCanAccessStock(false);
        limitedManager.setCanAccessSuppliers(false);
        
        System.out.println("Permissions set:");
        System.out.println("  Can Access Stock: " + limitedManager.getCanAccessStock());
        System.out.println("  Can Access Items: " + limitedManager.getCanAccessItems());
        System.out.println("  Can Access Categories: " + limitedManager.getCanAccessCategories());
        System.out.println("  Can Access Cashiers: " + limitedManager.getCanAccessCashiers());
        System.out.println("  Can Access Suppliers: " + limitedManager.getCanAccessSuppliers());
        
        // Step 2: Attempt restricted actions (simulated)
        System.out.println("Step 2: Verify permission restrictions");
        
        // Verify stock access is disabled
        assertFalse(limitedManager.getCanAccessStock());
        System.out.println("✓ Stock access properly restricted");
        
        // Verify supplier access is disabled
        assertFalse(limitedManager.getCanAccessSuppliers());
        System.out.println("✓ Supplier access properly restricted");
        
        // Verify allowed actions still work
        assertTrue(limitedManager.getCanAccessItems());
        assertTrue(limitedManager.getCanAccessCategories());
        assertTrue(limitedManager.getCanAccessCashiers());
        System.out.println("✓ Allowed actions remain accessible");
        
        // Save and reload to verify persistence
        FileHandler.appendFile(TEST_PREFIX + "managers.dat", limitedManager);
        ArrayList<Manager> managers = FileHandler.readFile(TEST_PREFIX + "managers.dat");
        
        Manager retrieved = managers.get(0);
        assertFalse(retrieved.getCanAccessStock());
        assertFalse(retrieved.getCanAccessSuppliers());
        assertTrue(retrieved.getCanAccessItems());
        System.out.println("✓ Permissions persist after save/load");
        
    }

    // ST-008: Item Search and Update
    @Test
    @DisplayName("ST-008: Item Search and Update")
    public void testItemSearchAndUpdate() throws Exception {
        System.out.println("SYSTEM TEST 008: Item Search and Update");
        
        // Step 1: Login as manager
        System.out.println("Step 1: Manager login");
        
        // Create multiple items
        Item item1 = new Item("Dell Laptop", systemCategory, systemSupplier, 999.99);
        Item item2 = new Item("HP Laptop", systemCategory, systemSupplier, 899.99);
        Item item3 = new Item("Lenovo Laptop", systemCategory, systemSupplier, 799.99);
        
        FileHandler.appendFile(TEST_PREFIX + "items.dat", item1);
        FileHandler.appendFile(TEST_PREFIX + "items.dat", item2);
        FileHandler.appendFile(TEST_PREFIX + "items.dat", item3);
        
        // Step 2: Search for item
        System.out.println("Step 2: Search for items containing 'Laptop'");
        ArrayList<Item> allItems = FileHandler.readFile(TEST_PREFIX + "items.dat");
        
        ArrayList<Item> searchResults = new ArrayList<>();
        String searchPattern = ".*laptop.*";
        for (Item item : allItems) {
            if (item.getName().toLowerCase().matches(searchPattern)) {
                searchResults.add(item);
            }
        }
        
        assertEquals(3, searchResults.size());
        System.out.println("Found " + searchResults.size() + " matching items");
        
        // Step 3: Update price/supplier
        System.out.println("Step 3: Update item details");
        Item itemToUpdate = searchResults.get(0);
        String originalName = itemToUpdate.getName();
        double newPrice = 1099.99;
        
        Supplier newSupplier = new Supplier("New Supplier", "555-7000", "new@supplier.com");
        FileHandler.appendFile(TEST_PREFIX + "suppliers.dat", newSupplier);
        
        itemToUpdate.setSellingPrice(newPrice);
        itemToUpdate.setSupplier(newSupplier);
        
        System.out.println("Updated item: " + originalName);
        System.out.println("  New price: $" + newPrice);
        System.out.println("  New supplier: " + newSupplier.getName());
        
        // Step 4: Save changes
        System.out.println("Step 4: Save changes");
        FileHandler.updateItem(itemToUpdate);
        
        // Verify update persisted
        ArrayList<Item> updatedItems = FileHandler.readFile(TEST_PREFIX + "items.dat");
        Item updated = null;
        for (Item item : updatedItems) {
            if (item.getName().equals(originalName)) {
                updated = item;
                break;
            }
        }
        
        assertNotNull(updated);
        assertEquals(newPrice, updated.getSellingPrice(), 0.01);
        assertEquals(newSupplier.getName(), updated.getSupplier().getName());
        System.out.println("✓ Changes saved successfully");
        
    }

    // ST-009: Low Stock Alert
    @Test
    @DisplayName("ST-009: Low Stock Alert")
    public void testLowStockAlert() throws Exception {
        System.out.println("SYSTEM TEST 009: Low Stock Alert");
        
        // Step 1: Login as manager
        System.out.println("Step 1: Manager login");
        
        // Step 2: Create category with minimum quantity
        System.out.println("Step 2: Create category with min quantity = 10");
        Category category = new Category("Monitors", systemSector, 10);
        FileHandler.appendFile(TEST_PREFIX + "categories.dat", category);
        
        // Step 3: Add items below minimum
        System.out.println("Step 3: Add items with low stock");
        Item item1 = new Item("Monitor 1", category, systemSupplier, 299.99);
        item1.changeQuantity(3);
        
        Item item2 = new Item("Monitor 2", category, systemSupplier, 399.99);
        item2.changeQuantity(2);
        
        FileHandler.appendFile(TEST_PREFIX + "items.dat", item1);
        FileHandler.appendFile(TEST_PREFIX + "items.dat", item2);
        
        System.out.println("Item 1 stock: " + item1.getQuantity());
        System.out.println("Item 2 stock: " + item2.getQuantity());
        System.out.println("Total stock: " + (item1.getQuantity() + item2.getQuantity()));
        System.out.println("Minimum required: " + category.getMinQuantity());
        
        // Verify low stock warning
        int totalQuantity = category.getTotalQuantity();
        assertEquals(5, totalQuantity);
        
        String warning = category.checkLowQuantity();
        assertEquals("Low quantity", warning);
        System.out.println("✓ Low stock alert triggered: " + warning);
        
        // Test with adequate stock
        Item item3 = new Item("Monitor 3", category, systemSupplier, 499.99);
        item3.changeQuantity(10);
        FileHandler.appendFile(TEST_PREFIX + "items.dat", item3);
        
        int newTotal = category.getTotalQuantity();
        assertEquals(15, newTotal);
        
        String noWarning = category.checkLowQuantity();
        assertEquals("", noWarning);
        System.out.println("✓ No warning when stock is adequate");
        
    }

    // ST-010: Complete Sales Cycle
    @Test
    @DisplayName("ST-010: Complete Sales Cycle")
    public void testCompleteSalesCycle() throws Exception {
        System.out.println("SYSTEM TEST 010: Complete Sales Cycle");
        
        // Step 1: Add stock to items
        System.out.println("Step 1: Add stock to items");
        Item laptop = new Item("Business Laptop", systemCategory, systemSupplier, 1199.99);
        laptop.changeQuantity(10);
        FileHandler.appendFile(TEST_PREFIX + "items.dat", laptop);
        
        int stockToAdd = 20;
        double purchasePrice = 950.00;
        FileHandler.addStock(laptop, stockToAdd, purchasePrice);
        
        ArrayList<Item> items = FileHandler.readFile(TEST_PREFIX + "items.dat");
        int stockAfterPurchase = items.get(0).getQuantity();
        assertEquals(30, stockAfterPurchase);
        System.out.println("Stock after purchase: " + stockAfterPurchase);
        
        // Step 2: Create bills as cashier
        System.out.println("Step 2: Create sales bills");
        Bill bill1 = new Bill(systemCashier);
        bill1.addItem(laptop, 8);
        FileHandler.saveBill(bill1);
        
        Bill bill2 = new Bill(systemCashier);
        bill2.addItem(laptop, 5);
        FileHandler.saveBill(bill2);
        
        System.out.println("Created 2 bills (8 + 5 units sold)");
        
        // Verify stock reduced
        items = FileHandler.readFile(TEST_PREFIX + "items.dat");
        int stockAfterSales = items.get(0).getQuantity();
        assertEquals(17, stockAfterSales);
        System.out.println("Stock after sales: " + stockAfterSales);
        
        // Step 3: Add salary for cost calculation
        System.out.println("Step 3: Record salary");
        Salary salary = new Salary(systemCashier, 3500.0);
        FileHandler.appendFile(TEST_PREFIX + "salaries.dat", salary);
        
        // Step 4: View statistics as admin
        System.out.println("Step 4: Calculate comprehensive statistics");
        LocalDate startDate = LocalDate.now().minusDays(1);
        LocalDate endDate = LocalDate.now().plusDays(1);
        
        double revenue = StatisticsDAO.getTotalRevenue(startDate, endDate);
        double itemCosts = StatisticsDAO.getItemCosts(startDate, endDate);
        double staffCosts = StatisticsDAO.getStaffCosts(startDate, endDate);
        double totalCosts = StatisticsDAO.getTotalCosts(startDate, endDate);
        double profits = StatisticsDAO.getTotalProfits(startDate, endDate);
        
        // Verify calculations
        double expectedRevenue = (8 + 5) * 1199.99;
        assertEquals(expectedRevenue, revenue, 0.01);
        System.out.println("Total Revenue: $" + String.format("%.2f", revenue));
        
        double expectedItemCosts = stockToAdd * purchasePrice;
        assertEquals(expectedItemCosts, itemCosts, 0.01);
        System.out.println("Item Costs: $" + String.format("%.2f", itemCosts));
        
        assertEquals(3500.0, staffCosts, 0.01);
        System.out.println("Staff Costs: $" + String.format("%.2f", staffCosts));
        
        assertEquals(expectedItemCosts + staffCosts, totalCosts, 0.01);
        System.out.println("Total Costs: $" + String.format("%.2f", totalCosts));
        
        assertEquals(expectedRevenue - totalCosts, profits, 0.01);
        System.out.println("Net Profits: $" + String.format("%.2f", profits));
        
        // Verify cycle integrity
        assertTrue(revenue > 0, "Revenue should be positive");
        assertTrue(totalCosts > 0, "Costs should be positive");
        System.out.println("✓ Complete sales cycle verified");
        
    }
}