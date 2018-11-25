package com.glodon.water.model;

import org.springframework.boot.actuate.autoconfigure.jdbc.DataSourceHealthIndicatorAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import tk.mybatis.spring.annotation.MapperScan;

@EnableFeignClients
@EnableEurekaClient
// @EnableAutoConfiguration
@SpringBootApplication (exclude = DataSourceHealthIndicatorAutoConfiguration.class)
@MapperScan("com.glodon.water.model.dao")
public class ModelServiceApplication {
	@SuppressWarnings("deprecation")
	public static void main(String[] args) {
		new SpringApplicationBuilder(ModelServiceApplication.class).web(true).run(args);
	}
	@Configuration
	public static class SecurityPermitAllConfig extends WebSecurityConfigurerAdapter {
		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http.authorizeRequests().anyRequest().permitAll()
					.and().csrf().disable();
		}
	}
}
