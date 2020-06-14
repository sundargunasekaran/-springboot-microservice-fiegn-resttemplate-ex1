package com.example.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.dto.CustomerModel;
import com.example.dto.PolicyModel;

@FeignClient(name = "customer-policy-service", url = "${test.mcs2.policy.url}")
public interface PolicyFeignClient {
	
	@GetMapping(value = "/policy/list")
	public List<PolicyModel> getAllPolicyList();
	
	@PostMapping(value = "/policy/save")
	ResponseEntity<Integer> addPolicy(@RequestBody PolicyModel policy);
	
	@PostMapping(value = "/policy/saveCustomerPolicy")
	ResponseEntity<Integer> addCustomerPolicy(@RequestBody PolicyModel policy);
	

}
