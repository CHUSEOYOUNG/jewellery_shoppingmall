<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>

<!DOCTYPE html>
<html lang="ko">
<head>
    <%@ include file="/WEB-INF/views/include/head3.jsp" %>
    <title>커뮤니티 · Ring Ring</title>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <style>
        body {
            font-family: 'Noto Sans KR', 'Playfair Display', serif;
            background-color: #fff;
            color: #111;
        }
        .community-container {
            max-width: 1200px;
            margin: 50px auto;
            padding: 20px;
        }
        .community-header {
            text-align: center;
            margin-bottom: 30px;
        }
        .community-header h2 {
            font-weight: 700;
            font-family: 'Playfair Display', serif;
            font-size: 32px;
        }
        .search-bar {
            max-width: 700px;
            margin: 0 auto 30px;
            display: flex;
            gap: 8px;
        }
        .search-bar select, .search-bar input {
            flex: 1;
            padding: 8px;
            border: 1px solid #ccc;
            border-radius: 6px;
        }
        .search-bar button {
            padding: 8px 16px;
            background-color: #000;
            color: #fff;
            border: none;
            border-radius: 6px;
        }
        .card-grid {
            display: grid;
            grid-template-columns: repeat(auto-fill, minmax(280px, 1fr));
            gap: 24px;
        }
        .community-card {
            border: 1px solid #eee;
            border-radius: 12px;
            padding: 20px;
            background-color: #fafafa;
            box-shadow: 0 0 8px rgba(0,0,0,0.05);
            transition: transform .2s;
        }
        .community-card:hover { transform: translateY(-5px); }
        .community-card h5 {
            font-size: 18px;
            font-weight: bold;
            margin-bottom: 10px;
        }
        .community-card h5 a {
            color: #333;
            text-decoration: none;
        }
        .community-card h5 a:hover {
            color: #000;
            text-decoration: underline;
        }
        .community-card p {
            font-size: 14px;
            color: #555;
            margin-bottom: 8px;
            line-height: 1.4;
            height: 3.2em;
            overflow: hidden;
            text-overflow: ellipsis;
        }
        .card-meta {
            font-size: 13px;
            color: #888;
            margin-bottom: 6px;
        }
        .btn-view {
            font-size: 13px;
            text-decoration: underline;
            color: #000;
        }
        .pagination {
            margin-top: 40px;
        }
        .pagination .page-link {
            background-color: #000;
            color: #fff;
            border: none;
            margin: 0 3px;
            border-radius: 4px;
        }
        .pagination .page-link:hover { background-color: #333; }
        .pagination .page-item.active .page-link {
            background-color: #000;
            color: #fff;
        }
        .btn-write {
            display: block;
            margin: 30px auto 0;
            padding: 8px 20px;
            font-size: 14px;
            background-color: #000;
            color: #fff;
            border: none;
            border-radius: 6px;
            transition: background-color .2s;
        }
        .btn-write:hover { background-color: #333; }
    </style>

    <script>
        $(document).ready(function(){
            $("#_searchType").change(function(){ $("#_searchValue").val(""); });

            $("#btnSearch").click(function(){
                if($("#_searchType").val() !== "" && $.trim($("#_searchValue").val()) === ""){
                    alert("조회항목 선택 시 검색어를 입력하세요.");
                    $("#_searchValue").focus();
                    return;
                }
                document.bbsForm.searchType.value = $("#_searchType").val();
                document.bbsForm.searchValue.value = $("#_searchValue").val();
                document.bbsForm.curPage.value = "1";
                document.bbsForm.action = "/board/list2";
                document.bbsForm.submit();
            });
        });

        function fn_list(curPage){
            document.bbsForm.curPage.value = curPage;
            document.bbsForm.action = "/board/list2";
            document.bbsForm.submit();
        }

        function fn_view(postId){
            document.bbsForm.postId.value = postId;
            document.bbsForm.action = "/board/view2";
            document.bbsForm.submit();
        }
    </script>
</head>

<body>
<%@ include file="/WEB-INF/views/include/navigation3.jsp" %>

<div class="container community-container">
    <div class="community-header">
        <h2>USER COMMUNITY</h2>
        <p class="text-muted">사용자들과 다양한 후기를 나눠보세요.</p>
    </div>

    <div class="search-bar">
        <select id="_searchType">
            <option value="">조회 항목</option>
            <option value="1" <c:if test="${searchType eq '1'}">selected</c:if>>작성자</option>
            <option value="2" <c:if test="${searchType eq '2'}">selected</c:if>>제목</option>
            <option value="3" <c:if test="${searchType eq '3'}">selected</c:if>>내용</option>
        </select>
        <input type="text" id="_searchValue" value="${searchValue}" placeholder="검색어 입력" maxlength="20" />
        <button type="button" id="btnSearch">조회</button>
    </div>

    <div class="card-grid">
        <c:if test="${!empty list}">
            <c:forEach var="board" items="${list}">
                <div class="community-card">
                    <c:if test="${not empty board.fileList}">
                        <div style="text-align: center; margin-bottom: 15px;">
                            <img src="/resources/upload/style/${board.fileList[0].fileName}" 
                                 alt="${board.fileList[0].fileOrgName}" 
                                 style="max-width: 100%; height: 180px; object-fit: cover; border-radius: 8px; box-shadow: 0 2px 8px rgba(0,0,0,0.1);" />
                        </div>
                    </c:if>
                    <h5><a href="javascript:void(0)" onclick="fn_view(${board.postId})">${board.title}</a></h5>
                    <div class="card-meta">${board.userId} · ${board.regDate}</div>
                    <p>
                        <c:choose>
                            <c:when test="${fn:length(board.content) > 60}">
                                ${fn:substring(board.content, 0, 60)}...
                            </c:when>
                            <c:otherwise>
                                ${board.content}
                            </c:otherwise>
                        </c:choose>
                    </p>
                    <a href="javascript:void(0)" class="btn-view" onclick="fn_view(${board.postId})">자세히 보기 →</a>
                </div>
            </c:forEach>
        </c:if>

        <c:if test="${empty list}">
            <div class="community-card" style="grid-column:1/-1; text-align:center;">
                <p>등록된 게시물이 없습니다.</p>
            </div>
        </c:if>
    </div>

    <nav>
        <ul class="pagination justify-content-center">
            <c:if test="${paging.prevBlockPage gt 0}">
                <li class="page-item"><a class="page-link" href="javascript:void(0)" onclick="fn_list(${paging.prevBlockPage})">이전</a></li>
            </c:if>
            <c:forEach var="i" begin="${paging.startPage}" end="${paging.endPage}">
                <c:choose>
                    <c:when test="${i ne curPage}">
                        <li class="page-item"><a class="page-link" href="javascript:void(0)" onclick="fn_list(${i})">${i}</a></li>
                    </c:when>
                    <c:otherwise>
                        <li class="page-item active"><a class="page-link" href="#">${i}</a></li>
                    </c:otherwise>
                </c:choose>
            </c:forEach>
            <c:if test="${paging.nextBlockPage gt 0}">
                <li class="page-item"><a class="page-link" href="javascript:void(0)" onclick="fn_list(${paging.nextBlockPage})">다음</a></li>
            </c:if>
        </ul>
    </nav>

    <a href="/board/writeForm2" class="btn-write">✏️ 글쓰기</a>

    <form name="bbsForm" id="bbsForm" method="post">
        <input type="hidden" name="postId" />
        <input type="hidden" name="searchType" value="${searchType}" />
        <input type="hidden" name="searchValue" value="${searchValue}" />
        <input type="hidden" name="curPage" value="${curPage}" />
    </form>
</div>
</body>
</html>
