package domain;

public class Account {
	private String accountNumber;
	private String customerId;
	private double balance;
	private String accountType;
	/**
	 * @param accountNumber
	 * @param customerId
	 * @param balance
	 * @param accountType
	 */
	public Account(String accountNumber, String customerId, double balance, String accountType) {
		super();
		this.accountNumber = accountNumber;
		this.customerId = customerId;
		this.balance = balance;
		this.accountType = accountType;
	}
	public String getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public double getBalance() {
		return balance;
	}
	public void setBalance(double balance) {
		this.balance = balance;
	}
	public String getAccountType() {
		return accountType;
	}
	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}
	
	

}
