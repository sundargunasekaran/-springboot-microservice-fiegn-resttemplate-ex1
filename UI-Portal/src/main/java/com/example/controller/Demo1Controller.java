package com.example.controller;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.example.client.CustomerFeignClient;
import com.example.client.PolicyFeignClient;
import com.example.dto.CustomerModel;
import com.example.dto.PolicyModel;

@Controller
@RequestMapping("/op")
public class Demo1Controller {
	
	@Autowired
	private CustomerFeignClient customerFeignClient;
	
	@Autowired
	private PolicyFeignClient policyFeignClient;
	
	/*
	 * @Autowired private WebUIProperty property;
	 */
	
	@RequestMapping(value = {"/"}, method = RequestMethod.GET)
	public ModelAndView userLogin() {
		ModelAndView model = new ModelAndView("welcome");
		model.addObject("message", "openfeignclient");
		return model;
	}
	
	@RequestMapping(value = {"/add"}, method = RequestMethod.GET)
	public ModelAndView getAddCustomer() {
		ModelAndView model = new ModelAndView("user_registration");
		Map< String, String > roles = new HashMap<String, String>();
		roles.put("0", "--Select--");
		roles.put("1", "Admin");
		roles.put("2", "User");
		roles.put("3", "Guests");
         
		model.addObject("roles", roles);
		
		model.addObject("customers", new CustomerModel());
		return model;
	}
	
	@RequestMapping(value = "/addUserPolicy/{customerId}", method = RequestMethod.GET)
	public ModelAndView getAddUserPolicy(@ModelAttribute("customer") CustomerModel customer) {
		ModelAndView model = new ModelAndView("policy_registration");
		System.out.println("policy_registration");
		List<CustomerModel> customerList = customerFeignClient.getCustomerListbyId(String.valueOf(customer.getCustomerId()));
		model.addObject("customers",  customerList.size() > 0 ? customerList.get(0) : null);
		Map< String, String > policy = new HashMap<String, String>();
		policy.put("0", "--Select--");
		policy.put("1000", "LIC");
		policy.put("1001", "BIRLA");
		policy.put("1002", "KOTAK");
		policy.put("9999", "OTHERS");
         
		model.addObject("policy", policy);
		return model;
	}
	
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public ModelAndView saveOrUpdate(@ModelAttribute("customer") CustomerModel customer) {
		ResponseEntity<String> responseEntity = customerFeignClient.addCustomer(customer);
		return new ModelAndView("redirect:/op/all");
	}

	
	@RequestMapping(value = "/details/{opFlag}/{customerId}", method = RequestMethod.GET)
	public ModelAndView getDetails(@ModelAttribute("customer") CustomerModel customer,@PathVariable("opFlag") String opFlag,
			@PathVariable("customerId") String customerId) {
		ModelAndView model = null;
		List<CustomerModel> customerList = customerFeignClient.getCustomerListbyId(customerId);
		if(opFlag != null && opFlag.trim().equalsIgnoreCase("update")) {
			model = new ModelAndView("user_registration");
			model.addObject("customers",  customerList.size() > 0 ? customerList.get(0) : null);
		}else {
			model = new ModelAndView("home");
			model.addObject("customers",  customerList);
		}
		model.addObject("customerId",  customerId);
		Map< String, String > roles = new HashMap<String, String>();
		roles.put("0", "--Select--");
		roles.put("1", "Admin");
		roles.put("2", "User");
		roles.put("3", "Guests");
         
		model.addObject("roles", roles);
		return model;
	}
	
	@RequestMapping(value = "/all", method = RequestMethod.GET)
	public ModelAndView getAll(@ModelAttribute("customer") CustomerModel customer) {
		ModelAndView model = new ModelAndView("home");
		List<CustomerModel> customerList = customerFeignClient.getAllCustomerList();
		model.addObject("customers",  customerList);
		return model;
	}
	
	@RequestMapping(value = "/delete/{customerId}", method = RequestMethod.GET)
	public ModelAndView deleteCustomer(@ModelAttribute("customer") CustomerModel customer) {
		ResponseEntity<String> responseEntity = customerFeignClient.deleteCustomer(String.valueOf(customer.getCustomerId()));
		System.out.println("--> "+responseEntity.getBody());
		return new ModelAndView("redirect:/op/all");
	}
	
	
	@RequestMapping(value = "/savepolicy", method = RequestMethod.POST)
	public ModelAndView saveOrUpdatePolicy(@ModelAttribute("policy") PolicyModel policy) {
		if(policy.getPolicyId() == 9999) {
			ResponseEntity<Integer> responseEntity = policyFeignClient.addPolicy(policy);
			if(responseEntity.getStatusCode().equals(HttpStatus.OK)) {
				policy.setPolicyId(responseEntity.getBody());
			}			
		}
		ResponseEntity<Integer> responseEntity = policyFeignClient.addCustomerPolicy(policy);
		return new ModelAndView("redirect:/op/all");
	}
}
