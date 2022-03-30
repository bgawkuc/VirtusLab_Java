package com.virtuslab.internship;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {
	
	public static void main(String[] args) throws JsonProcessingException {
		SpringApplication.run(Application.class, args);
	}
}
