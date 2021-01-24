package com.marecompanie.socialparking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class IllegalParkingEventsApplication {
	public static void main(String[] args) {
		SpringApplication.run(IllegalParkingEventsApplication.class, args);
	}
}
