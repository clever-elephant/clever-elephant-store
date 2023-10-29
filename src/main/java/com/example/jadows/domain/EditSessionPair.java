package com.example.jadows.domain;

import lombok.Data;
import org.springframework.web.socket.WebSocketSession;

@Data
public class EditSessionPair {
	private WebSocketSession miniSession;

	private WebSocketSession webSession;
}
