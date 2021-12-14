package com.murilocosta.customers.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.murilocosta.customers.entity.Customer;

@Repository
public interface CustomerMongoRepository extends MongoRepository<Customer, Long> {

	public List<Customer> findByIdCustomer(String id);
	public List<Customer> findByFirstName(String firstName);
	public List<Customer> findByLastName(String lastName);
	public List<Customer> findByEmail(String email);
	public List<Customer> findByAge(Integer age);
	public List<Customer> findByCity(String city);
	public List<Customer> findByState(String state);
	public List<Customer> findAllByEnable(Boolean enable);
	public List<Customer> findAllByRemoved(Boolean removed);

}
