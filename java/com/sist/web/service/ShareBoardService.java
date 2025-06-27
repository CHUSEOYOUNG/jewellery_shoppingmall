package com.sist.web.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sist.web.dao.ShareBoardDao;
import com.sist.web.dao.ShareBoardFileDao;
import com.sist.web.model.ShareBoard;
import com.sist.web.model.ShareBoardFile;
import com.sist.web.model.ShareComment;

@Service("shareBoardService")
public class ShareBoardService {

    @Autowired
    private ShareBoardDao shareBoardDao;

    @Autowired
    private ShareBoardFileDao shareBoardFileDao;

    // 게시글 리스트
    public List<ShareBoard> boardList(ShareBoard board) {
        List<ShareBoard> list = shareBoardDao.boardList(board);
        for (ShareBoard b : list) {
            b.setFileList(shareBoardFileDao.fileList(b.getPostId()));
        }
        return list;
    }

    // 게시글 수
    public long boardListCount(ShareBoard board) {
        return shareBoardDao.boardListCount(board);
    }

    // 게시글 상세조회 + 조회수 증가 + 첨부파일 포함
    public ShareBoard boardView(long postId) {
        shareBoardDao.boardReadCntPlus(postId);
        ShareBoard board = shareBoardDao.boardSelect(postId);
        if (board != null) {
            board.setFileList(shareBoardFileDao.fileList(postId));
        }
        return board;
    }

    // 게시글 단건 조회 (조회수 증가 없이)
    public ShareBoard boardSelect(long postId) {
        ShareBoard board = shareBoardDao.boardSelect(postId);
        if (board != null) {
            board.setFileList(shareBoardFileDao.fileList(postId));
        }
        return board;
    }

    // 게시글 등록
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int insertBoard(ShareBoard board) {
        return shareBoardDao.insertBoard(board);
    }

    // 게시글 삭제
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int boardDelete(long postId) {
        shareBoardDao.deleteBoardFiles(postId); // 파일 먼저 삭제
        return shareBoardDao.boardDelete(postId);
    }

    // 게시글 수정
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int updateBoard(ShareBoard board) {
        return shareBoardDao.updateBoard(board);
    }

    // 첨부파일 등록 (여러 개)
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void insertBoardFiles(List<ShareBoardFile> fileList) {
        if (fileList != null && !fileList.isEmpty()) {
            shareBoardDao.insertBoardFiles(fileList);
        }
    }

    // 첨부파일 등록 (단건)
    public int insertBoardFile(ShareBoardFile boardFile) {
        return shareBoardDao.insertBoardFile(boardFile);
    }

    // 첨부파일 조회 (단건)
    public ShareBoardFile fileSelect(long fileId) {
        return shareBoardDao.fileSelect(fileId);
    }

    // 첨부파일 전체 삭제
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void deleteBoardFiles(long postId) {
        shareBoardDao.deleteBoardFiles(postId);
    }

    // 댓글 등록
    public int insertComment(ShareComment comment) {
        return shareBoardDao.insertComment(comment);
    }

    // 댓글 리스트 조회
    public List<ShareComment> commentList(long postId) {
        return shareBoardDao.commentList(postId);
    }

    // 댓글 단건 조회
    public ShareComment commentSelect(long commentId) {
        return shareBoardDao.commentSelect(commentId);
    }

    // 댓글 수 조회
    public int commentCount(long postId) {
        return shareBoardDao.commentCount(postId);
    }
}
