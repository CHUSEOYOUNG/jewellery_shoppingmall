package com.sist.web.model;

import java.io.Serializable;

public class Admin implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3402097313463679411L;
	private String adminId;
	private String adminPwd;
	private String adminName;
	private String adminRegdate;
	private String adminEmail;
	
	
	public Admin()
	{
		adminId = "";
		adminPwd = "";
		adminName = "";
		adminRegdate = "";
		adminEmail = "";
	}


	public String getAdminId() {
		return adminId;
	}


	public void setAdminId(String adminId) {
		this.adminId = adminId;
	}


	public String getAdminPwd() {
		return adminPwd;
	}


	public void setAdminPwd(String adminPwd) {
		this.adminPwd = adminPwd;
	}


	public String getAdminName() {
		return adminName;
	}


	public void setAdminName(String adminName) {
		this.adminName = adminName;
	}


	public String getAdminRegdate() {
		return adminRegdate;
	}


	public void setAdminRegdate(String adminRegdate) {
		this.adminRegdate = adminRegdate;
	}


	public String getAdminEmail() {
		return adminEmail;
	}


	public void setAdminEmail(String adminEmail) {
		this.adminEmail = adminEmail;
	}
	
	
	
}

	
