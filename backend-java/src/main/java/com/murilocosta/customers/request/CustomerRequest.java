package com.murilocosta.customers.request;

import java.io.Serializable;

import com.murilocosta.customers.entity.Customer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerRequest implements Serializable {

	private static final long serialVersionUID = -7166088962475728287L;
	
	Customer customer;
	
	@Builder.Default
	private Integer page = 1;
	
	@Builder.Default
	private Integer count = 1;
	
	@Builder.Default
	private Integer pageSize = 5;
	
	@Builder.Default
	private Integer totalItens = 0;
	
	@Builder.Default
	private String searchName = "";
}