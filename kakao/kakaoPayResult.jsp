<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<!DOCTYPE html>
<html>
<head>
  <script>
    window.onload = function() {
      if (window.opener && typeof window.opener.fn_kakaoPayResult === "function") {
        window.opener.fn_kakaoPayResult(${code}, "${msg}");
        window.close();
      } else {
        alert("${msg}");
        window.close();
      }
    };
  </script>
</head>
<body></body>
</html>

