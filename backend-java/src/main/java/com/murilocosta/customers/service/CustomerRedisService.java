package com.murilocosta.customers.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.murilocosta.customers.entity.Customer;
import com.murilocosta.customers.repository.CustomerRepository;
import com.murilocosta.customers.useful.Useful;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class CustomerRedisService {
	
	@Autowired
	private CustomerRepository repository;

    public void saveAll(List<Customer> customers) {
    	Map<String, Customer> customerMap = new HashMap<>();
    	String index;
    	for (Customer customer : customers) {
    		index = String.valueOf(customer.getDatabaseId().getTimestamp());
    		customerMap.put(index, customer);
		}
		if(!customers.isEmpty()) {
			repository.saveAll(customerMap,Useful.CUSTOMERS);
		}
    }
    
    public List<Customer> findAll() {
		return repository.findAll(Useful.CUSTOMERS);
    }
    
    public List<Customer> findAll(final int pageNum, final int pageSize, List<Predicate<Customer>> predicateList) throws Exception {
    	return repository.findAll(pageNum, pageSize, Useful.CUSTOMERS, predicateList);
    }
    
    public Customer findById(String id) {
		return repository.findById(Useful.CUSTOMERS, id);
    }

    public void update(List<Customer> customers) {
    	saveAll(customers);
    }

    public void delete() {
    	repository.delete(Useful.CUSTOMERS);
    }
}
