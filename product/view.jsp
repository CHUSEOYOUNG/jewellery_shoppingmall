<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>
<html lang="ko">
<head>
  <%@ include file="/WEB-INF/views/include/head3.jsp" %>
  <title>${product.productName} · Ring Ring</title>

  <style>
    body {
      font-family: 'Noto Sans KR', 'Playfair Display', serif;
      background-color: #fff;
      color: #111;
      margin: 0;
      padding: 0;
    }
    .product-detail-container {
      max-width: 1000px;
      margin: 60px auto;
    }
    .product-top {
      display: flex;
      gap: 60px;
      align-items: flex-start;
    }
    .product-image {
      width: 500px;
      border-radius: 16px;
      box-shadow: 0 0 10px rgba(0,0,0,0.05);
    }
    .product-info {
      flex: 1;
    }
    .product-info h2 {
      font-family: 'Playfair Display', serif;
      font-size: 36px;
      margin-bottom: 16px;
    }
    .product-price {
      font-size: 28px;
      font-weight: bold;
      color: #000;
      margin-bottom: 16px;
    }
    .product-desc {
      font-size: 16px;
      line-height: 1.8;
      color: #444;
      margin-bottom: 24px;
    }
    .btn-dark-outline {
      border: 1px solid #000;
      background-color: transparent;
      color: #000;
    }
    .btn-dark-outline:hover {
      background-color: #000;
      color: #fff;
    }
    .button-group {
      display: flex;
      gap: 16px;
      margin-bottom: 24px;
    }
    .shipping-info {
      font-size: 14px;
      color: #666;
      line-height: 1.6;
    }
    .tab-container {
      margin-top: 80px;
    }
    .tabs {
      display: flex;
      border-bottom: 2px solid #eee;
    }
    .tab {
      flex: 1;
      text-align: center;
      padding: 16px;
      font-weight: 600;
      font-size: 18px;
      cursor: pointer;
      border-bottom: 2px solid transparent;
      transition: 0.3s;
    }
    .tab.active {
      border-color: #000;
      color: #000;
    }
    .tab.inactive {
      color: #aaa;
    }
    .tab-content {
      padding: 30px 0;
    }
    .detail-images img {
      width: 100%;
      margin-bottom: 30px;
      border-radius: 10px;
      box-shadow: 0 0 8px rgba(0,0,0,0.1);
    }
    .review-item {
      border-bottom: 1px solid #ddd;
      padding: 20px 0;
    }
  </style>
</head>
<body>

<%@ include file="/WEB-INF/views/include/navigation3.jsp" %>

<c:choose>
  <c:when test="${product != null}">
    <div class="product-detail-container">

      <!-- 상단 대표 영역 -->
      <div class="product-top">
        <img src="/resources/upload/small/${product.productImage}" class="product-image" alt="${product.productName}">

        <div class="product-info">
          <h2>${product.productName}</h2>
          <p class="product-price">&#8361;<fmt:formatNumber value="${product.productPrice}" type="number" /></p>
          <p class="product-desc">${product.productDesc}</p>

          <div class="button-group">
            <form action="/product/cartInsertProc" method="post">
              <input type="hidden" name="productId" value="${product.productId}">
              <input type="hidden" name="quantity" value="1">
              <button type="submit" class="btn btn-dark px-4">장바구니</button>
            </form>
            <button type="button" class="btn btn-dark-outline px-4 btn-buy">바로 구매</button>
          </div>
<!-- 수량 선택 필드 추가 -->
<form action="/cart/cartInsertProc" method="post" class="d-flex align-items-center">
  <input type="hidden" name="productId" value="${product.productId}">
  
  <label for="quantity" class="me-2">수량</label><br>
  <input type="number" name="quantity" id="quantity" value="1" min="1" class="form-control me-3" style="width: 80px;">

</form>
<br>
          <div class="shipping-info">
            • 오후 2시 이전 결제 시 당일 출고<br>
            • 무료배송 / 7일 이내 배송<br>
            • 고객센터: 1544-1234
          </div>
        </div>
      </div>

      <!-- 상세정보 & 후기 -->
      <div class="tab-container">
        <div class="tabs">
          <div id="tab-detail" class="tab active">상세 정보</div>
          <div id="tab-review" class="tab inactive">상품 후기 (${fn:length(reviewList)})</div>
        </div>

        <div id="content-detail" class="tab-content">
          <c:forEach var="file" items="${productFileList}">
            <img src="/resources/upload/big/${file.productFileName}" alt="상세 이미지">
          </c:forEach>
        </div>

        <div id="content-review" class="tab-content" style="display: none;">
          <c:if test="${not empty reviewList}">
            <c:forEach var="review" items="${reviewList}">
              <div class="review-item">
                <b>${review.userName}</b> · <span style="color:#999;">${review.regDate}</span>
                <div class="mt-2">${review.content}</div>
              </div>
            </c:forEach>
          </c:if>
          <c:if test="${empty reviewList}">
            <p>등록된 리뷰가 없습니다.</p>
          </c:if>
        </div>

      </div>

    </div>
  </c:when>

  <c:otherwise>
    <script>alert("${errorMessage}"); history.back();</script>
  </c:otherwise>
</c:choose>

<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script>

$(function () {
  $("#tab-detail").click(function () {
    $(this).addClass("active").removeClass("inactive");
    $("#tab-review").addClass("inactive").removeClass("active");
    $("#content-detail").show();
    $("#content-review").hide();
  });

  $("#tab-review").click(function () {
    $(this).addClass("active").removeClass("inactive");
    $("#tab-detail").addClass("inactive").removeClass("active");
    $("#content-detail").hide();
    $("#content-review").show();
  });
});

let kakaoPayPopup = null;


function fn_kakaoPayReady(productId) {
  $.ajax({
    type: "POST",
    url: "/kakao/readyAjax2",
    data: { productId: productId },
    dataType: "json",
    beforeSend: function (xhr) {
      xhr.setRequestHeader("AJAX", "true");
    },
    success: function (res) {
		if(res.code == 0)
		{
			let _width = 500;
			let _height = 500;
			
			let _left = Math.ceil((window.screen.width - _width) / 2);
			let _top = Math.ceil((window.screen.height - _height) / 2);
			
			kakaoPayPopup = window.open(res.data.next_redirect_pc_url, "카카오페이 결제",
				"width=" + _width + ", height=" + _height + ", left=" + _left + ", top=" + _top + 
				", resizable=false, scrollbars=false, status=false, titlebar=false, toolbar=false, menubar=false");
		}
      else {
        alert(res.msg);
      }
    },
    error: function () {
      alert("카카오페이 결제 준비 중 오류가 발생했습니다.");
    }
  });
}


function fn_kakaoPayResult(code, msg) {

  if (kakaoPayPopup && !kakaoPayPopup.closed) kakaoPayPopup.close();
  kakaoPayPopup = null;

  alert(msg);

 
  if (code === 0) {
    location.href = "/mypage/orderList";
  }
}


$(".btn-buy").on("click", function () {
  fn_kakaoPayReady("${product.productId}");
});
</script>


</body>
</html>
