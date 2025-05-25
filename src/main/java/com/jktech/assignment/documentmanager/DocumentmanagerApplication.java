package com.jktech.assignment.documentmanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.jktech.assignment.documentmanager.repository")
@EntityScan(basePackages = "com.jktech.assignment.documentmanager.model")
public class DocumentmanagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(DocumentmanagerApplication.class, args);
	}

}
