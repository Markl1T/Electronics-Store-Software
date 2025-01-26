package dao;

import java.util.ArrayList;

import model.Administrator;
import model.Bill;
import model.Cashier;
import model.Item;
import model.Manager;
import model.Sector;
import model.Stock;
import model.Supplier;
import model.User;

import java.io.IOException;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.time.LocalDate;

public class FileHandler {

	public static final String STOCK = "src/files/stocks.dat";
	public static final String ITEM = "src/files/items.dat";
	public static final String CATEGORY = "src/files/categories.dat";
	public static final String SECTOR = "src/files/sectors.dat";
	public static final String BILL = "src/files/bills.dat";
	public static final String CASHIER = "src/files/cashiers.dat";
	public static final String MANAGER = "src/files/managers.dat";
	public static final String ADMINISTRATOR = "src/files/administrator.dat";
	public static final String SUPPLIER = "src/files/suppliers.dat";
	public static final String SALARY = "src/files/salaries.dat";

	public static void seedAdministrator() throws DataAccessException {
		File administratorFile = new File(FileHandler.ADMINISTRATOR);
		if (administratorFile.exists() && administratorFile.length() == 0) {
			try {
				Administrator seedAdministrator = new Administrator("administrator", "123", "Administrator", "123456789", "administrator@",
						LocalDate.now());
				FileHandler.appendFile(FileHandler.ADMINISTRATOR, seedAdministrator);
			} catch (Exception ex) {
				throw new DataAccessException("Could not seed administrator");
			}
		}
	}

	public static <E> ArrayList<E> readFile(String filename) throws DataAccessException {
		ArrayList<E> list = new ArrayList<>();
		try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
			while (true) {
				@SuppressWarnings("unchecked")
				E obj = (E) ois.readObject();
				list.add(obj);
			}
		} catch (EOFException ex) {
			return list;
		} catch (Exception ex) {
			throw new DataAccessException("Could Not Access Data From " + filename);
		}
	}

	public static <E> void updateFile(String filename, ArrayList<E> list) throws DataAccessException {
		try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
			for (E obj : list)
				oos.writeObject(obj);
		} catch (IOException ex) {
			throw new DataAccessException("Data Not Updated In " + filename);
		}
	}

	public static <E> void appendFile(String filename, E obj) throws DataAccessException {
		ArrayList<E> list;
		try {
			list = readFile(filename);
			list.add(obj);
			updateFile(filename, list);
		} catch (Exception ex) {
			throw new DataAccessException("Failed to append to file " + filename);
		}

	}

	public static void printBill(Bill bill) throws DataAccessException {
		File billFile = new File("src/bills/" + bill.getBillNumber() + ".txt");
		try (PrintWriter writer = new PrintWriter(billFile)) {
			writer.write("Bill Number: " + bill.getBillNumber());
			writer.write("\nCashier: " + bill.getCashier().getName());
			writer.write("\nDate: " + bill.getFormattedDate());
			writer.write("\n\n");
			ArrayList<Item> itemList = bill.getItemList();
			ArrayList<Double> priceList = bill.getPriceList();
			ArrayList<Integer> quantityList = bill.getQuantityList();
			for (int i = 0; i < itemList.size(); i++) {
				writer.write(String.format("\nItem #%d: %s; Price: %.2f; Quantity: %d", (i + 1),
						itemList.get(i).getName(), priceList.get(i), quantityList.get(i)));
			}
			writer.write("\n\n");
			writer.write(String.format("\nTotal Price: %.2f", bill.getTotalPrice()));
		} catch (Exception ex) {
			throw new DataAccessException("Failed to print bill");
		}
	}

	public static void deleteUser(User user) throws DataAccessException {
		ArrayList<User> userList;

		if (user instanceof Cashier)
			userList = FileHandler.readFile(FileHandler.CASHIER);
		else if (user instanceof Manager)
			userList = FileHandler.readFile(FileHandler.MANAGER);
		else
			throw new DataAccessException("User Not Found");

		userList.removeIf(u -> u.getUsername().equals(user.getUsername()));

		if (user instanceof Cashier)
			FileHandler.updateFile(FileHandler.CASHIER, userList);
		else if (user instanceof Manager)
			FileHandler.updateFile(FileHandler.MANAGER, userList);
	}

	public static void delete(Supplier supplier) throws DataAccessException {
		ArrayList<Supplier> supplierList = FileHandler.readFile(SUPPLIER);

		supplierList.removeIf(s -> s.getName().equals(supplier.getName()));

		FileHandler.updateFile(SUPPLIER, supplierList);
	}

	public static void saveBill(Bill bill) throws Exception {
		ArrayList<Item> allItemsList = FileHandler.readFile(FileHandler.ITEM);
		ArrayList<Item> billItemList = bill.getItemList();
		ArrayList<Integer> billQuantityList = bill.getQuantityList();
		for (int i = 0; i < billItemList.size(); i++) {
			for (Item item : allItemsList) {
				if (billItemList.get(i).getName().equals(item.getName())) {
					item.changeQuantity((-1) * billQuantityList.get(i));
					break;
				}
			}
		}
		FileHandler.updateFile(FileHandler.ITEM, allItemsList);

		FileHandler.appendFile(FileHandler.BILL, bill);
		FileHandler.printBill(bill);
	}

	public static void addStock(Item item, int stockQuantity, double purchasePrice) throws Exception {
		Stock newStock = new Stock(item, stockQuantity, purchasePrice);
		ArrayList<Item> itemList = FileHandler.readFile(FileHandler.ITEM);
		for (Item i : itemList) {
			if (i.getName().equals(item.getName())) {
				i.changeQuantity(stockQuantity);
				break;
			}
		}
		FileHandler.updateFile(FileHandler.ITEM, itemList);
		FileHandler.appendFile(FileHandler.STOCK, newStock);
	}

	public static void updateSupplier(Supplier supplier) throws Exception {
		ArrayList<Supplier> supplierList = FileHandler.readFile(FileHandler.SUPPLIER);
		for (Supplier s : supplierList) {
			if (s.getName().equals(supplier.getName())) {
				s.setPhoneNumber(supplier.getPhoneNumber());
				s.setEmail(supplier.getEmail());
				break;
			}
		}
		FileHandler.updateFile(FileHandler.SUPPLIER, supplierList);
	}

	public static void updateItem(Item item) throws Exception {
		ArrayList<Item> itemList = FileHandler.readFile(FileHandler.ITEM);

		for (Item i : itemList) {
			if (i.getName().equals(item.getName())) {
				i.setSellingPrice(item.getSellingPrice());
				i.setSupplier(item.getSupplier());
				break;
			}
		}
		FileHandler.updateFile(FileHandler.ITEM, itemList);
	}

	public static void updateCashier(Cashier cashier) throws Exception {
		ArrayList<Cashier> cashierList = FileHandler.readFile(FileHandler.CASHIER);

		for (Cashier c : cashierList) {
			if (c.getUsername().equals(cashier.getUsername())) {
				c.setPassword(cashier.getPassword());
				c.setPhoneNumber(cashier.getPhoneNumber());
				c.setEmail(cashier.getEmail());
				c.setSector(cashier.getSector());
				c.setCurrentSalary(cashier.getCurrentSalary());
				c.setCanAccessToday(cashier.getCanAccessToday());
				c.setCanCreateBill(cashier.getCanCreateBill());
				break;
			}
		}
		FileHandler.updateFile(FileHandler.CASHIER, cashierList);
	}

	public static void registerEmployee(String role, String username, String password, String name, String phoneNumber,
			String email, LocalDate dateOfBirth, double salary, Sector sector) throws Exception {
		if (role.equalsIgnoreCase("Manager")) {
			FileHandler.appendFile(FileHandler.MANAGER,
					new Manager(username, password, name, phoneNumber, email, dateOfBirth, salary));
		} else if (role.equalsIgnoreCase("Cashier")) {
			FileHandler.appendFile(FileHandler.CASHIER,
					new Cashier(username, password, name, phoneNumber, email, dateOfBirth, salary, sector));
		}
	}

	public static void updateSector(Sector sector) throws Exception {
		ArrayList<Sector> sectorList = FileHandler.readFile(FileHandler.SECTOR);

		for (Sector s : sectorList) {
			if (s.getName().equals(sector.getName())) {
				s.setManager(sector.getManager());
				break;
			}
		}
		FileHandler.updateFile(FileHandler.SECTOR, sectorList);

	}
	
	public static void updateManager(Manager manager) throws Exception {
		ArrayList<Manager> managerList = FileHandler.readFile(FileHandler.MANAGER);
		
		for (Manager m : managerList) {
            if (m.getUsername().equals(manager.getUsername())) {
                m.setPassword(manager.getPassword());
                m.setPhoneNumber(manager.getPhoneNumber());
                m.setEmail(manager.getEmail());
                m.setCurrentSalary(manager.getCurrentSalary());
                m.setCanAccessStock(manager.getCanAccessStock());
                m.setCanAccessItems(manager.getCanAccessItems());
                m.setCanAccessCategories(manager.getCanAccessCategories());
                m.setCanAccessCashiers(manager.getCanAccessCashiers());
                m.setCanAccessSuppliers(manager.getCanAccessSuppliers());
                break;
            }
        }
        FileHandler.updateFile(FileHandler.MANAGER, managerList);
	}

	
}
