package com.morales.bootcamp.spring_boot_pet_adoption;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringBootPetAdoptionApplication {
	private static final Logger log = LoggerFactory.getLogger(SpringBootPetAdoptionApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(SpringBootPetAdoptionApplication.class, args);
		System.out.println("API PET ADOPTION!");

	}

}
