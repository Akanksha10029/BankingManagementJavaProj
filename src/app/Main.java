package app;

import java.util.Scanner;

import service.BankService;
import service.impl.BankServiceImpl;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner sc = new Scanner(System.in);
		BankService bankService = new BankServiceImpl();
		System.out.println("Welcome to Console Bank");
		boolean running = true;
		while(running) {
			System.out.println(
			        """
			        1) Open Account
			        2) Deposit
			        3) Withdraw
			        4) Transfer
			        5) Account Statement
			        6) List Accounts
			        7) Search Accounts by Customer Name
			        0) Exit
			        """
			);
			System.out.println("Choose: ");
			String choice = sc.nextLine().trim();
			System.out.println("CHOICE Chosen by user: " + choice);
			
			switch (choice) {
			case "1" -> openAccount(sc, bankService);
			case "2" ->deposit(sc, bankService);
			case "3" ->withdraw(sc, bankService);
			case "4" ->transfer(sc, bankService);
			case "5" ->statement(sc, bankService);
			case "6" ->listAccounts(sc, bankService);
			case "7" ->searchAccounts(sc, bankService);
			case "0" -> running = false;	
			default -> System.out.println("Invalid choice!");
			}
		}
	}
	
	private static void openAccount(Scanner sc, BankService bankService) {
		
		while(true) {
			try {
				System.out.println("Customer name: ");
				String name = sc.nextLine().trim();
				System.out.println("Customer email: ");
				String email = sc.nextLine().trim();
				System.out.println("Account Type (SAVINGS/CURRENT): ");
				String accountType = sc.nextLine().trim();
				System.out.println("Initial deposit (optional, blank for 0)");
				String amountStr = sc.nextLine().trim();
				if(amountStr.isBlank() || amountStr.isEmpty()) {
					amountStr = "0";
				}
				Double initial = Double.valueOf(amountStr);
				String accountNumber = bankService.openAccount(name, email, accountType);
				if(initial>0) {
					bankService.deposit(accountNumber, initial, "Initial deposit");
				}
				System.out.println("Account opened: " + accountNumber);
				break;
			}
			catch (Exception e) {
				System.out.println("Error ❌ " + e.getMessage());
			}
		}		
	}
	
	
	private static void deposit(Scanner sc, BankService bankService) {
		
		while(true) {
			try {
				System.out.println("Account Number: ");
				String accountNumber = sc.nextLine().trim();
				
				System.out.println("Amount: ");
				double amount = Double.parseDouble(sc.nextLine().trim());
				
				boolean success = false;
				try {
					success = bankService.deposit(accountNumber, amount, "Deposit");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if(success) {
					System.out.println("Deposited: " + amount);
				}
				else {
					System.out.println("Error!!! Account not found!");
				}
				break;
			}
			catch (Exception e) {
	            System.out.println("Error ❌ " + e.getMessage());
	        }
		}
	} 
	
	private static void withdraw(Scanner sc, BankService bankService) {
	    while(true) {
	    	try {
	    		System.out.println("Account Number: ");
	    	    String accountNumber = sc.nextLine().trim();

	    	    System.out.println("Amount: ");
	    	    double amount = Double.parseDouble(sc.nextLine().trim());

	    	    String result = bankService.withdraw(accountNumber, amount, "Withdrawal");

	    	    switch(result) {
	    	        case "SUCCESS":
	    	            System.out.println("Withdrawn: " + amount);
	    	            break;
	    	        case "INSUFFICIENT":
	    	            System.out.println("Insufficient Balance!!!");
	    	            break;
	    	        case "NOT_FOUND":
	    	            System.out.println("Account not found!");
	    	            break;
	    	        default:
	    	            System.out.println("Error processing request!");
	    	    }
	    	    break;
	    	}
	    	catch (Exception e) {
	            System.out.println("Error ❌ " + e.getMessage());
	        }
	    }
	}
	
	private static void transfer(Scanner sc, BankService bankService) {
	    while(true) {
	    	try {
	    		System.out.println("From Account: ");
	    	    String fromAccount = sc.nextLine().trim();
	    	    
	    	    System.out.println("To Account: ");
	    	    String toAccount = sc.nextLine().trim();

	    	    System.out.println("Amount: ");
	    	    double amount = Double.parseDouble(sc.nextLine().trim());
	    	    bankService.transfer(fromAccount, toAccount, amount, "transfer");
	    	    break;
	    	}
	    	catch (Exception e) {
	            System.out.println("Error ❌ " + e.getMessage());
	        }
	    }
	}

	private static void statement(Scanner sc, BankService bankService) {
		System.out.println("Account");
		String account = sc.nextLine().trim();
		bankService.getStatement(account).forEach(t -> {
			System.out.println(t.getTimestamp() + " | " + t.getType() + " | " + t.getAccountNumber() + " | " + t.getAmount() + " | " + t.getNote());
		});;
	}

	private static void listAccounts(Scanner sc, BankService bankService) {
		bankService.listAccounts().forEach(a->
		System.out.println("AccNo: " + a.getAccountNumber() + " | " + "AccType: " + a.getAccountType() + " | " + "Bal: " + a.getBalance() + " | " + "Customer ID: " +a.getCustomerId()));
	}

	private static void searchAccounts(Scanner sc, BankService bankService) {
		System.out.println("Enter Customer name: ");
		String custName = sc.nextLine().trim();
		bankService.searchAccountsbyCustomerName(custName)
	    .forEach(account -> System.out.println(
	        account.getAccountNumber() + " | " +
	        account.getAccountType() + " | " +
	        account.getBalance() + " | " +
	        account.getCustomerId()
	    ));	}
	
}