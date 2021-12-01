package com.murilocosta.customers.entity;

import java.io.Serializable;
import java.util.Date;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Customer implements Serializable {

	private static final long serialVersionUID = -5282549479767080619L;

	@Id 
	private ObjectId databaseId;
	
	private String idCustomer;
    private String firstName;
    private String lastName;
    private String email;
	private String age;
    private String state;
    private String city;
	private String createAt;
	private String updateAt;
	private Boolean removed;
	private String removedAt;
	
}