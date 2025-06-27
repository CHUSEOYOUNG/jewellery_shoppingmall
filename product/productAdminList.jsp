<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>

<!DOCTYPE html>
<html lang="ko">
<head>
<%@ include file="/WEB-INF/views/include/adminHead.jsp" %>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <title>상품 관리 · 관리자</title>
    <style>
        body { font-family: 'Noto Sans KR', sans-serif; }
        .container { max-width: 1200px; margin: 30px auto; }
        .page-title { font-family: 'Playfair Display', serif; font-size: 28px; text-align: center; margin-bottom: 30px; }
        .search-bar, .btn-insert { display: flex; justify-content: space-between; margin-bottom: 20px; }
        .search-bar input, .search-bar select { padding: 6px; border: 1px solid #ccc; border-radius: 4px; }
        .search-bar button, .btn-insert a { padding: 6px 16px; background: #000; color: #fff; border: none; border-radius: 4px; text-decoration: none; }
        table { width: 100%; border-collapse: collapse; font-size: 15px; }
        th, td { padding: 10px; border-bottom: 1px solid #ccc; text-align: center; }
        .product-img { width: 50px; height: 50px; object-fit: cover; border-radius: 8px; }
        .pagination { margin-top: 30px; display: flex; justify-content: center; gap: 5px; }
        .pagination a { padding: 6px 12px; background: #000; color: #fff; text-decoration: none; border-radius: 4px; }
        .pagination a.active { background: #333; }
    </style>
</head>
<body>
<%@ include file="/WEB-INF/views/include/adminNavigation.jsp" %>

<div class="container">
    <h2 class="page-title">🏦 상품 관리</h2>

    <div class="btn-insert">
        <a href="/product/productInsert">상품 등록</a> 
    </div>

    <form name="productForm" method="get" class="search-bar" action="/product/productAdminList">
        <input type="hidden" name="curPage" value="${paging.curPage}">
        <input type="hidden" name="categoryId" value="${param.categoryId}">

        <select name="searchType" id="_searchType">
            <option value="">조회 항목</option>
            <option value="1" ${param.searchType == '1' ? 'selected' : ''}>상품명</option>
            <option value="2" ${param.searchType == '2' ? 'selected' : ''}>카테고리</option>
            <option value="3" ${param.searchType == '3' ? 'selected' : ''}>설명</option>
        </select>
        <input type="text" name="searchValue" id="_searchValue" value="${param.searchValue}" placeholder="검색어 입력">
        <button type="submit" id="btnSearch">조회</button>
    </form>

    <table>
        <thead>
            <tr>
                <th>이미지</th>
                <th>상품명</th>
                <th>카테고리</th>
                <th>가격</th>
                <th>재고</th>
                <th>상태</th>
                <th>수정</th>
            </tr>
        </thead>
        <tbody>
            <c:choose>
                <c:when test="${not empty productList}">
                    <c:forEach var="product" items="${productList}">
                        <tr>
                            <td>
                                <c:if test="${not empty product.productImage}">
                                    <img class="product-img" src="/resources/upload/small/${product.productImage}" alt="상품 이미지"/>
                                </c:if>
                                <c:if test="${empty product.productImage}">
                                    <span>이미지 없음</span>
                                </c:if>
                            </td>
                            <td>${product.productName}</td>
                            <td>${product.categoryName}</td>
                            <td>${product.productPrice}</td>
                            <td>${product.productStock}</td>
                            <td>
                                <c:choose>
                                    <c:when test="${product.productOnoff == 'Y'}">판매중</c:when>
                                    <c:otherwise>판매중지</c:otherwise>
                                </c:choose>
                            </td>
                            <td>
                                <a href="/product/update?productId=${product.productId}">수정</a>
                            </td>
                        </tr>
                    </c:forEach>
                </c:when>
                <c:otherwise>
                    <tr>
                        <td colspan="7">등록된 상품이 없습니다.</td>
                    </tr>
                </c:otherwise>
            </c:choose>
        </tbody>
    </table>

    <!-- 페이징 영역 (옵션으로 활용) -->
    <div class="pagination">
        <c:forEach var="page" begin="1" end="${paging.totalPage}">
            <a href="/product/productAdminList?curPage=${page}" class="${paging.curPage == page ? 'active' : ''}">${page}</a>
        </c:forEach>
    </div>

</div>

</body>
</html>
