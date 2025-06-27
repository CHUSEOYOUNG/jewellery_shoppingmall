package com.sist.web.model;

import java.io.Serializable;

public class NoticeBoard implements Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 9147384561268315470L;
	private long notiNum;
	private String adminId;
	private String adminName;
	private String adminEmail;
	private String notiTitle;
	private String notiContent;
	private int notiReadCnt;
	private String notiRegdate;
	private long startRow;
	private long endRow;

	
	public NoticeBoard()
	{
		notiNum = 0;
		adminId = "";
		adminName = "";
		adminEmail = "";
		notiTitle = "";
		notiContent = "";
		notiReadCnt = 0;
		notiRegdate = "";
		startRow = 0;
		endRow = 0;
		
	}


	public long getNotiNum() {
		return notiNum;
	}


	public void setNotiNum(long notiNum) {
		this.notiNum = notiNum;
	}


	public String getAdminId() {
		return adminId;
	}


	public void setAdminId(String adminId) {
		this.adminId = adminId;
	}


	public String getNotiTitle() {
		return notiTitle;
	}


	public void setNotiTitle(String notiTitle) {
		this.notiTitle = notiTitle;
	}


	public String getNotiContent() {
		return notiContent;
	}


	public void setNotiContent(String notiContent) {
		this.notiContent = notiContent;
	}


	public int getNotiReadCnt() {
		return notiReadCnt;
	}


	public void setNotiReadCnt(int notiReadCnt) {
		this.notiReadCnt = notiReadCnt;
	}


	public String getNotiRegdate() {
		return notiRegdate;
	}


	public void setNotiRegdate(String notiRegdate) {
		this.notiRegdate = notiRegdate;
	}


	public String getAdminName() {
		return adminName;
	}


	public void setAdminName(String adminName) {
		this.adminName = adminName;
	}


	public String getAdminEmail() {
		return adminEmail;
	}


	public void setAdminEmail(String adminEmail) {
		this.adminEmail = adminEmail;
	}


	public long getStartRow() {
		return startRow;
	}


	public void setStartRow(long startRow) {
		this.startRow = startRow;
	}


	public long getEndRow() {
		return endRow;
	}


	public void setEndRow(long endRow) {
		this.endRow = endRow;
	}
	
	
	
}
