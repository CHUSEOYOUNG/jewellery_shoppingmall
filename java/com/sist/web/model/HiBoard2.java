package com.sist.web.model;

import java.io.Serializable;

public class HiBoard2 implements Serializable
{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7586136847576887321L;
	private long hibbsSeq;	    	//게시물번호(시퀀스:SEQ_HIBOARD_SEQ)
	private String userId;        	//사용자아이디
	private String userName;
	private String userEmail;
	private long hibbsGroup;	    //그룹번호
	private int hibbsOrder; 		//그룹내 순서
	private int hibbsIndent;		//들여쓰기
	private String hibbsTitle;	    //게시물 제목
	private String hibbsContent;	//게시물 내용
	private int hibbsReadCnt;		//게시물 조회수
	private String regDate;	    	//게시물 등록일
	private long hibbsParent;		//부모 게시물 번호
	
	private String searchType; 		//검색타입(1: 이름, 2: 제목, 3: 내용)
	private String searchValue;
	
	private long startRow;			//시작페이지 rownum
	private long endRow;			//끝페이지 rownum
	
	private HiBoardFile2 hiBoardFile;	//첨부파일

	private String fileName;
	private String fileOrgName;
	private String fileExt;
	private long fileSize;

    // ✅ 추가된 두번째 첨부파일 (상세 이미지)
    private HiBoardFile2 hiBoardFile2;

	public HiBoard2()
	{
		hibbsSeq = 0;	    
		userId = "";
		userEmail = "";
		userName = "";
		hibbsGroup = 0;  
		hibbsOrder = 0;
		hibbsIndent	= 0;
		hibbsTitle = "";   
		hibbsContent = "";	
		hibbsReadCnt = 0;	
		regDate	= "";    
		hibbsParent	= 0;
		
		searchType = "";
		searchValue = "";
		
		startRow = 0;
		endRow = 0;
		hiBoardFile = null;
		hiBoardFile2 = null;
		fileName = "";
		fileOrgName = "";
		fileExt = "";
		fileSize = 0;


	}


    public HiBoardFile2 getHiBoardFile2() {
        return hiBoardFile2;
    }

    public void setHiBoardFile2(HiBoardFile2 hiBoardFile2) {
        this.hiBoardFile2 = hiBoardFile2;
    }
	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileOrgName() {
		return fileOrgName;
	}

	public void setFileOrgName(String fileOrgName) {
		this.fileOrgName = fileOrgName;
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

	public HiBoardFile2 getHiBoardFile() {
		return hiBoardFile;
	}

	public void setHiBoardFile(HiBoardFile2 hiBoardFile) {
		this.hiBoardFile = hiBoardFile;
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

	public String getSearchType() {
		return searchType;
	}

	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}

	public String getSearchValue() {
		return searchValue;
	}

	public void setSearchValue(String searchValue) {
		this.searchValue = searchValue;
	}

	public long getHibbsSeq() {
		return hibbsSeq;
	}

	public void setHibbsSeq(long hibbsSeq) {
		this.hibbsSeq = hibbsSeq;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public long getHibbsGroup() {
		return hibbsGroup;
	}

	public void setHibbsGroup(long hibbsGroup) {
		this.hibbsGroup = hibbsGroup;
	}

	public int getHibbsOrder() {
		return hibbsOrder;
	}

	public void setHibbsOrder(int hibbsOrder) {
		this.hibbsOrder = hibbsOrder;
	}

	public int getHibbsIndent() {
		return hibbsIndent;
	}

	public void setHibbsIndent(int hibbsIndent) {
		this.hibbsIndent = hibbsIndent;
	}

	public String getHibbsTitle() {
		return hibbsTitle;
	}

	public void setHibbsTitle(String hibbsTitle) {
		this.hibbsTitle = hibbsTitle;
	}

	public String getHibbsContent() {
		return hibbsContent;
	}

	public void setHibbsContent(String hibbsContent) {
		this.hibbsContent = hibbsContent;
	}

	public int getHibbsReadCnt() {
		return hibbsReadCnt;
	}

	public void setHibbsReadCnt(int hibbsReadCnt) {
		this.hibbsReadCnt = hibbsReadCnt;
	}

	public String getRegDate() {
		return regDate;
	}

	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}

	public long getHibbsParent() {
		return hibbsParent;
	}

	public void setHibbsParent(long hibbsParent) {
		this.hibbsParent = hibbsParent;
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
