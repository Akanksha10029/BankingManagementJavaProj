package repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import domain.Customer;

public class CustomerRepository {
	private final Map<String, Customer> customersById = new HashMap<>(); //<id,customer obj>
	
	public List<Customer> findAllcust() {
		return new ArrayList<>(customersById.values());
	}

	public void saveCustomer(Customer customer) {
		customersById.put(customer.getId(), customer);
	}

}
