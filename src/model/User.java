package model;

import java.io.Serializable;
import java.time.LocalDate;

public abstract class User implements Serializable {
	
	private static final long serialVersionUID = -2889029603447674647L;
	
	private String username;
	private String password;
	private String name;
	private String phoneNumber;
	private String email;
	private LocalDate dateOfBirth;
	private double currentSalary;
	
	protected User(String username, String password, String name, String phoneNumber, String email,
			LocalDate dateOfBirth, double salary) throws InvalidSalaryException, InvalidEmailException, InvalidDateException {
		this.username = username;
		this.password = password;
		this.name = name;
		this.phoneNumber = phoneNumber;
		setEmail(email);
		setDateOfBirth(dateOfBirth);
		setCurrentSalary(salary);
	}
	
	public String getUsername() {
		return username;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getName() {
		return name;
	}
	
	public String getPhoneNumber() {
		return phoneNumber;
	}
	
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) throws InvalidEmailException {
	    if (email == null)
	        throw new InvalidEmailException("Email cannot be null");

	    String regex = "^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$";

	    if (!email.matches(regex)) {
	        throw new InvalidEmailException("Invalid email format");
	    }

	    this.email = email;
	}
	
	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}
	
	public void setDateOfBirth(LocalDate dateOfBirth) throws InvalidDateException {
		if(dateOfBirth.isAfter(LocalDate.now())) {
			throw new InvalidDateException("Invalid Date");
		}
		this.dateOfBirth = dateOfBirth;
	}
	
	public double getCurrentSalary() {
		return currentSalary;
	}
	
	public void setCurrentSalary(double salary) throws InvalidSalaryException{
		if(salary >= 0)
			currentSalary = salary;
		else
			throw new InvalidSalaryException("Salary can't be less than 0");
	}
	
}

