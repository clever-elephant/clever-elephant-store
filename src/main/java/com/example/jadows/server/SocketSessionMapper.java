package com.example.jadows.server;

import com.example.jadows.domain.EditSessionPair;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class SocketSessionMapper {

	Map<String, EditSessionPair> pairMap = new ConcurrentHashMap<>();


	public WebSocketSession getMiniSession(String sessionId) {
		return pairMap.getOrDefault(sessionId, new EditSessionPair()).getMiniSession();
	}

	public WebSocketSession getWebSession(String sessionId) {
		return pairMap.getOrDefault(sessionId, new EditSessionPair()).getWebSession();
	}

	public EditSessionPair getPair(String sessionId) {
		return Optional.ofNullable(pairMap.get(sessionId)).orElse(new EditSessionPair());
	}

	public void registerEditSessionPair(String sessionId,EditSessionPair pair){
		pairMap.put(sessionId,pair);
	}
}
