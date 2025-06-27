<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="ko" data-bs-theme="auto">
<%@ include file="/WEB-INF/views/include/head3.jsp" %>

<head>
  <meta charset="UTF-8" />
  <title>Ring Ring · 홈</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.6/dist/css/bootstrap.min.css" rel="stylesheet">
  <link href="https://fonts.googleapis.com/css2?family=Playfair+Display&family=Noto+Sans+KR&display=swap" rel="stylesheet">
  <style>
    body {
      font-family: 'Noto Sans KR', 'Playfair Display', serif;
    }

    .main-banner {
      background-color: #000;
      color: #fff;
      min-height: 480px;
    }

    .main-banner .text-area h1 {
      font-family: 'Playfair Display', serif;
    }

    .main-banner .text-area p {
      font-family: 'Noto Sans KR', sans-serif;
    }

    .main-banner .text-area p span {
      font-weight: bold;
    }

    @media (max-width: 768px) {
      .main-banner .container {
        flex-direction: column !important;
        text-align: center;
      }

      .main-banner .text-area {
        padding-right: 0 !important;
      }
    }

    .product-card:hover {
      transform: scale(1.03);
      transition: transform 0.3s ease;
    }

    .btn-view-all {
      margin-top: 48px;
      padding: 12px 28px;
      font-size: 16px;
    }
  </style>
</head>

<body>
<%@ include file="/WEB-INF/views/include/navigation3.jsp" %>

<main>

  <!-- ✅ 메인 배너 -->
  <section class="main-banner d-flex align-items-center text-white">
    <div class="container d-flex flex-column flex-md-row align-items-center py-5">
      <div class="text-area text-md-start text-center pe-md-5">
        <p class="small mb-2">큐레이션</p>
        <h1 class="display-5 fw-bold mb-3">BEST 14K HEART ITEM</h1>
        <p class="lead">최대 <span>48%</span> 할인 + <span>15%</span> 쿠폰</p>
      </div>
      <div class="image-area mt-4 mt-md-0">
        <img src="${pageContext.request.contextPath}/upload/small/14.jpeg" alt="14k 목걸이" class="img-fluid rounded shadow" style="max-height: 400px;">
      </div>
    </div>
  </section>

  <!-- ✅ 인기 상품 카드 -->
  <div class="container mt-5">
    <div class="row g-4">

        <div class="col-md-4">
          <div class="card product-card h-100">
            <img src="/producImage/necklace1.jpeg" class="card-img-top" alt="목걸이">
            <div class="card-body">
              <h5 class="card-title">${p2.productName}</h5>
              <br /><br />
              <p class="card-text">심플한 디자인과 고급스러운 광택이 돋보이는 목걸이.</p>
              <a href="/product/productView.jsp?productId=${p2.productId}" class="btn btn-outline-dark">자세히 보기</a>
            </div>
          </div>
        </div>

        <div class="col-md-4">
          <div class="card product-card h-100">
            <img src="/producImage/earing1.jpeg" class="card-img-top" alt="귀걸이">
            <div class="card-body">
              <h5 class="card-title">${p3.productName}</h5>
              <br /><br />
              <p class="card-text">우아한 무드를 더해주는 진주 포인트 귀걸이.</p>
              <a href="/product/productView.jsp?productId=${p3.productId}" class="btn btn-outline-dark">자세히 보기</a>
            </div>
          </div>
        </div>

        <div class="col-md-4">
          <div class="card product-card h-100">
            <img src="/producImage/ring1.jpeg" class="card-img-top" alt="반지">
            <div class="card-body">
              <h5 class="card-title">${p1.productName}</h5>
              <br /><br />
              <p class="card-text">깔끔하고 트렌디한 디자인의 데일리 반지.</p>
              <a href="/product/productView.jsp?productId=${p1.productId}" class="btn btn-outline-dark">자세히 보기</a>
            </div>
          </div>
        </div>

    </div>

    <!-- ✅ 전체 상품 보기 -->
    <div class="text-center">
      <a href="/product/productList.jsp" class="btn btn-dark btn-view-all">전체 상품 보기 →</a>
    </div>
  </div>

</main>

<footer class="py-5 text-center text-muted bg-body-tertiary mt-5">
  <p>Mini Mall ⓒ 2025. Built with <a href="https://getbootstrap.com/">Bootstrap</a>.</p>
</footer>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.6/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
