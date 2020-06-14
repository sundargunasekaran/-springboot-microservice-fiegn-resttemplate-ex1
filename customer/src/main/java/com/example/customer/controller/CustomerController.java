package com.example.customer.controller;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.client.PolicyClient;
import com.example.dto.CustomerModel;
import com.example.dto.PolicyModel;
import com.example.customer.service.CustomerService;

@RestController
@RequestMapping("/customer")
public class CustomerController {
	
	
	@Autowired
	private CustomerService customerServrice;
	
	@Autowired
	private PolicyClient policyClient;
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public List<CustomerModel> getAll() {
		List<CustomerModel> customerList =  customerServrice.getAllCustomerDetails();
		Map<Integer, List<PolicyModel>> customerPolicy =  policyClient.getCustomerPolicyDetails();
		List<CustomerModel> cl = customerServrice.getCustomerPolicyDetails(customerList,customerPolicy);
		return cl;
	}
	
	@RequestMapping(value = "/{customerId}", method = RequestMethod.GET)
	public List<CustomerModel> getById(@PathVariable String customerId) {	
		CustomerModel c = new CustomerModel();
		c.setCustomerId(Integer.parseInt(customerId));
		CustomerModel customerModel = customerServrice.getCustomerById(c);
		List<CustomerModel> custList = new ArrayList<CustomerModel>();
		custList.add(customerModel);
		Map<Integer, List<PolicyModel>> customerPolicy =  policyClient.getCustomerPolicyDetails();
		List<CustomerModel> cl = customerServrice.getCustomerPolicyDetails(custList,customerPolicy);
		return cl;
	}
	
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public ResponseEntity<String> saveCustomer(@RequestBody CustomerModel customer) {
		if (customer.getCustomerId() > 0) { 
			 int ret = customerServrice.updateCustomer(customer);
			 if(ret > 0) {
				  return ResponseEntity.status(HttpStatus.OK).body("updated successfully");
			  }else {
				  return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed");
			  }
		  } else {
			  int ret = customerServrice.addCustomer(customer);
			  if(ret > 0) {
				  return ResponseEntity.status(HttpStatus.OK).body("Insert successfully");
			  }else {
				  return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed");
			  }
		  }
	}
	
	@RequestMapping(value = "/delete/{customerId}", method = RequestMethod.GET)
	public ResponseEntity<String> deleteCustomer(@PathVariable String customerId) {
		if(customerServrice.deleteCustomer(Integer.parseInt(customerId)) > 0) {
			return ResponseEntity.status(HttpStatus.OK).body("deleted successfully");
		}else {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed");
		}
	}


}
