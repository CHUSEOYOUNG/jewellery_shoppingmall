<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="ko">
<head>
    <%@ include file="/WEB-INF/views/include/head3.jsp" %>
    <title>상품 목록 · Ring Ring</title>
    <style>
        body {
            font-family: 'Noto Sans KR', sans-serif;
            background-color: #fff;
            color: #111;
        }
        .container {
            max-width: 1200px;
            margin: 20px auto;
            padding: 20px;
        }
        .page-title {
            font-family: 'Playfair Display', serif;
            font-size: 36px;
            font-weight: bold;
            text-align: center;
            margin: 20px 0 30px;
        }
        .search-bar {
            max-width: 700px;
            margin: 0 auto 20px;
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
        .product-grid {
            display: grid;
            grid-template-columns: repeat(auto-fill, minmax(260px, 1fr));
            gap: 32px;
        }
        .product-card {
            position: relative;
            border: 1px solid #111;
            border-radius: 16px;
            padding: 20px;
            text-align: center;
            transition: transform 0.2s ease;
            cursor: pointer;
        }
        .product-card:hover {
            transform: scale(1.02);
            box-shadow: 0 4px 12px rgba(0,0,0,0.1);
        }
        .product-img {
            width: 100%;
            height: 180px;
            object-fit: cover;
            border-radius: 12px;
            margin-bottom: 16px;
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
        .pagination .page-link:hover {
            background-color: #333;
        }
        .pagination .page-item.active .page-link {
            background-color: #000;
            color: #fff;
        }
    </style>
</head>
<body>

<%@ include file="/WEB-INF/views/include/navigation3.jsp" %>

<div class="container">
    <h1 class="page-title">PRODUCT LIST</h1>

    <!-- 검색 영역 -->
    <div class="search-bar">
        <form name="productForm" method="get" action="/product/list">
            <select name="searchType">
                <option value="">조회 항목</option>
                <option value="1" ${searchType eq '1' ? 'selected' : ''}>상품명</option>
                <option value="2" ${searchType eq '2' ? 'selected' : ''}>카테고리</option>
                <option value="3" ${searchType eq '3' ? 'selected' : ''}>설명</option>
            </select>
            <input type="text" name="searchValue" value="${searchValue}" placeholder="검색어 입력" maxlength="20" />
            <button type="submit">조회</button>
            <input type="hidden" name="curPage" value="${paging.curPage}" />
            <input type="hidden" name="categoryId" value="${categoryId}" />
        </form>
    </div>

    <!-- 상품 카드 영역 -->
    <div class="product-grid">
        <c:if test="${not empty productList}">
            <c:forEach var="p" items="${productList}">
                <div class="product-card">
                    <a href="/product/view?productId=${p.productId}" class="stretched-link"></a>

                    <c:if test="${not empty p.productImage}">
                        <img class="product-img" src="/resources/upload/small/${p.productImage}" alt="상품 이미지" />
                    </c:if>
                    <c:if test="${empty p.productImage}">
                        <img class="product-img" src="/resources/images/no-image.png" alt="이미지 없음" />
                    </c:if>

                    <div class="product-name fw-bold mt-2">${p.productName}</div>
                    <div class="product-price text-muted">₩ <fmt:formatNumber value="${p.productPrice}" type="number" /></div>
                    <div class="product-category text-secondary">${p.categoryName}</div>
                </div>
            </c:forEach>
        </c:if>

        <c:if test="${empty productList}">
            <div class="product-card" style="grid-column: 1 / -1; text-align: center;">
                상품이 없습니다.
            </div>
        </c:if>
    </div>

    <!-- 페이징 영역 -->
    <nav>
        <ul class="pagination justify-content-center">
            <c:if test="${paging.prevBlockPage > 0}">
                <li class="page-item">
                    <a class="page-link" href="?curPage=${paging.prevBlockPage}&searchType=${searchType}&searchValue=${searchValue}">이전</a>
                </li>
            </c:if>

            <c:forEach var="i" begin="${paging.startPage}" end="${paging.endPage}">
                <c:choose>
                    <c:when test="${i == paging.curPage}">
                        <li class="page-item active"><a class="page-link" href="#">${i}</a></li>
                    </c:when>
                    <c:otherwise>
                        <li class="page-item">
                            <a class="page-link" href="?curPage=${i}&searchType=${searchType}&searchValue=${searchValue}">${i}</a>
                        </li>
                    </c:otherwise>
                </c:choose>
            </c:forEach>

            <c:if test="${paging.nextBlockPage > 0}">
                <li class="page-item">
                    <a class="page-link" href="?curPage=${paging.nextBlockPage}&searchType=${searchType}&searchValue=${searchValue}">다음</a>
                </li>
            </c:if>
        </ul>
    </nav>

</div>
</body>
</html>
