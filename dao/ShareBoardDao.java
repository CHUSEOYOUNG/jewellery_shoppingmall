package com.sist.web.dao;

import java.util.List;

import javax.xml.stream.events.Comment;

import com.sist.web.model.ShareBoard;
import com.sist.web.model.ShareBoardFile;
import com.sist.web.model.ShareComment;

public interface ShareBoardDao {

    // 게시글 리스트 + 검색 + 페이징
    List<ShareBoard> boardList(ShareBoard board);

    long boardListCount(ShareBoard board);

    // 게시글 상세 조회 (파일 포함 조회)
    ShareBoard boardView(long postId);

    ShareBoard boardSelect(long postId);
    List<ShareBoardFile> selectFileListByPostId(long postId); // MyBatis가 내부적으로 호출함
    // 게시글 등록
    int insertBoard(ShareBoard board);

    // 게시글 삭제
    int boardDelete(long postId);

    // 첨부파일 관련
    void insertBoardFiles(List<ShareBoardFile> fileList);

    ShareBoardFile fileSelect(long fileId);

    // 댓글 등록
    int insertComment(ShareComment comment);

    // 댓글 리스트
    List<ShareComment> commentList(long postId);
    void boardReadCntPlus(long postId);
    List<ShareBoardFile> fileList(long postId);
    public int insertBoardFile(ShareBoardFile boardFile);
    public ShareComment commentSelect(long commentId);

    public int commentCount(long postId);
    public void deleteBoardFiles(long postId);
    public int updateBoard(ShareBoard board);


}
