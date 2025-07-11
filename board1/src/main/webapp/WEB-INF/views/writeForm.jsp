<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h2>게시판 글쓰기 폼</h2>
	<form action="write" method="post">
		작성자 : <input type="text" name="writer" size="50"><br>
		제목 : <input type="text" name="title" size="100"><br>
		내용 : <input type="text" name="content" size="100"><br>
		<input type="submit" var="작성">
	</form>
</body>
</html>