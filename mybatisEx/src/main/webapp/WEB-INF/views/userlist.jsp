<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h2> 사용자 정보</h2>
	<c:forEach items="${users }" var="user">
		${user.id } - ${user.name }<br>
	</c:forEach>
</body>
</html>