package model;

import java.time.LocalDate;

public class Cashier extends User  {
	
	private static final long serialVersionUID = -7004974590426408874L;
	private boolean canCreateBill = true;
	private boolean canAccessToday = true;
	private Sector sector;
	
	public Cashier(String username, String password, String name, String phoneNumber, String email, LocalDate dateOfBirth, double currentSalary, Sector sector) throws Exception{
		super(username, password, name, phoneNumber, email, dateOfBirth, currentSalary);
		this.sector = sector;
	}
	
	public Sector getSector()
	{
		return sector;
	}
	
	public void setSector(Sector sector)
	{
		this.sector = sector;
	}
	
	public boolean getCanCreateBill() {
		return canCreateBill;
	}

	public void setCanCreateBill(boolean canCreateBill) {
		this.canCreateBill = canCreateBill;
	}

	public boolean getCanAccessToday() {
		return canAccessToday;
	}

	public void setCanAccessToday(boolean canAccessToday) {
		this.canAccessToday = canAccessToday;
	}
	
	
}

