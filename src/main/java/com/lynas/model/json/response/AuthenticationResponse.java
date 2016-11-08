package com.lynas.model.json.response;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

public class AuthenticationResponse {

	private String token;

	public AuthenticationResponse() {
		super();
	}

	public AuthenticationResponse(String token) {
		this.setToken(token);
	}

	public String getToken() {
		return this.token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}

}
