package com.sist.web.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sist.web.dao.NoticeBoardDao;
import com.sist.web.model.NoticeBoard;

@Service("noticeBoardService")
public class NoticeBoardService
{
	private static Logger logger = LoggerFactory.getLogger(NoticeBoardService.class);
	
	@Autowired
	private NoticeBoardDao noticeBoardDao;
	
	//게시물 리스트
	public List<NoticeBoard> noticeList(NoticeBoard noticeBoard)
	{
		List<NoticeBoard> list = null;
		
		try
		{
			list = noticeBoardDao.noticeList(noticeBoard);
		}
		catch(Exception e)
		{
			logger.error("[NoticeBoardService]noticeList Exception", e);
		}
		
		return list;
	} 
	
	
	public long noticeTotalCount(NoticeBoard noticeBoard)
	{
		long count = 0;
		
		try
		{
			count = noticeBoardDao.noticeTotalCount(noticeBoard);
		}
		catch(Exception e)
		{
			logger.error("[HiBoardService2]boardListCount Exception", e);
		}
		
		return count;
	}
	
	
}

