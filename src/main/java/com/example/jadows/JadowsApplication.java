package com.example.jadows;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.socket.config.annotation.EnableWebSocket;

@SpringBootApplication
public class JadowsApplication {

	public static void main(String[] args) {
		SpringApplication.run(JadowsApplication.class, args);
	}

}
