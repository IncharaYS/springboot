package com.tcs.parcel_management;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class ParcelManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(ParcelManagementApplication.class, args);
	}


//	@Bean
//	CommandLineRunner printPasswordHash(PasswordEncoder passwordEncoder) {
//		return args -> {
//			System.out.println(passwordEncoder.encode("Admin@123"));
//		};
//	}
}
