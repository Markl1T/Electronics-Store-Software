package model;

import java.io.Serializable;
import java.time.LocalDate;

public class Salary implements Serializable {
	
	private static final long serialVersionUID = -8564002535760064836L;
	
	private User employee;
	private double amount;
	private LocalDate startDate;
	
	public Salary(User employee, double amount) throws InvalidSalaryException{
		if(amount < 0) {
			throw new InvalidSalaryException("Salary cannot be less than 0");
		}
		this.employee = employee;
		this.amount = amount;
		startDate = LocalDate.now();
	}
	
	public User getEmployee() {
		return employee;
	}
	
	public double getAmount() {
		return amount;
	}
	
	public LocalDate getStartDate() {
		return startDate;
	}
}


