package model;

@SuppressWarnings("serial")
public class InvalidDateException extends Exception {
	public InvalidDateException(){
		super();
	}
	
	public InvalidDateException(String message){
		super(message);
	}
}
