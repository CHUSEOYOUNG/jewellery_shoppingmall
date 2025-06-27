<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!doctype html>
<html lang="ko">
  <head>
  <%@ include file="/WEB-INF/views/include/head3.jsp" %>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <title>회원가입 · Ring Ring</title>

    <!-- ✅ Bootstrap -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.6/dist/css/bootstrap.min.css" rel="stylesheet" crossorigin="anonymous">

    <!-- ✅ Fonts -->
    <link href="https://fonts.googleapis.com/css2?family=Playfair+Display:wght@700;900&family=Noto+Sans+KR&display=swap" rel="stylesheet">

    <!-- ✅ Custom Style -->
    <style>
      body {
        font-family: 'Noto Sans KR', 'Playfair Display', serif;
        background-color: #fff;
        color: #111;
      }

      .register-container {
        max-width: 500px;
        margin: 80px auto;
        padding: 40px;
        background-color: #f9f9f9;
        border-radius: 12px;
        box-shadow: 0 0 12px rgba(0, 0, 0, 0.08);
      }

      .form-label {
        font-weight: 600;
      }

      .btn-custom-dark {
        background-color: #000;
        color: #fff;
        border: none;
      }

      .btn-custom-dark:hover {
        background-color: #222;
        color: #fff;
      }

      .form-control {
        border-radius: 8px;
      }

      a {
        color: #000;
        text-decoration: none;
      }

      a:hover {
        text-decoration: underline;
      }

      .text-muted {
        font-size: 14px;
      }
    </style>

    <!-- ✅ jQuery -->
    <script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
     <script>
    $(document).ready(function(){
    	$("#userId").focus();
    	
    	$("#btnReg").on("click", function(){
    		//공백체크
    		var emptCheck = /\s/g;
    		//영문 대소문자, 숫자로만 이루어진 4~12자리 정규식
    		var idPwCheck = /^[a-zA-Z0-9]{4,12}$/;
    		
    		if($.trim($("#userId").val()).length <= 0)
    		{
    			alert("사용자를 입력하세요.");
    			$("#userId").val("");
    			$("#userId").focus();
    			return;
    		}
    		
    		if(emptCheck.test($("#userId").val()))
    		{
    			alert("사용자 아이디는 공백을 포함할 수 없습니다.");
    			$("#userId").focus();
    			return;
    		}
    		
    		if(!idPwCheck.test($("#userId").val()))
    		{
    			alert("사용자 아이디는 4~12자 영문 대소문자와 숫자로만 입력가능합니다.");
    			$("#userId").focus();
    			return;
    		}
    		
    		if($.trim($("#userPwd1").val()).length <= 0)
    		{
    			alert("비밀번호를 입력하세요.");
    			$("#userPwd1").val("");
    			$("#userPwd1").focus();
    			return;
    		}
    		if(!idPwCheck.test($("#userPwd1").val()))
    		{
    			alert("비밀번호는 영문 대소문자 숫자로 4~12자리로 입력가능합니다.");
    			$("#userPwd1").val("");
    			$("#userPwd1").focus();			
    			return;
    		}
    		
    		if($.trim($("#userPwd2").val()).length <= 0)
    		{
    			alert("비밀번호 확인을 입력하세요");
    			$("#userPwd2").val("");
    			$("#userPwd2").focus();			
    			return;
    		}
    		
    		if($("#userPwd1").val() != $("#userPwd2").val())
    		{
    			alert("비밀번호가 일치하지 않습니다.");
    			$("#userPwd2").focus();			
    			return;
    		}
    		$("#userPwd").val($("#userPwd1").val());
    		
    		if($.trim($("#userName").val()).length <= 0)
    		{
    			alert("사용자 이름을 입력하세요.");
    			$("#userName").val("");
    			$("#userName").focus();			
    			return;
    		}
    		
    		if($.trim($("#userEmail").val()).length <= 0)
    		{
    			alert("이메일을 입력하세요.");
    			$("#userEmail").val("");
    			$("#userEmail").focus();
    			return;

    		}
    		
    		if(!fn_validateEmail($("#userEmail").val()))
    		{
    			alert("사용자 이메일 형식이 올바르지 않습니다.");
    			$("#userEmail").focus();
    			return;
    		}
    		//중복아이디체크
    		$.ajax({
    			type:"POST",
    			url:"/user/idCheck3",
    			data:{
    				userId:$("#userId").val()
    				
    			},
    			dataType:"JSON",
    			beforeSend:function(xhr)
    			{
    				xhr.setRequestHeader("AJAX", "true");
    			},
    			success:function(response)
    			{
    				if(response.code == 1)
    				{
    					alert("회원가입 가능합니다.");
    					fn_userReg();
    				}
    				else if(response.code == -1)
    				{
    					alert("중복된 아이디가 존재합니다.");
    					$("#userId").focus();
    				}
    				else if(response.code == 999)
    				{
    					alert("접근 할 수 없습니다.");	
    					$("#userId").focus();
    				}
    				else
    				{
    					alert("오류가 발생했습니다.");	
    					$("#userId").focus();
    				}
    			},
    			error:function(xhr, status, error)
    			{
    				icia.common.error(error);
    			}
    		});
    		
    	});
    });
    function fn_userReg()
    {
    	$.ajax({
    		type:"POST",
    		url:"/user/regProc3",
    		data:{
    			userId:$("#userId").val(),
    			userPwd:$("#userPwd").val(),
    			userName:$("#userName").val(),
    			userEmail:$("#userEmail").val()
    		},
    		dataType:"JSON",
    		beforeSend:function(xhr)
    		{
    			xhr.setRequestHeader("AJAX", "true");
    		},
    		success:function(response)
    		{
    			if(response.code == 1)
    			{
    				alert("회원가입에 성공했습니다. 환영합니다!");
    				location.href = "/home";
    			}
    			else if(response.code == -1)
    			{
    				alert("회원 아이디가 중복되었습니다.");
    				$("#userId").focus();
    			}
    			else if(response.code == 999)
    			{
    				alert("접근 할 수 없는 서버입니다.");
    				$("#userId").focus();
    			}		
    			else if(response.code == 400)
    			{
    				alert("회원가입을 실패했습니다.");
    				$("#userId").focus();
    			}
    			else
    			{
    				alert("회원가입 중 알 수 없는 오류가 발생하였습니다.");
    				$("#userId").focus();
    			}
    		},
    		error:function(xhr, status, error)
    		{
    			icia.common.error(error);
    		}
    	});
    }

    function fn_validateEmail(value)
    {
       var emailReg = /^([\w-\.]+@([\w-]+\.)+[\w-]{2,4})?$/;
       
       return emailReg.test(value);
    }
    </script>
    
  </head>

  <body>
<%@ include file="/WEB-INF/views/include/navigation3.jsp" %>
    <div class="container text-center mt-5">
      <a href="/" class="text-dark fs-2 fw-bold text-decoration-none">Ring Ring</a>
      <p class="text-muted mb-4">아래 정보를 입력해 가입을 완료해 주세요 !</p>
    </div>

    <div class="register-container">
      <form id="regForm" name="regForm" action="/user/regProc3" method="post">
        <div class="mb-3">
          <label for="userId" class="form-label">아이디</label>
          <input type="text" class="form-control" id="userId" name="userId" placeholder="아이디 입력" maxlength="12" required />
        </div>

        <div class="mb-3">
          <label for="userPwd1" class="form-label">비밀번호</label>
          <input type="password" class="form-control" id="userPwd1" name="userPwd1" placeholder="비밀번호 입력" maxlength="12" required />
        <input type="hidden" id="userPwd" name="userPwd" />
        </div>

        <div class="mb-3">
          <label for="userPwd2" class="form-label">비밀번호 확인</label>
          <input type="password" class="form-control" id="userPwd2" name="userPwd2" placeholder="비밀번호 재입력" maxlength="12" required />
        </div>

        <div class="mb-3">
          <label for="userName" class="form-label">이름</label>
          <input type="text" class="form-control" id="userName" name="userName" placeholder="이름 입력" maxlength="15" required />
        </div>

        <div class="mb-3">
          <label for="userEmail" class="form-label">이메일</label>
          <input type="email" class="form-control" id="userEmail" name="userEmail" placeholder="example@domain.com" maxlength="30" required />
        </div>

        <div class="d-grid">
          <button type="button" id="btnReg" class="btn btn-custom-dark">회원가입 완료</button>
        </div>

        <div class="text-center mt-3">
          <span class="text-muted">이미 계정이 있으신가요? </span>
          <a href="/user/loginPage">로그인하기</a>
        </div>
      </form>
    </div>

    <!-- ✅ JS: 유효성 검사 + AJAX 아이디 중복체크 -->
   
    <!-- ✅ Bootstrap JS -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.6/dist/js/bootstrap.bundle.min.js" crossorigin="anonymous"></script>
  </body>
</html>
