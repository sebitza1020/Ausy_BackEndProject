package com.ausy_technologies.finalproject;

import com.ausy_technologies.finalproject.Error.ErrorResponse;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FinalprojectApplication {

	public static void main(String[] args) {
		ErrorResponse.setupLogger();
		SpringApplication.run(FinalprojectApplication.class, args);
	}

}
