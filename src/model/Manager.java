package model;

import java.time.LocalDate;
import java.util.ArrayList;

import dao.FileHandler;

public class Manager extends User {

	private static final long serialVersionUID = -6718067695187497718L;
	
	private boolean canAccessStock = true;
	private boolean canAccessItems = true;
	private boolean canAccessCategories = true;
	private boolean canAccessCashiers = true;
	private boolean canAccessSuppliers = true;

	public Manager(String username, String password, String name, String phoneNumber, String email,
            LocalDate dateOfBirth, double salary) throws Exception {
        super(username, password, name, phoneNumber, email, dateOfBirth, salary);
    }

    public boolean getCanAccessStock() {
		return canAccessStock;
	}

	public void setCanAccessStock(boolean canAccessStock) {
		this.canAccessStock = canAccessStock;
	}

	public boolean getCanAccessItems() {
		return canAccessItems;
	}

	public void setCanAccessItems(boolean canAccessItems) {
		this.canAccessItems = canAccessItems;
	}

	public boolean getCanAccessCategories() {
		return canAccessCategories;
	}

	public void setCanAccessCategories(boolean canAccessCategories) {
		this.canAccessCategories = canAccessCategories;
	}

	public boolean getCanAccessCashiers() {
		return canAccessCashiers;
	}

	public void setCanAccessCashiers(boolean canAccessCashiers) {
		this.canAccessCashiers = canAccessCashiers;
	}

	public boolean getCanAccessSuppliers() {
		return canAccessSuppliers;
	}

	public void setCanAccessSuppliers(boolean canAccessSuppliers) {
		this.canAccessSuppliers = canAccessSuppliers;
	}

	public ArrayList<Sector> getSectorList() throws Exception{
        ArrayList<Sector> sectorList = FileHandler.readFile(FileHandler.SECTOR);
        sectorList.removeIf(s -> !(s.getManager().getUsername().equals(this.getUsername())));
        return sectorList;
    }
    

    public ArrayList<Cashier> getCashierList() throws Exception{
    	ArrayList<Cashier> allCashiersList = FileHandler.readFile(FileHandler.CASHIER);
    	ArrayList<Cashier> cashierList = new ArrayList<>();
    	ArrayList<Sector> sectorList = getSectorList();
    	for(Cashier c: allCashiersList) {
    		for(Sector s: sectorList) {
    			if (c.getSector().getName().equals(s.getName())) {
    				cashierList.add(c);
    				break;
    			}
    		}
    	}
    	return cashierList;
    }

    
    
    @Override
    public String toString() {
    	return getName();
    }

}


