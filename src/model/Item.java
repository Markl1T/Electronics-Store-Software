package model;

import java.io.Serializable;

public class Item implements Serializable {

	private static final long serialVersionUID = -6214950857710648405L;
	
	private String name;
	private Category category;
	private Sector sector;
	private Supplier supplier;
	private int quantity;
	private double sellingPrice;
	
	public Item(String name, Category category, Supplier supplier, double sellingPrice) throws InvalidPriceException{
		this.name = name;
		this.category = category;
		this.sector = category.getSector();
		this.supplier = supplier;
		setSellingPrice(sellingPrice);
	}
	
	public String getName() {
		return name;
	}
	
	public Category getCategory() {
		return category;
	}
	
	public Sector getSector() {
		return sector;
	}
	
	public Supplier getSupplier() {
		return supplier;
	}
	
	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}
	
	public int getQuantity() {
		return quantity;
	}
	
	public void changeQuantity(int quantity) throws InvalidQuantityException{
		if ((this.quantity + quantity) < 0)
			throw new InvalidQuantityException("Quantity added cannot be greater than current quantity");
		else
			this.quantity += quantity;
	}
	
	public double getSellingPrice() {
		return sellingPrice;
	}
	
	public void setSellingPrice(double price) throws InvalidPriceException{
		if (price <= 0) {
			throw new InvalidPriceException("Item selling price should be greater than 0");
		} else {
			sellingPrice = price;
		}
	}
	
	public String checkStock() {
		if (quantity == 0) {
			return "No Stock";
		}
		else {
			return String.valueOf(quantity);
		}
	}
	
	@Override
	public String toString() {
		return name;
	}
	
}
