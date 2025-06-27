package com.sist.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sist.common.util.StringUtil;
import com.sist.web.model.Response;
import com.sist.web.model.User3;
import com.sist.web.service.UserService3;
import com.sist.web.util.CookieUtil;
import com.sist.web.util.HttpUtil;
import com.sist.web.util.JsonUtil;

@Controller("userController3")
public class UserController3 	
{
	private static Logger logger = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	private UserService3 userService;
	
	@Value("#{env['auth.cookie.name']}")
	
	private String AUTH_COOKIE_NAME;
	
	@RequestMapping("/home")
	public String homePage(HttpServletRequest request, Model model) {
	    String userId = CookieUtil.getHexValue(request, "USER_ID");
	    User3 user = userService.userSelect(userId);

	    if (user != null) {
	        model.addAttribute("user", user);
	    }

	    return "/home";
	}
	
	
	@RequestMapping(value = "/user/loginPage", method=RequestMethod.GET)
	public String loginPage(HttpServletRequest request, HttpServletResponse response)
	{
		return "/user/loginPage";
	}

	
	@RequestMapping(value = "/user/login3", method = RequestMethod.POST)
	@ResponseBody
	public Response<Object> login(HttpServletRequest request, HttpServletResponse response)
	{
		Response<Object> ajax = new Response<Object>();
		
		String userId = HttpUtil.get(request, "userId");
		String userPwd = HttpUtil.get(request, "userPwd");
		
		if(!StringUtil.isEmpty(userId)&&!StringUtil.isEmpty(userPwd))
		{
			User3 user = userService.userSelect(userId);
			
			if(user != null)
			{
				if(StringUtil.equals(userPwd, user.getUserPwd()))
				{
					if(StringUtil.equals(user.getStatus(), "Y"))
					{
						CookieUtil.addCookie(response, "/", -1, AUTH_COOKIE_NAME, CookieUtil.stringToHex(userId));
						logger.debug("userId : " + userId);
						logger.debug("userId hex : " + CookieUtil.stringToHex(userId));
						ajax.setResponse(1, "success");

					}
					else
					{
						ajax.setResponse(-100, "status error");
					}
				}
				else
				{
					ajax.setResponse(-1, "password mismatch");
				}
			}
			else
			{
				ajax.setResponse(999, "not found");
			}
		}
		else
		{
			ajax.setResponse(400, "Bad Request");
		}
		
		if(logger.isDebugEnabled())
		{
			logger.debug("[Usercontroller]/user/login3 response \n" + JsonUtil.toJsonPretty(ajax));
		}
		
		return ajax;
	}
	//로그아웃 loginOut2
	@RequestMapping(value = "/user/loginOut3" , method = RequestMethod.GET)

	public String loginOut(HttpServletRequest request, HttpServletResponse response)
	{
		if(CookieUtil.getCookie(request, AUTH_COOKIE_NAME) != null)
		{
			CookieUtil.deleteCookie(request, response,"/", AUTH_COOKIE_NAME);
		}
		return "redirect:/home";
	}
	
	//회원가입 화면 regForm2.jsp
	@RequestMapping(value = "/user/regForm3", method = RequestMethod.GET)
	public String regForm(HttpServletRequest request, HttpServletResponse response)
	{
		String cookieUserId = CookieUtil.getHexValue(request, AUTH_COOKIE_NAME);
		logger.debug("cookieUserId = " + cookieUserId);
		if(!StringUtil.isEmpty(cookieUserId))
		{
			CookieUtil.deleteCookie(request, response, "/", AUTH_COOKIE_NAME);
			return "redirect:/home";
		}
		else
		{
			return "/user/regForm3";
		}
	}
	
	//아이디 중복체크 idCheck
	@RequestMapping(value = "/user/idCheck3", method = RequestMethod.POST)
	@ResponseBody
	public Response<Object> idCheck(HttpServletRequest request, HttpServletResponse response)
	{
		Response<Object> ajax = new Response<Object>();
		String userId = HttpUtil.get(request, "userId");
		if(!StringUtil.isEmpty(userId))
		{
			if(userService.userSelect(userId) == null)
			{//중복 아님
				ajax.setResponse(1, "success");
			}
			else
			{//중복 발생
				ajax.setResponse(-1, "same Id");
			}
		}
		else
		{
			ajax.setResponse(999, "can't access");
		}
		
		if(logger.isDebugEnabled())
		{
			logger.debug("[UserController3]/user/idCheck3 response \n" + JsonUtil.toJsonPretty(ajax));
		}
		return ajax;
	}
	
	//회원등록 regProc3
	@RequestMapping(value = "/user/regProc3" , method = RequestMethod.POST)
	@ResponseBody
	public Response<Object> regProc(HttpServletRequest request, HttpServletResponse response)
	{
		Response<Object> ajax = new Response<Object>();
		
		String userId = HttpUtil.get(request, "userId");
		String userPwd = HttpUtil.get(request, "userPwd");
		String userName = HttpUtil.get(request, "userName");
		String userEmail = HttpUtil.get(request, "userEmail");
		
		if(!StringUtil.isEmpty(userId) && !StringUtil.isEmpty(userPwd)&& !StringUtil.isEmpty(userName)&& !StringUtil.isEmpty(userEmail))
		{
			if(userService.userSelect(userId) == null)
			{
				User3 user = new User3();
				user.setUserId(userId);
				user.setUserPwd(userPwd);
				user.setUserName(userName);
				user.setUserEmail(userEmail);
				user.setStatus("Y");
				
				if(userService.userInsert(user) > 0)
				{
					ajax.setResponse(1, "success");
				}
				else
				{
					ajax.setResponse(999, "server error");
				}
			}
			else
			{
				ajax.setResponse(-1, "same Id");
			}
			
		}
		else
		{
			ajax.setResponse(400, "can't access");
		}
		
		if(logger.isDebugEnabled())
		{
			logger.debug("[UserController3]/user/regProc3 response \n" + JsonUtil.toJsonPretty(ajax));
		}
		
		return ajax;

	}
	
	
	
	//회원정보 수정 화면
	@RequestMapping(value = "/user/updateForm3", method = RequestMethod.GET)
	public String updateForm(ModelMap model, HttpServletRequest request, HttpServletResponse response)
	{
		
		String cookieUserId = CookieUtil.getHexValue(request, AUTH_COOKIE_NAME);
		User3 user = userService.userSelect(cookieUserId);
		
		model.addAttribute("user", user);
		
		return "/user/updateForm3";
	}
	
	//화면정보 수정 ajax통신용
	@RequestMapping(value = "/user/updateProc3" , method = RequestMethod.POST)
	@ResponseBody
	public Response<Object> updateProc(HttpServletRequest request, HttpServletResponse response)
	{
		Response<Object> ajax = new Response<Object>();
		

		String userId = HttpUtil.get(request, "userId");
		String userPwd = HttpUtil.get(request, "userPwd");
		String userName = HttpUtil.get(request, "userName");
		String userEmail = HttpUtil.get(request, "userEmail");
		
		String cookieUserId = CookieUtil.getHexValue(request, AUTH_COOKIE_NAME);
		
		if(!StringUtil.isEmpty(cookieUserId))
		{
			if(StringUtil.equals(userId, cookieUserId))
			{
				User3 user = userService.userSelect(cookieUserId);
				
				if(user != null)
				{
					
					if(!StringUtil.isEmpty(userPwd) && !StringUtil.isEmpty(userName) && !StringUtil.isEmpty(userEmail))
					{
						user.setUserPwd(userPwd);
						user.setUserName(userName);
						user.setUserEmail(userEmail);
						
						if(userService.userUpdate(user) > 0)
						{
							ajax.setResponse(1, "success");
						}
						else
						{
							ajax.setResponse(500, "internal server error");

						}
					}
					else
					{
						ajax.setResponse(400, "bad request");

					}

				}
				else
				{
					CookieUtil.deleteCookie(request, response,"/", AUTH_COOKIE_NAME);
					ajax.setResponse(404, "not found");

				}
			}
			else
			{
				CookieUtil.deleteCookie(request, response,"/", AUTH_COOKIE_NAME);
				ajax.setResponse(430, "id information is different");

			}
		}
		else
		{
			ajax.setResponse(410, "failed login");
		}
		
		if(logger.isDebugEnabled())
		{
			logger.debug("[UserController]/user/updateProc3 response \n" + JsonUtil.toJsonPretty(ajax));
			
		}
		
		return ajax;
	}
	
	@RequestMapping(value = "/user/userDelete2", method = RequestMethod.POST)
	@ResponseBody
	public Response<Object> userDelete(HttpServletRequest request, HttpServletResponse response)
	{
		Response<Object> ajax = new Response<Object>();

		String cookieUserId = CookieUtil.getHexValue(request, AUTH_COOKIE_NAME);

		if (!StringUtil.isEmpty(cookieUserId))
		{
			User3 user = userService.userSelect(cookieUserId);

			if (user != null)
			{
				user.setStatus("N");

				if (userService.userDelete(user) > 0)
				{
					// 쿠키 삭제
					CookieUtil.deleteCookie(request, response, "/", AUTH_COOKIE_NAME);

					ajax.setResponse(1, "delete success");
				}
				else
				{
					ajax.setResponse(500, "update failed");
				}
			}
			else
			{
				ajax.setResponse(404, "user not found");
			}
		}
		else
		{
			ajax.setResponse(401, "not logged in");
		}

		if (logger.isDebugEnabled())
		{
			logger.debug("[UserController3]/user/userDelete2 response \n" + JsonUtil.toJsonPretty(ajax));
		}

		return ajax;
	}

	@RequestMapping(value = "/user/myPage", method=RequestMethod.GET)
	public String myPage(HttpServletRequest request, HttpServletResponse response)
	{
		return "/user/myPage";
	}
	
	

}
