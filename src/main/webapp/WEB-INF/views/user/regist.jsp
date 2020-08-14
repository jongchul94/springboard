<%@ page contentType="text/html; charset=UTF-8"%>
<%
	request.setCharacterEncoding("UTF-8");
	String cp = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>회 원 가 입</title>

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
		
		str2 = f.userPwd_ok.value;
		str2 = str2.trim();
		if(!str2){
			alert("\n비밀번호가 일치하지 않습니다.");
			f.userPwd_ok.focus();
			return;
		}
		if(str!=str2){
			alert("\n비밀번호가 일치하지 않습니다.");
			f.userPwd_ok.focus();
			return;
		}
		f.userPwd_ok.value = str;
		
		
		str = f.userName.value;
		str = str.trim();
		if(!str){
			alert("\n이름을 입력하세요.");
			f.userName.focus();
			return;
		}
		f.userName.value = str;
		
		
		str = f.userEmail.value;
		str = str.trim();
		
		if(!str){
			alert("E-Mail을 입력하세요.");
			f.userEmail.focus();
			return;
		}
		f.userEmail.value = str;
		
		if(str){
			
			if(!isValidEmail(str)){
				alert("\n정상적인 E-Mail을 입력하세요.");
				f.userEmail.focus();
				return;
			}
		}
		
		
		
		str = f.userPhone.value;
		str = str.trim();
		if(!str){
			alert("\n전화번호를 입력하세요.");
			f.userPhone.focus();
			return;
		}
		f.userPhone.value = str;
		
		
		f.action = "<%=cp%>/regist_ok.action";
		f.submit();
		
	}

</script>

</head>
<body>

<div id="bbs">
	<div id="bbs_title">
	회 원 가 입(Spring)	
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
		
		<div class="bbsCreated_bottomLine">
			<dl>
				<dt>비밀번호 확인</dt>
				<dd>
					<input type="password" name="userPwd_ok" size="35" maxlength="20" class="boxTF"/>
				</dd>							
			</dl>		
		</div>
		
		<div class="bbsCreated_bottomLine">
			<dl>
				<dt>이름</dt>
				<dd>
					<input type="text" name="userName" size="35" maxlength="20" class="boxTF"/>
				</dd>							
			</dl>		
		</div>
		
		<div class="bbsCreated_bottomLine">
			<dl>
				<dt>E-Mail</dt>
				<dd>
					<input type="text" name="userEmail" size="35" maxlength="30" class="boxTF"/>
				</dd>							
			</dl>		
		</div>
		
		<div class="bbsCreated_noLine">
			<dl>
				<dt>전화번호</dt>
				<dd>
					<input type="text" name="userPhone" size="35" maxlength="15" class="boxTF"/>
				</dd>
			</dl>
		</div>
	
	</div>	
	
	<div id="bbsCreated_footer">
	<input type="button" value=" 회원가입 " class="btn2" 
	onclick="sendIt();"/>
	<input type="button" value=" 취소 " class="btn2" 
	onclick="javascript:location.href='<%=cp%>/list.action';"/>	
	</div>
	
	</form>
	
</div>

</body>
</html>


