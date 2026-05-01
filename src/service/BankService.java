package service;

import java.util.List;

import domain.Account;
import domain.Transaction;

public interface BankService {
	String openAccount(String name, String email, String accountType);
	List<Account> listAccounts();
	boolean deposit(String accountNumber, double amount, String note);
	String withdraw(String accountNumber, double amount, String note);
	void transfer(String fromAccount, String toAccount, double amount, String note);
	List<Transaction> getStatement(String account);
	List<Account> searchAccountsbyCustomerName(String custName);
}

