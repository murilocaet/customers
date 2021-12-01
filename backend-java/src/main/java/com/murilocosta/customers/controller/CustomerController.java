package com.murilocosta.customers.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
    
    
    @PostMapping(value = "/all", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CustomerResponse> getAll(@RequestBody CustomerRequest request) {
    	CustomerResponse response;
        try{
            response = service.getAll(request);

        } catch(Exception ex){
        	ex.printStackTrace();
            log.error(ex.getMessage());
            log.error(Useful.ER_0001);
            response = CustomerResponse.builder().build();
            response.getError().add(Useful.ER_0001);
        }
        return ResponseEntity.ok().body(response);
    }
   	
   	@GetMapping(params = "id", produces = MediaType.APPLICATION_JSON_VALUE)
   	public ResponseEntity<CustomerResponse> getById(@RequestParam String id){
   		CustomerResponse response;
   		try{
			response = service.getById(id);
			
		} catch(Exception ex){
        	ex.printStackTrace();
            log.error(ex.getMessage());
            log.error(Useful.ER_0001);
            response = CustomerResponse.builder().build();
            response.getError().add(Useful.ER_0001);
		}
        return ResponseEntity.ok().body(response);
	}

   	@PostMapping(value = "/save", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CustomerResponse> save(@RequestBody CustomerRequest request){
   		CustomerResponse response;
   		try{
    	   response = service.save(request);
    	   
   		} catch(Exception ex){
			ex.printStackTrace();
			log.error(ex.getMessage());
			log.error(Useful.ER_0002);
			response = CustomerResponse.builder().build();
			response.getError().add(Useful.ER_0002);
   		}
   		return ResponseEntity.ok().body(response);
    }

   	@DeleteMapping(params = "id", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CustomerResponse> delete(@RequestParam String id){
   		CustomerResponse response;
        try{
        	response = service.remove(id);
        	
        } catch(Exception ex){
           	ex.printStackTrace();
            log.error(ex.getMessage());
            log.error(Useful.ER_0003);
            response = CustomerResponse.builder().build();
            response.getError().add(Useful.ER_0003);
        }
        return ResponseEntity.ok().body(response);
    }
   	
   	@GetMapping(value = "/disableAll", produces = MediaType.APPLICATION_JSON_VALUE)
   	public ResponseEntity<CustomerResponse> disableAll(){
   		CustomerResponse response;
   		try{
			response = service.changeAll(true);
			
		} catch(Exception ex){
           	ex.printStackTrace();
            log.error(ex.getMessage());
            log.error(Useful.ER_0003);
            response = CustomerResponse.builder().build();
            response.getError().add(Useful.ER_0003);
		}
		return ResponseEntity.ok().body(response);
	}
   	
   	@GetMapping(value = "/activateAll", produces = MediaType.APPLICATION_JSON_VALUE)
   	public ResponseEntity<CustomerResponse> activateAll(){
   		CustomerResponse response;
   		try{
			response = service.changeAll(false);
			
		} catch(Exception ex){
           	ex.printStackTrace();
            log.error(ex.getMessage());
            log.error(Useful.ER_0003);
            response = CustomerResponse.builder().build();
            response.getError().add(Useful.ER_0003);
		}
		return ResponseEntity.ok().body(response);
	}
}
