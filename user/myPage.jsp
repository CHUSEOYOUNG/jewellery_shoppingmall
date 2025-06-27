<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
  <%@ include file="/WEB-INF/views/include/head3.jsp" %>
  <title>마이페이지 · Ring Ring</title>
  <style>
    body {
      font-family: 'Noto Sans KR', sans-serif;
      background-color: #fff;
      color: #111;
      margin: 0;
      padding: 0;
    }

    .mypage-wrapper {
      max-width: 1000px;
      margin: 60px auto;
      padding: 20px;
    }

    .mypage-header {
      display: flex;
      align-items: center;
      justify-content: space-between;
      border-bottom: 1px solid #eee;
      padding-bottom: 20px;
    }

    .user-info {
      font-size: 18px;
      font-weight: 600;
    }

    .grade {
      font-size: 14px;
      color: #666;
      margin-top: 6px;
    }

    .btn-edit {
      border: 1px solid #000;
      background: transparent;
      color: #000;
      padding: 6px 12px;
      border-radius: 4px;
      font-size: 14px;
    }

    .btn-edit:hover {
      background-color: #000;
      color: #fff;
    }

    .mypage-summary {
      display: flex;
      justify-content: space-around;
      text-align: center;
      margin: 40px 0;
    }

    .summary-box {
      flex: 1;
      border-right: 1px solid #eee;
    }

    .summary-box:last-child {
      border-right: none;
    }

    .summary-box .value {
      font-size: 20px;
      font-weight: bold;
    }

    .summary-box .label {
      font-size: 14px;
      color: #666;
    }

    .section {
      margin-top: 50px;
    }

    .section h3 {
      font-family: 'Playfair Display', serif;
      font-size: 22px;
      margin-bottom: 16px;
      border-bottom: 1px solid #000;
      padding-bottom: 6px;
    }

    .link-list a {
      display: block;
      padding: 10px 0;
      color: #000;
      text-decoration: none;
      border-bottom: 1px solid #eee;
      transition: all 0.2s;
    }

    .link-list a:hover {
      color: #888;
    }
  </style>
</head>
<body>
<%@ include file="/WEB-INF/views/include/navigation3.jsp" %>

<div class="mypage-wrapper">
  <!-- 유저 정보 -->
  <div class="mypage-header">
    <div>
      <div class="user-info">
  <c:choose>
    <c:when test="${not empty loginUser}">
      ${loginUser.userName} 님
    </c:when>
    <c:otherwise>
      <c:out value="${sessionScope.S_USER.userName}" default="회원" /> 님
    </c:otherwise>
  </c:choose>
</div>
      <div class="grade">LV.${loginUser.grade} · ${loginUser.gradeName}</div>
    </div>
    <a href="/user/updateForm3" class="btn-edit">회원정보 수정</a>
  </div>

  <!-- 요약 정보 -->
  <div class="mypage-summary">
    <div class="summary-box">
      <div class="value">${point}원</div>
      <div class="label">적립금</div>
    </div>
    <div class="summary-box">
      <div class="value">${couponCount}장</div>
      <div class="label">쿠폰</div>
    </div>
    <div class="summary-box">
      <div class="value">${reviewCount}개</div>
      <div class="label">작성한 후기</div>
    </div>
  </div>

  <!-- 주요 기능 링크 -->
  <div class="section">
    <h3>내 활동</h3>
    <div class="link-list">
      <a href="/order/orderList">주문 내역</a>
      <a href="/order/refundList">취소/환불 내역</a>
      <a href="/order/reviewList">상품 후기</a>
      <a href="/product/wishlist">관심 상품</a>
    </div>
  </div>
</div>

</body>
</html>
