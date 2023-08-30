package a2;

//File Name: InsufficientFundsException.java
//Developer: Jonathan Bernard Bloch
//Purpose: It is used for declaring an exception for having illegal values stored.
//Inputs: None
//Outputs: None
//Modifications: None
//=========================================================================
//<<january 29 2018>> <<February 4 2018>> <<It is used for declaring the InsufficientFundsException exception>>

public class InsufficientFundsException extends Exception {
	static final long serialVersionUID = 0l; // why is this serializable?
	public InsufficientFundsException() {
	}

	public String getMessage() {
		return "Insufficient funds.";
	}
}
