package com.sist.web.controller;

import java.util.List;
import javax.servlet.http.HttpServletRequest;

import com.sist.web.model.NoticeBoard;
import com.sist.web.model.Paging;
import com.sist.web.service.NoticeBoardService;
import com.sist.web.util.HttpUtil;
import com.sist.common.util.StringUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class NoticeBoardController {

    @Autowired
    private NoticeBoardService noticeBoardService;

    private static final int LIST_COUNT = 10;
    private static final int PAGE_COUNT = 5;

    @RequestMapping("/board/noticelist")
    public String noticeList(ModelMap model, HttpServletRequest request) {
        String searchType = HttpUtil.get(request, "searchType", "");
        String searchValue = HttpUtil.get(request, "searchValue", "");
        long curPage = HttpUtil.get(request, "curPage", 1L);

        NoticeBoard search = new NoticeBoard();
        if (!StringUtil.isEmpty(searchType) && !StringUtil.isEmpty(searchValue)) {
            if ("1".equals(searchType)) search.setAdminName(searchValue);
            else if ("2".equals(searchType)) search.setNotiTitle(searchValue);
            else if ("3".equals(searchType)) search.setNotiContent(searchValue);
        }

        long totalCount = noticeBoardService.noticeTotalCount(search);
        Paging paging = new Paging("/board/noticelist", totalCount, LIST_COUNT, PAGE_COUNT, curPage, "curPage");

        search.setStartRow(paging.getStartRow());
        search.setEndRow(paging.getEndRow());

        List<NoticeBoard> list = noticeBoardService.noticeList(search);

        model.addAttribute("list", list);
        model.addAttribute("searchType", searchType);
        model.addAttribute("searchValue", searchValue);
        model.addAttribute("curPage", curPage);
        model.addAttribute("paging", paging);

        return "/board/noticelist";
    }
}
