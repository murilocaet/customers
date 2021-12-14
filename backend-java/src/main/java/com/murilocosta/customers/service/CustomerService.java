package com.murilocosta.customers.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.murilocosta.customers.entity.Customer;
import com.murilocosta.customers.repository.CustomerMongoRepository;
import com.murilocosta.customers.request.CustomerRequest;
import com.murilocosta.customers.response.CustomerResponse;
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
public class CustomerService {

    @Autowired
    private CustomerMongoRepository repository;

	@Autowired
	private CustomerRedisService redisService;

	@Autowired
	private ControlRedisService controlRedisService;
	
	private List<Customer> updateRedisList() {
		List<Customer> customers = getAllList();
		controlRedisService.disableUpdate();
		return customers;
	}
	
	/**
	 * Variable settings to Redis pagination
	 * 
	 * @param request CustomerRequest
	 * @param response CustomerResponse
	 * 
	 */
	private void paginateRedis(CustomerRequest request, CustomerResponse response) {
		response.setTotalItens(request.getTotalItens());
//		if(request.getPage() == 1 && request.getSearchName().isEmpty()) {
		if(request.getSearchName().isEmpty()) {
			List<Customer> customers = redisService.findAll();
			response.setTotalItens(customers.size());
		}
		response.setPage(request.getPage());
		response.setPageSize(request.getPageSize());
		response.setCount(response.getTotalItens()/response.getPageSize());
		if(response.getTotalItens()%response.getPageSize() > 0) {
			response.setCount(response.getCount()+1);	
		}
	}
	
	/**
	 * Predicate settings to Redis filtering
	 * 
	 * @param request CustomerRequest 
	 * @return List of Predicate<Customer>
	 */
	private List<Predicate<Customer>> getPredicates(CustomerRequest request){
		List<Predicate<Customer>> predicateList = null;
		if(request.getSearchName() != null && !request.getSearchName().isEmpty()) {
			String searchName = request.getSearchName().toLowerCase();
			
			predicateList = new ArrayList<>();
			
			predicateList.add(c -> c.getFirstName().concat(" ").concat(c.getLastName()).toLowerCase().contains(searchName));
			predicateList.add(c -> c.getEmail().toLowerCase().contains(searchName));
			predicateList.add(c -> c.getBirthDate().contains(searchName));
			predicateList.add(c -> c.getState().toLowerCase().contains(searchName));
			predicateList.add(c -> c.getCity().toLowerCase().contains(searchName));
		}
		return predicateList;
	}
	
	/**
	 * Settings the total itens to performe Redis Filter
	 * 
	 * @param request CustomerRequest
	 * @param response CustomerResponse
	 */
	private void setTotalItensByFilterRedis(CustomerRequest request, CustomerResponse response) {
		List<Predicate<Customer>> predicateList = getPredicates(request);
		if(predicateList != null) {
			List<Customer> customers = redisService.findAll();

			customers = customers.stream()
				.filter(predicateList.get(0)
					.or(predicateList.get(1))
					.or(predicateList.get(2))
					.or(predicateList.get(3))
					.or(predicateList.get(4)))
	            .collect(Collectors.toList());

			request.setTotalItens(customers.size());
			response.setTotalItens(customers.size());
		}
	}
	
	/**
	 * Get all Customers
	 * 
	 * @param request CustomerRequest
	 * @return CustomerResponse
	 * @throws Exception
	 */
    public CustomerResponse getAll(CustomerRequest request) throws Exception{
    	CustomerResponse response = CustomerResponse.builder().build();
    	List<Customer> customers = null;
    	
    	if(controlRedisService.needUpdate()) {
   			customers = updateRedisList();
			response.setTotalItens(customers.size());
    	}
    	
    	setTotalItensByFilterRedis(request, response);
		paginateRedis(request, response);
		
		customers = redisService.findAll(request.getPage(), request.getPageSize(), getPredicates(request));
		if(customers == null || customers.isEmpty()) {
    		try {
    			customers = getAllList();
    			if(customers != null && !customers.isEmpty()) {
    				customers = redisService.findAll(request.getPage(), request.getPageSize(), getPredicates(request));
    			}
			} catch (Exception ex) {
	    		ex.printStackTrace();
	            log.error(Useful.ER_0001);
	            response.getError().add(Useful.ER_0001);
	            log.error(ex.getMessage());
			}
    	}
    	
    	if(customers != null && !customers.isEmpty()) {
			for(Customer c : customers) {
				c.setAge(Useful.getAge(c.getBirthDate()));
			}
    		response.setCustomers(customers);
    	}    	
        
		return response;
    }
    
    /**
     * Get all Customers and save it in Redis too
     * 
     * @return List of Customer
     */
    public List<Customer> getAllList(){
		List<Customer> customers = repository.findAllByRemoved(false);
		if(!customers.isEmpty()) {
			redisService.delete();
			redisService.saveAll(customers);
		}
		return customers; 
    }

    /**
     * Get Customer by ID
     * 
     * @param id String
     * @return CustomerResponse
     */
    public CustomerResponse getById(String id){
    	CustomerResponse response = CustomerResponse.builder().build();
    	try {
	    	if(controlRedisService.needUpdate()) {
	    		List<Customer> customers = updateRedisList();
    			response.setTotalItens(customers.size());
	    	}
	    	Customer customer = redisService.findById(id);
			if(customer == null || customer.getIdCustomer() == null) {
    			List<Customer> rs = repository.findByIdCustomer(id);
    	        if(rs != null && !rs.isEmpty() && !rs.get(0).getRemoved()) {
    	        	response.getCustomers().add(rs.get(0));
    	        }
	    	}else {
	    		response.getCustomers().add(customer);
	    	}
    	} catch (Exception ex) {
    		ex.printStackTrace();
            log.error(Useful.ER_0001);
            response.getError().add(Useful.ER_0001);
            log.error(ex.getMessage());
		}
    	
        return response;
    }
    
    /**
     * Validade entry Data
     * 
     * @param request CustomerRequest
     * @return List os String
     */
    private List<String> validate(CustomerRequest request) {
    	List<String> errors = new ArrayList<>();
    	if(request.getCustomer() == null) {
    		errors.add(Useful.ER_0005);
    		return errors;
    	}
		if(request.getCustomer().getFirstName() == null || request.getCustomer().getFirstName().isEmpty()) {
			errors.add(Useful.ER_0009);
    	}
		if(request.getCustomer().getLastName() == null || request.getCustomer().getLastName().isEmpty()) {
			errors.add(Useful.ER_0010);
    	}
		if(request.getCustomer().getEmail() == null || request.getCustomer().getEmail().isEmpty()) {
			errors.add(Useful.ER_0011);
    	}
    	if(request.getCustomer().getBirthDate() == null || request.getCustomer().getBirthDate().isEmpty()) {
    		errors.add(Useful.ER_0006);
    	}
		if(request.getCustomer().getState() == null || request.getCustomer().getState().isEmpty()) {
			errors.add(Useful.ER_0008);
    	}
		if(request.getCustomer().getCity() == null || request.getCustomer().getCity().isEmpty()) {
			errors.add(Useful.ER_0007);
    	}
    	return errors;
    }
    
    /**
     * Save Customer and create a Redis flag
     * 
     * @param request CustomerRequest
     * @return CustomerResponse
     */
    public CustomerResponse save(CustomerRequest request){
    	CustomerResponse response = CustomerResponse.builder().build();
    	Customer customer = null;
    	List<String> errors = validate(request);
    	if(errors.isEmpty()) {
    		List<Customer> rs = null;
    		if(request.getCustomer().getDatabaseId() == null) {
        		rs = repository.findByEmail(request.getCustomer().getEmail());
	        	if(rs == null || rs.isEmpty()) {
	        		request.getCustomer().setDatabaseId(new ObjectId());
	        		request.getCustomer().setIdCustomer(request.getCustomer().getDatabaseId().toString());
	        		request.getCustomer().setCreateAt(Useful.sdf.format(new Date()));
	        		request.getCustomer().setUpdateAt(request.getCustomer().getCreateAt());
	        		request.getCustomer().setEnable(true);
	        		request.getCustomer().setRemoved(false);
	        		request.getCustomer().setRemovedAt(null);
	        		customer = saveEntity(request);
	     	   		response.getCustomers().add(customer);
	        	   	controlRedisService.enableUpdate();
    	        }else {
    	        	response.getError().add(Useful.ER_0012);
    	        }
        	}
    	}else {
    		response.setError(errors);
    	}
    	
        return response;
    }
    
    /**
     * Update Customer and create a Redis flag
     * 
     * @param request CustomerRequest
     * @return CustomerResponse
     */
    public CustomerResponse edit(CustomerRequest request){
    	CustomerResponse response = CustomerResponse.builder().build();
    	Customer customer = null;
    	List<String> errors = validate(request);
    	if(errors.isEmpty()) {
    		List<Customer> rs = null;
    		if(request.getCustomer().getDatabaseId() != null) {
    			rs = repository.findByIdCustomer(request.getCustomer().getIdCustomer());
    	        if(rs != null && !rs.isEmpty()) {
    	        	request.getCustomer().setDatabaseId(rs.get(0).getDatabaseId());
    	        	rs = repository.findByEmail(request.getCustomer().getEmail());
    	        	if(rs == null || rs.isEmpty() || rs.get(0).getIdCustomer().equals(request.getCustomer().getIdCustomer())) {
                		customer = saveEntity(request);
             	   		response.getCustomers().add(customer);
                	   	controlRedisService.enableUpdate();
    	        	}else {
    	        		response.getError().add(Useful.ER_0012);
    	        	}
    	        }else {
    	        	response.getError().add(Useful.ER_0004);
    	        }
        	}else {
  	        	response.getError().add(Useful.ER_0004);
        	}
    	}else {
    		response.setError(errors);
    	}
    	
        return response;
    }
    
    
    /**
     * Activate Customer and create a Redis flag
     * 
     * @param request CustomerRequest
     * @return CustomerResponse
     */
    public CustomerResponse activate(String id){
    	CustomerResponse response = CustomerResponse.builder().build();
    	Customer customer = null;
		List<Customer> rs = repository.findByIdCustomer(id);
        if(rs != null && !rs.isEmpty()) {
        	rs.get(0).setEnable(true);
    		customer = repository.save(rs.get(0));
 	   		response.getCustomers().add(customer);
    	   	controlRedisService.enableUpdate();
        }else {
        	response.getError().add(Useful.ER_0004);
        }
        return response;
    }
    
    /**
     * Disable Customer and create a Redis flag
     * 
     * @param request CustomerRequest
     * @return CustomerResponse
     */
    public CustomerResponse disable(String id){
    	CustomerResponse response = CustomerResponse.builder().build();
    	Customer customer = null;
		List<Customer> rs = repository.findByIdCustomer(id);
        if(rs != null && !rs.isEmpty()) {
        	rs.get(0).setEnable(false);
    		customer = repository.save(rs.get(0));
 	   		response.getCustomers().add(customer);
    	   	controlRedisService.enableUpdate();
        }else {
        	response.getError().add(Useful.ER_0004);
        }
        return response;
    }
    
    /**
     * Save Customer on database
     * 
     * @param request CustomerRequest
     * @return Customer
     */
    public Customer saveEntity(CustomerRequest request){
        log.info("Salvando o Customer");
        repository.save(request.getCustomer());
        log.info("Customer salvo com sucesso.");
        return request.getCustomer();
    }
    
    /**
     * Remove Customer logically and create a Redis flag
     * 
     * @param id String
     * @return CustomerResponse
     */
    public CustomerResponse remove(String id){
    	CustomerResponse response = CustomerResponse.builder().build();
    	if(id != null) {
	    	Customer customer = removeEntity(id);
	    	if(customer != null && customer.getIdCustomer() != null) {
	 	   		response.getCustomers().add(customer);
	 	   		controlRedisService.enableUpdate();
	    	}else {
	            log.info("Customer não encontrado.");
	            response.getError().add(Useful.ER_0004);
	    	}
    	}
        return response;
    }
    
    /**
     * Remove Customer logically on database
     * 
     * @param id String
     * @return Customer
     */
    public Customer removeEntity(String id){
        log.info("Removendo o Customer");
    	List<Customer> rs = repository.findByIdCustomer(id);
        if(rs != null && !rs.isEmpty()) {
        	rs.get(0).setRemoved(true);
        	rs.get(0).setRemovedAt(Useful.sdf.format(new Date()));
            repository.save(rs.get(0));
            log.info("Customer removido com sucesso.");
            return rs.get(0);
        }
        return null;
    }
    
    /**
     * Change Removed flag for all Customers 
     * 
     * @param status boolean
     * @return CustomerResponse
     */
    public CustomerResponse changeAll(boolean status){
    	CustomerResponse response = CustomerResponse.builder().build();
    	
    	List<Customer> list = getAllList();    	
    	for (Customer c : list) {
    		c.setEnable(status);
		}
        repository.saveAll(list);
   		controlRedisService.enableUpdate();
   		response.setCustomers(list);
   		
        return response;
    }
}
