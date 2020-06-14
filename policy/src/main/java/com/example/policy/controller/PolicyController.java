package com.example.policy.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.dto.PolicyModel;
import com.example.policy.service.PolicyService;



@RestController
@RequestMapping("/policy")
public class PolicyController {
	
	@Autowired
	PolicyService policyServrice;
	
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public List<PolicyModel> getAll() {
		return policyServrice.getAllPoplicyDetails();
	}
	
	@RequestMapping(value = "/customerPolicy", method = RequestMethod.GET)
	public Map<Integer, List<PolicyModel>> getCustomerPolicyDetails(PolicyModel policy) {
		return policyServrice.getCustomerPolicyDetails(policy);
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public PolicyModel getById(@PathVariable String id) {	
		PolicyModel p = new PolicyModel();
		p.setPolicyId(Integer.parseInt(id));
		return policyServrice.getPolicyById(p);
	}
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public ResponseEntity<Integer> saveNewPolicy(@RequestBody PolicyModel policy) {
		int ret = policyServrice.addNewPolicy(policy);
		if(ret > 0) {
			return ResponseEntity.status(HttpStatus.OK).body(ret);
		}else {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(-1);
		}
		
	}
	
	@RequestMapping(value = "/saveCustomerPolicy", method = RequestMethod.POST)
	public ResponseEntity<Integer> saveNewCustomerPolicy(@RequestBody PolicyModel policy) {
		int ret = policyServrice.addNewCustomerPolicy(policy);
		if(ret > 0) {
			return ResponseEntity.status(HttpStatus.OK).body(ret);
		}else {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(-1);
		}
		
	}

}
