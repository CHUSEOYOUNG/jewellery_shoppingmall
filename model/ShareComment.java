package com.sist.web.model;

public class ShareComment {
    private long commentId;   // 댓글 ID
    private long postId;      // 소속 게시글 ID
    private String userId;    // 작성자
    private int orderNo;      // 그룹내 순서
    private int indent;       // 들여쓰기 단계
    private String content;   // 댓글 내용
    private long parentId;    // 부모 댓글 ID
    private String regDate;     // 등록일

    
    public ShareComment()
    {
        commentId = 0;   // 댓글 ID
        postId = 0;      // 소속 게시글 ID
        userId = "";    // 작성자
        orderNo = 0;      // 그룹내 순서
        indent = 0;       // 들여쓰기 단계
        content = "";   // 댓글 내용
        parentId = 0;    // 부모 댓글 ID
        regDate = "";     // 등록일
    }
    // getter / setter
    public Long getCommentId() {
        return commentId;
    }
    public void setCommentId(Long commentId) {
        this.commentId = commentId;
    }
    public Long getPostId() {
        return postId;
    }
    public void setPostId(Long postId) {
        this.postId = postId;
    }
    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }
    public int getOrderNo() {
        return orderNo;
    }
    public void setOrderNo(int orderNo) {
        this.orderNo = orderNo;
    }
    public int getIndent() {
        return indent;
    }
    public void setIndent(int indent) {
        this.indent = indent;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public Long getParentId() {
        return parentId;
    }
    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }
    public String getRegDate() {
        return regDate;
    }
    public void setRegDate(String regDate) {
        this.regDate = regDate;
    }
}
