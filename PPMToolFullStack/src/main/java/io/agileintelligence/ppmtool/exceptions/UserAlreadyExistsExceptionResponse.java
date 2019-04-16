package io.agileintelligence.ppmtool.exceptions;

public class UserAlreadyExistsExceptionResponse {
	private String username;

	
	public UserAlreadyExistsExceptionResponse(String username) {
		this.username = username;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	

}
