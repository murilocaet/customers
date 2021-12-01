package com.murilocosta.customers.repository;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import com.murilocosta.customers.entity.Customer;

@Repository
public class ControlRedisRepository {

	@Autowired
	private RedisTemplate<String, Customer> redisControlTemplate;

    private HashOperations hashOperations;

    public ControlRedisRepository(RedisTemplate<String, Customer> redisTemplate) {
        this.redisControlTemplate = redisTemplate;
        hashOperations = redisTemplate.opsForHash();
    }

    public List<String> findKey(String key) {
    	List<String> mapList = (List<String>)hashOperations.values(key);
		return mapList;
    }
    
    public void addItensMap(Map<String,String> map, String key) {
   		hashOperations.putAll(key, map);
    }

    public void removeItemMap(String key, String hash) {
        hashOperations.delete(key, hash);
    }
}
