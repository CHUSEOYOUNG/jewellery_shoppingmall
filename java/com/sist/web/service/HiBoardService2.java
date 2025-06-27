package com.sist.web.service;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.sist.common.model.FileData;
import com.sist.common.util.FileUtil;
import com.sist.common.util.StringUtil;
import com.sist.web.dao.HiBoardDao2;
import com.sist.web.model.HiBoard;
import com.sist.web.model.HiBoard2;
import com.sist.web.model.HiBoardFile;
import com.sist.web.model.HiBoardFile2;
import com.sist.web.model.Response;
import com.sist.web.util.CookieUtil;
import com.sist.web.util.HttpUtil;

@Service("hiBoardService2")
public class HiBoardService2 
{
	private static Logger logger = LoggerFactory.getLogger(HiBoardService.class);
	
	@Autowired
	private HiBoardDao2 hiBoardDao;
	
	
	@Value("#{env['upload.save.dir']}")
	
	private String UPLOAD_SAVE_DIR;
	
	   
	   @Value("#{env['upload.save.dir.style']}")
	   private String UPLOAD_SAVE_DIR_STYLE;
	   
		@Value("#{env['upload.save.dir.styleDetail']}")
		private String UPLOAD_SAVE_DIR_STYLE_DETAIL;
	
	//게시물 리스트
	public List<HiBoard2> boardList(HiBoard2 hiBoard)
	{
		List<HiBoard2> list = null;
		
		try
		{
			list = hiBoardDao.boardList(hiBoard);
		}
		catch(Exception e)
		{
			logger.error("[HiBoardService2]boardList Exception", e);
		}
		
		return list;
	} 
	
	
	public long boardListCount(HiBoard2 hiBoard)
	{
		long count = 0;
		
		try
		{
			count = hiBoardDao.boardListCount(hiBoard);
		}
		catch(Exception e)
		{
			logger.error("[HiBoardService2]boardListCount Exception", e);
		}
		
		return count;
	}
	
	

	// 게시글 상세 조회
	public HiBoard2 boardView(long hibbsSeq)
	{
		
	    HiBoard2 hiBoard = null;

	    try 
	    {

	        // 게시글 내용 조회
	    	hiBoard = hiBoardDao.boardSelect(hibbsSeq);
			//조회수 증가와 첨부파일은 추가해야함 
			if(hiBoard != null)
			{
				//조회수 증가(내글일때 조회수 카운트 안하기: 메서드 오버로딩을 통해서, 쿠키아이디도 같이 받아오기)
				hiBoardDao.boardReadCntPlus(hibbsSeq);
			}
	    } 
	    catch(Exception e) {
	        logger.error("[HiBoardService2] boardView Exception", e);
	    }

	    return hiBoard;
	}

    // 댓글 목록 조회 (대댓글 포함)
	  public List<HiBoard2> commentListByGroup(long hibbsGroup) 
	  {
	        List<HiBoard2> list = null;

	        try 
	        {
	            list = hiBoardDao.commentListByGroup(hibbsGroup);
	        } 
	        catch(Exception e) 
	        {
	            logger.error("[HiBoardService2] commentListByGroup Exception", e);
	        }

	        return list;
	    }
	  
	//게시물 등록
		@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
		public int boardInsert(HiBoard2 hiBoard) throws Exception
		{	// Propagation.REQUIRED :트랜잭션이 있으면 그 트랜잭션에서 실행이 되고 없으면 현재 상태에서 새로운 트랜잭션을 실행 (기본설정)
			int count = 0;
			
			count = hiBoardDao.boardInsert(hiBoard);
			
			if(count > 0 && hiBoard.getHiBoardFile() != null)
			{
				HiBoardFile2 hiBoardFile = hiBoard.getHiBoardFile();
				hiBoardFile.setHibbsSeq(hiBoard.getHibbsSeq());
				
				//현재는 첨부파일한개, 2에서는 여러개(서브쿼리이용해서) 해보기
				hiBoardFile.setFileSeq((short)1);
				hiBoardDao.boardFileInsert(hiBoardFile);
				
				
				
				//위 로직과 동일한 결과 가능
//				hiBoard.getHiBoardFile().setHibbsSeq(hiBoard.getHibbsSeq());
//				hiBoard.getHiBoardFile().setFileSeq((short)1);
//				hiBoardDao.boardFileInsert(hiBoard.getHiBoardFile());
			}
			
			return count;
		}

		//첨부파일 조회
		public HiBoardFile2 boardFileSelect(long hibbsSeq)
		{
			HiBoardFile2 hiBoardFile = null;
			
			try
			{
				hiBoardFile = hiBoardDao.boardFileSelect(hibbsSeq);
			}
			catch(Exception e)
			{
				logger.error("[HiBoardService]boardFileSelect Exception", e);
			}
			
			return hiBoardFile;
		}
		public int boardFileInsert(HiBoardFile2 hiBoardFile) {
		    return hiBoardDao.boardFileInsert(hiBoardFile);
		}

		//다중조회
		@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
		public void boardFileMultiInsert(List<HiBoardFile2> fileList) throws Exception
		{
		    if (fileList != null && !fileList.isEmpty())
		    {
		        hiBoardDao.boardFileMultiInsert(fileList);
		    }
		}
		
		//게시물과 첨부파일 조회
		public HiBoard2 boardViewUpdate(long hibbsSeq)
		{
			HiBoard2 hiBoard = null;
			
			try
			{
				hiBoard = hiBoardDao.boardSelect(hibbsSeq);
				if(hiBoard != null)
				{
					HiBoardFile2 hiBoardFile = hiBoardDao.boardFileSelect(hibbsSeq);
					
					if(hiBoardFile != null)
					{
						hiBoard.setHiBoardFile(hiBoardFile);
					}
				}
			}
			catch(Exception e)
			{
				logger.error("[HiBoardService]boardViewUpdate Exception", e);
			}
			
			return hiBoard;
		}

		//게시글 삭제(첨부파일이 있으면 함께 삭제)
		@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
		 public int boardDelete(long hibbsSeq) throws Exception
		 {
			 int count = 0;
			 
			 HiBoard2 hiBoard = boardViewUpdate(hibbsSeq);
			 
			 if(hiBoard != null)
			 {
				 if(hiBoard.getHiBoardFile() != null)
				 {
					 if(hiBoardDao.boardFileDelete(hibbsSeq) > 0)
					 {
						 //첨부파일도 함께 삭제
						 FileUtil.deleteFile(UPLOAD_SAVE_DIR_STYLE + FileUtil.getFileSeparator() + hiBoard.getHiBoardFile().getFileName());
					 }
				 }
				 count = hiBoardDao.boardDelete(hibbsSeq);
			 }
			 
			 return count;
		 }
		
		//게시물 삭제시 답변글 수 조회
		public int boardAnswersCount(long hibbsSeq)
		{
			int count = 0;
			
			try
			{
				count = hiBoardDao.boardAnswersCount(hibbsSeq);
			}
			catch(Exception e)
			{
				logger.error("[HiBoardService2]boardAnswersCount Exception", e);
			}
			return count;
		}

		//게시물 조회
		public HiBoard2 boardSelect(long hibbsSeq)
		{
			HiBoard2 hiBoard = null;
			try
			{
				hiBoard = hiBoardDao.boardSelect(hibbsSeq);
			}
			catch(Exception e)
			{
				logger.error("[HiBoardService2]boardSelect Exception", e);
			}

			return hiBoard;
		}
		
		

}
