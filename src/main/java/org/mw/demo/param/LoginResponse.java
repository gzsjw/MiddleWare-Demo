package org.mw.demo.param;

public class LoginResponse {
	private int loginResult = ILoginConstants.UNKNOWN_ERROR;

	private String loginMessage;

	private User user;

	private String[] funccodes;

	public LoginResponse() {
		super();
	}
	
	public LoginResponse(int loginResult, String loginMessage) {
		this.loginResult = loginResult;
		this.loginMessage = loginMessage;
	}

	public int getLoginResult() {
		return loginResult;
	}

	public void setLoginResult(int loginResult) {
		this.loginResult = loginResult;
	}

	public String getLoginMessage() {
		return loginMessage;
	}

	public void setLoginMessage(String loginMessage) {
		this.loginMessage = loginMessage;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String[] getFunccodes() {
		return funccodes;
	}

	public void setFunccodes(String[] funccodes) {
		this.funccodes = funccodes;
	}

}
