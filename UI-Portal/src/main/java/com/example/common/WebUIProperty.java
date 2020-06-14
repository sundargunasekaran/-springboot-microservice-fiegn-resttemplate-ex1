package com.example.common;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class WebUIProperty {
	
	 @Value("${test.mcs1.customer.url}")
	 private String mcs1CustomerUrl;

	 @Value("${test.mcs2.policy.url}")
	 private String mcs2PolicyUrl;

	public String getMcs1CustomerUrl() {
		return mcs1CustomerUrl;
	}

	public void setMcs1CustomerUrl(String mcs1CustomerUrl) {
		this.mcs1CustomerUrl = mcs1CustomerUrl;
	}

	public String getMcs2PolicyUrl() {
		return mcs2PolicyUrl;
	}

	public void setMcs2PolicyUrl(String mcs2PolicyUrl) {
		this.mcs2PolicyUrl = mcs2PolicyUrl;
	}

	
	 
	 
	    

}
