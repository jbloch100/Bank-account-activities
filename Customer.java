package a2;

//File Name: Customer.java
//Developer: Jonathan Bernard Bloch
//Purpose: Used for storing all customer's information.
//Inputs: None
//Outputs: Result of the toString method.
//Modifications: None
//=========================================================================
//<<january 29 2018>> <<February 4 2018>> <<It is used for storing all customer's information>>
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.ThreadLocalRandom;
import java.util.Map;

public class Customer implements Iterable<BankAccount>
{
	// "A customer has a name, a unique customer number, and a discount percentage."
	private String name;
	private final int id;
	private double discountPercentage;
	private ArrayList <BankAccount> accounts;
		
	// Add a customer to customers with name and discountPercentage to the customerMap
	//Name: Customer(ArrayList<Customer> customerMap, String name, double discountPercentage)
	//Developer: Jonathan Bernard Bloch
	//Purpose: <<This is a constructor for customer>>
	//Inputs: String name: the name of the holder of the accounts, double discountPercentage: between [0, 100], inclusive.
	//Outputs: None
	//Side effects: A new customer is created
	//Special notes: None
	public Customer(Map<Integer, Customer> customerMap, String name, double discountPercentage) throws IllegalArgumentException
	{
		if(customerMap == null || name == null || name.length() == 0 || discountPercentage > 100.0 || discountPercentage < 0.0) throw new IllegalArgumentException("invalid customer information");
		this.name = name;
		this.discountPercentage = discountPercentage;
		this.accounts = new ArrayList<BankAccount>();
		// Random customer id. In the unlikely event that it conflicts, drop it and get another one
		Integer id;
		do 
		{
			// https://stackoverflow.com/questions/363681/how-do-i-generate-random-integers-within-a-specific-range-in-java
			id = ThreadLocalRandom.current().nextInt(1, Integer.MAX_VALUE);
		} while(customerMap.containsKey(id));
		this.id = id;
		customerMap.put(id, this);
	}
	
	//Name: iterator()
	//Developer: Jonathan Bernard Bloch
	//Purpose: <<A Customer class is Iterable on it's BankAccounts>>
	//Inputs: None
	//Outputs: None
	//Side effects: None
	//Special notes: None
	public Iterator<BankAccount> iterator() {
		return accounts.iterator();
	}
	
	//Name: getDiscountPercentage()
	//Developer: Jonathan Bernard Bloch
	//Purpose: <<This method is used as a getter for the customer's discount percentage.>>
	//Inputs: None
	//Outputs: System calls output
	//Side effects: None
	//Special notes: None
	public double getDiscountPercentage()
	{
		return discountPercentage;
	}
	
	//Name: getName
	//Developer: Jonathan Bernard Bloch
	//Purpose: <<This method is used for displaying the customer's name>>
	//Inputs: None
	//Outputs: return strings
	//Side effects: None
	//Special notes: None
	public String getName()
	{
		return name;
	}	
	
	//Name: toString
	//Developer: Jonathan Bernard Bloch
	//Purpose: <<This toString method is used for displaying the customer's name>>
	//Inputs: None
	//Outputs: return strings
	//Side effects: None
	//Special notes: None
	public String toString()
	{
		return name + " id " + id;
	}

	//Name: addAccount
	//Developer: Jonathan Bernard Bloch
	//Purpose: <<This links the account to a customer.>>
	//Inputs: None
	//Outputs: return strings
	//Side effects: None
	//Special notes: None
	public void addAccount(BankAccount account) {
		this.accounts.add(account);
	}
}
