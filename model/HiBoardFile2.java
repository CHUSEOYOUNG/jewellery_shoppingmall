package com.sist.web.model;

import java.io.Serializable;

public class HiBoardFile2 implements Serializable
{

	private static final long serialVersionUID = -1516894677180190144L;
	private long hibbsSeq;			//게시물 번호(TBL_HIBOARD.HIBBS_SEQ)
	private int fileSeq;			//파일번호(HIBBS_SEQ.MAX+1)
	private String fileOrgName;		//원본파일명
	private String fileName;		//파일명
	private String fileExt;			//파일 확장자
	private long fileSize;			//파일 크기
	private String regDate;			//등록일
	
	public HiBoardFile2()
	{
		hibbsSeq = 0;
		fileSeq = 0;
		fileOrgName = "";
		fileName = "";
		fileExt = "";
		fileSize = 0;
		regDate = "";
	}

	public long getHibbsSeq() {
		return hibbsSeq;
	}

	public void setHibbsSeq(long hibbsSeq) {
		this.hibbsSeq = hibbsSeq;
	}

	public int getFileSeq() {
		return fileSeq;
	}

	public void setFileSeq(int fileSeq) {
		this.fileSeq = fileSeq;
	}

	public String getFileOrgName() {
		return fileOrgName;
	}

	public void setFileOrgName(String fileOrgName) {
		this.fileOrgName = fileOrgName;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileExt() {
		return fileExt;
	}

	public void setFileExt(String fileExt) {
		this.fileExt = fileExt;
	}

	public long getFileSize() {
		return fileSize;
	}

	public void setFileSize(long fileSize) {
		this.fileSize = fileSize;
	}

	public String getRegDate() {
		return regDate;
	}

	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}
	

}
