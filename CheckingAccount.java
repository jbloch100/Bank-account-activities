/**
 * 
 */
package a2;

//File Name: CheckingAccount.java
//Developer: Jonathan Bernard Bloch
//Purpose: It is used for creating a checking bank account.
//Inputs: None
//Outputs: Result of the toString method.
//Modifications: None
//=========================================================================
//<<january 29 2018>> <<February 4 2018>> <<It is used for creating a savings bank account>>
/**
/**
 * @author jbloch1
 *
 */
public class CheckingAccount extends BankAccount 
{

	private static double baseCharge = 1.00;
	/**
	 * @param balance
	 * @param isChecking
	 * @param isSavings
	 */
	//Name: CheckingAccount(Customer customer, double balance)
	//Developer: Jonathan Bernard Bloch
	//Purpose: <<This is a constructor for creating a checking account>>
	//Inputs: A created customer and a balance
	//Outputs: None
	//Side effects: A checking bank account is created
	//Special notes: None
	public CheckingAccount(Customer customer, double balance) throws InsufficientFundsException {
		super(customer, balance);
	}

	//Name: withdrawal(double amount) method
	//Developer: Jonathan Bernard Bloch
	//Purpose: <<This method is used for withdrawing money from a checking bank account>>
	//Inputs: An amount of money to withdraw. 
	//Outputs: None
	//Side effects: The balance decreases by the amount of money to withdraw.
	/*Special notes: A checking account charges for withdrawals. The default charge is $1 but it is modified by the 
		 * customer’s discount percentage. modified how? I do withdraw(amount + $1 (1 + discount) == amount + 1 + discount) */
	public void withdrawal(double amount) throws InsufficientFundsException
	{
		synchronized (this)
		{
			if(amount > this.balance) throw new InsufficientFundsException();
				super.withdrawal(amount + baseCharge * (1.00 + this.customer.getDiscountPercentage() / 100.00));
		}
	}
		
	//Name: deposit(double amount) method
	//Developer: Jonathan Bernard Bloch
	//Purpose: <<This method is used for adding money to a checking bank account>>
	//Inputs: An amount of money to deposit. 
	//Outputs: None
	//Side effects: The balance increases by the amount deposited.
	//Special notes: None
	public void deposit(double amount)
	{
		synchronized(this)
		{
			super.deposit(amount);
		}
	}	
	
	//Name: toString()
	//Developer: Jonathan Bernard Bloch
	//Purpose: <<This toString method is used for returning the type of account including the number of bank accounts>>
	//Inputs: None
	//Outputs: Returns a string
	//Side effects: The number of the bank accounts changes and the original toString gets overridden
	//Special notes: None
	public String toString() {
		return "Checking number " + number;
	}
}
