package dao;

import java.time.LocalDate;
import java.util.ArrayList;

import model.Bill;
import model.Cashier;
import model.Item;
import model.Salary;
import model.Stock;

public class StatisticsDAO {
	public static double getTotalRevenue(LocalDate startDate, LocalDate endDate) throws Exception {
		double totalRevenue = 0.0;
		ArrayList<Bill> billList = FileHandler.readFile(FileHandler.BILL);
		billList.removeIf(bill -> bill.getDate().toLocalDate().isBefore(startDate)
				|| bill.getDate().toLocalDate().isAfter(endDate));
		for (Bill bill : billList) {
			totalRevenue += bill.getTotalPrice();
		}
		return totalRevenue;
	}

	public static double getItemCosts(LocalDate startDate, LocalDate endDate) throws Exception {
		double itemCosts = 0.0;
		ArrayList<Stock> stockList = FileHandler.readFile(FileHandler.STOCK);
		stockList.removeIf(
				stock -> stock.getPurchaseDate().isBefore(startDate) || stock.getPurchaseDate().isAfter(endDate));
		for (Stock stock : stockList) {
			itemCosts += (stock.getPurchasePrice() * stock.getStockQuantity());
		}
		return itemCosts;
	}

	public static double getStaffCosts(LocalDate startDate, LocalDate endDate) throws Exception {
		double staffCosts = 0.0;
		ArrayList<Salary> salaryList = FileHandler.readFile(FileHandler.SALARY);

		for (Salary s : salaryList) {
			if (s.getStartDate().isAfter(startDate) || s.getStartDate().isEqual(startDate)) {
				if (s.getStartDate().isBefore(endDate) || s.getStartDate().isEqual(endDate))
					staffCosts += s.getAmount();
			}
		}
		return staffCosts;
	}

	public static double getTotalCosts(LocalDate startDate, LocalDate endDate) throws Exception {
		return getItemCosts(startDate, endDate) + getStaffCosts(startDate, endDate);
	}

	public static double getTotalProfits(LocalDate startDate, LocalDate endDate) throws Exception {
		return getTotalRevenue(startDate, endDate) - getTotalCosts(startDate, endDate);
	}

	public static int getNumberOfBillsByCashier(Cashier cashier, LocalDate startDate, LocalDate endDate)
			throws Exception {
		int count = 0;
		ArrayList<Bill> billList = FileHandler.readFile(FileHandler.BILL);
		for (Bill bill : billList) {
			if (bill.getDate().toLocalDate().isAfter(startDate.minusDays(1))
					&& bill.getDate().toLocalDate().isBefore(endDate.plusDays(1))
					&& bill.getCashier().getUsername().equals(cashier.getUsername())) {
				count++;
			}
		}
		return count;
	}

	public static int getNumberOfItemsSoldByCashier(Cashier cashier, LocalDate startDate, LocalDate endDate)
			throws Exception {
		int itemCount = 0;
		ArrayList<Bill> billList = FileHandler.readFile(FileHandler.BILL);
		for (Bill bill : billList) {
			if (bill.getDate().toLocalDate().isAfter(startDate.minusDays(1))
					&& bill.getCashier().getUsername().equals(cashier.getUsername())
					&& bill.getDate().toLocalDate().isBefore(endDate.plusDays(1))) {
				itemCount += bill.getTotalQuantity();
			}
		}
		return itemCount;
	}

	public static double getTotalRevenueGeneratedByCashier(Cashier cashier, LocalDate startDate, LocalDate endDate)
			throws Exception {
		double totalRevenue = 0.0;
		ArrayList<Bill> billList = FileHandler.readFile(FileHandler.BILL);
		for (Bill bill : billList) {
			if (bill.getDate().toLocalDate().isAfter(startDate.minusDays(1))
					&& bill.getCashier().getUsername().equals(cashier.getUsername())
					&& bill.getDate().toLocalDate().isBefore(endDate.plusDays(1))) {
				totalRevenue += bill.getTotalPrice();
			}
		}
		return totalRevenue;
	}

	public static ArrayList<Bill> getTodayBillsByCashier(Cashier cashier) throws Exception {
		ArrayList<Bill> bills = FileHandler.readFile(FileHandler.BILL);
		ArrayList<Bill> todayBills = new ArrayList<Bill>();
		LocalDate today = LocalDate.now();
		for (Bill bill : bills) {
			if (bill.getDate().toLocalDate().isEqual(today) && bill.getCashier().getUsername().equals(cashier.getUsername()))
				todayBills.add(bill);
		}
		return todayBills;
	}

	public static int getNumberOfItemsSold(Item item, LocalDate startDate, LocalDate endDate) throws Exception {
		int numberOfItemsSold = 0;
		ArrayList<Bill> bills = FileHandler.readFile(FileHandler.BILL);
		for (Bill bill : bills) {
			if (bill.getDate().toLocalDate().isBefore(endDate.plusDays(1))
					&& bill.getDate().toLocalDate().isAfter(startDate.minusDays(1))) {
				ArrayList<Item> items = bill.getItemList();
				for (Item i : items) {
					if (i.getName().equals(item.getName())) {
						numberOfItemsSold++;
					}
				}
			}

		}
		return numberOfItemsSold;
	}

	public static int getNumberOfItemsBought(Item item, LocalDate startDate, LocalDate endDate) throws Exception {
		int numberOfItemsBought = 0;
		ArrayList<Stock> stocks = FileHandler.readFile(FileHandler.STOCK);
		for (Stock stock : stocks) {
			if (stock.getItem().getName().equals(item.getName())
					&& stock.getPurchaseDate().isAfter(startDate.minusDays(1))
					&& stock.getPurchaseDate().isBefore(endDate.plusDays(1))) {
				numberOfItemsBought+= stock.getStockQuantity();
			}
		}
		return numberOfItemsBought;
	}

	public static double getTodayTotalByCashier(Cashier cashier) {
		double total = 0;
		try {
			ArrayList<Bill> todayBills = getTodayBillsByCashier(cashier);
			for (Bill bill : todayBills) {
				total += bill.getTotalPrice();
			}
		} catch (Exception ex) {
			return 0;
		}
		return total;
	}
}