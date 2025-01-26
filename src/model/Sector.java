package model;

import java.io.Serializable;
import java.util.ArrayList;

import dao.FileHandler;

public class Sector implements Serializable{

	private static final long serialVersionUID = -6691621220286318967L;
	
	private String name;
    private Manager manager;

    public Sector(String name, Manager manager) {
        this.name = name;
        this.manager = manager;
    }

    public String getName() {
        return name;
    }

    public Manager getManager() {
        return manager;
    }
    
    public void setManager(Manager manager) {
    	this.manager = manager;
    }
    
    public ArrayList<Category> getCategoryList() throws Exception{
		ArrayList<Category> categoryList = FileHandler.readFile(FileHandler.CATEGORY);
		categoryList.removeIf(c -> !(c.getSector().getName().equals(name)));
		return categoryList;
	}
    
    public ArrayList<Item> getItemList() throws Exception{
    	ArrayList<Item> itemList = FileHandler.readFile(FileHandler.ITEM);
    	itemList.removeIf(i -> !(i.getSector().getName().equals(name)));
    	return itemList;
    }
    
    @Override
    public String toString() {
    	return name;
    }
}


