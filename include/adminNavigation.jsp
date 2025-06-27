<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!-- 전체 관리자 영역을 감싸는 wrapper -->
<div class="wrapper">

  <!-- 상단 네비게이션 바 -->
  <header class="navbar bg-black text-white px-3 d-flex justify-content-between align-items-center" style="height: 56px;">
    <div class="fw-bold" style="font-family: 'Playfair Display', serif;">Ring Ring ADMIN</div>
    <div class="d-flex gap-2 align-items-center">
      <c:choose>
        <c:when test="${empty adminId}">
          <a class="btn btn-sm btn-outline-light" href="/admin">로그인</a>
          <a class="btn btn-sm btn-light" href="/board/loginProc">회원가입</a>
        </c:when>
        <c:otherwise>
          <span class="fw-semibold">${adminId}</span>
          <a class="btn btn-sm btn-outline-light" href="/board/logout">로그아웃</a>
        </c:otherwise>
      </c:choose>
    </div>
  </header>

  <!-- 사이드바 + 메인 콘텐츠 영역 -->
  <div class="layout d-flex" style="min-height: calc(100vh - 56px);">
    
    <!-- 사이드바 -->
    <aside class="sidebar bg-black text-white p-3" style="width: 220px;">
      <ul class="nav flex-column">
        <li class="nav-item"><a class="nav-link text-white fw-bold" href="/admin">HOME</a></li>
          <li class="nav-item"><a class="nav-link text-white" href="/noticeboard/list">공지관리</a></li>
          <li class="nav-item"><a class="nav-link text-white" href="/product/productAdminList">상품관리</a></li>
          <li class="nav-item"><a class="nav-link text-white" href="#">사용자관리</a></li>
          <li class="nav-item"><a class="nav-link text-white" href="#">Reports</a></li>
      </ul>
    </aside>

    <!-- 여기서부터 메인 콘텐츠 시작됨 -->
    <main class="flex-fill p-4 bg-white">
