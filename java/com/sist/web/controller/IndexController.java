/**
 * <pre>
 * 프로젝트명 : HiBoard
 * 패키지명   : com.icia.web.controller
 * 파일명     : IndexController.java
 * 작성일     : 2021. 1. 21.
 * 작성자     : daekk
 * </pre>
 */
package com.sist.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sist.web.model.TourSpot;
import com.sist.web.service.MarkerService;
import com.sist.web.util.CookieUtil;

/**
 * <pre>
 * 패키지명   : com.icia.web.controller
 * 파일명     : IndexController.java
 * 작성일     : 2021. 1. 21.
 * 작성자     : daekk
 * 설명       : 인덱스 컨트롤러
 * </pre>
 */
@Controller("indexController")
public class IndexController
{
	private static Logger logger = LoggerFactory.getLogger(IndexController.class);

	/**
	 * <pre>
	 * 메소드명   : index
	 * 작성일     : 2021. 1. 21.
	 * 작성자     : daekk
	 * 설명       : 인덱스 페이지 
	 * </pre>
	 * @param request  HttpServletRequest
	 * @param response HttpServletResponse
	 * @return String
	 */
	@RequestMapping(value = "/index", method=RequestMethod.GET)
	public String index(HttpServletRequest request, HttpServletResponse response)
	{
		return "/index";
	}
	
	@RequestMapping(value = "/index2", method=RequestMethod.GET)
	public String index2(HttpServletRequest request, HttpServletResponse response)
	{
		return "/index2";
	}
	@RequestMapping(value = "/index4", method=RequestMethod.GET)
	public String index4(HttpServletRequest request, HttpServletResponse response)
	{
		return "/index4";
	}

	@RequestMapping(value = "/home", method=RequestMethod.GET)
	public String home(HttpServletRequest request, HttpServletResponse response)
	{
		return "/home";
	}
	
	@RequestMapping("/admin")
	public String admin(HttpServletRequest request, Model model) {
	    String adminId = CookieUtil.getValue(request, "S_ADMIN_ID");
	    model.addAttribute("adminId", adminId);
	    return "/admin"; // → /WEB-INF/views/admin/home.jsp
	}


	
	@GetMapping("/view")
	public String weatherViewPage() {
	    return "/weather/weather";  // /WEB-INF/views/weather/weather.jsp
	}
	
	@RequestMapping(value = "/index3", method=RequestMethod.GET)
	public String index3(HttpServletRequest request, HttpServletResponse response)
	{
		return "/index3";
	}
}
