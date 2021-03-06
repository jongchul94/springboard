<%@ page contentType="text/html; charset=UTF-8"%>
<%
	request.setCharacterEncoding("UTF-8");
	String cp = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>로 그 인</title>

<link rel="stylesheet" href="/springboard/resources/css/style.css" type="text/css"/>
<link rel="stylesheet" href="/springboard/resources/css/created.css" type="text/css"/>

<script type="text/javascript" src="/springboard/resources/js/util.js"></script>

<script type="text/javascript">

	function sendIt(){
		
		f = document.myForm;
		
		str = f.userID.value;
		str = str.trim();
		if(!str){
			alert("\n아이디를 입력하세요.");
			f.userID.focus();
			return;
		}
		f.userID.value = str;
		
		str = f.userPwd.value;
		str = str.trim();
		if(!str){
			alert("\n비밀번호를 입력하세요.");
			f.userPwd.focus();
			return;
		}
		f.userPwd.value = str;
		
		
		f.action = "<%=cp%>/login_ok.action";
		f.submit();
		
	}

</script>

</head>
<body>

<div id="bbs">
	<div id="bbs_title">
	로 그 인(Spring)	
	</div>
	
	<form action="" name="myForm" method="post">
	<div id="bbsCreated">
		<div class="bbsCreated_bottomLine">
			<dl>
				<dt>아이디</dt>
				<dd>
					<input type="text" name="userID" size="35" maxlength="20" class="boxTF"/>
				</dd>							
			</dl>		
		</div>
		
		<div class="bbsCreated_bottomLine">
			<dl>
				<dt>비밀번호</dt>
				<dd>
					<input type="password" name="userPwd" size="35" maxlength="20" class="boxTF"/>
				</dd>							
			</dl>		
		</div>
		
	</div>	
	<br/>
	<p align="center"><b>${message }</b></p>
	
	<div id="bbsCreated_footer">
	<input type="button" value=" 로그인 " class="btn2" 
	onclick="sendIt();"/>
	<input type="button" value=" 취소 " class="btn2" 
	onclick="javascript:location.href='<%=cp%>/list.action';"/>	
	</div>
	
	</form>
	
</div>

</body>
</html>


