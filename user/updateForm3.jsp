<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="ko">
<head>
	<%@ include file="/WEB-INF/views/include/head3.jsp" %>
	<title>회원정보 수정 · Ring Ring</title>
	<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>

	<!-- ✅ 스타일 (Playfair + Noto Sans + 카드 디자인) -->
	<style>
		@import url('https://fonts.googleapis.com/css2?family=Playfair+Display:wght@700&family=Noto+Sans+KR:wght@400;700&display=swap');

		body {
			font-family: 'Noto Sans KR', sans-serif;
			background-color: #f7f7f7;
			margin: 0;
			padding: 0;
		}

		.form-container {
			max-width: 700px;
			margin: 60px auto;
			background: #fff;
			border-radius: 16px;
			box-shadow: 0 5px 25px rgba(0, 0, 0, 0.05);
			padding: 40px 50px;
		}

		h2 {
			font-family: 'Playfair Display', serif;
			font-size: 32px;
			font-weight: bold;
			margin-bottom: 30px;
			text-align: center;
		}

		label {
			font-weight: 600;
			margin-bottom: 5px;
			color: #222;
		}

		.form-group {
			margin-bottom: 25px;
		}

		.form-control {
			border: none;
			border-bottom: 2px solid #ccc;
			border-radius: 0;
			padding: 12px 10px;
			background: transparent;
			transition: border-color 0.3s ease;
		}

		.form-control:focus {
			border-bottom-color: #000;
			box-shadow: none;
			outline: none;
		}

		strong {
			font-size: 16px;
			display: inline-block;
			background-color: #f0f0f0;
			padding: 8px 14px;
			border-radius: 6px;
		}

		.btn-dark {
			background-color: #000;
			color: #fff;
			padding: 12px 24px;
			border: none;
			border-radius: 10px;
			width: 100%;
			font-size: 16px;
			font-weight: 600;
			transition: background-color 0.3s ease;
		}

		.btn-dark:hover {
			background-color: #333;
			color: #fff;
		}
		  
		  button.btn-sm {
	    min-width: 100px;
	  }
	</style>

<script type="text/javascript">
$(document).ready(function(){

	  $("#btnDelete").on("click", function() {
	    alert("회원탈퇴는 취소가 불가능합니다.");
	    
	      $.ajax({
	        type: "POST",
	        url: "/user/userDelete2",
	        dataType: "JSON",
	        beforeSend: function(xhr) {
	          xhr.setRequestHeader("AJAX", "true");
	        },
	        success: function(response) 
	        {
	          if (response.code === 1) 
	          {
	            alert("회원 탈퇴가 완료되었습니다.");
	            location.href = "/home";
	          } 
	          else if (response.code === 401) 
	          {
	            alert("로그인이 필요합니다.");
	            location.href = "/home";
	          } 
	          else if (response.code === 404) 
	          {
	            alert("회원 정보를 찾을 수 없습니다.");
	          } 
	          else if (response.code === 500) 
	          {
	            alert("회원 탈퇴 중 오류가 발생했습니다.");
	          } 
	          else 
	          {
	            alert("알 수 없는 오류가 발생했습니다.");
	          }
	        },
	        error: function(xhr, status, error) 
	        {
				icia.common.error(error);
	        }
	      });
	  });

	$("#btnUpdate").on("click", function(){
		var emptCheck = /\s/g;
		var idPwCheck = /^[a-zA-Z0-9]{4,12}$/;
		
		if($.trim($("#userPwd1").val()).length <= 0)
		{
			alert("비밀번호를 입력하세요.");
			$("#userPwd1").val("");
			$("#userPwd1").focus();
			return;
		}
		if(!idPwCheck.test($("#userPwd1").val()))
		{
			alert("비밀번호를 영문 대소문자 숫자로만 이루어진 4~12자리로 입력하세요.");
			$("#userPwd1").focus();
			return;
		}
		
		if($.trim($("#userPwd2").val()).length <= 0)
		{
			alert("비밀번호를 확인을 입력하세요.");
			$("#userPwd2").val("");
			$("#userPwd2").focus();
			return;
		}
		if($("#userPwd1").val() != $("#userPwd2").val())
		{
			alert("비밀번호가 일치하지 않습니다.");
			$("#userPwd1").focus();
			return;
		}
		
		$("#userPwd").val($("#userPwd1").val());
		
		if($.trim($("#userName").val()).length <= 0)
		{
			alert("사용자의 이름을 입력하세요.");
			$("#userName").val("");
			$("#userName").focus();
			return;
		}
		
		if($.trim($("#userEmail").val()).length <= 0)
		{
			alert("사용자의 이메일을 입력하세요.");
			$("#userEmail").val("");
			$("#userEmail").focus();
			return;
		}
		if(!fn_validateEmail($("#userEmail").val()))
		{
			alert("이메일 형식이 올바르지 않습니다.");
			$("#userEmail").focus();
			return;
		}
		
		$.ajax({
			type:"POST",
			url:"/user/updateProc3",
			data:{
				userId:$("#userId").val(),
				userPwd:$("#userPwd").val(),
				userName:$("#userName").val(),
				userEmail:$("#userEmail").val(),
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
					alert("회원정보가 수정되었습니다.");
					location.href = "/user/updateForm3";
				}
				else if(response.code == 400)
				{
					alert("값이 올바르지않습니다.");
					$("#userPwd1").focus();
				}
				else if(response.code == 404)
				{
					alert("회원정보가 존재하지 않습니다.");
					location.href = "/home";
				}
				else if(response.code == 410)
				{
					alert("로그인을 하세요.");
					location.href = "/home";
				}
				else if(response.code == 430)
				{
					alert("아이디 정보가 일치하지 않습니다.");
					location.href = "/home";
				}
				else if(response.code == 500)
				{
					alert("회원정보 수정 중 오류가 발생했습니다.");
					$("#userId").focus();
				}
				else
				{
					alert("회원정보 수정 중 알 수 없는 오류가 발생했습니다.");
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

function fn_validateEmail(value)
{
   var emailReg = /^([\w-\.]+@([\w-]+\.)+[\w-]{2,4})?$/;
   
   return emailReg.test(value);
}
</script>
</head>

<body>
	<!-- ✅ 네비게이션 -->
	<%@ include file="/WEB-INF/views/include/navigation3.jsp" %>

	<!-- ✅ 본문 -->
	<div class="container form-container">
		<h2>USER INFORM</h2>
		<form name="updateForm" id="updateForm" method="post" action="/user/updateProc3">
			<div class="form-group">
				<label>아이디</label><br />
				<strong>${user.userId}</strong>
			</div>
			<div class="form-group">
				<label for="userPwd1">비밀번호</label>
				<input type="password" id="userPwd1" name="userPwd1" class="form-control" value="${user.userPwd}" maxlength="12" />
			</div>
			<div class="form-group">
				<label for="userPwd2">비밀번호 확인</label>
				<input type="password" id="userPwd2" name="userPwd2" class="form-control" value="${user.userPwd}" maxlength="12" />
			</div>
			<div class="form-group">
				<label for="userName">이름</label>
				<input type="text" id="userName" name="userName" class="form-control" value="${user.userName}" maxlength="15" />
			</div>
			<div class="form-group">
				<label for="userEmail">이메일</label>
				<input type="text" id="userEmail" name="userEmail" class="form-control" value="${user.userEmail}" maxlength="30" />
			</div>
			<input type="hidden" id="userId" name="userId" value="${user.userId}">
			<input type="hidden" id="userPwd" name="userPwd" value="" />
			<div class="d-flex justify-content-start mt-3 gap-2">
			  <button type="button" id="btnUpdate" class="btn btn-sm btn-outline-dark">정보 수정</button>
			  <button type="button" id="btnDelete" class="btn btn-sm btn-outline-dark">회원 탈퇴</button>
			</div>
			
		</form>
	</div>
</body>
</html>


