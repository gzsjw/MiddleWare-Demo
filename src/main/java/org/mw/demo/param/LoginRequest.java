package org.mw.demo.param;

public class LoginRequest {
	private String userCode;
	private String userPWD;

	public LoginRequest() {
		super();
	}

	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public String getUserPWD() {
		return userPWD;
	}

	public void setUserPWD(String userPWD) {
		this.userPWD = userPWD;
	}

}
