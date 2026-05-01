package repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import domain.Transaction;

public class TransactionRepository {
	// map<accno, transactions of that account>
	private final Map<String, List<Transaction>> txnByAccount = new HashMap<>();

	public void add(Transaction transaction) {
		// if this key is not found like if there is no transaction done against that account then it would create a first new transaction for that account
		List<Transaction> list =  txnByAccount.computeIfAbsent(transaction.getAccountNumber(),
				k -> new ArrayList<>());
		list.add(transaction);
	}

	public List<Transaction> findByAccountTxn(String account) {
		// TODO Auto-generated method stub
		return new ArrayList<>(txnByAccount.getOrDefault(account, Collections.emptyList()));
	}
}
