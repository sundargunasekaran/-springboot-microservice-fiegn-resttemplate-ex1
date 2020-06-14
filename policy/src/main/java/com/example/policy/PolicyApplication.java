package com.example.policy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages="com.example.")
public class PolicyApplication {

	public static void main(String[] args) {
		SpringApplication.run(PolicyApplication.class, args);
	}

}
