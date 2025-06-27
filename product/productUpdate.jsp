<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8" />
  <title>상품 수정 · Ring Ring</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.6/dist/css/bootstrap.min.css" rel="stylesheet">
  <style>
    body { font-family: 'Noto Sans KR', 'Playfair Display', serif; background: #fff; color: #111; }
    .container { max-width: 900px; margin: 60px auto; padding: 30px; background: #f9f9f9; border-radius: 12px; }
    .form-control { border-radius: 8px; }
    .btn-dark { background: #000; color: #fff; border: none; }
    .btn-dark:hover { background: #222; }
    .img-preview { max-width: 200px; margin-top: 10px; }
  </style>
</head>
<body>

<div class="container">
  <h3>📦 상품 수정</h3>

  <form id="productForm" action="/product/productUpdateProc" method="post" enctype="multipart/form-data">
    <input type="hidden" name="productId" value="${product.productId}" />

    <div class="mb-3">
      <label class="form-label">상품명</label>
      <input type="text" class="form-control" name="productName" value="${product.productName}" required />
    </div>

    <div class="mb-3">
      <label class="form-label">카테고리</label>
      <select class="form-select" name="categoryId" required>
        <option value="">선택</option>
        <option value="1" ${product.categoryId == '1' ? 'selected' : ''}>반지</option>
        <option value="2" ${product.categoryId == '2' ? 'selected' : ''}>목걸이</option>
        <option value="3" ${product.categoryId == '3' ? 'selected' : ''}>귀걸이</option>
      </select>
    </div>

    <div class="mb-3">
      <label class="form-label">가격</label>
      <input type="number" class="form-control" name="productPrice" value="${product.productPrice}" min="0" required />
    </div>

    <div class="mb-3">
      <label class="form-label">재고</label>
      <input type="number" class="form-control" name="productStock" value="${product.productStock}" min="0" required />
    </div>

    <div class="mb-3">
      <label class="form-label">상품 설명</label>
      <textarea class="form-control" name="productDesc" rows="5" required>${product.productDesc}</textarea>
    </div>

    <div class="mb-3">
      <label class="form-label">판매 상태</label><br/>
      <input type="radio" name="productOnoff" value="Y" ${product.productOnoff == 'Y' ? 'checked' : ''} /> 판매중
      <input type="radio" name="productOnoff" value="N" ${product.productOnoff == 'N' ? 'checked' : ''} /> 판매중지
    </div>

    <!-- ✅ 대표 이미지 변경 -->
    <div class="mb-3">
      <label class="form-label">대표 이미지</label>
      <input type="file" class="form-control" name="uploadFile" />
      <div>현재 이미지: ${product.productImage}</div>
    </div>

    <!-- ✅ 상세 이미지 추가 -->
    <div class="mb-3">
      <label class="form-label">상세 이미지 추가</label>
      <input type="file" class="form-control" name="productDetailFiles" multiple />
    </div>

    <div class="text-end">
      <button type="submit" class="btn btn-dark px-4">수정</button>
      <a href="/product/productAdminList" class="btn btn-outline-dark px-4 ms-2">리스트</a>
    </div>
  </form>
</div>

</body>
</html>
