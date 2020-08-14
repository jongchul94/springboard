<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	request.setCharacterEncoding("UTF-8");
	String cp = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>

<link rel="stylesheet" href="/springboard/resources/css/style.css" type="text/css"/>
<link rel="stylesheet" href="/springboard/resources/css/list.css" type="text/css"/>

</head>
<body>

<div id="bbsList">

<div align="right"> 
	<a href="login.action">로그인</a> / 
	<a href="regist.action">회원가입</a> 
</div><br>

	<div id="bbsList_title">
	회원가입 완료(Spring)
	</div>
	
	<div align="center"><h1>회원가입이 완료되었습니다. 메인페이지에서 로그인해주세요.</h1></div>
	<br/><br/>
	<div align="center"><h2><a href="list.action">메인페이지</a></h2></div>
	
</div>

</body>
</html>

