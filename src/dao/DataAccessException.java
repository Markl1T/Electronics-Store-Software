package dao;

@SuppressWarnings("serial")
public class DataAccessException extends Exception{
	public DataAccessException(){
		super();
	}
	
	public DataAccessException(String message){
		super(message);
	}
}
