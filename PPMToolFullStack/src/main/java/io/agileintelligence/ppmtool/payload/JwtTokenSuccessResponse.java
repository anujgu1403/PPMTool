package io.agileintelligence.ppmtool.payload;

public class JwtTokenSuccessResponse {
private String token;
private boolean success;


public JwtTokenSuccessResponse(String token, boolean success) {
	super();
	this.token = token;
	this.success = success;
}
public String getToken() {
	return token;
}
public void setToken(String token) {
	this.token = token;
}
public boolean isSuccess() {
	return success;
}
public void setSuccess(boolean success) {
	this.success = success;
}
@Override
public String toString() {
	
	return "sucess: "+success+" token: "+token;
}


}
