<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String imgName = (String) request.getAttribute("imgName");
	boolean noImage = (Boolean) request.getAttribute("noImage");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>정보수정</title>
<link rel="stylesheet" href="http://www.w3schools.com/lib/w3.css">
<link rel="stylesheet" href="http://www.w3schools.com/lib/w3-theme-green.css">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<script src="./resources/js/jquery-3.1.0.js"></script>
<script type="text/javascript">
$(function(){
	$("#iconChange").hover(function(){
		$("#spanIconTag").show();
	});
	
    $("#iconChange").mouseleave(function(){
		$("#spanIconTag").hide();
	});
});
//UTF-8 관련 정보는 http://ko.wikipedia.org/wiki/UTF-8 를 참조해서 계산했다.
//UTF-8 문자열의 Byte 크기 구하기.
//주의!! : UTF-8 웹 페이지에서만 정상작동한다. EUC-KR에서는 작동하지 않는다.
//참고 자료 : http://ko.wikipedia.org/wiki/UTF-8 위키 백과의 UTF-8 항목
//테스트 환경 : IE6, IE7, FF 2.0, Safari 3, Opera 9.2
//각 문자의 유니코드 코드를 분석하여, UTF-8로 변환시 차지하는
//byte 수를 리턴한다.
function charByteSize(ch) {
	if (ch == null || ch.length == 0) {
		return 0;
	}
	
	var charCode = ch.charCodeAt(0);
	
	if (charCode <= 0x00007F) {
		return 1;
	} else if (charCode <= 0x0007FF) {
		return 2;
	} else if (charCode <= 0x00FFFF) {
		return 3;
	} else {
		return 4;
	}
}
//문자열을 UTF-8로 변환했을 경우 차지하게 되는 byte 수를 리턴한다.
function stringByteSize(str) {
	if (str == null || str.length == 0) {
		return 0;
	}
	var size = 0;
	
	for (var i = 0; i < str.length; i++) {
		size += charByteSize(str.charAt(i));
	}
	return size;
}
//maxlength 체크
function checkMaxLength(formName, inputName, spanId, size){
	var str = document.forms[formName][inputName].value;
	var len = stringByteSize(str);
	if(len > size) {
		document.getElementById(spanId).innerHTML = "("+ size + "자 초과입니다.)";
	} else {
		document.getElementById(spanId).innerHTML = "";
	}
}
//minlength 체크
function checkMinLength(formName, inputName, spanId, size){
	var str = document.forms[formName][inputName].value;
	var len = stringByteSize(str);
	if(len < size) {
		document.getElementById(spanId).innerHTML = "("+ size + "자 미만입니다.)";
	} else {
		document.getElementById(spanId).innerHTML = "";
	}
}
//max&min length 체크
function checkLength(formName, inputName, spanId, sizeMin, sizeMax){
	var str = document.forms[formName][inputName].value;
	var len = stringByteSize(str);
	if(len < sizeMin) {
		document.getElementById(spanId).innerHTML = "("+ sizeMin + "자 미만입니다.)";
	} else if(len > sizeMax) {
		document.getElementById(spanId).innerHTML = "("+ sizeMax + "자 초과입니다.)";
	} else {
		document.getElementById(spanId).innerHTML = "";
	}
}


function infoData(formName) {
	
	var reg1 = /[0-9]{0,2}-?[0-9]{2,3}-[0-9]{3,4}-[0-9]{4}/; // 전화번호
	var reg2 = /^((\w|[\-\.])+)@((\w|[\-\.][^(\.)\1])+)\.([A-Za-z]+)$/; // 이메일
	
	document.forms[formName]["emp_phone"].value = document.forms[formName]["emp_phone"].value.trim();
    document.forms[formName]["emp_email"].value = document.forms[formName]["emp_email"].value.trim();
    document.forms[formName]["emp_addr"].value = document.forms[formName]["emp_addr"].value.trim();
    
    var phone = document.forms[formName]["emp_phone"].value; // 전화번호
    var email = document.forms[formName]["emp_email"].value; // 이메일
    var address = document.forms[formName]["emp_addr"].value; // 주소
    
    if (phone != null && phone != "" && phone.search(reg1) == -1) {
    	alert("전화 번호는 숫자와 - 로 입력해 주세요.");
    	document.forms[formName]["emp_phone"].value="";
    	document.forms[formName]["emp_phone"].focus();
    	return false;
    }else if (email != null && email != "" && email.search(reg2) == -1) {
    	alert("이메일은 <영문자 숫자 @ . - _> 로 입력해 주세요.");
    	document.forms[formName]["emp_email"].value="";
    	document.forms[formName]["emp_email"].focus();
    	return false;
    }
    return true;
}


function windowclose(){ // 닫기버튼 종료
	opener.location.reload();
	self.close();
}

</script>
<style>
form {
    border: 3px solid #f1f1f1;
    width: 500px;
}

input[type=text]{
    width: 100%;
    padding: 12px 20px;
    margin: 8px 0;
    display: inline-block;
    border: 1px solid #ccc;
    box-sizing: border-box;
}

button {
    background-color: #4CAF50;
    color: white;
    padding: 14px 20px;
    margin: 8px 0;
    border: none;
    cursor: pointer;
    width: 100%;
}

.cancelbtn {
    width: auto;
    padding: 5px 13px;
    background-color: #f44336;
}

.imgcontainer {
    text-align: center;
    margin: 24px 0 12px 0;
}

img.avatar {
    width: 20%;
    border-radius: 50%;
}

.container {
    padding: 8px;
}

.btnCancel {
	padding: 3px;
	text-align: right;
}

span.psw {
    float: left;
    padding-top: 8px;
 	font-size: 22px;
}

/* Change styles for span and cancel button on extra small screens */
@media screen and (max-width: 300px) {
    span.psw {
       display: block;
       float: none;
    }
    .cancelbtn {
       width: 50px;
    }
}
</style>
</head>
<body  class="w3-container w3-light-grey">

    <br>
	<form action="./MainInfoChange.em" name="infoForm" onsubmit="return infoData('infoForm')"
	class="w3-container w3-card-4 w3-light-grey">
	
	 <div class="btnCancel" style="background-color:#f1f1f1" >
   
    	<span class="psw" ><b>&nbsp;정보수정</b></span>
   		<button type="button" class="cancelbtn" onclick="windowclose()" 
   		class="w3-border w3-border-blank w3-round-xlarge "  >닫기</button>
  	</div>
	
	<hr style="border: 1px solid silver;" width="100%"> <!-- 선긋기 -->
	
	  <div class="imgcontainer">
	    
	<% if (noImage) { %>
		<img src="./resources/images/img_avatar3.png" alt="Profile Image" class="avatar">			
	<% } else { %>
		<img src="./resources/tempImg/<%= imgName %>" alt="Profile Image" class="avatar">
	<% } %>
	    
	    <a href="./MainImgChange.em"><i id="iconChange" class="fa fa-user-circle" style="font-size:24px;color:gray;position:absolute;right:170px;"></i>
	    <span id="spanIconTag" class="w3-text w3-tag w3-round w3-green " style="display : none;position:absolute;right:30px;">프로필 사진 변경</span>
	    </a>
	  </div>
	
	  <div class="container">
  		
	    <label class="w3-label" style="font-weight: bold;">전화번호
	    <span id="w_emp_phone" class="w3-text-red"></span></label>
	    <input type="text"  name="emp_phone" value=""  id="selectedPhone" placeholder="Enter Phone" 
	    onkeyup="return checkMaxLength('infoForm','emp_phone','w_emp_phone',20);"
	 	onchange="return checkMaxLength('infoForm','emp_phone','w_emp_phone',20);" required/>
	
	    <label class="w3-label" style="font-weight: bold;">E-mail
	    <span id="w_emp_email" class="w3-text-red"></span></label>
	    <input type="text" name="emp_email" value="" id="selectedEmail" placeholder="Enter e-mail" 
	    onkeyup="return checkMaxLength('infoForm','emp_email','w_emp_email',100);"
	 	onchange="return checkMaxLength('infoForm','emp_email','w_emp_email',100);"required/>
	    
	    <label class="w3-label" style="font-weight: bold;">주소
	    <span id="w_emp_addr" class="w3-text-red"></span></label>
	    <input type="text" name="emp_addr" value="" id="selectedAddr" placeholder="Enter address"  
	    onkeyup="return checkMaxLength('infoForm','emp_addr','w_emp_addr',200);"
	 	onchange="return checkMaxLength('infoForm','emp_addr','w_emp_addr',200);" required/>    
	        
	    <button type="submit" class="w3-border w3-border-blank " 
	  	 style="width: 100%" >확인</button>
	  </div>
	
	</form>
    
</body>
</html>







