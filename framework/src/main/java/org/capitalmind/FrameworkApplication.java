package org.capitalmind;


import org.capitalmind.config.DotenvConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class FrameworkApplication {

	public static void main(String[] args) {
		 new DotenvConfig(); 
		SpringApplication.run(FrameworkApplication.class, args);
	}

}
