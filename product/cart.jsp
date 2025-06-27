<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
  <%@ include file="/WEB-INF/views/include/head3.jsp" %>
  <title>장바구니 · Ring Ring</title>
  <style>
    body { margin: 0; font-family: 'Noto Sans KR', sans-serif; background: #fff; color: #111; }
    .wrapper { max-width: 1000px; margin: 60px auto; padding: 20px; }
    .title { font-family: 'Playfair Display', serif; font-size: 32px; font-weight: 600; text-align: center; margin-bottom: 40px; }

    .cart-item {
      display: flex;
      align-items: center;
      border: 1px solid #ddd;
      border-radius: 12px;
      padding: 20px;
      margin-bottom: 24px;
      transition: box-shadow 0.2s ease;
    }
    .cart-item:hover {
      box-shadow: 0 6px 20px rgba(0,0,0,0.1);
    }
    .custom-checkbox {
      appearance: none;
      width: 20px;
      height: 20px;
      border: 2px solid #000;
      border-radius: 4px;
      margin-right: 18px;
      position: relative;
      cursor: pointer;
    }
    .custom-checkbox:checked {
      background-color: #000;
    }
    .custom-checkbox:checked::after {
      content: '✔';
      color: #fff;
      font-size: 14px;
      position: absolute;
      top: -2px;
      left: 3px;
    }
    .item-img {
      width: 100px;
      height: 100px;
      object-fit: cover;
      border-radius: 8px;
      border: 1px solid #ccc;
      margin-right: 20px;
    }
    .item-info {
      flex: 1;
    }
    .item-name {
      font-size: 18px;
      font-weight: 600;
      margin-bottom: 8px;
    }
    .item-meta {
      font-size: 14px;
      color: #666;
      display: flex;
      align-items: center;
      gap: 10px;
    }
    .quantity-box {
      display: flex;
      align-items: center;
      gap: 6px;
    }
    .quantity-box input[type="number"] {
      width: 60px;
      padding: 4px 6px;
      border: 1px solid #999;
      border-radius: 6px;
      font-size: 14px;
    }
    .btn-update, .btn-del {
      border: 1px solid #000;
      background: #000;
      color: #fff;
      padding: 6px 14px;
      border-radius: 8px;
      font-size: 14px;
      cursor: pointer;
    }
    .btn-update:hover, .btn-del:hover {
      background: #333;
    }
    .cart-actions {
      text-align: center;
      margin-top: 40px;
    }
    .btn-pay {
      padding: 12px 28px;
      font-size: 16px;
      font-weight: 600;
      border: none;
      border-radius: 8px;
      background: #111;
      color: #fff;
      transition: background 0.3s ease;
    }
    .btn-pay:hover {
      background: #000;
    }
    .empty {
      margin-top: 80px;
      text-align: center;
      color: #888;
      font-size: 16px;
    }
  </style>
</head>
<body>
<%@ include file="/WEB-INF/views/include/navigation3.jsp" %>

<div class="wrapper">
  <h1 class="title">CART</h1>

  <c:choose>
    <c:when test="${empty cartList}">
      <p class="empty">장바구니에 담긴 상품이 없습니다.</p>
    </c:when>

    <c:otherwise>
      <form id="payForm">
        <c:forEach var="c" items="${cartList}">
          <div class="cart-item">
            <input type="checkbox" name="cartIds" value="${c.cartId}" class="custom-checkbox">
            <img class="item-img" src="/resources/upload/small/${c.productImage}" alt="${c.productName}">
            <div class="item-info">
              <div class="item-name">${c.productName}</div>
              <div class="item-meta">
                <form action="/cart/update" method="post" style="display:inline-flex; align-items:center;">
                  <input type="hidden" name="cartId" value="${c.cartId}">
                  <div class="quantity-box">
                    <label>수량:</label>
                    <input type="number" name="quantity" value="${c.quantity}" min="1">
                    <button type="submit" class="btn-update">변경</button>
                  </div>
                </form>
                <span>· 등록일: ${c.regDate}</span>
              </div>
            </div>
            <form action="/cart/delete" method="post">
              <input type="hidden" name="cartId" value="${c.cartId}">
              <button type="submit" class="btn-del">삭제</button>
            </form>
          </div>
        </c:forEach>

        <div class="cart-actions">
          <button type="button" class="btn-pay" onclick="fn_kakaoCartPay()">선택 결제하기</button>
        </div>
      </form>
    </c:otherwise>
  </c:choose>
</div>

<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script>
  let kakaoPayPopup = null;

  function fn_kakaoCartPay() {
    const cartIds = [];
    document.querySelectorAll("input[name='cartIds']:checked").forEach(cb => {
      cartIds.push(cb.value);
    });

    if (cartIds.length === 0) {
      alert("결제할 상품을 선택해주세요.");
      return;
    }

    $.ajax({
      type: "POST",
      url: "/kakao/readyCartAjax",
      data: { cartIds: cartIds },
      traditional: true,
      dataType: "json",
      beforeSend: function (xhr) {
        xhr.setRequestHeader("AJAX", "true");
      },
      success: function (res) {
        if (res.code === 0) {
          let popupWidth = 500, popupHeight = 500;
          let left = Math.ceil((window.screen.width - popupWidth) / 2);
          let top = Math.ceil((window.screen.height - popupHeight) / 2);

          kakaoPayPopup = window.open(res.data.next_redirect_pc_url, "카카오페이 결제",
            `width=${popupWidth}, height=${popupHeight}, left=${left}, top=${top}`);
        } else {
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
    alert(msg);
    if (code === 0) {
      location.href = "/order/orderList";
    }
  }
</script>

</body>
</html>
