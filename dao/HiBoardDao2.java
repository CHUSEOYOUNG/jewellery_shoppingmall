package com.sist.web.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.sist.web.model.HiBoard2;
import com.sist.web.model.HiBoardFile2;

@Repository("hiBaordDao2")
public interface HiBoardDao2 
{
	//게시물 리스트
	public List<HiBoard2> boardList(HiBoard2 hiBoard);
	
	//총게시물수
	public long boardListCount(HiBoard2 hiBoard);
	// 게시글 상세 조회
    public HiBoard2 boardView(long hibbsSeq);         
    // 댓글 목록 조회
    public List<HiBoard2> commentListByGroup(long hibbsGroup);  // 그룹 기반 전체 댓글/대댓글 조회
    
    public HiBoard2 boardSelect(long hibbsSeq);
    
	//조회수 증가
	public int boardReadCntPlus(long hibbsSeq);
	
	//게시물 등록
	public int boardInsert(HiBoard2 hiBoard);
	
	//게시물 첨부파일 등록
	public int boardFileInsert(HiBoardFile2 hiBoardFile);
	
	public HiBoardFile2 boardFileSelect(long hibbsSeq);

	// 다중 첨부파일 등록
	public int boardFileMultiInsert(List<HiBoardFile2> fileList);
	
	
	//게시글 삭제시 답변글수 조회
		public int boardAnswersCount(long hibbsSeq);
		
		//게시글  삭제
		public int boardDelete(long hibbsSeq);
		
		//게시글 첨부파일 삭제
		public int boardFileDelete(long hibbsSeq);

}
