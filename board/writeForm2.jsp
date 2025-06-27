<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>

<!DOCTYPE html>
<html lang="ko">
<head>
    <%@ include file="/WEB-INF/views/include/head3.jsp" %>

    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <title>커뮤니티 글쓰기 · Ring Ring</title>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.6/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css2?family=Playfair+Display:wght@700;900&family=Noto+Sans+KR&display=swap" rel="stylesheet">

    <style>
        body {
            font-family: 'Noto Sans KR', 'Playfair Display', serif;
            background-color: #fff;
            color: #111;
        }
        .write-container {
            max-width: 800px;
            margin: 60px auto;
            padding: 30px;
            background-color: #f9f9f9;
            border-radius: 12px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.05);
        }
        .form-control { border-radius: 8px; }
        .btn-custom-dark {
            background-color: #000; color: #fff; border: none;
        }
        .btn-custom-dark:hover {
            background-color: #222; color: #fff;
        }
    </style>


<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script>
$(document).ready(function() {
    $("#btnWrite").click(function() {
        if ($.trim($("#title").val()) === "" || $.trim($("#content").val()) === "") {
            alert("제목과 내용을 입력하세요.");
            return;
        }
        var formData = new FormData($("#writeForm")[0]);

        $.ajax({
            url: "/board/writeProc2",
            type: "POST",
            data: formData,
            processData: false,
            contentType: false,
            success: function(res) {
                if(res.code == 0) {
                    alert("등록 성공");
                    location.href = "/board/list2";
                } else {
                    alert("등록 실패");
                }
            }
        });
    });
});
</script>
</head>
<body>

<%@ include file="/WEB-INF/views/include/navigation3.jsp"%>

<div class="container">
    <h3>커뮤니티 글쓰기</h3>

    <form id="writeForm" enctype="multipart/form-data">
        <input type="text" class="form-control mb-2" value="${user.userName}" readonly/>
        <input type="text" class="form-control mb-2" value="${user.userEmail}" readonly/>

        <div class="mb-3">
            <label>제목</label>
            <input type="text" id="title" name="title" class="form-control"/>
        </div>

        <div class="mb-3">
            <label>내용</label>
            <textarea id="content" name="content" class="form-control" rows="8"></textarea>
        </div>

        <div class="mb-3">
            <label>대표 이미지 첨부</label>
            <input type="file" id="shareFile" name="shareFile" class="form-control"/>
        </div>

        <button type="button" id="btnWrite" class="btn btn-dark">등록</button>
        <a href="/board/list2" class="btn btn-secondary">취소</a>
    </form>
</div>
</body>
</html>
