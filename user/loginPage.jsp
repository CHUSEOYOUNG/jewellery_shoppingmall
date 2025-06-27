<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!doctype html>
<html lang="ko">
  <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
  <head>
    <%@ include file="/WEB-INF/views/include/head3.jsp" %>

    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>회원 로그인</title>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.6/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR&display=swap" rel="stylesheet">

    <style>
      body {
        background-color: #fff;
        color: #000;
        font-family: 'Noto Sans KR', sans-serif;
      }

      .login-container {
        max-width: 400px;
        margin: 80px auto;
        padding: 40px;
        background-color: #f9f9f9;
        border-radius: 12px;
        box-shadow: 0 0 20px rgba(0, 0, 0, 0.1);
      }

      .form-control {
        border-radius: 8px;
        background-color: #fff;
        color: #000;
      }

      .form-control:focus {
        border-color: #000;
        box-shadow: 0 0 0 0.2rem rgba(0, 0, 0, 0.1);
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
        font-size: 14px;
        color: #666 !important;
      }
    </style>

 
<script>
$(document).ready(function(){
	$("#userId").focus();
	$("#userId").on("keypress", function(e){
		if(e.which == 13)
		{
			fn_loginCheck();
		}
	});
	
	$("#userPwd").on("keypress", function(e){
		if(e.which == 13)
		{
			fn_loginCheck();
		}
	});
	
	$("#btnLogin").on("click", function(){
		fn_loginCheck();
	});
	
	$("#btnReg").on("click", function(){
		location.href = "/user/regForm3";	
	});
});

function fn_loginCheck()
{
	if($.trim($("#userId").val()).length <= 0)
	{
		alert("아이디를 입력하세요.");
		$("#userId").val("");
		$("#userId").focus();
		return;
	}
	
	if($.trim($("#userPwd").val()).length <= 0)
	{
		alert("비밀번호를 입력하세요");
		$("#userPwd").val("");
		$("#userPwd").focus();
		return;
	}
	
	$.ajax({
		type:"POST",
		url:"/user/login3",
		data:{
			userId:$("#userId").val(),
			userPwd:$("#userPwd").val()
		},
		dataType:"JSON",
		beforeSend:function(xhr){
			xhr.setRequestHeader("AJAX", "true");
		},
		success:function(response){
			if(!icia.common.isEmpty(response))
			{
				icia.common.log(response);
				
				var code = icia.common.objectValue(response, "code", -500);
				
				if(code == 1)
				{
					alert("로그인 성공");
					location.href = "/home";
				}
				else
				{
					if(code == -1)
					{
						alert("비밀번호가 올바르지 않습니다.");
						$("#userPwd").focus();
						
					}
					else if(code == -100)
					{
						alert("비활성화된 사용자 입니다.");
						$("#userId").focus();
					}
					else if(code == 999)
					{
						alert("사용자 정보가 없습니다.");
						$("#userId").focus();
					}
					else if(code == 400)
					{
						alert("값이 올바르지 않습니다.");
						$("#userId").focus();
					}
					else
					{
						alert("오류가 발생했습니다.");	
						$("#userId").focus();
					}
				}
			}
			else
			{
				alert("로그인 할 수 없습니다.");	
				$("#userId").focus();
			}
		},
		complete:function(data)
		{
			icia.common.log(data);
		},
		error:function(xhr, status, error)
		{
			icia.common.error(error);
		}
	});
}
</script>
  </head>

  <body>
    <%@ include file="/WEB-INF/views/include/navigation3.jsp" %>

    <div class="container">
      <div class="text-center mt-4">
        <a href="/home" class="text-decoration-none text-dark fs-2 fw-bold">Ring Ring</a>
        <p class="text-muted">로그인 후 다양한 혜택을 만나보세요</p>
      </div>

      <div class="login-container">
        <form name="loginForm" action="/user/login" method="post">
          <div class="mb-3">
            <label for="userId" class="form-label">아이디</label>
            <input type="text" class="form-control" id="userId" name="userId" placeholder="아이디 입력" required>
          </div>
          <div class="mb-3">
            <label for="userPwd" class="form-label">비밀번호</label>
            <input type="password" class="form-control" id="userPwd" name="userPwd" placeholder="비밀번호 입력" required>
          </div>
          <div class="d-grid mb-3">
            <button type="button" class="btn btn-black" id="btnLogin">로그인</button>
          </div>
          <div class="text-center">
            <span class="text-muted">계정이 없으신가요? </span>
            <a href="/user/userRegForm.jsp" class="fw-semibold">회원가입</a>
          </div>
        </form>
      </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.6/dist/js/bootstrap.bundle.min.js"></script>
  </body>
</html>
