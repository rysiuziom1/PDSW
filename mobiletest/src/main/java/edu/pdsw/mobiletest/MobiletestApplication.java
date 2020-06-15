package edu.pdsw.mobiletest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class MobiletestApplication {

	public static void main(String[] args) {
		SpringApplication.run(MobiletestApplication.class, args);
	}

}
