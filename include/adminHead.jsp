<!DOCTYPE html>
<html lang="ko" data-bs-theme="auto">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <title><c:out value="${pageTitle != null ? pageTitle : 'Ring Ring'}"/> · Ring Ring</title>

    <!-- ✅ Bootstrap CSS -->
    <link
      href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.6/dist/css/bootstrap.min.css"
      rel="stylesheet"
      integrity="sha384-4Q6Gf2aSP4eDXB8Miphtr37CMZZQ5oXLH2yaXMJ2w8e2ZtHTl7GptT4jmndRuHDT"
      crossorigin="anonymous"
    />

    <!-- ✅ 차분한 흑백 테마 -->
    <style>

  /* 전체 본문 글꼴 */
  body,
  .navbar-nav .nav-link,
  #sidebarMenu .nav-link {
    font-family: 'Noto Sans KR', sans-serif;
  }

  /* 로고(브랜드) 글꼴 */
  .navbar-brand {
    font-family: 'Playfair Display', serif;
    font-size: 1.5rem; /* 필요에 따라 조절 */
  }
      a { text-decoration: none; }
      /* 헤더 */
      header.navbar {
        background-color: #000;
      }
      header .navbar-brand,
      header .nav-link {
        color: #fff;
      }
      header .nav-link:hover {
        color: #ddd;
      }
      /* 사이드바 */
      .sidebar {
        background-color: #000;
        height: 100vh;
        padding-top: 1rem;
      }
      .sidebar .nav-link {
        color: #fff;
      }
      .sidebar .nav-link:hover {
        background-color: #333;
        color: #fff;
      }
      .sidebar .nav-link.active {
        background-color: #444;
        font-weight: 600;
        color: #fff;
      }
      /* 본문 */
      main {
        padding: 2rem;
      }
      /* 버튼 */
      .btn-outline-secondary {
        color: #000;
        border-color: #000;
      }
      .btn-outline-secondary:hover {
        background-color: #000;
        color: #fff;
      }
      /* 테이블 헤더 */
      .table thead {
        background-color: #000;
      }
      .table thead th {
        color: #fff;
      }
    </style>
  </head>
  <body>
