<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<!DOCTYPE html>
<html>
<head>

<%@ include file="/WEB-INF/views/include/head3.jsp" %>
<script type="text/javascript">
$(function(){
	if(icia.common.type(window.opner) == "object")
	{
		if(icia.common.type(window.opner.fn_kakaoPayResult) == "function")
		{
			window.opner.fn_kakaoPayResult(${code}, "${msg}");
		}
		else
		{
			alert("${msg}");
			window.close();
		}
	}
	else
	{
		alert("${msg}");
		window.close();
	}
});
</script>
</head>
<body>

</body>
</html>