package com.nanum.enrollservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication(scanBasePackages = "com.nanum")
@EnableDiscoveryClient
@EnableFeignClients
@Configuration
@EnableJpaAuditing
public class EnrollServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(EnrollServiceApplication.class, args);
	}

}
