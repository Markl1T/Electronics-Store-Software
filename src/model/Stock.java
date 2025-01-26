package model;

import java.time.LocalDate;
import java.io.Serializable;

public class Stock implements Serializable{

	private static final long serialVersionUID = -2995942738298793909L;
	
	private Item item;
	private int stockQuantity;
	private LocalDate purchaseDate;
	private double purchasePrice;
	
	public Stock(Item item, int stockQuantity, double purchasePrice) throws InvalidQuantityException, InvalidPriceException{
		this.item = item;
		
		if (stockQuantity <= 0)
			throw new InvalidQuantityException("Stock quantity should be greater than 0");
		else
			this.stockQuantity = stockQuantity;
		
		if (purchasePrice <= 0)
			throw new InvalidPriceException("Stock purchase price should be greater than 0");
		else
			this.purchasePrice = purchasePrice;
		
		purchaseDate = LocalDate.now();
	}
	
	public Item getItem() {
		return item;
	}
	
	public int getStockQuantity() {
		return stockQuantity;
	}
	
	public LocalDate getPurchaseDate() {
		return purchaseDate;
	}
	
	public double getPurchasePrice() {
		return purchasePrice;
	}
	
}
