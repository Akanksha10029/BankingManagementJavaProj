package service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import domain.Account;
import domain.Customer;
import domain.Transaction;
import exceptions.AccountNotFoundException;
import exceptions.InsufficientFundsException;
import exceptions.ValidationException;
import repository.AccountRepository;
import repository.CustomerRepository;
import repository.TransactionRepository;
import service.BankService;
import util.Validation;

public class BankServiceImpl implements BankService {
	
	private final AccountRepository accountRepository = new AccountRepository();
	private final TransactionRepository transactionRepository = new TransactionRepository();
	private final CustomerRepository customerRepository = new CustomerRepository();
	
	private final Validation<String> validateName = name -> {
		if(name==null || name.isBlank()) throw new ValidationException("Name can not be null or blank"); 
	};
	
	private final Validation<String> validateEmail = email -> {
		if(email==null || email.isBlank() || !email.contains("@")) throw new ValidationException("Kindly fill valid email"); 
	};
	
	private final Validation<String> validateAccountType = accountType -> {
		if(accountType==null || accountType.isBlank() || !accountType.equalsIgnoreCase("SAVINGS") && !accountType.equalsIgnoreCase("CURRENT")) throw new ValidationException("Account Type should be either Savings or Current"); 
	};
	
	@Override
	public String openAccount(String name, String email, String accountType) {
		
		// Validate name, email and accountType
		validateName.validate(name);
		validateEmail.validate(email);
		validateAccountType.validate(accountType);
		
		String customerId = UUID.randomUUID().toString();
		Customer customer = new Customer(customerId, name, email);
		customerRepository.saveCustomer(customer);
		
		String accountNumber = getAccountNumber();
		
		Account account = new Account(accountNumber, customerId, 0, accountType);
		
		// save
		accountRepository.save(account);
		
		return accountNumber;
	}

	private String getAccountNumber() {
		// change later -> (old - no. of accounts present)size + 1(newly creating) = prefix_(size+newly added) : 10+ 1 = AC11
//		String accountNumber = UUID.randomUUID().toString();
		int size = accountRepository.findAll().size() + 1;
		String accountNumber = String.format("AC%06d", size);
		return accountNumber;
	}

	
	@Override
	public List<Account> listAccounts() {
		// TODO Auto-generated method stub
		return accountRepository.findAll().stream().sorted(Comparator.comparing(Account::getAccountNumber)).collect(Collectors.toList());
	}

	@Override
	public boolean deposit(String accountNumber, double amount, String note) {
		// TODO Auto-generated method stub
		Account account = accountRepository.findByAccountNumber(accountNumber);
		if(account == null) return false;
		
		account.setBalance(account.getBalance() + amount);
		Transaction transaction = new Transaction(UUID.randomUUID().toString(), domain.Type.DEPOSIT, account.getAccountNumber(), amount, LocalDateTime.now(), note);
		transactionRepository.add(transaction);
		return true;
	}

	@Override
	public String withdraw(String accountNumber, double amount, String note) {
	    Account account = accountRepository.findByAccountNumber(accountNumber);
	    if(account == null) return "NOT_FOUND";

	    if(account.getBalance() < amount) return "INSUFFICIENT";

	    account.setBalance(account.getBalance() - amount);
	    Transaction transaction = new Transaction(UUID.randomUUID().toString(), domain.Type.WITHDRAW, account.getAccountNumber(), amount, LocalDateTime.now(), note);
		transactionRepository.add(transaction);
	    return "SUCCESS";
	}

	@Override
	public void transfer(String fromAccount, String toAccount, double amount, String note) {
		// TODO Auto-generated method stub
		if(fromAccount.equals(toAccount)) {
			throw new ValidationException("Cannot transfer to your own account! Kindly choose another account");
		}
		Account from = accountRepository.findByAccountNumber(fromAccount);
		if(from == null) throw new AccountNotFoundException("Account not found" + fromAccount);
		
		Account to = accountRepository.findByAccountNumber(toAccount);
		if(to == null) throw new AccountNotFoundException("Account not found" + toAccount);
		
		if(from.getBalance() < amount) throw new InsufficientFundsException("Insufficient Balance");
		
		from.setBalance(from.getBalance() - amount);
		to.setBalance(to.getBalance() + amount);

		Transaction fromTransaction = new Transaction(UUID.randomUUID().toString(), domain.Type.TRANSFER_OUT, from.getAccountNumber(), amount, LocalDateTime.now(), note);
		transactionRepository.add(fromTransaction);
		
		Transaction toTransaction = new Transaction(UUID.randomUUID().toString(), domain.Type.TRANSFER_IN, to.getAccountNumber(), amount, LocalDateTime.now(), note);
		transactionRepository.add(toTransaction);
		
		System.out.println("Amount successfully transferred from " + fromAccount + " to " + toAccount);
		
	}

	@Override
	public List<Transaction> getStatement(String account) {
		// TODO Auto-generated method stub
		return transactionRepository.findByAccountTxn(account).stream().sorted(Comparator.comparing(Transaction::getTimestamp)).collect(Collectors.toList());
	}

	@Override
	public List<Account> searchAccountsbyCustomerName(String custName) {
		// TODO Auto-generated method stub
		String query = (custName == null) ? "" : custName.toLowerCase();
		
		/* using for loop */
		
//		List<Account> filteredResult = new ArrayList<>();
//		for(Customer cust: customerRepository.findAllcust()){
//			if(cust.getName().toLowerCase().contains(query)) {
//				filteredResult.addAll(accountRepository.findByCustomerId(cust.getId()));
//			}
//		}
//		filteredResult.sort(Comparator.comparing(Account::getAccountNumber));
//		return null;
		
		/* using streams in just one line */
		
		return customerRepository.findAllcust().stream()
				.filter(cust -> cust.getName().toLowerCase().contains(query))
				.flatMap(cust -> accountRepository.findByCustomerId(cust.getId()).stream())
				.sorted(Comparator.comparing(Account::getAccountNumber))
				.collect(Collectors.toList());
	}

}
