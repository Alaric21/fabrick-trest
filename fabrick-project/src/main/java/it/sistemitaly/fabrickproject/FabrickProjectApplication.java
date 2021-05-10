package it.sistemitaly.fabrickproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class FabrickProjectApplication {
	
	@Bean
	public RestTemplate creatRestTemplate() {
		return new RestTemplate();
	}

	public static void main(String[] args) {
		SpringApplication.run(FabrickProjectApplication.class, args);
	}

}
