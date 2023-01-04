package com.bicicleta.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class BicicletaServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(BicicletaServiceApplication.class, args);
	}

}
