package model;

import java.io.Serializable;
import java.util.ArrayList;

import dao.FileHandler;

public class Supplier implements Serializable{

	private static final long serialVersionUID = 1282988716546351195L;
	
	private String name;
    private String phoneNumber;
    private String email;

    public Supplier(String name, String phoneNumber, String email) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
    
    public void setPhoneNumber(String phoneNumber) {
    	this.phoneNumber =  phoneNumber;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
    	this.email = email;
    }
    
    public ArrayList<Item> getItems() throws Exception{
    	ArrayList<Item> itemList = FileHandler.readFile(FileHandler.ITEM);
    	itemList.removeIf(item -> !(item.getSupplier().getName().equals(this.name)));
    	return itemList;
    }
    
    @Override
    public String toString() {
    	return name;
    }
}


