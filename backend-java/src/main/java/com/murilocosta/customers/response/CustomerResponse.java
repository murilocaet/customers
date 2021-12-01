package com.murilocosta.customers.response;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.murilocosta.customers.entity.Customer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerResponse implements Serializable {

	private static final long serialVersionUID = 2949404617508707464L;

	@Builder.Default
	private List<Customer> customers = new ArrayList<>();
	
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

	@Builder.Default
	private String note = "";

	@Builder.Default
	private String success = "";

	@Builder.Default
	private List<String> error = new ArrayList<>();
}
