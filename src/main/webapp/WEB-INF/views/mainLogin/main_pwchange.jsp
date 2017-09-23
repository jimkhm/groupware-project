<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>비밀번호 변경</title>
<link rel="stylesheet" href="http://www.w3schools.com/lib/w3.css">
<link rel="stylesheet" href="http://www.w3schools.com/lib/w3-theme-green.css">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<script src="./resources/js/jquery-3.1.0.js"></script>
<script type="text/javascript">

function viewPWCheckBtn(formName){
	var btn = document.getElementById("pwCheckButton");
	var inputVal = document.forms[formName]["now_pw"].value;
	var defaultVal = document.forms[formName]["now_pw"].placeholder;
	if(inputVal != defaultVal){
		btn.className = btn.className.replace(" w3-blue", " w3-pink");
	} else {
		btn.className = btn.className.replace(" w3-pink", " w3-blue");
	}
    return;	
}

//pw체크
function checkPW(formName){ //true/false = isEmpPw(comp,id,pw)
	var btn = document.getElementById("pwCheckButton");
    var serData = $('#'+formName).serialize(); // 폼에 입력된 여러가지 값을 구분하여 가져옴
    if (btn.className.indexOf("w3-blue") == -1) {
    	// indexOf() method는 string안의 명시된 값의 첫 번째 발생의 위치를 반환한다.
    	//이 method는 찾는 값이 발생하지 않는다면 -1을 반환한다. 
	    $.post(
	    	"./mainPwCheck.em",
	    	serData,
	    	function(data) {
	    		var jsonData = JSON.parse(data);
	    		if(jsonData.result){
	    			alert("해당 비밀번호를 등록하시면 됩니다.");
	    			btn.className = btn.className.replace(" w3-pink", " w3-blue");
	    		} else {
	    			alert("등록되지 않은 비밀번호 입니다.");
	    			btn.className = btn.className.replace(" w3-blue", " w3-pink");
	    		}
	    	}
	    );
    }
}

 function dataSave(formName){
    var btn = document.getElementById("pwCheckButton");

	if (btn.className.indexOf("w3-blue") == -1) {
		alert("유효한 비밀번호인지 비밀번호 체크 버튼을 클릭하여 체크해 주세요.");
		return false;
	}
}

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
		document.getElementById(spanId).innerHTML = "("+ size + "자 이하 입력하셔야 합니다.)";
	} else {
		document.getElementById(spanId).innerHTML = "";
	}
}
//minlength 체크
function checkMinLength(formName, inputName, spanId, size){
	var str = document.forms[formName][inputName].value;
	var len = stringByteSize(str);
	if(len < size) {
		document.getElementById(spanId).innerHTML = "("+ size + "자 이상 입력하셔야 합니다.)";
	} else {
		document.getElementById(spanId).innerHTML = "";
	}
}
//max&min length 체크
function checkLength(formName, inputName, spanId, sizeMin, sizeMax){
	var str = document.forms[formName][inputName].value;
	var len = stringByteSize(str);
	if(len < sizeMin) {
		document.getElementById(spanId).innerHTML = "("+sizeMin + "자 이상 입력하셔야 합니다.)";
	} else if(len > sizeMax) {
		document.getElementById(spanId).innerHTML = "("+sizeMax + "자 이하 입력하셔야 합니다.)";
	} else {
		document.getElementById(spanId).innerHTML = "";
	}
}

// 비밀번호 안전도 체크
function passwordStrength(new_pw) {
	
	var desc = [{'width':'0px'}, {'width':'30%'}, {'width':'70%'}, {'width':'100%'}];
	var descClass = ['', 'progress-bar-danger', 'progress-bar-warning', 'progress-bar-success'];
	var score = 0;
	

	// 숫자만, 영어만, 특수문자만 , 영 대소문자만 입력할 때
	if ((new_pw.length > 3) && (new_pw.match(/[0-9]/) || 
		new_pw.match(/[!#$%&()*+,./:;<=>?@＼^_`|~]/) ||
		new_pw.match(/[a-zA-Z]/))){
		score++;
	}
	// 숫자+영어, 숫자+ 특수문자, 영어+특수문자	
	if ((new_pw.match(/[0-9]/) && new_pw.match(/[a-zA-Z]/)) || 
		(new_pw.match(/[0-9]/) && new_pw.match(/[!#$%&()*+,./:;<=>?@＼^_`|~]/)) ||	
		(new_pw.match(/[a-zA-Z]/) && new_pw.match(/[!#$%&()*+,./:;<=>?@＼^_`|~]/))){
		score++;
	} 
	// 특수문자+영 대소문자+ 숫자 = 높음
	if (new_pw.match(/[!#$%&()*+,./:;<=>?@＼^_`|~]/) && 
		new_pw.match(/[a-zA-Z]/) && new_pw.match(/[0-9]/)){
		score++;
	}
	
	// display indicator
	$("#jak_pstrength").removeClass(descClass[score-1]).addClass(descClass[score]).css(desc[score]);
}

jQuery(document).ready(function(){
	/* jQuery("#oddpass").focus(); */
	jQuery("#new_pw").keyup(function() {
	  passwordStrength(jQuery(this).val());
	});
});




function pwCheck(){
	
	var new_pwObj = document.getElementById("new_pw");
	var ok_pwObj = document.getElementById("pw_ok");

	// 앞 뒤 공백 제거
	new_pwObj.value = new_pwObj.value.trim();
	ok_pwObj.value = ok_pwObj.value.trim();
	
	var npw = new_pwObj.value
	var pwok = ok_pwObj.value
	
	var pattern1 = /[0-9]/g;
	var pattern2 = /[a-zA-Z]/g;
	var pattern3 = /[!#$%&()*+,./:;<=>?@＼^_`|~]/g; 
	
	if(!pattern1.test(npw)|| !pattern2.test(npw) || !pattern3.test(npw) || npw.length<4 || npw.length>20){
 		alert("비밀번호는 영문 대소문자, 숫자 및 특수문자를 혼합하여 4~20자 이상 입력하셔야 합니다.");
 		pwform.new_pw.value="";
		pwform.pw_ok.value="";
		pwform.new_pw.focus();
		return false;
	} 
	if(npw != pwok){
		alert("새 비밀번호와 비밀번호 확인이 일치하지 않습니다.");
		pwform.new_pw.value="";
		pwform.pw_ok.value="";
		pwform.new_pw.focus();
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

input[type=password] {
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
}

.cancelbtn {
    width: auto;
    padding: 5px 13px;
    background-color: #f44336;
}

.container {
    padding: 8px;
    text-align: left;
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
<body class="w3-container w3-light-grey">

<br>
<form action="./MainPwChange.em" name="pwform" id="pwform" method="post"
		onsubmit="return pwCheck();" class="w3-container w3-card-4 w3-light-grey"  >
	
   <div class="btnCancel" style="background-color:#f1f1f1">
   
    <span class="psw" ><b>&nbsp;비밀번호 변경</b></span>
    <button type="button" class="cancelbtn" onclick="windowclose()" 
    		class="w3-border w3-border-blank w3-round-xlarge "  >닫기</button>
  </div>
  
  <hr style="border: 1px solid silver;" width="100%"> <!-- 선긋기 -->
	
  <div class="container" align="left">
  
    <label class="w3-label" style="font-weight: bold;" >기존 비밀번호</label><br>
    <input type="password" id="emp_pw" name="now_pw" value=""
    	style="width: 330px"  placeholder="Enter Password" required
    	onchange="viewPWCheckBtn('pwform')" >
	<button type="button" class="w3-btn w3-blue w3-border w3-border-blank w3-round-xlarge w3-small "
	id="pwCheckButton" onclick="return checkPW('pwform');">비밀번호 체크</button>
	
	<br>
    <label class="w3-label" style="font-weight: bold;" >새 비밀빈호
    <span id="w_new_pw" class="w3-text-red"></span></label>
    <input type="password" name="new_pw" id="new_pw" value=""
      placeholder="Enter Password" required
	  onkeyup="return checkLength('pwform','new_pw','w_new_pw',4,20);" 
	  onchange="return checkLength('pwform','new_pw','w_new_pw',4,20);">
      
      <!-- 비밀번호 안전도 --> 
	<div class="progress" >
          <div id="jak_pstrength" class="progress-bar" role="progressbar" aria-valuenow="0" 
          aria-valuemin="0" aria-valuemax="100" style="width: 0%">비밀번호 안전도</div>
   </div>

	
    <label  class="w3-label" style="font-weight: bold;">새 비밀빈호 확인
    <span id="w_pw_ok" class="w3-text-red"></span></label>
    <input type="password" name="pw_ok" id="pw_ok"  value=""
     placeholder="Enter Password"  required
    onkeyup="return checkLength('pwform','pw_ok','w_pw_ok',4,20);"
	onchange="return checkLength('pwform','pw_ok','w_pw_ok',4,20);">   
        
    <button type="submit"  class="w3-border w3-border-blank" 
    style="width: 100%" onclick="return dataSave('pwform');">확인</button>
  </div>
  
</form>


</body>
</html>




















