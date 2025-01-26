package model;

import java.time.LocalDate;

public class Administrator extends User {
	
	private static final long serialVersionUID = 7202620264001058740L;

	public Administrator(String username, String password, String name, String phoneNumber, String email, LocalDate dateOfBirth) throws Exception {
		super(username, password, name, phoneNumber, email, dateOfBirth, 0);
	}
	
}



