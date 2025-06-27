<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="ko">
<head>
  <%@ include file="/WEB-INF/views/include/head3.jsp" %>
  <title>주문 내역 · Ring Ring</title>
  <style>
    body {
      font-family: 'Noto Sans KR', sans-serif;
      background-color: #fff;
      color: #111;
      margin: 0;
      padding: 0;
    }

    .order-wrapper {
      max-width: 1000px;
      margin: 60px auto;
      padding: 20px;
    }

    .order-date {
      font-size: 16px;
      font-weight: bold;
      margin-top: 40px;
      margin-bottom: 10px;
      border-bottom: 1px solid #000;
      padding-bottom: 6px;
    }

    .order-box {
      border: 1px solid #ddd;
      border-radius: 10px;
      margin-bottom: 30px;
      padding: 20px;
      background-color: #fafafa;
    }

    .order-info {
      display: flex;
      gap: 20px;
      align-items: center;
    }

    .order-info img {
      width: 100px;
      height: 100px;
      object-fit: cover;
      border-radius: 8px;
      border: 1px solid #ccc;
    }

    .order-details {
      flex: 1;
    }

    .product-name {
      font-size: 16px;
      font-weight: bold;
      margin-bottom: 6px;
    }

    .product-option {
      font-size: 14px;
      color: #666;
    }

    .product-price {
      margin-top: 10px;
      font-size: 16px;
      font-weight: 600;
      color: #000;
    }
  </style>
</head>
<body>
<%@ include file="/WEB-INF/views/include/navigation3.jsp" %>

<div class="order-wrapper">
  <h2>ORDER LIST</h2>

  <c:if test="${empty orderList}">
    <p>결제 완료된 주문이 없습니다.</p>
  </c:if>

  <c:forEach var="order" items="${orderList}">
    <div class="order-date">${order.orderDate}</div>
    <div class="order-box">
      <c:forEach var="detail" items="${order.detailList}">
        <div class="order-info">
          <img src="/resources/upload/small/${detail.productImage}" alt="${detail.productName}">
          <div class="order-details">
            <div class="product-name">${detail.productName}</div>
            <div class="product-option">${detail.quantity}개</div>
            <div class="product-price">
              &#8361;<fmt:formatNumber value="${detail.totalPrice}" type="number"/>
            </div>
          </div>
        </div>
      </c:forEach>
    </div>
  </c:forEach>
</div>

</body>
</html>
