package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.example.common.WebConstants;
import com.example.common.WebUIProperty;
import com.example.dto.CustomerModel;

@Controller
@RequestMapping("/rt")
public class Demo2Controller {
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private WebUIProperty property;
	
	@RequestMapping(value = {"/"}, method = RequestMethod.GET)
	public ModelAndView userLogin() {
		ModelAndView model = new ModelAndView("welcome");
		model.addObject("message", "resttemplate");
		return model;
	}

	
	@RequestMapping(value = "/details/{customerId}", method = RequestMethod.GET)
	public ModelAndView getDetails(@ModelAttribute("customer") CustomerModel customer) {
		ModelAndView model = new ModelAndView("home");
		

		return model;
	}
	
	@RequestMapping(value = "/all", method = RequestMethod.GET)
	public ModelAndView getAll(@ModelAttribute("customer") CustomerModel customer) {
		ModelAndView model = new ModelAndView("home");
		HttpHeaders httpHeaders = new HttpHeaders();
        HttpEntity<CustomerModel> customerHttpEntity = new HttpEntity<>(httpHeaders);
        try {
        	
        	RestTemplate restTemplate = new RestTemplate();
        	//ResponseEntity<List<CustomerModel>> response = restTemplate.exchange(property.getMcs1Url()+WebConstants.MCS1_CUSTOMER_ALL_PATH, HttpMethod.GET,null,new ParameterizedTypeReference<List<CustomerModel>>(){});
        	//List<CustomerModel> customerList = response.getBody();
        	
            ResponseEntity<CustomerModel[]> responseEntity = restTemplate.exchange(property.getMcs1CustomerUrl()+WebConstants.MCS1_CUSTOMER_ALL_PATH, HttpMethod.GET, customerHttpEntity, CustomerModel[].class);
            //Gson gson = new GsonBuilder().setPrettyPrinting().create();
            model.addObject("customers",  responseEntity.getBody());
        } catch (HttpStatusCodeException e) {
            ResponseEntity responseEntity = ResponseEntity.status(e.getRawStatusCode()).headers(e.getResponseHeaders()).body(e.getResponseBodyAsString());
            model.addObject("error", responseEntity);
        }

		return model;
	}
}
