package com.example.ui;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages="com.example.")
@EnableFeignClients("com.example.client")
public class UiPortalApplication {

	public static void main(String[] args) {
		SpringApplication.run(UiPortalApplication.class, args);
	}

}
