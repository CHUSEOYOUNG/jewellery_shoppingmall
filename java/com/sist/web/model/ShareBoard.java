package com.sist.web.model;

import java.io.Serializable;
import java.util.List;

public class ShareBoard implements Serializable {

    private static final long serialVersionUID = 768853238331278139L;

    private Long postId;
    private String userId;
    private String title;
    private String content;
    private int readCnt;
    private String regDate;

    private String searchType;
    private String searchValue;

    private long startRow;
    private long endRow;

    private List<ShareBoardFile> fileList;

    public ShareBoard() {
        postId = 0L;
        userId = "";
        title = "";
        content = "";
        readCnt = 0;
        regDate = "";
        searchType = "";
        searchValue = "";
        startRow = 0;
        endRow = 0;
    }

    public Long getPostId() { return postId; }
    public void setPostId(Long postId) { this.postId = postId; }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public int getReadCnt() { return readCnt; }
    public void setReadCnt(int readCnt) { this.readCnt = readCnt; }

    public String getRegDate() { return regDate; }
    public void setRegDate(String regDate) { this.regDate = regDate; }

    public String getSearchType() { return searchType; }
    public void setSearchType(String searchType) { this.searchType = searchType; }

    public String getSearchValue() { return searchValue; }
    public void setSearchValue(String searchValue) { this.searchValue = searchValue; }

    public long getStartRow() { return startRow; }
    public void setStartRow(long startRow) { this.startRow = startRow; }

    public long getEndRow() { return endRow; }
    public void setEndRow(long endRow) { this.endRow = endRow; }

    public List<ShareBoardFile> getFileList() { return fileList; }
    public void setFileList(List<ShareBoardFile> fileList) { this.fileList = fileList; }
}
