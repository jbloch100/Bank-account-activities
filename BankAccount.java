package a2;

//File Name: Bank.java
//Developer: Jonathan Bernard Bloch
//Purpose: "A bank account has a balance. Operations that can be performed are: finding out the balance, making a
//deposit, making a withdrawal, and transferring money to another account (both accounts must be
//owned by the same customer). Creating a bank account also initializes the balance to an amount
//determined by the customer"
// It is an abstract bank account class from which objects cannot be instantiated from due to missing information. It's children are SavingsAccount and CheckingAccount
//Inputs: None
//Outputs: None
//Modifications: None
//=========================================================================
//<<january 29 2018>> <<February 4 2018>> <<It is used for storing all the information of the bank account>>
import java.lang.IllegalArgumentException;

// todo: protect this with a mutex
public abstract class BankAccount
{
	protected Customer customer;
	protected double balance;
	protected int number;
	
	//Name: BankAccount(Customer customer, double balance)
	//Developer: Jonathan Bernard Bloch
	//Purpose: <<This is a constructor for bank account with a customer>>
	//Inputs: Customer customer, double balance
	//Outputs: None
	//Side effects: A new bank account is created.
	//Special notes: The synchronized is not useful when one thread is using this class, but when multiple threads use the same class, it provides protection, "enforcing exclusive access to an object's state" https://docs.oracle.com/javase/tutorial/essential/concurrency/locksync.html
	BankAccount(Customer customer, double balance) throws InsufficientFundsException
	{
		synchronized(this) {
			if(customer == null) throw new NullPointerException("Customer must exist");
			if(balance < 0.0) throw new InsufficientFundsException();
			this.customer = customer;
			this.balance = balance;
			// figure out a unique id of this bank account with this user
			int max_number = 0;
			for(BankAccount a : customer) {
				if(a.number > max_number) max_number = a.number;
			}
			this.number = max_number + 1;
			customer.addAccount(this);
		}
	}
	
	//Name: getBalance()
	//Developer: Jonathan Bernard Bloch
	//Purpose: <<This method is used as a getter for returning the current balance of the selected bank account>>
	//Inputs: None
	//Outputs: returns current balance
	//Side effects: None
	//Special notes: None
	double getBalance()
	{
		return balance;
	}
	
	//Name: main method
	//Developer: Jonathan Bernard Bloch
	//Purpose: <<This method is used as a getter for returning the current customer>>
	//Outputs: Returns selected customer
	//Side effects: None
	//Special notes: None
	Customer getCustomer() {
		return customer;
	}
	
	//Name: getAccountNumber()
	//Developer: Jonathan Bernard Bloch
	//Purpose: <<This method is used for returning the current number of bank accounts>>
	//Inputs: None
	//Outputs: Returns number
	//Side effects: None
	//Special notes: None
	public int getAccountNumber() {
		return number;
	}
	
	//Name: withdrawal(double amount)
	//Developer: Jonathan Bernard Bloch
	//Purpose: <<This method is used for withdrawing an amount of money from a bank account>>
	//Inputs: An amount of money to withdraw from a bank account.
	//Outputs: None
	//Side effects: An amount of money is withdrawn from the bank account
	//Special notes: None
	public void withdrawal(double amount) throws InsufficientFundsException
	{
		synchronized(this) {
			if(amount < 0.0) throw new IllegalArgumentException("It is impossible to withdraw negative amount");
			if(amount > this.balance) throw new InsufficientFundsException();
			this.balance-=amount;
		}
	}
	
	//Name: deposit(double amount)
	//Developer: Jonathan Bernard Bloch
	//Purpose: <<This method is used for depositing an amount of money to a bank account>>
	//Inputs: An amount of money to deposit to a bank account.
	//Outputs: None
	//Side effects: An amount of money is deposited to the bank account
	//Special notes: None
	public void deposit(double amount)
	{
		synchronized(this) {
			if(amount < 0.0) throw new IllegalArgumentException("It is impossible to deposit negative amount");
			balance += amount;
		}
	}
	
	//Name: transfer(double amount, BankAccount transferAccount)
	//Developer: Jonathan Bernard Bloch
	//Purpose: <<transfer is a special case (don't have fees) so I need to handle it in bankAccount directly>>
	//Inputs: <<an amount of money to transfer and an account to transfer the money into>>
	//Outputs: None
	/*Side effects: The balance of the account transferred to increases whereas the account we transferred the money 
	 * from has a decrease in the balance.*/
	//Special notes: None
	public void transfer(double amount, BankAccount transferAccount) throws InsufficientFundsException {
		synchronized(this) {
			if(this.customer != transferAccount.customer) throw new IllegalArgumentException("It is not your account");
			if(amount < 0.0) throw new IllegalArgumentException("It is impossible to transfer negative amount");
			if(amount > this.balance) throw new InsufficientFundsException();
			this.balance -= amount;
			transferAccount.balance += amount;
		}
	}
}
