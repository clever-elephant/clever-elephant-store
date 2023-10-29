package com.example.jadows.domain;

import lombok.Data;
import org.springframework.web.socket.WebSocketSession;

@Data
public class ConnectSocketSession {

	private WebSocketSession webSession;


	private WebSocketSession miniSession;
}