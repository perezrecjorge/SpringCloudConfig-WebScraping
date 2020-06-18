package com.tfg.webscraping;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class WebscrapingApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebscrapingApplication.class, args);
	}

}
