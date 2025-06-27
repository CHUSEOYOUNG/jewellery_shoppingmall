package com.sist.web.dao;

import java.util.List;

import com.sist.web.model.ShareBoardFile;

public interface ShareBoardFileDao {
	
    List<ShareBoardFile> fileList(long postId);
    
    List<ShareBoardFile> selectFileListByPostId(long postId);

}