<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ page import="com.sist.common.util.StringUtil" %>
<%@ page import="com.sist.web.util.CookieUtil" %>

<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>Navigation · Ring Ring</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.6/dist/css/bootstrap.min.css" rel="stylesheet">
  <link href="https://fonts.googleapis.com/css2?family=Playfair+Display:wght@700&family=Noto+Sans+KR&display=swap" rel="stylesheet">
  <style>
    body {
      font-family: 'Noto Sans KR', sans-serif;
    }
    .shop-logo {
      font-family: 'Playfair Display', serif;
      font-size: 28px;
      font-weight: 700;
      color: #111;
      text-decoration: none;
    }
    .shop-logo:hover {
      text-decoration: underline;
    }
    .btn-cart {
      background-color: #000;
      color: #fff;
      border-radius: 6px;
      padding: 6px 12px;
      display: inline-flex;
      align-items: center;
      font-size: 14px;
      text-decoration: none;
    }
    .btn-cart:hover {
      background-color: #333;
      color: #fff;
    }
    .btn-cart svg {
      margin-right: 6px;
    }
    .user-icon {
      display: inline-flex;
      align-items: center;
      font-weight: 500;
      font-size: 14px;
      color: #111;
    }
    .user-icon svg {
      width: 18px;
      height: 18px;
      margin-right: 4px;
      fill: #111;
    }
  </style>
</head>
<body>

<%
    String navUserId = CookieUtil.getValue(request, "USER_ID");
%>
<div class="container">
  <header class="border-bottom lh-1 py-3">
    <div class="row flex-nowrap justify-content-between align-items-center">
      <div class="col-4 pt-1">
        <a class="btn btn-sm btn-outline-secondary d-inline-flex align-items-center" href="/product/cart">
  <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" viewBox="0 0 16 16" style="margin-right:6px;">
    <path d="M0 1a1 1 0 0 1 1-1h1.22a1 1 0 0 1 .97.757L3.89 3H14a1 1 0 0 1 .97 1.243l-1.5 6A1 1 0 0 1 12.5 11H4.12a1 1 0 0 1-.97-.757L1.61 2H1a1 1 0 0 1-1-1zm5 12a1 1 0 1 0 0 2 1 1 0 0 0 0-2zm6.5 1a1 1 0 1 1-2 0 1 1 0 0 1 2 0z"/>
  </svg>
  장바구니
</a>
      </div>
      <div class="col-4 text-center">
        <a class="shop-logo" href="/home">Ring Ring</a>
      </div>
      <div class="col-4 d-flex justify-content-end align-items-center">
        <%
          if (StringUtil.isEmpty(navUserId)) {
        %>
          <a class="btn btn-sm btn-outline-secondary me-2" href="/user/loginPage">로그인</a>
          <a class="btn btn-sm btn-dark" href="/user/regForm3">회원가입</a>
        <%
          } else {
        %>
          <span class="user-icon me-3">
            <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24">
              <path d="M12 12c2.7 0 5-2.3 5-5s-2.3-5-5-5-5 2.3-5 5 2.3 5 5 5zm0 2c-3.3 0-10 1.7-10 5v3h20v-3c0-3.3-6.7-5-10-5z"/>
            </svg>
            ${user.userId}
          </span>
          <a class="btn btn-sm btn-outline-secondary me-2" href="/user/loginOut3">로그아웃</a>
        <%
          }
        %>
      </div>
    </div>
  </header>
<%
    String curCategory = request.getParameter("categoryId");
    if(curCategory == null) curCategory = "";
%>

  <div class="nav-scroller py-1 mb-3 border-bottom">
    <nav class="nav nav-underline justify-content-between">
      <a class="nav-item nav-link link-body-emphasis active" href="/home">HOME</a>
      <a class="nav-item nav-link link-body-emphasis" href="/board/noticelist">NOTICE</a>
<a class="nav-item nav-link link-body-emphasis <%= (StringUtil.isEmpty(curCategory) ? "active" : "") %>" href="/product/list">ACCESSARY</a>

<a class="nav-item nav-link link-body-emphasis <%= ("3".equals(curCategory) ? "active" : "") %>" href="/product/list?categoryId=3">EARRING</a>

<a class="nav-item nav-link link-body-emphasis <%= ("1".equals(curCategory) ? "active" : "") %>" href="/product/list?categoryId=1">RING</a>

<a class="nav-item nav-link link-body-emphasis <%= ("2".equals(curCategory) ? "active" : "") %>" href="/product/list?categoryId=2">NECKLACE</a>

      <a class="nav-item nav-link link-body-emphasis" href="/board/list2">COMMUNITY</a>
      <a class="nav-item nav-link link-body-emphasis" href="/user/myPage">MYPAGE</a>
    </nav>
  </div>
</div>

</body>
</html>
