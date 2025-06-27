<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/WEB-INF/views/include/head.jsp" %>
<script type="text/javascript">
$(document).ready(function() {

<c:choose>
	<c:when test = "${empty hiBoard}">
		alert("답변 할 게시물이 존재하지 않습니다.");
		location.href = "/board/list";
	</c:when>
	<c:otherwise>
	$("#hiBbsTitle").focus();
	
	$("#btnList").on("click", function(){
		document.bbsForm.action = "/board/list";
		document.bbsForm.submit();		
	});
	
	$("#btnReply").on("click", function(){
		$("#btnReply").prop("disabled", true); //버튼 비활성화
		
		if($.trim($("#hiBbsTitle").val()).length <= 0)
		{
			alert("제목을 입력하세요.");
			$("#hiBbsTitle").val("");
			$("#hiBbsTitle").focus();
			$("#btnReply").prop("disabled", false); //버튼 활성화
			return;
		}
		
		if($.trim($("#hiBbsContent").val()).length <= 0)
		{
			alert("내용을 입력하세요.");
			$("#hiBbsContent").val("");
			$("#hiBbsContent").focus();
			$("#btnReply").prop("disabled", false); //버튼 활성화
			return;
		}
		
		var form = $("#replyForm")[0];
		var formData = new FormData(form);
		
		$.ajax({
			type: "POST",
			enctype: "multipart/form-data",
			url:"/board/replyProc",
			data: formData,
			processData:false,
			contentType:false,
			cache:false,
			beforeSend:function(xhr)
			{
				xhr.setRequestHeader("AJAX", "true");
			},
			success:function(response)
			{
				if(response.code == 0)
				{
					alert("답변이 완료되었습니다.");
					location.href = "/board/list";
					
					//document.bbsForm.action ="/board/list";
					//document.bbsForm.submit();
				}
				else if(response.code == 400)
				{
					alert("파라미터 값이 올바르지않습니다.");
					$("#btnReply").prop("disabled", false); //버튼 활성화
					$("#hiBbsTitle").focus();
				}
				else if(response.code == 404)
				{
					alert("해당 게시물을 찾을 수 없습니다.");
					location.href ="/board/list";
				}
				else
				{
					alert("게시물 답변 중 오류가 발생하였습니다.");
					$("#btnReply").prop("disabled", false); //버튼 활성화
					$("#hiBbsTitle").focus();
				}
			},
			error:function(error)
			{
				icia.common.error(error);
				alert("게시물 답변 중 오류가 발생하였습니다.");
				$("#btnReply").prop("disabled", false); //버튼 활성화
				
			}
		
		});
	});
	</c:otherwise>
	
</c:choose>	
});
</script>
</head>
<body>
<c:if test="${!empty hiBoard}">
<%@ include file="/WEB-INF/views/include/navigation.jsp" %>
<div class="container">
   <h2>게시물 답변</h2>
   <form name="replyForm" id="replyForm" method="post" enctype="multipart/form-data">
   
      <input type="text" name="userName" id="userName" maxlength="20" value="${user.userName}" style="ime-mode:active;" class="form-control mt-4 mb-2" placeholder="이름을 입력해주세요." readonly />
      <input type="text" name="userEmail" id="userEmail" maxlength="30" value="${user.userEmail}"  style="ime-mode:inactive;" class="form-control mb-2" placeholder="이메일을 입력해주세요." readonly />
      <input type="text" name="hiBbsTitle" id="hiBbsTitle" maxlength="100" style="ime-mode:active;" value="" class="form-control mb-2" placeholder="제목을 입력해주세요." required />
      <div class="form-group">
         <textarea class="form-control" rows="10" name="hiBbsContent" id="hiBbsContent" style="ime-mode:active;" placeholder="내용을 입력해주세요" required></textarea>
      </div>
      <input type="file" name="hiBbsFile" id="hiBbsFile" class="form-control mb-2" placeholder="파일을 선택하세요." required />
      <input type="hidden" name="hibbsSeq" value="${hiBoard.hibbsSeq}" />
      <input type="hidden" name="searchType" value="${searchType}" />
      <input type="hidden" name="searchValue" value="${searchValue}" />
      <input type="hidden" name="curPage" value="${curPage}" />
   </form>
   
   <div class="form-group row">
      <div class="col-sm-12">
         <button type="button" id="btnReply" class="btn btn-primary" title="답변">답변</button>
         <button type="button" id="btnList" class="btn btn-secondary" title="리스트">리스트</button>
      </div>
   </div>
</div>
</c:if>
<form name = "bbsForm" id = "bbsForm" method = "post">
	<input type = "hidden" name = "hibbsSeq" value = "${hiBoard.hibbsSeq}"/>
	<input type = "hidden" name = "searchType" value = "${searchType}"/>
	<input type = "hidden" name = "searchValue" value = "${searchValue}"/>
	<input type = "hidden" name = "curPage" value = "${curPage}"/>

</form>

</body>
</html>