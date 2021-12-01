package com.murilocosta.customers.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.murilocosta.customers.repository.ControlRedisRepository;
import com.murilocosta.customers.useful.Useful;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ControlRedisService {
	
	@Autowired
	private ControlRedisRepository repository;
    
    public boolean needUpdate() {
    	List<String> keys = repository.findKey(Useful.CUSTOMERS_UPDATE);
    	if(keys == null || keys.isEmpty()) {
    		return false;
    	}
    	return true;
    }
    
    public void enableUpdate(){
   		Map<String,String> controlMap = new HashMap<>();
   		controlMap.put(Useful.CUSTOMERS_UPDATE, "true");
   		addItensMap(controlMap, Useful.CUSTOMERS_UPDATE);
    }
    
    public void disableUpdate(){
   		removeItemMap(Useful.CUSTOMERS_UPDATE, Useful.CUSTOMERS_UPDATE);
    }
    
    private void addItensMap(Map<String,String> map, String key) {
    	repository.addItensMap(map, key);
    }
    
    private void removeItemMap(String key, String hash) {
    	repository.removeItemMap(key, hash);
    }
}