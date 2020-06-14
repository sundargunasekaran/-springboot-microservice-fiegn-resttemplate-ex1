package com.example.policy.config;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;

public class PolicyConfig {
	

	
	
	private String validationQry = "SELECT 1 ";
	
	
	@Bean
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource webDataSource() {
        return (DataSource) DataSourceBuilder.create().build();
    }

    @Bean
    public JdbcTemplate webJdbcTemplate(@Qualifier("webDataSource") DataSource datasource) throws Exception{
    	datasource.setTestWhileIdle(true);
    	datasource.setTimeBetweenEvictionRunsMillis(60000);
    	datasource.setValidationQuery(validationQry);
        return new JdbcTemplate(datasource);
    }



}
