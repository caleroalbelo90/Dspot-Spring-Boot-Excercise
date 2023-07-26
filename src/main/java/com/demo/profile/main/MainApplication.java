package com.demo.profile.main;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
@OpenAPIDefinition(
		info = @Info(
				title = "Profile API",
				version = "1.0",
				description = "Profile API v1.0"
		),
		servers = {
				@Server(
						url = "http://localhost:8080",
						description = "Local server"
				)
		}
)
public class MainApplication {
	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(MainApplication.class, args);

		TriggerCreator triggerCreator = context.getBean(TriggerCreator.class);
		triggerCreator.createTrigger();
	}

}
