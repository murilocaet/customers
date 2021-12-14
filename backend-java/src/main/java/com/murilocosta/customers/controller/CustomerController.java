package com.murilocosta.customers.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.murilocosta.customers.request.CustomerRequest;
import com.murilocosta.customers.response.CustomerResponse;
import com.murilocosta.customers.service.CustomerService;
import com.murilocosta.customers.useful.Useful;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/api/customers")
@AllArgsConstructor
@NoArgsConstructor
public class CustomerController {

    @Autowired
    private CustomerService service;
    
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CustomerResponse> findAllCustomers(
    		@RequestParam(name = "page") Integer page,
    		@RequestParam(name = "pageSize") Integer pageSize,
    		@RequestParam(name = "searchName", required=false, defaultValue = "") String searchName) {
    	CustomerResponse response;
        try{
        	CustomerRequest request = CustomerRequest.builder()
        			.page(page)
        			.pageSize(pageSize)
        			.searchName(searchName)
        			.build();
        	
            response = service.getAll(request);
            if(!response.getCustomers().isEmpty()) {
            	return ResponseEntity.ok().body(response);
            }else {
                return ResponseEntity.noContent().build();
            }
        } catch(Exception ex){
        	ex.printStackTrace();
            log.error(ex.getMessage());
            log.error(Useful.ER_0001);
            response = CustomerResponse.builder().build();
            response.getError().add(Useful.ER_0001);
            return ResponseEntity.internalServerError().build();
        }
    }
   	
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
   	public ResponseEntity<CustomerResponse> findCustomerById(@PathVariable String id){
   		CustomerResponse response;
   		try{
			response = service.getById(id);
			if(!response.getCustomers().isEmpty()) {
            	return ResponseEntity.ok().body(response);
            }else {
                return ResponseEntity.noContent().build();
            }
		} catch(Exception ex){
        	ex.printStackTrace();
            log.error(ex.getMessage());
            log.error(Useful.ER_0001);
            response = CustomerResponse.builder().build();
            response.getError().add(Useful.ER_0001);
            return ResponseEntity.internalServerError().build();
		}
	}

   	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CustomerResponse> addCustomer(@RequestBody CustomerRequest request){
   		CustomerResponse response;
   		try{
    	   response = service.save(request);
    	   if(!response.getCustomers().isEmpty()) {
    		   return ResponseEntity.created(null).body(response);
           }else {
               return ResponseEntity.internalServerError().build();
           }
   		} catch(Exception ex){
			ex.printStackTrace();
			log.error(ex.getMessage());
			log.error(Useful.ER_0002);
			response = CustomerResponse.builder().build();
			response.getError().add(Useful.ER_0002);
            return ResponseEntity.internalServerError().build();
   		}
    }

   	@PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CustomerResponse> updateCustomer(@PathVariable String id, @RequestBody CustomerRequest request){
   		CustomerResponse response;
   		try{
    	   response = service.edit(request);//@PathVariable("id")
    	   if(!response.getCustomers().isEmpty()) {
    		   return ResponseEntity.noContent().build();
           }else {
               return ResponseEntity.internalServerError().build();
           }
   		} catch(Exception ex){
			ex.printStackTrace();
			log.error(ex.getMessage());
			log.error(Useful.ER_0002);
			response = CustomerResponse.builder().build();
			response.getError().add(Useful.ER_0002);
            return ResponseEntity.internalServerError().build();
   		}
    }
   	
   	@DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CustomerResponse> deleteCustomer(@PathVariable String id){
   		CustomerResponse response;
        try{
        	response = service.remove(id);
            if(!response.getCustomers().isEmpty()) {
            	return ResponseEntity.ok().body(response);
            }else {
                return ResponseEntity.noContent().build();
            }
        } catch(Exception ex){
           	ex.printStackTrace();
            log.error(ex.getMessage());
            log.error(Useful.ER_0003);
            response = CustomerResponse.builder().build();
            response.getError().add(Useful.ER_0003);
            return ResponseEntity.internalServerError().build();
        }
    }
   	
   	@PatchMapping(value = "/activate/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
   	public ResponseEntity<CustomerResponse> activateCustomer(@PathVariable String id){
   		CustomerResponse response;
   		try{
    	   response = service.activate(id);
    	   if(!response.getCustomers().isEmpty()) {
    		   return ResponseEntity.ok().body(response);
           }else {
               return ResponseEntity.internalServerError().build();
           }
   		} catch(Exception ex){
			ex.printStackTrace();
			log.error(ex.getMessage());
			log.error(Useful.ER_0002);
			response = CustomerResponse.builder().build();
			response.getError().add(Useful.ER_0002);
            return ResponseEntity.internalServerError().build();
   		}
   	}
   	
   	@PatchMapping(value = "/activateAll", produces = MediaType.APPLICATION_JSON_VALUE)
   	public ResponseEntity<CustomerResponse> activateAllCustomers(){
   		CustomerResponse response;
   		try{
			response = service.changeAll(true);
			if(!response.getCustomers().isEmpty()) {
				return ResponseEntity.noContent().build();
			}else {
				return ResponseEntity.internalServerError().build();
			}
			
		} catch(Exception ex){
           	ex.printStackTrace();
            log.error(ex.getMessage());
            log.error(Useful.ER_0003);
            response = CustomerResponse.builder().build();
            response.getError().add(Useful.ER_0003);
            return ResponseEntity.internalServerError().build();
		}
	}
   	
   	@PatchMapping(value = "/disable/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
   	public ResponseEntity<CustomerResponse> disableCustomer(@PathVariable String id){
   		CustomerResponse response;
   		try{
    	   response = service.disable(id);
    	   if(!response.getCustomers().isEmpty()) {
    		   return ResponseEntity.ok().body(response);
           }else {
               return ResponseEntity.internalServerError().build();
           }
   		} catch(Exception ex){
			ex.printStackTrace();
			log.error(ex.getMessage());
			log.error(Useful.ER_0002);
			response = CustomerResponse.builder().build();
			response.getError().add(Useful.ER_0002);
            return ResponseEntity.internalServerError().build();
   		}
   	}
   	
   	@PatchMapping(value = "/disableAll", produces = MediaType.APPLICATION_JSON_VALUE)
   	public ResponseEntity<CustomerResponse> disableAllCustomers(){
   		CustomerResponse response;
   		try{
			response = service.changeAll(false);
			if(!response.getCustomers().isEmpty()) {
				return ResponseEntity.noContent().build();
			}else {
				return ResponseEntity.internalServerError().build();
			}
			
		} catch(Exception ex){
           	ex.printStackTrace();
            log.error(ex.getMessage());
            log.error(Useful.ER_0003);
            response = CustomerResponse.builder().build();
            response.getError().add(Useful.ER_0003);
            return ResponseEntity.internalServerError().build();
		}
	}
}
