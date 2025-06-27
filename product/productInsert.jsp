<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>

<!DOCTYPE html>
<html lang="ko">
<%@ include file="/WEB-INF/views/include/adminHead.jsp" %>
<head>
  <meta charset="UTF-8" />
  <title>상품 등록 · 관리자</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.6/dist/css/bootstrap.min.css" rel="stylesheet">
  <link href="https://fonts.googleapis.com/css2?family=Playfair+Display:wght@700;900&family=Noto+Sans+KR&display=swap" rel="stylesheet">
  <style>
    body { font-family: 'Noto Sans KR', 'Playfair Display', serif; background-color: #fff; color: #111; }
    .write-container { max-width: 800px; margin: 60px auto; padding: 30px; background-color: #f9f9f9; border-radius: 12px; box-shadow: 0 0 10px rgba(0, 0, 0, 0.05); }
    .form-control { border-radius: 8px; }
    .btn-custom-dark { background-color: #000; color: #fff; border: none; }
    .btn-custom-dark:hover { background-color: #222; }
  </style>

  <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
  <script>
    $(function(){
      $("#productName").focus();

      $("#btnInsert").click(function(){
        if ($.trim($("#productName").val()) === "") { alert("상품명을 입력하세요."); $("#productName").focus(); return; }
        if ($.trim($("#productPrice").val()) === "") { alert("가격을 입력하세요."); $("#productPrice").focus(); return; }
        if ($.trim($("#productStock").val()) === "") { alert("재고 수량을 입력하세요."); $("#productStock").focus(); return; }
        if ($("#productImage").val() === "") { alert("상품 이미지를 업로드하세요."); return; }
        if ($.trim($("#productDesc").val()) === "") { alert("상품 설명을 입력하세요."); $("#productDesc").focus(); return; }
        $("#productForm").submit();
      });

      $("#btnList").click(function(){
        location.href = "/product/productAdminList";
      });
    });
  </script>
</head>

<body>
<%@ include file="/WEB-INF/views/include/adminNavigation.jsp" %>

<div class="container write-container">
  <h3 class="mb-4">📦 상품 등록</h3>

<form id="productForm" action="/product/insertProc" method="post" enctype="multipart/form-data">

    <!-- 관리자 정보 -->
    <input type="text" class="form-control mb-2" value="${admin.adminName}" readonly />
    <input type="text" class="form-control mb-4" value="${admin.adminEmail}" readonly />

    <!-- 상품명 -->
    <div class="mb-3">
        <label>상품명</label>
        <input type="text" class="form-control" id="productName" name="productName" required>
    </div>

    <!-- 카테고리 -->
    <div class="mb-3">
        <label>카테고리</label>
        <select class="form-select" id="categoryId" name="categoryId" required>
            <option value="">카테고리 선택</option>
            <option value="1">반지</option>
            <option value="2">목걸이</option>
            <option value="3">귀걸이</option>
        </select>
    </div>

    <!-- 가격 -->
    <div class="mb-3">
        <label>가격</label>
        <input type="number" class="form-control" name="productPrice" min="0" required>
    </div>

    <!-- 재고 -->
    <div class="mb-3">
        <label>재고 수량</label>
        <input type="number" class="form-control" name="productStock" min="0" required>
    </div>

    <!-- 대표 이미지 -->
    <div class="mb-3">
        <label>대표 이미지</label>
        <input type="file" class="form-control" name="uploadFile" accept="image/*" required>
    </div>

    <!-- 상세 이미지 -->
    <div class="mb-3">
        <label>상세 이미지</label>
        <input type="file" class="form-control" name="productDetailFiles" multiple accept="image/*">
    </div>

    <!-- 설명 -->
    <div class="mb-3">
        <label>상품 설명</label>
        <textarea class="form-control" name="productDesc" rows="5" required></textarea>
    </div>

    <!-- 판매 상태 -->
    <div class="mb-3">
        <label>판매 상태</label><br>
        <input type="radio" name="productOnoff" value="Y" checked> 판매중
        <input type="radio" name="productOnoff" value="N"> 판매중지
    </div>

    <!-- 버튼 -->
    <div class="text-end">
        <button type="submit" class="btn btn-dark">등록</button>
    </div>

</form>

</div>

</body>
</html>
