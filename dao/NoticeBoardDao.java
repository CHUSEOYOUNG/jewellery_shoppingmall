package com.sist.web.dao;

import java.util.List;

import com.sist.web.model.NoticeBoard;

public interface NoticeBoardDao {
    public List<NoticeBoard> noticeList(NoticeBoard search);
    public long noticeTotalCount(NoticeBoard search);
}
