//package com.sist.web.controller;
//
//
//
//import java.io.File;
//import java.util.ArrayList;
//import java.util.List;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.ModelMap;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.web.multipart.MultipartFile;
//import org.springframework.web.multipart.MultipartHttpServletRequest;
//import org.springframework.web.servlet.ModelAndView;
//
//import com.sist.common.model.FileData;
//import com.sist.common.util.FileUtil;
//import com.sist.common.util.StringUtil;
//import com.sist.web.model.HiBoard;
//import com.sist.web.model.HiBoard2;
//import com.sist.web.model.HiBoardFile2;
//import com.sist.web.model.Paging;
//import com.sist.web.model.Product;
//import com.sist.web.model.Response;
//import com.sist.web.model.User3;
//import com.sist.web.service.HiBoardService2;
//import com.sist.web.service.UserService3;
//import com.sist.web.util.CookieUtil;
//import com.sist.web.util.HttpUtil;
//
//@Controller("hiBoardController2")
//public class HiBoardController2 
//{
//   private static Logger logger = LoggerFactory.getLogger(HiBoardController2.class);
//   
//   @Value("#{env['auth.cookie.name']}")
//   private String AUTH_COOKIE_NAME;
//   
//   @Value("#{env['upload.save.dir']}")
//   private String UPLOAD_SAVE_DIR;
//   
//   @Value("#{env['upload.save.dir.detail']}")
//   private String UPLOAD_SAVE_DIR_DETAIL;
//   
//   @Value("#{env['upload.save.dir.style']}")
//   private String UPLOAD_SAVE_DIR_STYLE;
//   
//	@Value("#{env['upload.save.dir.styleDetail']}")
//	private String UPLOAD_SAVE_DIR_STYLE_DETAIL;
//   
//   
//   @Autowired
//   private HiBoardService2 hiBoardService;
//   
//   @Autowired
//   private UserService3 userService;
//   
//   private static final int LIST_COUNT = 10;    //한페이지 게시물 수
//   private static final int PAGE_COUNT = 2;     //페이징 수 
//   
//   
//   //게시판 리스트 페이지
//   @RequestMapping(value = "/board/list2")
//   public String list2(ModelMap model, HttpServletRequest request, HttpServletResponse response)
//   {
//      //조회항목(1: 작성자, 2: 제목, 3: 내용)
//      String searchType = HttpUtil.get(request, "searchType", "");
//      //조회값
//      String searchValue = HttpUtil.get(request, "searchValue", "");
//      //현재페이지
//      long curPage = HttpUtil.get(request, "curPage", (long)1);
//      //게시물 리스트
//      List<HiBoard2> list = null;
//      
//      HiBoard2 search = new HiBoard2();
//      
//      //총 게시물수
//      long totalCount = 0;
//      
//      //페이징 객체
//      Paging paging = null;
//      
//      if(!StringUtil.isEmpty(searchType) && !StringUtil.isEmpty(searchValue))
//      {
//         search.setSearchType(searchType);
//         search.setSearchValue(searchValue);
//      }
//      else
//      {
//         searchType = "";
//         searchValue = "";
//      }
//      totalCount = hiBoardService.boardListCount(search);
//      
//      logger.debug("======================");
//      logger.debug("totalCount = " + totalCount);
//      logger.debug("======================");
//      
//      if(totalCount > 0)
//      {
//         
//         paging = new Paging("/board/list2", totalCount, LIST_COUNT, PAGE_COUNT, curPage, "curPage");
//         search.setStartRow(paging.getStartRow());
//         search.setEndRow(paging.getEndRow());
//         
//         list = hiBoardService.boardList(search);
//      }
//
//      model.addAttribute("list", list);
//      model.addAttribute("searchType", searchType);
//      model.addAttribute("searchValue", searchValue);
//      model.addAttribute("curPage", curPage);
//      model.addAttribute("paging",paging);
//      
//      return "/board/list2";
//   }
//   
//   
//   // 게시글 상세 보기
//   @RequestMapping(value="/board/view2")
//   public String view2(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
//
//       long hibbsSeq = HttpUtil.get(request, "hibbsSeq", (long)0);
//       HiBoard2 board = null;
//       List<HiBoard2> commentList = null;
//
//       if (hibbsSeq > 0) {
//           board = hiBoardService.boardView(hibbsSeq);
//           
//           // ★ 첨부파일 정보 추가로 조회
//           HiBoardFile2 hiBoardFile = hiBoardService.boardFileSelect(hibbsSeq);
//           board.setHiBoardFile(hiBoardFile);   // <-- 이게 핵심 추가
//           if (board != null) {
//               long hibbsGroup = board.getHibbsGroup();  // 댓글 그룹 기준
//               commentList = hiBoardService.commentListByGroup(hibbsGroup);
//           }
//       }
//
//       model.addAttribute("board", board);
//       model.addAttribute("commentList", commentList);
//       model.addAttribute("loginUserId", CookieUtil.getValue(request, "USER_ID"));
//
//       return "/board/view2";
//   }
//    // 등록 폼 이동
//    @RequestMapping(value = "/board/writeForm2")
//    public String writeForm2(ModelMap model, HttpServletRequest request, HttpServletResponse response)
//    {
//        String cookieUserId = CookieUtil.getHexValue(request, AUTH_COOKIE_NAME);
//        String searchType = HttpUtil.get(request, "searchType", "");
//        String searchValue = HttpUtil.get(request, "searchValue", "");
//        long curPage = HttpUtil.get(request, "curPage", 1L);
//
//        User3 user = userService.userSelect(cookieUserId);
//
//        model.addAttribute("user", user);
//        model.addAttribute("searchType", searchType);
//        model.addAttribute("searchValue", searchValue);
//        model.addAttribute("curPage", curPage);
//
//        return "/board/writeForm2";
//    }
//
//    // 게시물 등록 처리
//    @RequestMapping(value = "/board/writeProc2", method = RequestMethod.POST)
//    @ResponseBody
//    public Response<Object> writeProc2(MultipartHttpServletRequest request, HttpServletResponse response)
//    {
//        Response<Object> ajaxResponse = new Response<Object>();
//
//        String cookieUserId = CookieUtil.getHexValue(request, AUTH_COOKIE_NAME);
//        String title = HttpUtil.get(request, "title", "");
//        String content = HttpUtil.get(request, "content", "");
//        
//        HiBoard2 hiBoard = new HiBoard2();
//
//        String realfile = hiBoard.getHibbsSeq()+"";
//        
//        FileData fileData = HttpUtil.getFile(request, "hiBbsFile", UPLOAD_SAVE_DIR_STYLE, realfile);
//
//        if (!StringUtil.isEmpty(title) && !StringUtil.isEmpty(content)) 
//        {
//            hiBoard.setUserId(cookieUserId);
//            hiBoard.setHibbsTitle(title);
//            hiBoard.setHibbsContent(content);
//
//            if (fileData != null && fileData.getFileSize() > 0) 
//            {
//                HiBoardFile2 hiBoardFile = new HiBoardFile2();
//                hiBoardFile.setFileName(fileData.getFileName());
//                hiBoardFile.setFileOrgName(fileData.getFileOrgName());
//                hiBoardFile.setFileExt(fileData.getFileExt());
//                hiBoardFile.setFileSize(fileData.getFileSize());
//                hiBoard.setHiBoardFile(hiBoardFile);
//            }
//
//            try 
//            {
//                if (hiBoardService.boardInsert(hiBoard) > 0) 
//                {
//
//                    ajaxResponse.setResponse(0, "success");
//                } 
//                else 
//                {
//                    ajaxResponse.setResponse(500, "internal server error");
//                }
//            } 
//            catch (Exception e) 
//            {
//                logger.error("[HiBoardController2]writeProc2 Exception", e);
//                ajaxResponse.setResponse(500, "internal server error2");
//            }
//        } 
//        else 
//        {
//            ajaxResponse.setResponse(400, "bad request");
//        }
//
//        return ajaxResponse;
//    }
//
//   //첨부파일 다운로드 
//   @RequestMapping(value = "/board/download2")
//   public ModelAndView download(HttpServletRequest request, HttpServletResponse response)
//   {
//      ModelAndView modelAndView = null;
//      
//      long hibbsSeq = HttpUtil.get(request, "hibbsSeq", (long)0);
//      
//      if(hibbsSeq > 0)
//      {
//         
//         HiBoardFile2 hiBoardFile = hiBoardService.boardFileSelect(hibbsSeq);
//         if(hiBoardFile != null)
//         {
//            File file = new File(UPLOAD_SAVE_DIR_STYLE + FileUtil.getFileSeparator() + hiBoardFile.getFileName());
//            logger.debug("=================================");
//            logger.debug("UPLOAD_SAVE_DIR_style" + UPLOAD_SAVE_DIR_STYLE);
//            logger.debug("FileUtil.getFileSeparator()" + FileUtil.getFileSeparator());
//            logger.debug("hiBoardFile.getFileName()" + hiBoardFile.getFileName());
//            logger.debug("hiBoardFile.getFileOrgName()" + hiBoardFile.getFileOrgName());
//            logger.debug("=================================");
//            
//            if(FileUtil.isFile(file))
//            {
//               modelAndView = new ModelAndView();
//               
//               //응답할 view설정(servlet-context.xml에 정의한 FileDownloadView)
//               modelAndView.setViewName("fileDownloadView");
//               modelAndView.addObject("file", file);
//               modelAndView.addObject("fileName", hiBoardFile.getFileOrgName());
//               
//               
//               return modelAndView;
//               
//            }
//            
//            
//         }
//      }
//      
//      return modelAndView;
//   }
//
//   
//   @RequestMapping(value = "/board/writeProcMulti", method = RequestMethod.POST)
//   @ResponseBody
//   public Response<Object> writeProcMulti(MultipartHttpServletRequest request, HttpServletResponse response)
//   {
//       Response<Object> ajaxResponse = new Response<Object>();
//
//       String cookieUserId = CookieUtil.getHexValue(request, AUTH_COOKIE_NAME);
//       String title = HttpUtil.get(request, "title", "");
//       String content = HttpUtil.get(request, "content", "");
//
//       if (!StringUtil.isEmpty(title) && !StringUtil.isEmpty(content)) 
//       {
//           HiBoard2 hiBoard = new HiBoard2();
//           hiBoard.setUserId(cookieUserId);
//           hiBoard.setHibbsTitle(title);
//           hiBoard.setHibbsContent(content);
//
//           try 
//           {
//               int result = hiBoardService.boardInsert(hiBoard);  // ✅ 먼저 게시물 insert
//               long hibbsSeq = hiBoard.getHibbsSeq();             // ✅ insert 후에 시퀀스 꺼냄
//
//               if (result > 0) 
//               {
//                   // 이제 hibbsSeq를 확보했으므로 파일처리 가능
//                   List<FileData> fileDataList = HttpUtil.getFiles(request, "hiBbsFiles", UPLOAD_SAVE_DIR_STYLE_DETAIL);
//                   List<HiBoardFile2> fileList = new ArrayList<>();
//                   int seq = 1;
//
//                   for (FileData fileData : fileDataList) {
//                       HiBoardFile2 hiBoardFile = new HiBoardFile2();
//                       hiBoardFile.setHibbsSeq(hibbsSeq);  // 🔑 이제 세팅 가능
//                       hiBoardFile.setFileSeq((short)seq++);
//                       hiBoardFile.setFileOrgName(fileData.getFileOrgName());
//                       hiBoardFile.setFileName(fileData.getFileName());
//                       hiBoardFile.setFileExt(fileData.getFileExt());
//                       hiBoardFile.setFileSize(fileData.getFileSize());
//                       fileList.add(hiBoardFile);
//                   }
//
//                   if (!fileList.isEmpty()) {
//                       hiBoardService.boardFileMultiInsert(fileList);
//                   }
//
//                   ajaxResponse.setResponse(0, "success");
//               } 
//               else 
//               {
//                   ajaxResponse.setResponse(500, "internal server error");
//               }
//           } 
//           catch (Exception e) 
//           {
//               logger.error("[HiBoardController2]writeProcMulti Exception", e);
//               ajaxResponse.setResponse(500, "internal server error");
//           }
//       } 
//       else 
//       {
//           ajaxResponse.setResponse(400, "bad request");
//       }
//
//       return ajaxResponse;
//   }
// //게시물 삭제
// 	@RequestMapping(value = "/board/delete2", method = RequestMethod.POST)
// 	@ResponseBody
// 	public Response<Object> delete(HttpServletRequest request, HttpServletResponse response)
// 	{
// 		Response<Object> ajaxResponse = new Response<Object>();
// 		
// 		long hibbsSeq = HttpUtil.get(request, "hibbsSeq", (long)0);
// 		
// 		String cookieUserId = CookieUtil.getHexValue(request, AUTH_COOKIE_NAME);
// 		
// 		if(hibbsSeq > 0)
// 		{
// 			HiBoard2 hiBoard = hiBoardService.boardSelect(hibbsSeq);
// 			
// 			if(hiBoard != null)
// 			{
// 				if(StringUtil.equals(cookieUserId, hiBoard.getUserId()))
// 				{
// 					//답글 존재 확인
// 					if(hiBoardService.boardAnswersCount(hibbsSeq) > 0)
// 					{
// 						//답글이 있을경우 삭제 불가능하도록 처리
// 						ajaxResponse.setResponse(-999, "answer exist and cannot be deleted");
// 					}
// 					else
// 					{
// 						try
// 						{
// 							if(hiBoardService.boardDelete(hibbsSeq) > 0)
// 							{
// 								ajaxResponse.setResponse(0, "success");
// 							}
// 							else
// 							{
// 								ajaxResponse.setResponse(500, "server error1");
// 							}
// 						}
// 						catch(Exception e)
// 						{
// 							logger.error("[HiBoardController]delete Exception" ,e );
// 							ajaxResponse.setResponse(500, "server error2");
// 						}
// 					}
// 				}
// 				else
// 				{ 
// 					//내 글이 아닐 경우
// 					ajaxResponse.setResponse(403, "server error");
// 				}
// 			}
// 			else
// 			{
// 				ajaxResponse.setResponse(404, "not found");
// 			}
// 		}
// 		else
// 		{
// 			ajaxResponse.setResponse(400, "bad request");
// 		}
// 		return ajaxResponse;
// 	}
// 	
//
//}
