package a2;
//File Name: Bank.java
// Developer: Jonathan Bernard Bloch
//Purpose: It is a user inerface to get access to the other files.
//Inputs: None
//Outputs: Result of each method including system calls.
//Modifications: None
//=========================================================================
//<<january 29 2018>> <<February 4 2018>> <<It is a user interface file>>
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Bank
{
	// HashMap lookup is O(1) amortized, ArrayList is O(n) amortized which is unacceptable. nb, java hashmap has the same exponential growth as an ArrayList, https://stackoverflow.com/questions/5040753/why-arraylist-grows-at-a-rate-of-1-5-but-for-hashmap-its-2
	// customer number (bank card) -> Customer
	private static Map<Integer, Customer> customerMap = new HashMap<>();
	//static ArrayList<Customer> customers = new ArrayList<Customer>();

	//Name: main method
	//Developer: Jonathan Bernard Bloch
	//Purpose: <<This method is used as a user interface to play with all the bank accounts and to let the user start executing the code>>
	//Inputs: None
	//Outputs: System calls output
	//Side effects: None
	//Special notes: None
	public static void main(String[] args)
	{		
		boolean quit = false;
		String menuSelectionByNumber;
		Customer selectedCustomer = null;
		BankAccount selectedAccount = null;
		Scanner input = new Scanner(System.in);

		while(!quit)
		{	
			try
			{
				System.out.println("Select one of the following menus by entering the number near the menu you decide to select.");
				
				// main menu
				if(selectedCustomer == null) {
					// detect invalid state
					assert(selectedCustomer == null && selectedAccount == null);
					System.out.print(
						"1. If you want to quit\n" +
						"2. If you want to list all the customers\n" +
						"3. If you would like to add a customer\n" +
						"4. If you want to select a customer\n");
					menuSelectionByNumber = input.nextLine();					
					switch(Integer.parseInt(menuSelectionByNumber))
					{
						case 1: 
							quit = true;
							break;
						case 2:
							listCustomers();
							break;
						case 3:
							selectedCustomer = createCustomer();
							break;
						case 4:
							selectedCustomer = selectCustomer();
							break;
						default:
							throw new IllegalArgumentException("You did not enter any of the menus. Please try again");
					}
				}
				
				// customer menu
				else if(selectedAccount == null) {
					// detect invalid state
					assert(selectedCustomer != null && selectedAccount == null);
					System.out.println("Selected customer: " + selectedCustomer);
					for(BankAccount account : selectedCustomer)
					{
						System.out.println("Account " + account);
					}
					System.out.print("1. Back to main menu\n" +
						"2. Add bank account\n" +
						"3. Select bank account\n");
					menuSelectionByNumber = input.nextLine();					
					switch(Integer.parseInt(menuSelectionByNumber))
					{
						case 1: 
							selectedCustomer = null;
							selectedAccount = null;
							break;
						case 2:
							selectedAccount = createAccount(selectedCustomer);
							break;
						case 3:
							selectedAccount = selectBankAccount(selectedCustomer);
							break;
						default:
							throw new IllegalArgumentException("You did not enter any of the menus. Please try again");
					}
				}
				
				// account menu
				else
				{
					assert(selectedCustomer != null && selectedAccount != null);
					System.out.println("Selected customer: " + selectedCustomer);
					System.out.println("Selected account: " + selectedAccount);
					System.out.print("1. Back to customer menu\n" +
						"2. Check the balance\n" +
						"3. Deposit to the bank account\n" +
						"4. Withdraw from the bank account\n" +
						"5. Transfer money from the customer's account to one of their other accounts\n");
					menuSelectionByNumber = input.nextLine();					
					switch(Integer.parseInt(menuSelectionByNumber))
					{
					case 1:
						selectedAccount = null;
						break;
					case 2:
						System.out.printf("Current balance is $%.2f\n", selectedAccount.getBalance());
						break;
					case 3:
						bankAccountDeposit(selectedAccount);
						break;
					case 4:
						bankAccountWithdraw(selectedAccount);
						break;						
					case 5:
						transferMoney(selectedAccount);
						break;
					default:
						throw new IllegalArgumentException("You did not enter any of the menus. Please try again");
					}
				}
			}
			catch (Exception e) {
				/*System.err.println("Exception: " + e.getMessage());*/
				System.err.println("Exception " + e);
				/*e.printStackTrace();*/
			}
		}
	}
	
	//Name: listCustomers() method
	//Developer: Jonathan Bernard Bloch
	//Purpose: <<This method is used to list all the existing customers>>
	//Inputs: None
	//Outputs: each customer
	//Side effects: None
	//Special notes: None
	private static void listCustomers()
	{
		// https://stackoverflow.com/questions/46898/how-to-efficiently-iterate-over-each-entry-in-a-map
		for (Customer customer: customerMap.values())
			System.out.println(customer);	
	}
	
	
	//Name: createCustomer() method
	//Developer: Jonathan Bernard Bloch
	//Purpose: <<This method is used for creating a new customer. The customer is placed in the Map with a random customer_id>>
	//Inputs: None
	//Outputs: System calls output
	//Side effects: A new customer is created
	//Special notes: None
	private static Customer createCustomer() throws IllegalArgumentException
	{
		Scanner input = new Scanner(System.in);
		System.out.println("Please enter the customer's name: ");
		String name = input.nextLine();		
		System.out.println("Please enter a discount percentage for the customer: ");
		double discountPercentage = input.nextDouble();
		return new Customer(customerMap, name, discountPercentage);
	}
		
	//Name: selectCustomer() method
	//Developer: Jonathan Bernard Bloch
	//Purpose: <<This method is used for selecting a customer>>
	//Inputs: None
	//Outputs: System calls output
	//Side effects: A customer is selected
	//Special notes: None
	private static Customer selectCustomer() throws IllegalArgumentException
	{
		Scanner input = new Scanner(System.in);
		System.out.println("Please enter a customer Id number: " );
		int customerIdNumber = input.nextInt();
		// fixme: this can be done in O(1) instead of O(n) with HashMap
		/*for (Customer customer: customers) ... */
		Customer c = customerMap.get(customerIdNumber);
		if(c == null) throw new IllegalArgumentException("Customer number not found.");
		return c;
	}
	
	//Name: createAccount(Customer customer) method
	//Developer: Jonathan Bernard Bloch
	//Purpose: <<This method is used for creating a new bank account>>
	//Inputs: a customer
	//Outputs: System calls output
	//Side effects: A new bank account is created
	//Special notes: None
	private static BankAccount createAccount(Customer customer) throws InsufficientFundsException
	{
		Scanner input = new Scanner(System.in);
		System.out.println("What type of bank account would you like? Savings or checkings? " 
				+ "If you want savings, enter 1. If you want checkings, enter 2.");

		int selectAccountTypeByNumber = input.nextInt();
		
		System.out.println("What initial balance should this customer have in this account?");
		double balance = input.nextDouble();
		
		BankAccount account;
		switch(selectAccountTypeByNumber)
		{
			case 1: 
				account = new SavingsAccount(customer, balance);
				break;
			case 2:
				account = new CheckingAccount(customer, balance);
				break;
			default:
				throw new IllegalArgumentException("Sorry, we do not understand");
		}
		return account;
	}
	
	//Name: selectBankAccount(Customer customer) method
	//Developer: Jonathan Bernard Bloch
	//Purpose: <<This method is used for selecting a bank account and returning it>>
	//Inputs: customer
	//Outputs: returns a bank account
	//Side effects: IllegalArgumentException("no account") gets thrown when no bank account is actually passed on 
	//Special notes: None
	private static BankAccount selectBankAccount(Customer customer) throws IllegalArgumentException
	{
		Scanner input = new Scanner(System.in);
		System.out.println("select bank account by selecting the bank account number of this account: ");
		int selectAccount = input.nextInt();
		for (BankAccount account: customer)
		{
			if(account.getAccountNumber() == selectAccount) return account;
		}
		throw new IllegalArgumentException("Account number not found.");
	}

	//Name: bankAccountDeposit(BankAccount account) method
	//Developer: Jonathan Bernard Bloch
	//Purpose: <<This method is used for adding money to a bank account>>
	//Inputs: A bank account
	//Outputs: System calls output.
	//Side effects: IllegalArgumentException("no account") gets thrown when no bank account is actually passed on 
	//Special notes: None
	private static void bankAccountDeposit(BankAccount account)
	{
		Scanner input = new Scanner(System.in);
		System.out.println("How much money would you like to deposit into this account? ");
		account.deposit(input.nextDouble());
	}

	//Name: bankAccountWithdraw(BankAccount account) method
	//Developer: Jonathan Bernard Bloch
	//Purpose: <<This method is used for withdrawing money from a bank account>>
	//Inputs: A bank account
	//Outputs: System calls output.
	//Side effects: IllegalArgumentException("no account") gets thrown when no bank account is actually passed on 
	//Special notes: None
	private static void bankAccountWithdraw(BankAccount account) throws InsufficientFundsException
	{
		Scanner input = new Scanner(System.in);
		System.out.println("How much money would you like to withdraw from this account? ");
		account.withdrawal(input.nextDouble());
	}

	//Name: transferMoney(BankAccount account) method
	//Developer: Jonathan Bernard Bloch
	//Purpose: <<This method is used for for transferring money from the selected bank account to another>>
	//Inputs: A bank account
	//Outputs: System calls output.
	//Side effects: IllegalArgumentException("no account") gets thrown when no bank account is actually passed on 
	//Special notes: None
	private static void transferMoney(BankAccount account) throws InsufficientFundsException
	{
		Scanner input = new Scanner(System.in);
		System.out.println("Enter the account number of the account you would like to transfer the money into:");
		int transferAccountNumber = input.nextInt();
		BankAccount transferAccount = null;
		for(BankAccount a: account.getCustomer())
		{
			if(a.getAccountNumber() == transferAccountNumber)
			{
				transferAccount = a;
				break;
			}
		}
		if(transferAccount == null) throw new IllegalArgumentException("There is no account that exists");
		if(transferAccount == account) throw new IllegalArgumentException("Can't transfer to the same account");
		System.out.println("How much money would you like to transfer out of this account? ");
		double transferAmount = input.nextDouble();
		// transfer is a special case (don't have fees) so I need to handle it in bankAccount directly
		account.transfer(transferAmount, transferAccount);
	}
}
