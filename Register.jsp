<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql" %>

<sql:setDataSource
	driver="com.mysql.jdbc.Driver"
	url="jdbc:mysql://cs3.calstatela.edu/cs3220stu111"
	user="cs3220stu111"
	password="iMG2nvUI" />
	
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
<meta charset="UTF-8">
<title>Register here</title>
</head>
<body>

	<h1 class="text-primary"><center>Cloud <small class="text-muted">CS 3220</small></center></h1><br>
	
	<form action="Cloud.jsp"><center>
		<input type="text" name="newusername" placeholder="Choose a username" /><br>	
		<input type="password" name="newpassword" placeholder="Choose a password"><br><br>
		<input class="btn btn-outline-primary" type="submit" name="submitbtn" value="Register"><br>
	</form>
	
	<c:if test="${not empty param.username}">
	<c:if test="${not empty param.password}">
			<sql:update>
				INSERT INTO users VALUES ("${param.username}","${param.password}")
			</sql:update> 
	</c:if>
	</c:if>

</body>
</html>