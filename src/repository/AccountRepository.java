package repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import domain.Account;

public class AccountRepository {
	private final Map<String, Account> accountsByNumber = new HashMap<>();
	
	public void save(Account account) {
		accountsByNumber.put(account.getAccountNumber(), account);
	}

	public List<Account> findAll() {
		return new ArrayList<>(accountsByNumber.values());
	}
	
	public Account findByAccountNumber(String accountNumber) {
		return accountsByNumber.get(accountNumber); // returns null if not found
	}

	public List<Account> findByCustomerId(String customerId) {
		List<Account> result = new ArrayList<Account>();
		for(Account acc : accountsByNumber.values()) {
			if(acc.getCustomerId().equals(customerId)) {
				result.add(acc);
			}
		}
		return result;
	}
}
