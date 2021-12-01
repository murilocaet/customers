package com.murilocosta.customers.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.stereotype.Repository;

import com.murilocosta.customers.entity.Customer;
import com.murilocosta.customers.useful.Useful;

@Repository
public class CustomerRepository {

	@Autowired
	private RedisTemplate<String, Customer> redisTemplate;

    private HashOperations hashOperations;

    public CustomerRepository(RedisTemplate<String, Customer> redisTemplate) {
        this.redisTemplate = redisTemplate;
        hashOperations = redisTemplate.opsForHash();
    }
    
    public void saveAll(Map<String, Customer> map, String key) {
   		hashOperations.putAll(key, map);
    }
    
    public List<Customer> findAll(String key) {
    	List<Customer> rates = (List<Customer>)hashOperations.values(key);
		return rates;
    }
    
    public List<Customer> findAll(final int pageNum, final int pageSize, String key, 
    		List<Predicate<Customer>> predicateList) throws Exception {
        int tmpIndex = 1;
        int tmpEndIndex = 0;
        int tmpStartIndex = 0;
        List<Customer> entities = new ArrayList<>();
        
        try (Cursor<Entry<Object, Object>> cursor = redisTemplate.opsForHash().scan(key,
                ScanOptions.scanOptions().match("*").build())) {
            while (cursor.hasNext()) {
                if (tmpIndex >= pageNum && tmpEndIndex < pageSize) {
                    final Entry<Object, Object> entry = cursor.next();
                    final Customer entity = (Customer) entry.getValue();
                    entities.add(entity);
                    
                    if(predicateList != null) {
                    	entities = entities.stream()
    					.filter(predicateList.get(0)
    							.or(predicateList.get(1))
    							.or(predicateList.get(2))
    							.or(predicateList.get(3))
    							.or(predicateList.get(4)))
    			            .collect(Collectors.toList());
                    	
                    	tmpEndIndex = entities.size(); 
                    }else {
                    	tmpEndIndex++;	
                    }
                    continue;
                }
                if (tmpEndIndex >= pageSize) {
                    tmpIndex++;
                    break;
                }else {
                	tmpStartIndex++;
                    if (tmpStartIndex >= pageSize) {
                        tmpIndex++;
                        tmpStartIndex = 0;
                    }
                }
                cursor.next();
            }
        } catch (Exception ex) {
        	throw ex;
        }
        return entities;
    }
    
    public Customer findById(String key, String id) {
        return (Customer)hashOperations.get(key, id);
    }

    public void update(Map<String, Customer> map, String key) {
    	saveAll(map, key);
    }

    public void delete(String key) {
        hashOperations.delete(key);
    }
}
