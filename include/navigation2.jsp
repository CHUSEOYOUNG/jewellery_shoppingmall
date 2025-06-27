<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file = "/WEB-INF/views/include/taglib.jsp" %>

<%
	if(com.sist.web.util.CookieUtil.getCookie(request, (String)request.getAttribute("AUTH_COOKIE_NAME")) != null)
	{
		
	
%>
<nav class="navbar navbar-expand-sm bg-secondary navbar-dark mb-3"> 
	<ul class="navbar-nav"> 
	    <li class="nav-item"> 
	      <a class="nav-link" href="/user/loginOut2"> 로그아웃</a> 
	    </li> 
	    <li class="nav-item"> 
	      <a class="nav-link" href="/user/updateForm2">회원정보수정</a> 
	    </li> 
	    <li class="nav-item"> 
	      <a class="nav-link" href="/board/list2">게시판</a> 
	    </li> 
  </ul> 
</nav>


<%

	}
	else
	{

%>


<nav class="navbar navbar-expand-sm bg-secondary navbar-dark mb-3"> 
	<ul class="navbar-nav"> 
	    <li class="nav-item"> 
	      <a class="nav-link" href="/index2"> 로그인</a> 
	    </li> 
	    <li class="nav-item"> 
	      <a class="nav-link" href="/user/regForm2">회원가입</a> 
	    </li> 
  </ul> 
</nav>

<%
	}

%>
