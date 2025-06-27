package com.sist.web.model;

import java.io.Serializable;

public class ShareBoardFile implements Serializable {


    /**
	 * 
	 */
	private static final long serialVersionUID = -6045841517762189431L;
	private long fileId;         // PK
    private long postId;         // FK
    private int fileSeq;         // 게시글 내 순번
    private String fileOrgName;  // 원본 파일명
    private String fileName;     // 저장된 파일명
    private String fileExt;      // 확장자
    private long fileSize;       // 파일 크기
    private String regDate;      // 등록일

    public ShareBoardFile() {
        fileId = 0;
        postId = 0;
        fileSeq = 0;
        fileOrgName = "";
        fileName = "";
        fileExt = "";
        fileSize = 0;
        regDate = "";
    }

    public long getFileId() {
        return fileId;
    }
    public void setFileId(long fileId) {
        this.fileId = fileId;
    }
    public long getPostId() {
        return postId;
    }
    public void setPostId(long postId) {
        this.postId = postId;
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
