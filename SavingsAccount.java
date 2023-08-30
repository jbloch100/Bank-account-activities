/**
 * 
 */
package a2;

//File Name: SavingsAccount.java
//Developer: Jonathan Bernard Bloch
//Purpose: It is used for creating a savings bank account.
//Inputs: None
//Outputs: Result of the toString method.
//Modifications: None
//=========================================================================
//<<january 29 2018>> <<February 4 2018>> <<It is used for creating a savings bank account>>
/**
 * @author jbloch1
 *
 */
public class SavingsAccount extends BankAccount {
	
	private static double baseReward = 1.00;
	private static double transactionMinimum = 1000.00;
	
	/**
	 * @param balance
	 * @param isChecking
	 * @param isSavings
	 */
	//Name: SavingsAccount(Customer customer, double balance)
	//Developer: Jonathan Bernard Bloch
	//Purpose: <<This is a constructor for creating a savings account>>
	//Inputs: A created customer and a balance
	//Outputs: None
	//Side effects: A savings bank account is created
	//Special notes: None
	public SavingsAccount(Customer customer, double balance) throws InsufficientFundsException {
		super(customer, balance);
	}
	
	//Name: withdrawal(double amount) method
	//Developer: Jonathan Bernard Bloch
	//Purpose: <<This method is used for withdrawing money from a savings bank account>>
	//Inputs: An amount of money to withdraw. 
	//Outputs: None
	//Side effects: The balance decreases by the amount of money to withdraw.
	//Special notes: A savings account only permits withdrawals greater than or equal to $1000.
	public void withdrawal(double amount) throws InsufficientFundsException
	{
		synchronized (this)
		{
			if(amount < transactionMinimum) throw new IllegalArgumentException("Not enough money to withdraw from savings account");
			super.withdrawal(amount);
		}
	}
	
	//Name: deposit(double amount) method
	//Developer: Jonathan Bernard Bloch
	//Purpose: <<This method is used for adding money to a savings bank account>>
	//Inputs: An amount of money to deposit. 
	//Outputs: None
	//Side effects: The balance increases by the amount deposited including the base reward and discount percentage.
	/*Special notes: Every deposit is awarded $1 but it is increased based on the customer’s discount percentage.
	 * based on the percentage is very vague, I do deposit(amount + $1 (1 + discount) == amount + 1 + discount)*/
	public void deposit(double amount)
	{
		synchronized(this)
		{
			super.deposit(amount + baseReward * (1.00 + this.customer.getDiscountPercentage() / 100.00));
		}
	}	
	
	//Name: toString()
	//Developer: Jonathan Bernard Bloch
	//Purpose: <<This toString method is used for returning the type of account including the number of bank accounts>>
	//Inputs: None
	//Outputs: Returns a string
	//Side effects: None
	//Special notes: None
	public String toString() {
		return "Savings number " + number;
	}
}
