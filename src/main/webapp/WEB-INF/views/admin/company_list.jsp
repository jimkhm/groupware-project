<%--
 //******************************************************************************
//  프로그램ID : company_list.jsp
//  프로그램명 : 회사 정보
//  관련 DB 테이블 : COMPANY_INFO
//  기타 DB 테이블 : SEQ_INFO, EMPLOYEE_INFO
//  작  성  자 : 조 수 정 (Sujung Jo)
//  작  성  일 : 2016.09.23.
//--------------------------------------
//  수  정  자 : 조 수 정 (Sujung Jo)
//  수  정  일 : 2016.11.14.
//  변경 내용 : javascript validateForm() 패스워드 체크 로직 변경
//                  - 신규로 등록할 때는 null 이면 체크함. 기존 데이터가 변경되면(null 이 아니면) 그냥 update 이기 때문에 pass
//******************************************************************************
 --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%
// 1. 필수 입력 체크
// 2. 관리자 아이디 입력시 겹치지 않는지 체크 (테이블2군데 체크)
// 3. 화면에서 숫자랑 '-'만 입력되게 체크 [사업자등록번호,법인등록번호, 회사대표전화, 관리자연락처]
// 4. maxlength 체크
	boolean bData = false;
	String sComp_cd = (String) request.getSession().getAttribute("company"); // session company
	//String sComp_cd = "C001";
	//String sId = (String) request.getSession().getAttribute("id"); // session id 	
	String sDefaultComp = "";
	if(null != request.getAttribute("defaultComp")){
		sDefaultComp = (String) request.getAttribute("defaultComp");
	}
	String newComp = "";
	List list = (List) request.getAttribute("list");
	int listCount = list.size();
	if(listCount > 0){
		bData = true;		
	}
	String[] compId = new String[listCount+1];
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회사 정보</title>
<link rel="stylesheet" href="http://www.w3schools.com/lib/w3.css">
<link rel="stylesheet" href="http://www.w3schools.com/lib/w3-theme-green.css">
<script src="./resources/js/jquery-3.1.0.js"></script>
<script type="text/javascript">
// 관리자 ID 체크
function checkID(formName, idx){//true/false = isAdminId(comp,id)
	var btn = document.getElementById("idCheckButton" + idx);
    var serData = $('#'+formName).serialize(); // 폼에 입력된 여러가지 값을 구분하여 가져옴
    if (btn.className.indexOf("w3-blue") == -1) {
	    $.post(
	    	"./CompanyIDCheck.admin",
	    	serData,
	    	function(data) {
	    		var jsonData = JSON.parse(data);
	    		if(jsonData.result){
	    			alert("해당 아이디를 등록하시면 됩니다.");
	    			btn.className = btn.className.replace(" w3-pink", " w3-blue");
	    		} else {
	    			alert("동일한 아이디가 이미 등록되어 있습니다.");
	    			btn.className = btn.className.replace(" w3-blue", " w3-pink");
	    		}
	    	}
	    );
    }
}

// maxlength 체크 (jQuery)
/* $('#comp_nm').keyup(function(){			
	var str = $(this).val();
	var len = stringByteSize(str);
	if(len > 100) {
		$('#w_comp_nm').text("100byte를 초과하였습니다.");
	} else {
		$('#w_comp_nm').text("");
	}
});*/

// UTF-8 관련 정보는 http://ko.wikipedia.org/wiki/UTF-8 를 참조해서 계산했다.
// UTF-8 문자열의 Byte 크기 구하기.
// 주의!! : UTF-8 웹 페이지에서만 정상작동한다. EUC-KR에서는 작동하지 않는다.
// 참고 자료 : http://ko.wikipedia.org/wiki/UTF-8 위키 백과의 UTF-8 항목
// 테스트 환경 : IE6, IE7, FF 2.0, Safari 3, Opera 9.2
// 각 문자의 유니코드 코드를 분석하여, UTF-8로 변환시 차지하는
// byte 수를 리턴한다.
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
// 문자열을 UTF-8로 변환했을 경우 차지하게 되는 byte 수를 리턴한다.
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
// maxlength 체크
function checkMaxLength(formName, inputName, spanId, size){
	var str = document.forms[formName][inputName].value;
	var len = stringByteSize(str);
	if(len > size) {
		document.getElementById(spanId).innerHTML = size + "byte 초과입니다.";
	} else {
		document.getElementById(spanId).innerHTML = "";
	}
}
// minlength 체크
function checkMinLength(formName, inputName, spanId, size){
	var str = document.forms[formName][inputName].value;
	var len = stringByteSize(str);
	if(len < size) {
		document.getElementById(spanId).innerHTML = size + "byte 미만입니다.";
	} else {
		document.getElementById(spanId).innerHTML = "";
	}
}
// max&min length 체크
function checkLength(formName, inputName, spanId, sizeMin, sizeMax){
	var str = document.forms[formName][inputName].value;
	var len = stringByteSize(str);
	if(len < sizeMin) {
		document.getElementById(spanId).innerHTML = sizeMin + "byte 미만입니다.";
	} else if(len > sizeMax) {
		document.getElementById(spanId).innerHTML = sizeMax + "byte 초과입니다.";
	} else {
		document.getElementById(spanId).innerHTML = "";
	}
}
function validateForm(formName) {
	
	var reg1 = /[0-9]{3}-[0-9]{2}-[0-9]{5}/; // 사업자번호
    var reg2 = /[0-9]{6}-[0-9]{7}/; // 법인(주민)번호
    var reg3 = /[0-9]{0,2}-?[0-9]{2,3}-[0-9]{3,4}-[0-9]{4}/; // 전화번호
    
	// 앞 뒤 공백 제거
    document.forms[formName]["comp_nm"].value = document.forms[formName]["comp_nm"].value.trim();
    document.forms[formName]["comp_no1"].value = document.forms[formName]["comp_no1"].value.trim();
    document.forms[formName]["comp_no2"].value = document.forms[formName]["comp_no2"].value.trim();
    document.forms[formName]["comp_ceo_nm"].value = document.forms[formName]["comp_ceo_nm"].value.trim();
    document.forms[formName]["comp_addr"].value = document.forms[formName]["comp_addr"].value.trim();
    document.forms[formName]["comp_phone"].value = document.forms[formName]["comp_phone"].value.trim();
    document.forms[formName]["admin_id"].value = document.forms[formName]["admin_id"].value.trim();
    document.forms[formName]["admin_phone"].value = document.forms[formName]["admin_phone"].value.trim();
    document.forms[formName]["admin_pw"].value = document.forms[formName]["admin_pw"].value.trim();
    
    var check1 = document.forms[formName]["comp_nm"].value; // *회사명
    var check2 = document.forms[formName]["comp_no1"].value; // 사업자 등록 번호
    var check3 = document.forms[formName]["comp_no2"].value; // 법인 등록 번호
    var check4 = document.forms[formName]["comp_ceo_nm"].value; // 대표자(성명)
    var check5 = document.forms[formName]["comp_addr"].value; // 소재지(회사 주소)
    var check6 = document.forms[formName]["comp_phone"].value; // 회사 대표 전화
    var check7 = document.forms[formName]["admin_id"].value; // *관리자 아이디
    var check8 = document.forms[formName]["admin_phone"].value; // 관리자 연락처
    var check9 = document.forms[formName]["admin_pw"].value; // *관리자 비밀번호

    if (check1 == null || check1 == "") {
    	alert("회사명을 입력해 주세요.");
        document.forms[formName]["comp_nm"].focus();
        return false;
    }else if (check2 != null && check2 != "" && check2.search(reg1) == -1) {
    	alert("사업자 등록 번호는 숫자와 - 로 입력해 주세요.");
    	document.forms[formName]["comp_no1"].focus();
    	return false;
    }else if (check3 != null && check3 != "" && check3.search(reg2) == -1) {
    	alert("법인 등록 번호는 숫자와 - 로 입력해 주세요.");
    	document.forms[formName]["comp_no2"].focus();
    	return false;
    }else if (check6 != null && check6 != "" && check6.search(reg3) == -1) {
    	alert("회사 대표 전화는 숫자와 - 로 입력해 주세요.");
    	document.forms[formName]["comp_phone"].focus();
    	return false;
    }else if (check7 == null || check7 == "") {
    	alert("관리자 아이디를 입력해 주세요.");
    	document.forms[formName]["admin_id"].focus();
    	return false;
    }else if (check8 != null && check8 != "" && check8.search(reg3) == -1) {
    	alert("관리자 연락처는 숫자와 - 로 입력해 주세요.");
    	document.forms[formName]["admin_phone"].focus();
    	return false;
    }else if (formName == "newForm" && (check9 == null || check9 == "")) { // 신규로 등록할 때는 null 이면 체크함. 기존 데이터가 변경되면(null 이 아니면) 그냥 update 이기 때문에 pass
        alert("관리자 비밀번호를 입력해 주세요.");
    	document.forms[formName]["admin_pw"].focus();
    	return false;
    }else {
    	return true;
    }
    
}
function dataNew(formName) {
	var form = document.getElementById(formName);
	if(true==validateForm(formName)){
		form.action = "./CompanyNew.admin";
		form.submit();
		return true;
	}
	return false;
}
function dataSave(formName, idx) {
	var btn = document.getElementById("idCheckButton" + idx);
    var form = document.getElementById(formName);
    
	if (btn.className.indexOf("w3-blue") == -1) {
		alert("유효한 아이디인지 ID 체크 버튼을 클릭하여 체크해 주세요.");
		return false;
	}
	if(true==validateForm(formName)){
		form.action = "./CompanySave.admin";
		form.submit();
		return true;
	}
	return false;
}
function dataDelete(formName) {
	var form = document.getElementById(formName);
	if (confirm("기존 관련 데이터가 있을 수 있습니다 정말로 삭제 하시겠습니까?")) {
		form.action = "./CompanyDelete.admin";
		form.submit();
		return true;
	}
	return false;
}
function dataReset(formName) {
	var form = document.getElementById(formName);
	form.reset();
	return false;
}
function viewIDCheckBtn(formName, idx, defaultVal){
	var btn = document.getElementById("idCheckButton" + idx);
	var inputVal = document.forms[formName]["admin_id"].value;
	if(inputVal != defaultVal){
		btn.className = btn.className.replace(" w3-blue", " w3-pink");
	} else {
		btn.className = btn.className.replace(" w3-pink", " w3-blue");
	}
    return;	
}
</script>
</head>
<body class="w3-container" onload="myFunction1();" >

<div class="w3-container" style="width:800px;">
<h2>회사 정보</h2>

<div class="w3-card-4">
	<div class="w3-accordion w3-light-grey">
	
<%
	String	comp_cd;			// 회사 코드
	String	comp_nm;		// 회사명
	String	comp_no1;		// 사업자 등록 번호
	String	comp_no2;		// 법인 등록 번호
	String	comp_ceo_nm;// 대표자(성명)
	String	comp_addr;		// 소재지(회사 주소)
	String	comp_phone;	// 회사 대표 전화
	String	admin_id;			// 관리자 아이디
	String	admin_phone;	// 관리자 연락처
	String	admin_pw;		// 관리자 비밀번호
	
	for(int i = 0; i < listCount ; i++) {
		boolean newData = false;
		spring.mybatis.gw.admin.dto.CompanyDTO data = (spring.mybatis.gw.admin.dto.CompanyDTO)list.get(i);
		comp_cd = data.getComp_cd();
		if(comp_cd==null) {newData = true; comp_cd = "C001";}
		comp_nm = data.getComp_nm();
		if(comp_nm==null) {comp_nm = " + 회사 추가";}
		comp_no1 = data.getComp_no1();
		if (comp_no1==null) {comp_no1 = "";}		
		comp_no2 = data.getComp_no2(); 
		if (comp_no2==null) {comp_no2 = "";}
		comp_ceo_nm = data.getComp_ceo_nm();
		if(comp_ceo_nm==null) {comp_ceo_nm = "";}
		comp_addr = data.getComp_addr();
		if(comp_addr==null) {comp_addr = "";}
		comp_phone = data.getComp_phone();
		if(comp_phone==null) {comp_phone = "";}
		admin_id = data.getAdmin_id();
		if(admin_id==null) {admin_id = "";}
		admin_phone = data.getAdmin_phone();
		if (admin_phone == null) {admin_phone = "";}
		admin_pw = data.getAdmin_pw();
		if(admin_pw==null) {admin_pw = "";}
		
		compId[i] = comp_cd;
		
		if(i== listCount-1){
			newComp = "C" + String.format("%03d", Integer.parseInt(comp_cd.substring(1, comp_cd.length()))+1);
			compId[i+1] = newComp;
		}
				
%>

    <button onclick="myFunction2('<%= comp_cd %>')" class="w3-btn-block w3-left-align w3-green w3-large"><%= comp_nm %></button>
    <div id="<%= comp_cd %>" class="w3-accordion-content w3-container">
      <form action="#" method="post" name="myForm<%=i%>" id="myForm<%=i%>">      
      <br/>
	<div class="w3-row-padding w3-right">
	<% if (newData) { %>
		<button class="w3-btn w3-blue" onclick="return dataNew('myForm<%=i%>');">저장</button>
	<% } else { %>
		<% if (!sComp_cd.equals(comp_cd)) { %>
			<button class="w3-btn w3-red" onclick="return dataDelete('myForm<%=i%>');">삭제</button>
		<% } %>
		<button class="w3-btn w3-green" onclick="return dataReset('myForm<%=i%>');">취소</button>
		<button class="w3-btn w3-blue" onclick="return dataSave('myForm<%=i%>', <%=i%>);">저장</button>
	<% } %>		
	</div>
	<br/>
	<br/>
	
	<div class="w3-row-padding">
	<div class="w3-half">
	<label class="w3-label">회사 코드</label>
	<input class="w3-input" type="text" disabled value="<%= comp_cd %>">
	<input type="hidden" name="comp_cd" value="<%= comp_cd %>">
	<input type="hidden" name="defaultComp" value="<%= comp_cd %>">
	<br/>
	</div>
	<div class="w3-half">
	<label class="w3-label">*회사명 <span id="w_comp_nm_<%=i%>" class="w3-text-red"></span></label>
	<input class="w3-input" type="text" name="comp_nm" value="<%= newData ? " " : comp_nm %>" placeholder="<%= newData ? " " : comp_nm %>"
	 onkeyup="return checkMaxLength('myForm<%=i%>','comp_nm','w_comp_nm_<%=i%>',100);"
	 onchange="return checkMaxLength('myForm<%=i%>','comp_nm','w_comp_nm_<%=i%>',100);">
	<br/>
	</div>
	</div>
	
	<div class="w3-row-padding">
	<div class="w3-half">
	<label class="w3-label">사업자 등록 번호 <span id="w_comp_no1_<%=i%>" class="w3-text-red"></span></label>
	<input class="w3-input" type="text" name="comp_no1" value="<%= comp_no1 %>"
	 onkeyup="return checkMaxLength('myForm<%=i%>','comp_no1','w_comp_no1_<%=i%>',20);"
	 onchange="return checkMaxLength('myForm<%=i%>','comp_no1','w_comp_no1_<%=i%>',20);">
	<br/>
	</div>
	<div class="w3-half">
	<label class="w3-label">법인 등록 번호 <span id="w_comp_no2_<%=i%>" class="w3-text-red"></span></label>
	<input class="w3-input" type="text" name="comp_no2" value="<%= comp_no2 %>"
	 onkeyup="return checkMaxLength('myForm<%=i%>','comp_no2','w_comp_no2_<%=i%>',20);"
	 onchange="return checkMaxLength('myForm<%=i%>','comp_no2','w_comp_no2_<%=i%>',20);">
	<br/>
	</div>
	</div>
	
	<div class="w3-row-padding">
	<div class="w3-half">
	<label class="w3-label">대표자(성명) <span id="w_comp_ceo_nm_<%=i%>" class="w3-text-red"></span></label>
	<input class="w3-input" type="text" name="comp_ceo_nm" value="<%= comp_ceo_nm %>"
	 onkeyup="return checkMaxLength('myForm<%=i%>','comp_ceo_nm','w_comp_ceo_nm_<%=i%>',100);"
	 onchange="return checkMaxLength('myForm<%=i%>','comp_ceo_nm','w_comp_ceo_nm_<%=i%>',100);">
	<br/>
	</div>
	</div>
	
	<div class="w3-row ">
	<div class="w3-threequarter w3-container ">
	<label class="w3-label">소재지(회사 주소) <span id="w_comp_addr_<%=i%>" class="w3-text-red"></span></label>
	<input class="w3-input" type="text" name="comp_addr" value="<%= comp_addr %>"
	 onkeyup="return checkMaxLength('myForm<%=i%>','comp_addr','w_comp_addr_<%=i%>',200);"
	 onchange="return checkMaxLength('myForm<%=i%>','comp_addr','w_comp_addr_<%=i%>',200);">
	<br/>
	</div>
	</div>
	
	<div class="w3-row-padding">
	<div class="w3-half">
	<label class="w3-label">회사 대표 전화 <span id="w_comp_phone_<%=i%>" class="w3-text-red"></span></label>
	<input class="w3-input" type="text" name="comp_phone" value="<%= comp_phone %>"
	 onkeyup="return checkMaxLength('myForm<%=i%>','comp_phone','w_comp_phone_<%=i%>',20);"
	 onchange="return checkMaxLength('myForm<%=i%>','comp_phone','w_comp_phone_<%=i%>',20);">
	<br/>
	</div>
	</div>
	
	<div class="w3-row-padding">
	<div class="w3-half">
	<label class="w3-label">*관리자 아이디 <span id="w_admin_id_<%=i%>" class="w3-text-red"></span></label><br />
	<input class="w3-threequarter w3-input" type="text" name="admin_id" value="<%= admin_id %>" placeholder="<%= admin_id %>"
	 onkeyup="return checkLength('myForm<%=i%>','admin_id','w_admin_id_<%=i%>',4,20);"
	 onchange="viewIDCheckBtn('myForm<%=i%>',<%=i%>,'<%= admin_id %>');return checkLength('myForm<%=i%>','admin_id','w_admin_id_<%=i%>',4,20);">&nbsp;
	<button type="button" class="w3-btn w3-blue w3-border w3-border-blank w3-round-xlarge w3-small "
	id="idCheckButton<%=i%>" onclick="return checkID('myForm<%=i%>', <%=i%>);">ID 체크</button>
	<br/>
	</div>
	<div class="w3-half">
	<label class="w3-label">관리자 연락처 <span id="w_admin_phone_<%=i%>" class="w3-text-red"></span></label>
	<input class="w3-input" type="text" name="admin_phone" value="<%= admin_phone %>"
	 onkeyup="return checkMaxLength('myForm<%=i%>','admin_phone','w_admin_phone_<%=i%>',20);"
	 onchange="return checkMaxLength('myForm<%=i%>','admin_phone','w_admin_phone_<%=i%>',20);">
	<br/>
	</div>
	</div>
	
	<div class="w3-row-padding">
	<div class="w3-half">
	<label class="w3-label">*관리자 비밀번호 <span id="w_admin_pw_<%=i%>" class="w3-text-red"></span></label>
	<input class="w3-input" type="password" name="admin_pw" value=""
	 onkeyup="return checkLength('myForm<%=i%>','admin_pw','w_admin_pw_<%=i%>',4,20);"
	 onchange="return checkLength('myForm<%=i%>','admin_pw','w_admin_pw_<%=i%>',4,20);"><!-- 패스워드 암호화로 입력값이 있으면 수정으로 변경(자바에서 체크) -->
	<br/>
	</div>
	<div class="w3-half">
	<br/>
	<br/>
	</div>
	</div>

	</form>
	<Br/>		
	</div>
<% } %>
<% if(bData) { %>
    <button onclick="myFunction2('<%= newComp %>')" class="w3-btn-block w3-left-align w3-green w3-large"> + 회사 추가</button>
    <div id="<%= newComp %>" class="w3-accordion-content w3-container">
      <form action="#" method="post" name="newForm" id="newForm" >      
      <br/>
	<div class="w3-row-padding w3-right">
		<button class="w3-btn w3-green" onclick="return dataReset('newForm');">취소</button>
		<button class="w3-btn w3-blue" onclick="return dataNew('newForm');">저장</button>			
	</div>
	<br/>
	<br/>
	
	<div class="w3-row-padding">
	<div class="w3-half">
	<label class="w3-label">회사 코드</label>
	<input class="w3-input" type="text" disabled value="">
	<input type="hidden" name="comp_cd" value="">
	<br/>
	</div>
	<div class="w3-half">
	<label class="w3-label">*회사명 <span id="w_comp_nm" class="w3-text-red"></span></label>
	<input class="w3-input" type="text" name="comp_nm" value=""
	 onkeyup="return checkMaxLength('newForm','comp_nm','w_comp_nm',100);" onchange="return checkMaxLength('newForm','comp_nm','w_comp_nm',100);">
	<br/>
	</div>
	</div>
	
	<div class="w3-row-padding">
	<div class="w3-half">
	<label class="w3-label">사업자 등록 번호 <span id="w_comp_no1" class="w3-text-red"></span></label>
	<input class="w3-input" type="text" name="comp_no1" value=""
	 onkeyup="return checkMaxLength('newForm','comp_no1','w_comp_no1',20);" onchange="return checkMaxLength('newForm','comp_no1','w_comp_no1',20);">
	<br/>
	</div>
	<div class="w3-half">
	<label class="w3-label">법인 등록 번호 <span id="w_comp_no2" class="w3-text-red"></span></label>
	<input class="w3-input" type="text" name="comp_no2" value=""
	 onkeyup="return checkMaxLength('newForm','comp_no2','w_comp_no2',20);" onchange="return checkMaxLength('newForm','comp_no2','w_comp_no2',20);">
	<br/>
	</div>
	</div>
	
	<div class="w3-row-padding">
	<div class="w3-half">
	<label class="w3-label">대표자(성명) <span id="w_comp_ceo_nm" class="w3-text-red"></span></label>
	<input class="w3-input" type="text" name="comp_ceo_nm" value=""
	 onkeyup="return checkMaxLength('newForm','comp_ceo_nm','w_comp_ceo_nm',100);" onchange="return checkMaxLength('newForm','comp_ceo_nm','w_comp_ceo_nm',100);">
	<br/>
	</div>
	</div>
	
	<div class="w3-row ">
	<div class="w3-threequarter w3-container ">
	<label class="w3-label">소재지(회사 주소) <span id="w_comp_addr" class="w3-text-red"></span></label>
	<input class="w3-input" type="text" name="comp_addr" value=""
	 onkeyup="return checkMaxLength('newForm','comp_addr','w_comp_addr',200);" onchange="return checkMaxLength('newForm','comp_addr','w_comp_addr',200);">
	<br/>
	</div>
	</div>
	
	<div class="w3-row-padding">
	<div class="w3-half">
	<label class="w3-label">회사 대표 전화 <span id="w_comp_phone" class="w3-text-red"></span></label>
	<input class="w3-input" type="text" name="comp_phone" value=""
	 onkeyup="return checkMaxLength('newForm','comp_phone','w_comp_phone',20);" onchange="return checkMaxLength('newForm','comp_phone','w_comp_phone',20);">
	<br/>
	</div>
	</div>
	
	<div class="w3-row-padding">
	<div class="w3-half">
	<label class="w3-label">*관리자 아이디 <span id="w_admin_id" class="w3-text-red"></span></label>
	<input class="w3-input" type="text" name="admin_id" value=""
	 onkeyup="return checkLength('newForm','admin_id','w_admin_id',4,20);" onchange="return checkLength('newForm','admin_id','w_admin_id',4,20);">
	<br/>
	</div>
	<div class="w3-half">
	<label class="w3-label">관리자 연락처 <span id="w_admin_phone" class="w3-text-red"></span></label>
	<input class="w3-input" type="text" name="admin_phone" value=""
	 onkeyup="return checkMaxLength('newForm','admin_phone','w_admin_phone',20);" onchange="return checkMaxLength('newForm','admin_phone','w_admin_phone',20);">
	<br/>
	</div>
	</div>
	
	<div class="w3-row-padding">
	<div class="w3-half">
	<label class="w3-label">*관리자 비밀번호 <span id="w_admin_pw" class="w3-text-red"></span></label>
	<input class="w3-input" type="password" name="admin_pw" value=""
	 onkeyup="return checkLength('newForm','admin_pw','w_admin_pw',4,20);" onchange="return checkLength('newForm','admin_pw','w_admin_pw',4,20);">
	<br/>
	</div>
	<div class="w3-half">
	<br/>
	<br/>
	</div>
	</div>

	</form>
	<Br/>		
	</div>
<% } %>
</div>
</div>
</div>


<Br/>
<Br/>

<%-- 제목 div 클릭 했을 때 내용이 열리고 닫히는 부분 (시작) --%>
<%-- 최초 화면 로딩 후 myFunction1() 을 call 하고, 그 이후 div 클릭은 myFunction2() 를 call 한다 --%>
<%
String tempStr = "";
for(String s : compId){
	tempStr += ("\""+s+"\",");
}
tempStr = tempStr.substring(0, tempStr.length()-1);
%>
<script>
function myFunction1() {
    var compId = [<%=tempStr %>];
    //var sel = document.getElementById(compId[0]);
    var sel = document.getElementById(
    <%
    if (sDefaultComp != null && sDefaultComp.length() > 0){
    	out.print("\""+sDefaultComp+"\"");
    } else {
    	out.print("compId[0]");
    }
    %>
    );
	sel.className += " w3-show";
	return;
}
function myFunction2(id) {
    var sel = document.getElementById(id);
    var compId = [<%=tempStr %>];

    if (sel.className.indexOf("w3-show") == -1) {
        sel.className += " w3-show";
		for (i = 1; i <= <%= bData ? listCount+1 : listCount %>; i++) {
			if(id != compId[i-1] ){
				var y = document.getElementById(compId[i-1]);
				y.className = y.className.replace(" w3-show", "");
			}			
		}
    } else { 
		var sum = 0;
		for (i = 1; i <= <%= bData ? listCount+1 : listCount %>; i++) {			
			var y = document.getElementById(compId[i-1]);
			if (y.className.indexOf("w3-show") == -1) {
				sum++;
			}			
		}
		if(sum == 1) {
        	sel.className = sel.className.replace(" w3-show", "");
		}
    }
    return;
}
</script>
<%-- 제목 div 클릭 했을 때 내용이 열리고 닫히는 부분 (종료) --%>

</body>
</html>