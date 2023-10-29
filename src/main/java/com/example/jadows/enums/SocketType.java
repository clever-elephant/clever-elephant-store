package com.example.jadows.enums;

import lombok.AllArgsConstructor;
import org.springframework.util.StringUtils;

@AllArgsConstructor
public enum SocketType {

	WEB("web", "网页端socket"), MINI("mini", "小程序端socket");

	private String code;

	private String desc;

	public static SocketType getByCode(String code) {
		if (StringUtils.hasText(code)) {
			for (SocketType value : values()) {
				if (value.code.equals(code)) return value;
			}
		}
		return null;
	}
}
