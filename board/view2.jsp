<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <%@ include file="/WEB-INF/views/include/head3.jsp" %>
    <title>게시글 보기 · Ring Ring</title>
    <style>
        body {
            font-family: 'Noto Sans KR','Playfair Display', serif;
            background-color: #fafafa;
            color: #333;
        }
        .container {
            max-width: 960px;
            margin: 40px auto;
            padding: 0 16px;
        }
        .post-header, .post-body, .comment-section {
            background: #fff;
            border-radius: 8px;
            padding: 24px 32px;
            box-shadow: 0 2px 8px rgba(0,0,0,0.05);
            margin-bottom: 24px;
        }
        .post-header h2 {
            font-size: 1.75rem;
            font-weight: 600;
            margin: 0;
            color: #111;
        }
        .post-meta {
            display: flex;
            gap: 16px;
            font-size: .9rem;
            color: #666;
            margin-top: 8px;
        }
        .btn-download, .btn-action {
            margin-top: 10px;
            padding: 8px 16px;
            border: none;
            background-color: #000;
            color: #fff;
            border-radius: 5px;
            cursor: pointer;
            margin-right: 8px;
        }
        .comment-item {
            padding: 12px 0;
            border-bottom: 1px solid #eee;
        }
        .comment-meta {
            font-size: 13px;
            color: #777;
            margin-bottom: 5px;
        }
        .comment-box textarea {
            width: 100%;
            height: 80px;
            padding: 10px;
            border: 1px solid #ddd;
            border-radius: 6px;
        }
        .comment-box button {
            margin-top: 10px;
            background: #000;
            color: #fff;
            padding: 8px 20px;
            border: none;
            border-radius: 4px;
        }
    </style>

    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script>
        $(document).ready(function(){
            $("#btnDelete").on("click", function(){
                if(confirm("게시글을 삭제하시겠습니까?")) {
                    $.ajax({
                        type: "POST",
                        url: "/board/delete2",
                        data: { postId: ${board.postId} },
                        datatype: "JSON",
                        beforeSend: function(xhr) { xhr.setRequestHeader("AJAX", "true"); },
                        success: function(response) {
                            if(response.code == 0) {
                                alert("삭제되었습니다.");
                                location.href = "/board/list2";
                            } else {
                                alert("삭제 실패: " + response.msg);
                            }
                        },
                        error: function() { alert("서버 오류 발생"); }
                    });
                }
            });

            $("#btnComment").on("click", function(){
                var content = $("#commentContent").val().trim();
                if(content === ""){
                    alert("댓글 내용을 입력하세요.");
                    return;
                }

                $.ajax({
                    type: "POST",
                    url: "/board/commentWrite",
                    data: { 
                        postId: ${board.postId},
                        content: content
                    },
                    datatype: "JSON",
                    beforeSend: function(xhr) { xhr.setRequestHeader("AJAX", "true"); },
                    success: function(response) {
                        if(response.code == 0) {
                            alert("댓글이 등록되었습니다.");
                            location.reload();
                        } else {
                            alert("등록 실패: " + response.msg);
                        }
                    },
                    error: function() { alert("서버 오류 발생"); }
                });
            });
        });
    </script>
</head>

<body>
<%@ include file="/WEB-INF/views/include/navigation3.jsp" %>

<div class="container">

    <!-- 게시글 -->
    <div class="post-header">
        <h2>${board.title}</h2>
        <div class="post-meta">
            <span>${board.regDate}</span>
            <span>${board.readCnt}회</span>
            <span>${board.userId}</span>
        </div>
    </div>

    <div class="post-body">
        <c:out value="${board.content}" escapeXml="false" />

        <c:if test="${not empty board.fileList}">
            <div class="mt-3">
                <img src="/resources/upload/style/${board.fileList[0].fileName}" 
                     alt="${board.fileList[0].fileOrgName}" 
                     style="max-width:100%; height:auto; border-radius:12px; box-shadow:0 0 10px rgba(0,0,0,0.1);"/>
                <br/><br/>
            </div>
        </c:if>

        <button type="button" class="btn-action" onclick="location.href='/board/writeForm2?postId=${board.postId}'">수정</button>
        <button type="button" id="btnDelete" class="btn-action">삭제</button>
    </div>

    <!-- 댓글 -->
    <div class="comment-section">
        <h5>댓글</h5>

        <c:if test="${not empty commentList}">
            <c:forEach var="comment" items="${commentList}">
                <div class="comment-item">
                    <div class="comment-meta">${comment.userId} · ${comment.regDate}</div>
                    <div>${comment.content}</div>
                </div>
            </c:forEach>
        </c:if>

        <c:if test="${empty commentList}">
            <p>댓글이 없습니다.</p>
        </c:if>

        <div class="comment-box">
            <textarea id="commentContent" placeholder="댓글을 입력하세요"></textarea>
            <button type="button" id="btnComment">등록</button>
        </div>
    </div>

</div>
</body>
</html>
