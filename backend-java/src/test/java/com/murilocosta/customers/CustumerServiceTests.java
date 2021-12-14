package com.murilocosta.customers;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import com.murilocosta.customers.entity.Customer;
import com.murilocosta.customers.repository.CustomerMongoRepository;
import com.murilocosta.customers.repository.CustomerRepository;
import com.murilocosta.customers.request.CustomerRequest;
import com.murilocosta.customers.response.CustomerResponse;
import com.murilocosta.customers.service.ControlRedisService;
import com.murilocosta.customers.service.CustomerRedisService;
import com.murilocosta.customers.service.CustomerService;
import com.murilocosta.customers.useful.Useful;


@SpringBootTest
class CustumerServiceTests {

	private CustomerService service;
	private CustomerRepository repository = mock(CustomerRepository.class);
	private CustomerRequest request;
	private CustomerResponse response;
	private Customer customer;
	private CustomerMongoRepository repositoryMongoRepository = mock(CustomerMongoRepository.class);
	private CustomerRedisService redisService = mock(CustomerRedisService.class);
	private ControlRedisService controlRedisService = mock(ControlRedisService.class);
	
	@BeforeEach
	public void setUp() {
	    MockitoAnnotations.initMocks(this);
	    service = new CustomerService(repositoryMongoRepository, redisService, controlRedisService);
	    request = new CustomerRequest();
	    response = new CustomerResponse();
	    customer = new Customer();
	}
	
	@Test
	public void getCustumerList() throws Exception {
		givenAValidListResponse();
	    whenServicesIsCalled();
	    thenResponseIsNotEmpty();
	}
	
	@Test
	public void getFilteredSingleCustumer() throws Exception {
	    givenAValidSingleResponse();
	    whenServicesIsCalled();
	    thenResponseIsNotEmpty();
	    thenResponseHasSingleCustomer();
	}
	
	@Test
	public void getSingleCustumerById() throws Exception {
		givenAValidSingleResponseById();
		whenServicesGetByIdIsCalled();
	    thenResponseIsNotEmpty();
	    thenResponseHasSingleCustomer();
	}
	
	@Test
	public void UpdateCustumerFirstNameRequired() throws Exception {
		givenAValidSingleResponseById();
		whenServicesGetByIdIsCalled();
		whenFirstNameIsNull();
		whenServicesSaveIsCalled();
		thenResponseReturnErrorMsg();
	}
	
	@Test
	public void UpdateCustumerAllFieldsRequired() throws Exception {
		givenAValidSingleResponseById();
		whenServicesGetByIdIsCalled();
		whenAllFieldsIsNull();
		whenServicesSaveIsCalled();
		thenResponseReturnAllErrorMsg();
	}
	
	@Test
	public void SaveCustumerAllFieldsValid() throws Exception {
		givenAValidSingleResponseById();
		givenAValidNewCustomerRequest();
		givenAValidCustomerSaved();
		whenServicesSaveIsCalled();
		thenResponseReturnSavedCustomer();
	}
	
	
	//Given
	private List<Customer> getList() {
		Date d = new Date();
		List<Customer> customerList = new ArrayList<>();
		for(int i = 1; i < 10; i++) {
			customerList.add(Customer.builder()
		    		.databaseId(new ObjectId())
			        .idCustomer("61a48c10c0219a3dc19215a"+i)
			        .firstName("Name"+i)
			        .lastName("LastName" + i)
			        .email("teste"+i+d.getTime()+"@gmail.com")
			        .birthDate("1985-09-19")
			        .state("BA")
			        .city("Salvador")
			        .createAt("2021-11-29 06:15")
			        .updateAt("2021-11-29 06:15")
			        .enable(true)
			        .removed(false)
			        .removedAt(null)
		            .build());
		}
		return customerList;
	}
	
	///Given
	private List<Customer> getFilteredList() {
		List<Customer> customerList = getList();
		List<Predicate<Customer>> predicateList = getPredicateList(); 
		customerList = customerList.stream().filter(predicateList.get(0)).collect(Collectors.toList()); 
		return customerList;
	}
	
	///Given
	private List<Predicate<Customer>> getPredicateList() {
		List<Predicate<Customer>> predicateList = new ArrayList<>();
		predicateList.add(c -> c.getFirstName().concat(" ").concat(c.getLastName()).toLowerCase().contains("name1"));
		return predicateList;
	}
	
	///Given
	private void givenAValidListResponse() throws Exception {
	    when(redisService.findAll(1,5, null)).thenReturn(getList());
	}
	///Given
	private void givenAValidSingleResponse() throws Exception {
		List<Customer> customerList = getFilteredList();
	    when(redisService.findAll(1,5, null)).thenReturn(customerList);
	}
	///Given
	private void givenAValidSingleResponseById() throws Exception {
		customer = getList().get(0);
		response.getCustomers().add(customer);
		request.setCustomer(customer);
	    when(redisService.findById("61a48c10c0219a3dc19215a1")).thenReturn(customer);
	}
	///Given
	private void givenAValidNewCustomerRequest() throws Exception {
		Date d = new Date();
		customer = Customer.builder()
		        .firstName("NameSaved")
		        .lastName("LastName")
		        .email("testeSave"+d.getTime()+"@gmail.com")
		        .birthDate("1985-09-19")
		        .state("BA")
		        .city("Salvador")
		        .createAt("2021-11-29 06:15")
		        .updateAt("2021-11-29 06:15")
		        .enable(true)
		        .removed(false)
		        .removedAt(null)
	            .build();
		request.setCustomer(customer);
	}
	///Given
	private void givenAValidCustomerSaved() throws Exception {
		customer = request.getCustomer();
	    when(repositoryMongoRepository.save(request.getCustomer())).thenReturn(customer);
	}
	///When
	private void whenFirstNameIsNull() {
	    customer.setFirstName(null);
	}
	///When
	private void whenAllFieldsIsNull() {
	    customer.setFirstName(null);
	    customer.setLastName(null);
	    customer.setBirthDate(null);
	    customer.setEmail(null);
	    customer.setCity(null);
	    customer.setState(null);
	}
	///When
	private void whenServicesIsCalled() throws Exception{
	    response = service.getAll(new CustomerRequest());
	}
	///When
	private void whenServicesGetByIdIsCalled() throws Exception{
	    response = service.getById("61a48c10c0219a3dc19215a1");
	}
	///When
	private void whenServicesSaveIsCalled() throws Exception{
	    response = service.save(request);
	}
	
	///Then
	private void thenResponseIsNotEmpty(){
	    assertFalse(response.getCustomers().isEmpty());
	}
	///Then
	private void thenResponseHasSingleCustomer(){
		assertTrue(response.getCustomers().size() == 1);
	}
	///Then
	private void thenResponseReturnErrorMsg(){
		assertFalse(response.getError().isEmpty());
		assertTrue(response.getError().contains(Useful.ER_0009));
	}
	///Then
	private void thenResponseReturnAllErrorMsg(){
		assertFalse(response.getError().isEmpty());
		assertTrue(response.getError().contains(Useful.ER_0006));
		assertTrue(response.getError().contains(Useful.ER_0007));
		assertTrue(response.getError().contains(Useful.ER_0008));
		assertTrue(response.getError().contains(Useful.ER_0009));
		assertTrue(response.getError().contains(Useful.ER_0010));
		assertTrue(response.getError().contains(Useful.ER_0011));
	}
	///Then
	private void thenResponseReturnSavedCustomer(){
		assertFalse(response.getCustomers().isEmpty());
		assertTrue(response.getCustomers().get(0).getDatabaseId() != null);
	}
}