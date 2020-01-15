package com.example.contractclientdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(FactoryRegistrar.class)
public class ContractClientDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(ContractClientDemoApplication.class, args);
	}

}
