package com.example.jadows.config;

import com.example.jadows.handler.WebSocketHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.standard.ServletServerContainerFactoryBean;

@EnableWebSocket
@Configuration
@RequiredArgsConstructor
public class WebSocketConfig implements WebSocketConfigurer {

	private final WebSocketHandler webSocketHandler;


	private final Integer MAX_FILE_SIZE = 1024 * 1024 * 20;

	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		registry.addHandler(webSocketHandler, "/mini/socket").setAllowedOrigins("*");
	}

	@Bean
	public ServletServerContainerFactoryBean createServletServerContainerFactoryBean() {
		ServletServerContainerFactoryBean container = new ServletServerContainerFactoryBean();
		container.setMaxTextMessageBufferSize(MAX_FILE_SIZE);
		return container;
	}

}
