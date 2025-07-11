<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시판 list 페이지</title>
</head>
<body>
	<c:forEach items="${list }" var="dto">
		${dto.id } - ${dto.writer } - <a href="view?id=${dto.id }">${dto.title }</a> - ${dto.content }<br>
	</c:forEach>
</body>
</html>