package model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.UUID;
import java.io.Serializable;

public class Bill implements Serializable{
	private static final long serialVersionUID = 8627993670393707871L;
	
	private String billNumber;
	private Cashier cashier;
	private LocalDateTime date;
	private ArrayList<Item> itemList = new ArrayList<>();
	private ArrayList<Double> priceList = new ArrayList<>();
	private ArrayList<Integer> quantityList = new ArrayList<>();
	private double totalPrice;
	
	public Bill (Cashier cashier) {
		this.billNumber = UUID.randomUUID().toString();
		this.cashier = cashier;
		this.date = LocalDateTime.now();
	}
	
	public void addItem (Item item, int quantity) throws Exception{
		if (quantity > 0 && quantity <= item.getQuantity()) {
			itemList.add(item);
			quantityList.add(quantity);
		}
		else if (quantity > item.getQuantity())
			throw new InvalidQuantityException("Quantity can't be greater than the available quantity");
		else
			throw new InvalidQuantityException("Quantity should be greater than 0");
		
		double price = item.getSellingPrice();
		priceList.add(price);
		totalPrice += price * quantity;
	}
	
	public String getBillNumber() {return billNumber;}
	public Cashier getCashier() { return cashier;}
	public LocalDateTime getDate() { return date;}
	
	public String getFormattedDate() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd LLL yyyy, HH:mm:ss");
		return date.format(formatter);
	}
	
	public ArrayList<Item> getItemList() {
		return itemList;
	}
	
	public ArrayList<Double> getPriceList () {
		return priceList;
	}
	
	public ArrayList<Integer> getQuantityList() {
		return quantityList;
	}
	
	public int getTotalQuantity() {
		int total = 0;
		for(Integer quantity: quantityList) {
			total += quantity;
		}
		return total;
	}
	
	public double getTotalPrice() {
		return totalPrice;
	}
	
}

