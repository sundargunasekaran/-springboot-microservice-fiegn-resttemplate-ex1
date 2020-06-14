package com.example.customer.config;

import org.springframework.context.annotation.Bean;

import feign.codec.Encoder;
import feign.form.FormEncoder;

public class FeignSimpleEncoderConfig {

	@Bean
    public Encoder encoder() {
        return new FormEncoder();
    }
}
