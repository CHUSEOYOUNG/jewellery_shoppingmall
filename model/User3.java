package com.sist.web.model;

import java.io.Serializable;

public class User3 implements Serializable{


	private static final long serialVersionUID = -2420640649815987538L;
	private String userId;
	private String userPwd; 
	private String userName; 
	private String userEmail; 
	private String status;  
	private String regDate;
	
	public User3()
	{
		userId = "";
		userPwd = "";
		userName = "";
		userEmail = "";
		status = "N";
		regDate = "";
	}
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserPwd() {
		return userPwd;
	}

	public void setUserPwd(String userPwd) {
		this.userPwd = userPwd;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getRegDate() {
		return regDate;
	}

	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}

}
