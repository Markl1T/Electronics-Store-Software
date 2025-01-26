package model;

import java.io.Serializable;
import java.util.ArrayList;

import dao.FileHandler;

public class Category implements Serializable{

	private static final long serialVersionUID = -1276685425881633958L;
	
	private String name;
	private Sector sector;
	private int minQuantity;
	
	public Category(String name, Sector sector, int minQuantity) throws Exception{
		this.name = name;
		this.sector = sector;
		setMinQuantity(minQuantity);
	}
	
	public String getName() {
		return name;
	}
	
	public Sector getSector() {
		return sector;
	}
	public int getMinQuantity() {
		return minQuantity;
	}
	
	public int getTotalQuantity() throws Exception{
		int totalQuantity = 0;
		ArrayList<Item> itemList = FileHandler.readFile(FileHandler.ITEM);
		for(Item item: itemList) {
			if (item.getCategory().getName().equals(this.getName())) {
				totalQuantity += item.getQuantity();
			}
		}
		return totalQuantity;
	}
	
	public String checkLowQuantity() throws Exception{
		if (getTotalQuantity() < minQuantity)
			return "Low quantity";
		else
			return "";
	}
	
	private void setMinQuantity(int quantity) throws Exception{
		if (quantity >= 0)
			minQuantity = quantity;
		else
			throw new InvalidQuantityException("Minimum quantity can't be less than 0");
	}
	@Override
	public String toString() {
		return name;
	}
	
}
