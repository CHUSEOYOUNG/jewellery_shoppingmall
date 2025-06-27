package com.sist.web.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sist.web.model.ShareBoard;
import com.sist.web.model.ShareComment;
import com.sist.web.model.ShareBoardFile;
import com.sist.web.model.Paging;
import com.sist.web.model.Response;
import com.sist.web.model.User3;
import com.sist.web.service.ShareBoardService;
import com.sist.web.service.UserService3;
import com.sist.web.util.CookieUtil;
import com.sist.web.util.HttpUtil;
import com.sist.common.model.FileData;
import com.sist.common.util.FileUtil;
import com.sist.common.util.StringUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

@Controller("shareBoardController")
public class ShareBoardController {

    private static Logger logger = LoggerFactory.getLogger(ShareBoardController.class);

    @Autowired
    private ShareBoardService shareBoardService;

    @Autowired
    private UserService3 userService;

    @Value("#{env['auth.cookie.name']}")
    String AUTH_COOKIE_NAME;

    @Value("#{env['upload.save.dir.style']}")
    String UPLOAD_SAVE_DIR_STYLE;

    @Value("#{env['upload.save.dir.styleDetail']}")
    private String UPLOAD_SAVE_DIR_STYLE_DETAIL;

    static final int LIST_COUNT = 10;
    static final int PAGE_COUNT = 2;

    /** 리스트 */
    @RequestMapping(value = "/board/list2")
    public String list(ModelMap model, HttpServletRequest request) {

        String searchType = HttpUtil.get(request, "searchType", "");
        String searchValue = HttpUtil.get(request, "searchValue", "");
        long curPage = HttpUtil.get(request, "curPage", 1L);

        ShareBoard search = new ShareBoard();
        if (!StringUtil.isEmpty(searchType) && !StringUtil.isEmpty(searchValue)) {
            search.setSearchType(searchType);
            search.setSearchValue(searchValue);
        }

        long totalCount = shareBoardService.boardListCount(search);
        Paging paging = null;
        List<ShareBoard> list = null;

        if (totalCount > 0) {
            paging = new Paging("/board/list2", totalCount, LIST_COUNT, PAGE_COUNT, curPage, "curPage");
            search.setStartRow(paging.getStartRow());
            search.setEndRow(paging.getEndRow());
            list = shareBoardService.boardList(search);
        }
        

        model.addAttribute("list", list);
        model.addAttribute("searchType", searchType);
        model.addAttribute("searchValue", searchValue);
        model.addAttribute("curPage", curPage);
        model.addAttribute("paging", paging);
        
        
        

        return "/board/list2";
    }

    /** 등록폼 이동 */
    @RequestMapping(value = "/board/writeForm2")
    public String writeForm(ModelMap model, HttpServletRequest request) {
        String cookieUserId = CookieUtil.getHexValue(request, AUTH_COOKIE_NAME);
        User3 user = userService.userSelect(cookieUserId);
        model.addAttribute("user", user);
        return "/board/writeForm2";
    }

    /** 게시글 등록 */
    @RequestMapping(value = "/board/writeProc2", method = RequestMethod.POST)
    @ResponseBody
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Response<Object> writeProc(MultipartHttpServletRequest request) {
        Response<Object> ajaxResponse = new Response<Object>();

        String cookieUserId = CookieUtil.getHexValue(request, AUTH_COOKIE_NAME);
        String title = HttpUtil.get(request, "title", "");
        String content = HttpUtil.get(request, "content", "");

        if (!StringUtil.isEmpty(title) && !StringUtil.isEmpty(content)) {
            ShareBoard board = new ShareBoard();
            board.setUserId(cookieUserId);
            board.setTitle(title);
            board.setContent(content);

            int result = shareBoardService.insertBoard(board);

            if (result > 0) {
                long postId = board.getPostId();

                // ✅ 단일 파일만 처리 (shareFile 기준)
                List<FileData> fileDataList = HttpUtil.getFiles(request, "shareFile", UPLOAD_SAVE_DIR_STYLE);
                List<ShareBoardFile> fileList = new ArrayList<>();
                int seq = 1;

                for (FileData fileData : fileDataList) {
                    ShareBoardFile boardFile = new ShareBoardFile();
                    boardFile.setPostId(postId);
                    boardFile.setFileSeq(seq++);
                    boardFile.setFileName(fileData.getFileName());
                    boardFile.setFileOrgName(fileData.getFileOrgName());
                    boardFile.setFileExt(fileData.getFileExt());
                    boardFile.setFileSize(fileData.getFileSize());

                    // 기존에 list.add() 하지말고 바로 insert
                    shareBoardService.insertBoardFile(boardFile);
                }

                if (!fileList.isEmpty()) {
                    shareBoardService.insertBoardFiles(fileList);
                }
                ajaxResponse.setResponse(0, "success");
            } else {
                ajaxResponse.setResponse(500, "fail");
            }
        } else {
            ajaxResponse.setResponse(400, "empty input");
        }

        return ajaxResponse;
    }
    /** 상세보기 */
    @RequestMapping(value = "/board/view2")
    public String view(ModelMap model, HttpServletRequest request) {
        long postId = HttpUtil.get(request, "postId", 0L);
        ShareBoard board = shareBoardService.boardView(postId);
        List<ShareComment> commentList = shareBoardService.commentList(postId);
        model.addAttribute("board", board);
        model.addAttribute("commentList", commentList);
        model.addAttribute("loginUserId", CookieUtil.getValue(request, "USER_ID"));
        return "/board/view2";
    }

    /** 첨부파일 다운로드 */
    @RequestMapping(value = "/board/download2")
    public ModelAndView download(HttpServletRequest request) {
        ModelAndView modelAndView = null;
        long fileId = HttpUtil.get(request, "fileId", 0L);

        ShareBoardFile boardFile = shareBoardService.fileSelect(fileId);
        if (boardFile != null) {
            File file = new File(UPLOAD_SAVE_DIR_STYLE + FileUtil.getFileSeparator() + boardFile.getFileName());
            if (FileUtil.isFile(file)) {
                modelAndView = new ModelAndView();
                modelAndView.setViewName("fileDownloadView");
                modelAndView.addObject("file", file);
                modelAndView.addObject("fileName", boardFile.getFileOrgName());
            }
        }
        return modelAndView;
    }
    //게시물 삭제
    @RequestMapping(value = "/board/delete2", method = RequestMethod.POST)
    @ResponseBody
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Response<Object> delete(HttpServletRequest request) {
        Response<Object> ajaxResponse = new Response<Object>();

        long postId = HttpUtil.get(request, "postId", 0L);
        String cookieUserId = CookieUtil.getHexValue(request, AUTH_COOKIE_NAME);

        if (postId > 0) {
            ShareBoard board = shareBoardService.boardSelect(postId);
            if (board != null) {
                if (StringUtil.equals(cookieUserId, board.getUserId())) {
                    // 댓글 존재 확인
                    if (shareBoardService.commentCount(postId) > 0) {
                        ajaxResponse.setResponse(-999, "댓글이 존재하여 삭제할 수 없습니다.");
                    } else {
                        try {
                            // 첨부파일 먼저 삭제
                            shareBoardService.deleteBoardFiles(postId);
                            // 게시글 삭제
                            if (shareBoardService.boardDelete(postId) > 0) {
                                ajaxResponse.setResponse(0, "success");
                            } else {
                                ajaxResponse.setResponse(500, "server error");
                            }
                        } catch (Exception e) {
                            logger.error("[ShareBoardController] delete Exception", e);
                            ajaxResponse.setResponse(500, "server error");
                        }
                    }
                } else {
                    ajaxResponse.setResponse(403, "권한 없음");
                }
            } else {
                ajaxResponse.setResponse(404, "게시글 없음");
            }
        } else {
            ajaxResponse.setResponse(400, "잘못된 요청");
        }

        return ajaxResponse;
    }
    
    //게시물 수정
    @RequestMapping(value = "/board/updateProc2", method = RequestMethod.POST)
    @ResponseBody
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Response<Object> updateProc(MultipartHttpServletRequest request) {
        Response<Object> ajaxResponse = new Response<Object>();

        String cookieUserId = CookieUtil.getHexValue(request, AUTH_COOKIE_NAME);
        long postId = HttpUtil.get(request, "postId", 0L);
        String title = HttpUtil.get(request, "title", "");
        String content = HttpUtil.get(request, "content", "");

        FileData fileData = HttpUtil.getFile(request, "shareFile", UPLOAD_SAVE_DIR_STYLE);

        if (postId > 0 && !StringUtil.isEmpty(title) && !StringUtil.isEmpty(content)) {
            ShareBoard board = shareBoardService.boardSelect(postId);
            if (board != null) {
                if (StringUtil.equals(cookieUserId, board.getUserId())) {
                    board.setTitle(title);
                    board.setContent(content);

                    int result = shareBoardService.updateBoard(board);

                    if (result > 0) {
                        if (fileData != null && fileData.getFileSize() > 0) {
                            // 기존 파일 삭제
                            shareBoardService.deleteBoardFiles(postId);

                            ShareBoardFile boardFile = new ShareBoardFile();
                            boardFile.setPostId(postId);
                            boardFile.setFileSeq(1);
                            boardFile.setFileName(fileData.getFileName());
                            boardFile.setFileOrgName(fileData.getFileOrgName());
                            boardFile.setFileExt(fileData.getFileExt());
                            boardFile.setFileSize(fileData.getFileSize());

                            shareBoardService.insertBoardFile(boardFile);
                        }
                        ajaxResponse.setResponse(0, "success");
                    } else {
                        ajaxResponse.setResponse(500, "fail");
                    }
                } else {
                    ajaxResponse.setResponse(403, "권한 없음");
                }
            } else {
                ajaxResponse.setResponse(404, "게시글 없음");
            }
        } else {
            ajaxResponse.setResponse(400, "잘못된 입력");
        }

        return ajaxResponse;
    }



    /** 댓글 등록 */
    @RequestMapping(value = "/board/commentWrite", method = RequestMethod.POST)
    @ResponseBody
    public Response<Object> commentWrite(HttpServletRequest request) {
        Response<Object> ajaxResponse = new Response<Object>();

        String cookieUserId = CookieUtil.getHexValue(request, AUTH_COOKIE_NAME);
        long postId = HttpUtil.get(request, "postId", 0L);
        String content = HttpUtil.get(request, "content", "");
        long parentId = HttpUtil.get(request, "parentId", 0L);

        if (postId > 0 && !StringUtil.isEmpty(content)) {

            ShareComment comment = new ShareComment();
            comment.setPostId(postId);
            comment.setUserId(cookieUserId);
            comment.setContent(content);

            if (parentId > 0) {
                // 대댓글: 부모 댓글 정보 조회
                ShareComment parentComment = shareBoardService.commentSelect(parentId);
                if (parentComment != null) {
                    comment.setParentId(parentId);
                    comment.setIndent(parentComment.getIndent() + 1);
                    comment.setOrderNo(parentComment.getOrderNo() + 1);
                } else {
                    ajaxResponse.setResponse(404, "parent comment not found");
                    return ajaxResponse;
                }
            } else {
                // 일반 댓글
                comment.setParentId(0L);
                comment.setIndent(1);
                comment.setOrderNo(1);
            }

            int result = shareBoardService.insertComment(comment);

            if (result > 0) {
                ajaxResponse.setResponse(0, "success");
            } else {
                ajaxResponse.setResponse(500, "fail");
            }
        } else {
            ajaxResponse.setResponse(400, "invalid input");
        }

        return ajaxResponse;
    }

}
