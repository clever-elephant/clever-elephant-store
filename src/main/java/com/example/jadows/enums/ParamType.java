package com.example.jadows.enums;

import lombok.AllArgsConstructor;
import org.springframework.util.StringUtils;

@AllArgsConstructor
public enum ParamType {

	SESSION_ID("session", "会话id"), TOKEN("token", "token"),
	TYPE("type", "会话类型");

	private String code;

	private String desc;

	public static ParamType getByCode(String code) {
		if (StringUtils.hasText(code)) {

			for (ParamType value : values()) {
				if (value.code.equals(code)) return value;
			}
		}
		return null;
	}
}
