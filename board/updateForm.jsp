<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/WEB-INF/views/include/head.jsp" %>
<script type="text/javascript">
$(document).ready(function() {
    $("#hiBbsTitle").focus();

    $("#btnList").on("click", function() {
        document.bbsForm.action = "/board/list";
        document.bbsForm.submit();
    });

    $("#btnUpdate").on("click", function() {
        $("#btnUpdate").prop("disabled", true);

        if ($.trim($("#hiBbsTitle").val()).length <= 0) {
            alert("제목을 입력하세요.");
            $("#hiBbsTitle").focus();
            $("#btnUpdate").prop("disabled", false);
            return;
        }

        if ($.trim($("#hiBbsContent").val()).length <= 0) {
            alert("내용을 입력하세요.");
            $("#hiBbsContent").focus();
            $("#btnUpdate").prop("disabled", false);
            return;
        }

        var form = $("#updateForm")[0];
        var formData = new FormData(form);

        $.ajax({
            type: "POST",
            enctype: "multipart/form-data",
            url: "/board/updateProc",
            data: formData,
            processData: false,
            contentType: false,
            cache: false,
            beforeSend: function(xhr) {
                xhr.setRequestHeader("AJAX", "true");
            },
            success: function(response) {
                if (response.code == 0) {
                    alert("게시물 수정이 완료되었습니다.");
                    document.bbsForm.action = "/board/list";
                    document.bbsForm.submit();
                    
                    //기존 검색조건과 페이지번호 갖고 이동
                    //document.bbsForm.action = "/board/list";
                    //document.bbsForm.submit();
                } 
                else if(response.code == 400)
                {
                    alert("파라미터 값이 올바르지 않습니다.");
                    $("#btnUpdate").prop("disabled", false);

                }
                else if(response.code == 403)
                {
                	alert("본인 게시물이 아닙니다.");
                    $("#btnUpdate").prop("disabled", false);
                }
                else if(response.code == 404)
                {
                	alert("해당 게시물이 없습니다.");
                    loaction.href = "/board/list";
                }
                else
                {
                    alert("게시물 수정중 오류가 발생했습니다.(2)");
                    $("#btnUpdate").prop("disabled", false);
                }
            },
            error: function(error) {
                icia.common.error(error);
                alert("게시물 수정중 오류가 발생했습니다.");
                $("#btnUpdate").prop("disabled", false);
            }
        });
    });
});
</script>
</head>

<body>

<%@ include file="/WEB-INF/views/include/navigation.jsp" %>

<div class="container">
    <h2>게시물 수정</h2>
    <form name="updateForm" id="updateForm" method="post" enctype="multipart/form-data">
        <input type="text" name="userName" id="userName" maxlength="20" value="${hiBoard.userName}" class="form-control mt-4 mb-2" readonly />
        <input type="text" name="userEmail" id="userEmail" maxlength="30" value="${hiBoard.userEmail}" class="form-control mb-2" readonly />
        <input type="text" name="hiBbsTitle" id="hiBbsTitle" maxlength="100" value="${hiBoard.hibbsTitle}" class="form-control mb-2" placeholder="제목을 입력해주세요." required />
        
        <div class="form-group">
            <textarea class="form-control" rows="10" name="hiBbsContent" id="hiBbsContent" placeholder="내용을 입력해주세요." required>${hiBoard.hibbsContent}</textarea>
        </div>

        <input type="file" name="hiBbsFile" id="hiBbsFile" class="form-control mb-2" />

        <c:if test="${!empty hiBoard.hiBoardFile}">
            <div style="margin-bottom:0.3em;">[첨부파일 : ${hiBoard.hiBoardFile.fileOrgName}]</div>
        </c:if>

        <!-- ✅ EL 닫힘문제 완전 해결 -->
        <input type="hidden" name="hibbsSeq" value="${hiBoard.hibbsSeq}" />
        <input type="hidden" name="searchType" value="${searchType}" />
        <input type="hidden" name="searchValue" value="${searchValue}" />
        <input type="hidden" name="curPage" value="${curPage}" />
    </form>

    <div class="form-group row">
        <div class="col-sm-12">
            <button type="button" id="btnUpdate" class="btn btn-primary">수정</button>
            <button type="button" id="btnList" class="btn btn-secondary">리스트</button>
        </div>
    </div>
</div>

<form name="bbsForm" id="bbsForm" method="post">
    <input type="hidden" name="hibbsSeq" value="${hiBoard.hibbsSeq}" />
    <input type="hidden" name="searchType" value="${searchType}" />
    <input type="hidden" name="searchValue" value="${searchValue}" />
    <input type="hidden" name="curPage" value="${curPage}" />
</form>

</body>
</html>
