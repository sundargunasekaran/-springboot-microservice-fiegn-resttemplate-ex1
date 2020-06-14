package com.example.client;

import java.util.List;
import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.customer.config.FeignSimpleEncoderConfig;
import com.example.dto.PolicyModel;





@FeignClient(name = "policy-service", url = "${test.mcs2.policy.url}",configuration = FeignSimpleEncoderConfig.class)
public interface PolicyClient {
	
	@GetMapping(value = "/policy/customerPolicy")
	public Map<Integer, List<PolicyModel>> getCustomerPolicyDetails();

}
