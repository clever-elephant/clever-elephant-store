package com.example.jadows.handler;


import com.example.jadows.domain.ConnectSocketSession;
import com.example.jadows.enums.ParamType;
import com.example.jadows.enums.SocketType;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class WebSocketHandler extends TextWebSocketHandler {
	private Map<String, ConnectSocketSession> sessions = new ConcurrentHashMap<>();

	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		Map<ParamType, String> params = parseParams(session);
		ConnectSocketSession connectSocketSession = sessions.get(params.get(ParamType.SESSION_ID));
		if (connectSocketSession == null) return;
		SocketType socketType = SocketType.getByCode(params.get(ParamType.TYPE));
		if (socketType == null) return;
		if (socketType == null) return;
		WebSocketSession socketSession = null;
		switch (socketType) {
			case WEB -> socketSession = connectSocketSession.getMiniSession();
			case MINI -> socketSession = connectSocketSession.getWebSession();
		}
		if (socketSession == null) return;
		socketSession.sendMessage(message);
	}


	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		handleSession(session);
	}

	@Override
	public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
		System.out.println(exception);
		closeSession(session);
	}

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		closeSession(session);
	}

	private void handleSession(WebSocketSession session) throws IOException {
		try {
			Map<ParamType, String> params = parseParams(session);
			String sessionId = params.get(ParamType.SESSION_ID);
			if (!StringUtils.hasText(sessionId)) {
				closeSession(session);
				return;
			}
			SocketType socketType = SocketType.getByCode(params.get(ParamType.TYPE));
			if (socketType == null) {
				closeSession(session);
				return;
			}
			ConnectSocketSession connectSocketSession = sessions.getOrDefault(sessionId, new ConnectSocketSession());
			switch (socketType) {
				case WEB -> {
					connectSocketSession.setWebSession(session);
					sessions.put(sessionId,connectSocketSession);
				}
				case MINI ->{
					connectSocketSession.setMiniSession(session);
					sessions.put(sessionId,connectSocketSession);
				}
			}
		} catch (Exception e) {
			closeSession(session);
		}
	}

	private void closeSession(WebSocketSession session) throws IOException {
		Map<ParamType, String> params = parseParams(session);

		if (session.isOpen()) {
			session.close();
		}
	}


	/**
	 * 功能描述:
	 *
	 * @param session
	 * @return java.util.Map<com.ce.store.app.enums.ParamType, java.lang.String>
	 * @author wangzida
	 */
	private Map<ParamType, String> parseParams(WebSocketSession session) throws IOException {
		Map<ParamType, String> params = new HashMap<>(2);
		try {
			String queryStr = session.getUri().getQuery();
			String[] queries = queryStr.split("&");
			for (String query : queries) {
				String[] split = query.split("=");
				String s = split[0];
				ParamType paramType = ParamType.getByCode(s);
				if (paramType == null) continue;
				String s1 = split[1];
				params.put(paramType, s1);
			}
		} catch (Exception e) {
			closeSession(session);
			throw e;
		}
		return params;
	}

}
