<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>

<!doctype html>
<html lang="ko">
  <head>
    <%@ include file="/WEB-INF/views/include/adminHead.jsp" %>

    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>로그인</title>

    <!-- Bootstrap & Fonts -->
    <link
      href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.6/dist/css/bootstrap.min.css"
      rel="stylesheet"
    >
    <link
      href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR&display=swap"
      rel="stylesheet"
    >

    <style>
      html, body {
        height: 100%;
        margin: 0;
        font-family: 'Noto Sans KR', sans-serif;
      }
      body {
        display: flex;
        flex-direction: column;
      }
      .content {
        flex: 1;
        display: flex;
        align-items: center;
        justify-content: center;
      }
      .login-box {
        width: 100%;
        max-width: 400px;
        padding: 40px;
        background-color: #f9f9f9;
        border-radius: 12px;
        box-shadow: 0 0 20px rgba(0,0,0,0.1);
      }
      .form-control {
        border-radius: 8px;
      }
      .form-control:focus {
        border-color: #000;
        box-shadow: 0 0 0 0.2rem rgba(0,0,0,0.1);
      }
      .btn-black {
        background-color: #000;
        color: #fff;
        border: none;
      }
      .btn-black:hover {
        background-color: #222;
      }
      a {
        color: #000;
      }
      a:hover {
        text-decoration: underline;
      }
      .text-muted {
        color: #666 !important;
      }
    </style>
  </head>

  <body>
    <%@ include file="/WEB-INF/views/include/adminNavigation.jsp" %>

    <div class="content">
      <div class="login-box">
        <div class="text-center mb-4">
          <a href="/admin.jsp" class="text-decoration-none text-dark fs-2 fw-bold">어드민 로그인</a>
        </div>
        <form name="loginForm" method="post">
          <div class="mb-3">
            <label for="adminId" class="form-label">아이디</label>
            <input
              type="text"
              id="adminId"
              name="adminId"
              class="form-control"
              placeholder="아이디 입력"
              required
            >
          </div>
          <div class="mb-3">
            <label for="adminPwd" class="form-label">비밀번호</label>
            <input
              type="password"
              id="adminPwd"
              name="adminPwd"
              class="form-control"
              placeholder="비밀번호 입력"
              required
            >
          </div>
          <div class="d-grid mb-3">
            <button type="button" class="btn btn-black" id="btnLogin">로그인</button>
          </div>
          <div class="text-center">
            <span class="text-muted">계정이 없으신가요? </span>
            <a href="/board/adminRegForm.jsp" class="fw-semibold">회원가입</a>
          </div>
        </form>
      </div>
    </div>

    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script>
      $(function(){
        $("#adminId").focus();

        $("#btnLogin").click(function(){
          const adminId = $.trim($("#adminId").val());
          const adminPwd = $.trim($("#adminPwd").val());

          if (!adminId) {
            alert("아이디를 입력하세요.");
            $("#adminId").focus();
            return;
          }

          if (!adminPwd) {
            alert("비밀번호를 입력하세요.");
            $("#adminPwd").focus();
            return;
          }

          $.ajax({
            type: "POST",
            url: "/board/loginProc",
            dataType: "json",
            data: {
              adminId: adminId,
              adminPwd: adminPwd
            },
            success: function(response) {
              if (response.code === 1) {
                alert("로그인 성공!");
                location.href = "/admin";  // 로그인 후 이동할 경로
              } else if (response.code === -1) {
                alert("아이디 또는 비밀번호가 올바르지 않습니다.");
              } else {
                alert("잘못된 요청입니다.");
              }
            },
            error: function() {
              alert("서버 오류가 발생했습니다.");
            }
          });
        });
      });
    </script>
    <script
      src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.6/dist/js/bootstrap.bundle.min.js"
    ></script>
  </body>
</html>
