package com.example.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.config.FeignSimpleEncoderConfig;
import com.example.dto.CustomerModel;

import feign.Headers;
import feign.RequestLine;

@FeignClient(name = "customer-service", url = "${test.mcs1.customer.url}")
public interface CustomerFeignClient {

	@GetMapping(value = "/customer/list")
	// @RequestLine("GET /customer/list")
	public List<CustomerModel> getAllCustomerList();

	@GetMapping(value = "/customer/{customerId}")
	// @RequestLine("GET /customer/{customerId}")
	// @Headers("Content-Type: application/json")
	public List<CustomerModel> getCustomerListbyId(@PathVariable("customerId") String customerId);

	@PostMapping(value = "/customer/save")
	ResponseEntity<String> addCustomer(@RequestBody CustomerModel customer);
	
	@GetMapping(value = "/customer/delete/{customerId}")
	ResponseEntity<String> deleteCustomer(@PathVariable("customerId") String customerId);

}
