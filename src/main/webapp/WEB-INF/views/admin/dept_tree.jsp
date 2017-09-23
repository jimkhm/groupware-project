<%--
//******************************************************************************
//  프로그램ID : dept_tree.jsp
//  프로그램명 : 부서 정보(조직도) 화면
//  관련 DB 테이블 : DEPT_INFO
//  기타 DB 테이블 : 
//  작  성  자 : 조 수 정 (Sujung Jo)
//  작  성  일 : 2016.11.09.
//******************************************************************************
 --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%
	String sessionCompany = (String) request.getSession().getAttribute("company"); // session company
	//String sessionCompany = "C001";
	String company = sessionCompany;
//	if(request.getParameter("company") != null) {
//		company = request.getParameter("company");
//	}
	
	String xmlStr = (String) request.getAttribute("xmlStr");
	
	int listCount = 0;
	listCount = (Integer) request.getAttribute("listCount");
	List deptList = (List) request.getAttribute("selDeptList");
	int deptListCount = deptList.size();
	
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>부서 정보 (조직도)</title>
<link rel="stylesheet" href="http://www.w3schools.com/lib/w3.css">
<link rel="stylesheet" href="http://www.w3schools.com/lib/w3-theme-green.css">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.6.3/css/font-awesome.min.css">
<style>
ul {
    list-style: none; /* Remove list bullets */
    padding: 0;
    margin: 0;
}
li { 
    padding-left: 16px; 
}
.on:before {
    content: "\f115"; /* Insert content that looks like bullets \f07b \f07c  */
    padding-right: 8px;
    color: blue; /* Or a color you prefer */
}
.off:before {
    content: "\f114"; /* Insert content that looks like bullets */
    padding-right: 8px;
    color: blue; /* Or a color you prefer */
}
.rowoff {
	display : none;
}
.rowon {
	display : ;
}
</style>
<script src="./resources/js/jquery-3.1.0.js"></script>
<script src="./resources/js/jquery.rightClick.js"></script><!--  마우스 오른쪽 클릭 jQuery :: http://labs.abeautifulsite.net/archived/jquery-rightClick/demo/ -->
<script type="text/javascript">

var xml = '<%= xmlStr %>';

$(function(){
	
	<%if(listCount<=0){%>
		var m_dept_no = "M";
		var dept_no = "NEW";
		var dept_nm = "부서명을 입력하세요.";
		var dept_manager = "";
		var dept_manager_nm = "";
		var output = '';
			output += '<ul id=\"m_'+m_dept_no+'\">';
			output += '<li id=\"'+dept_no+'\">';
			output += '<span id=\"spanDept\" class=\"w3-hover-blue\">' + dept_nm;
			output += ' [' + dept_no + ']' + '</span>';
			output += '<input type="hidden" id="m_dept_no" value=\"'+m_dept_no+'\" class=\"mgr\">';
			output += '<input type="hidden" id="id_dept_no" value=\"'+dept_no+'\" class="off ">';
			output += '<input type="hidden" id="id_dept_nm" value=\"'+dept_nm+'\" >';
			output += '<input type="hidden" id="id_manager" value=\"'+dept_manager+'\" >';
			output += '<input type="hidden" id="id_manager_nm" value=\"'+dept_manager_nm+'\" >';
			output += '</li>';
			output += '</ul>';
		
		$("#deptTree").append(output);
		
		// value 값 넣어주기
		$("#deptName0").val(""); // 부서 명
		$("#deptNo0").val(dept_no); // 부서 코드
		$("#mDeptNo0").val(m_dept_no); // 상위 부서 코드
		$("#deptManager0").val(dept_manager); // 부서장[사번]
		$("#deptManagerNm0").val(dept_manager_nm); // 부서장[이름]
		
		// placeholder 값 넣어주기
		$("#deptName0").prop("placeholder", $("#deptName0").val()); // 부서 명
		$("#deptNo0").prop("placeholder", $("#deptNo0").val()); // 부서 코드
		$("#mDeptNo0").prop("placeholder", $("#mDeptNo0").val()); // 상위 부서 코드
		$("#deptManager0").prop("placeholder", $("#deptManager0").val()); // 부서장[사번]
		$("#deptManagerNm0").prop("placeholder", $("#deptManagerNm0").val()); // 부서장[이름]
	<%} else { %>
		var xmlDOC = $.parseXML(xml);
	
		// 가장 최 상위 부서[M] 부터 그려준다
		$(xmlDOC).find('dept').each(function(){
			var m_dept_no = $(this).find('m_dept_no').text();
			var dept_no = $(this).find('dept_no').text();
			var dept_nm = $(this).find('dept_nm').text();
			var dept_manager = $(this).find('dept_manager').text();
			var dept_manager_nm = $(this).find('dept_manager_nm').text();
			var output = '';
			if(m_dept_no=='M'){
				output += '<ul id=\"m_'+m_dept_no+'\">';
				output += '<li id=\"'+dept_no+'\">';
				output += '<span id=\"spanDept\" class=\"w3-hover-blue\">' + dept_nm;
				output += ' [' + dept_no + ']' + '</span>';
				output += '<input type="hidden" id="m_dept_no" value=\"'+m_dept_no+'\" class=\"mgr\">';
				output += '<input type="hidden" id="id_dept_no" value=\"'+dept_no+'\" class="off ">';
				output += '<input type="hidden" id="id_dept_nm" value=\"'+dept_nm+'\" >';
				output += '<input type="hidden" id="id_manager" value=\"'+dept_manager+'\" >';
				output += '<input type="hidden" id="id_manager_nm" value=\"'+dept_manager_nm+'\" >';
				output += '</li>';
				output += '</ul>';
			}
			
			$("#deptTree").append(output);
		});
		
		// 그 외 부서들을 그려준다
		var temp = '';
		$(xmlDOC).find('dept').each(function(){
			var m_dept_no = $(this).find('m_dept_no').text();
			var dept_no = $(this).find('dept_no').text();
			var dept_nm = $(this).find('dept_nm').text();
			var dept_manager = $(this).find('dept_manager').text();
			var dept_manager_nm = $(this).find('dept_manager_nm').text();
			var output = '';
			if(m_dept_no!='M'){
				
				if(temp == m_dept_no){
					output += '<li id=\"'+dept_no+'\">';
					output += '<span id=\"spanDept\" class=\"w3-hover-blue\">' + dept_nm;
					output += ' [' + dept_no + ']' + '</span>';
					output += '<input type="hidden" id="m_dept_no" value=\"'+m_dept_no+'\" class=\"mgr\">';
					output += '<input type="hidden" id="id_dept_no" value=\"'+dept_no+'\" class="off ">';
					output += '<input type="hidden" id="id_dept_nm" value=\"'+dept_nm+'\" >';
					output += '<input type="hidden" id="id_manager" value=\"'+dept_manager+'\" >';
					output += '<input type="hidden" id="id_manager_nm" value=\"'+dept_manager_nm+'\" >';
					output += '</li>';			
					$("#m_"+m_dept_no).append(output);
				}else{
					output += '<ul id=\"m_'+m_dept_no+'\" >';
					output += '<li id=\"'+dept_no+'\">';
					output += '<span id=\"spanDept\" class=\"w3-hover-blue\">' + dept_nm;
					output += ' [' + dept_no + ']' + '</span>';
					output += '<input type="hidden" id="m_dept_no" value=\"'+m_dept_no+'\" class=\"mgr\">';
					output += '<input type="hidden" id="id_dept_no" value=\"'+dept_no+'\" class="off ">';
					output += '<input type="hidden" id="id_dept_nm" value=\"'+dept_nm+'\" >';
					output += '<input type="hidden" id="id_manager" value=\"'+dept_manager+'\" >';
					output += '<input type="hidden" id="id_manager_nm" value=\"'+dept_manager_nm+'\" >';
					output += '</li>';
					output += '</ul>';
					$("#"+m_dept_no).append(output);
				}
				temp = m_dept_no;	
				
			}	
		});
	<%}%>
	
	$(".mgr").each(function(){
		var val = $(this).val();
		$("input[type=hidden]").each(function(){
			var val2 = $(this).val();
			if(val == val2 && $(this).is("#id_dept_no")){
				$(this).removeAttr("class");
				$("#"+val).attr("class","on");
			}
		});
	});
	
	$(".off").each(function(){
		var val = $(this).val();
		$("#"+val).attr("class","off");		
	});
	
	// 조직도[부서] 마우스 더블 클릭시, [시작]
	$("span[id='spanDept']").dblclick(function(){
		var val = $(this).text();
		var len = val.lastIndexOf("]") - val.lastIndexOf("[");
		var val = val.substr(val.lastIndexOf("[")+1, len-1);
		
 		if($("#"+val).hasClass("off")){ 			
			$("#"+val+":has(ul)>ul").show(); 			
			$("#"+val+":has(ul)").attr('class','on');	
		} else {
			$("#"+val+":has(ul)>ul").hide();
			$("#"+val).attr('class','off');
		}		
	});
	// 조직도[부서] 마우스 더블 클릭시, [끝]
	// 조직도[부서] 마우스 클릭시, [시작]
	$("span[id='spanDept']").click(function(){
		
		// 추가 한 후 저장 하지 않고 다른 부서를 클릭하면 없어짐.
		if(undefined!=$("#NEW").parent().html()){
			<%if(listCount>0){%>
			$("#NEW").parent().remove();
			<%}%>
		}
		
		// value 값 넣어주기
		var val = $(this).text();
		$("#deptName0").val(val.substr(0, val.lastIndexOf("[")).trim()); // 부서 명
		//$("#deptNo0").val($(this).parent().parent().children().find("#id_dept_no").val()); // 부서 코드
		//$("#mDeptNo0").val($(this).parent().parent().children().find("#m_dept_no").val()); // 상위 부서 코드
		//$("#deptManager0").val($(this).parent().parent().children().find("#id_manager").val()); // 부서장[사번]
		//$("#deptManagerNm0").val($(this).parent().parent().children().find("#id_manager_nm").val()); // 부서장[이름]		
		$("#deptNo0").val($(this).parent().find("#id_dept_no").val()); // 부서 코드
		$("#mDeptNo0").val($(this).parent().find("#m_dept_no").val()); // 상위 부서 코드
		$("#deptManager0").val($(this).parent().find("#id_manager").val()); // 부서장[사번]
		$("#deptManagerNm0").val($(this).parent().find("#id_manager_nm").val()); // 부서장[이름]
		
		// placeholder 값 넣어주기
		$("#deptName0").prop("placeholder", $("#deptName0").val()); // 부서 명
		$("#deptNo0").prop("placeholder", $("#deptNo0").val()); // 부서 코드
		$("#mDeptNo0").prop("placeholder", $("#mDeptNo0").val()); // 상위 부서 코드
		$("#deptManager0").prop("placeholder", $("#deptManager0").val()); // 부서장[사번]
		$("#deptManagerNm0").prop("placeholder", $("#deptManagerNm0").val()); // 부서장[이름]
		
		// 상위 부서 select
		$("#mDeptNoSel0").val($("#mDeptNo0").val());

	});
	// 조직도[부서] 마우스 클릭시, [끝]
	// 조직도[부서] 마우스 오버시, [시작]
	$("span[id='spanDept']").hover(function(event){
    	if($(this).siblings().is("#popupMenu") == false) {
    		var obj = $("#popupMenu");
    		$(this).after(obj);
    	}
	});
	// 조직도[부서] 마우스 오버시, [끝]
	// 조직도[부서] 마우스 오른쪽 클릭시, [팝업 메뉴 호출] [시작]
    $("span[id='spanDept']").rightClick(function(event){
    	var val = $(this).text();
    	//var deptName = val.substr(0, val.lastIndexOf("[")); // 부서 명
    	//var deptNo = $(this).parent().parent().children().find("#id_dept_no").val(); // 부서 코드
    	var deptNo = $(this).parent().find("#id_dept_no").val(); // 부서 코드
    	var mDeptNo = $(this).parent().parent().children().find("#m_dept_no").val(); // 상위 부서 코드
    	if(deptNo != $("#deptNo0").val()){ // 선택되어진 부서가 아닐 때 조회 먼저
    		
    		// value 값 넣어주기
			$("#deptName0").val(val.substr(0, val.lastIndexOf("[")).trim()); // 부서 명		
			//$("#deptNo0").val($(this).parent().parent().children().find("#id_dept_no").val()); // 부서 코드
			//$("#mDeptNo0").val($(this).parent().parent().children().find("#m_dept_no").val()); // 상위 부서 코드
			//$("#deptManager0").val($(this).parent().parent().children().find("#id_manager").val()); // 부서장[사번]
			//$("#deptManagerNm0").val($(this).parent().parent().children().find("#id_manager_nm").val()); // 부서장[이름]
			$("#deptNo0").val($(this).parent().find("#id_dept_no").val()); // 부서 코드
			$("#mDeptNo0").val($(this).parent().find("#m_dept_no").val()); // 상위 부서 코드
			$("#deptManager0").val($(this).parent().find("#id_manager").val()); // 부서장[사번]
			$("#deptManagerNm0").val($(this).parent().find("#id_manager_nm").val()); // 부서장[이름]
			
			// placeholder 값 넣어주기
			$("#deptName0").prop("placeholder", $("#deptName0").val()); // 부서 명
			$("#deptNo0").prop("placeholder", $("#deptNo0").val()); // 부서 코드
			$("#mDeptNo0").prop("placeholder", $("#mDeptNo0").val()); // 상위 부서 코드
			$("#deptManager0").prop("placeholder", $("#deptManager0").val()); // 부서장[사번]
			$("#deptManagerNm0").prop("placeholder", $("#deptManagerNm0").val()); // 부서장[이름]
			
			// 상위 부서 select
			$("#mDeptNoSel0").val($("#mDeptNo0").val());
		}

    	if($(this).siblings().is("#popupMenu")) {    		
        	if(mDeptNo == "M"){ // 선택되어진 부서가 최상위 부서일 때 팝업 메뉴에 [최상위 부서 추가] 메뉴를 추가 해 준다. 
        		$(this).siblings("#popupMenu").children("a[id='appendM']").removeClass("w3-hide");
        		$(this).siblings("#popupMenu").children("a[id='appendM']").addClass("w3-show");
        	} else { // 선택되어진 부서가 최상위 부서가 아닐 때 팝업 메뉴에 [최상위 부서 추가] 메뉴를 삭제 해 준다. 
        		$(this).siblings("#popupMenu").children("a[id='appendM']").removeClass("w3-show");
        		$(this).siblings("#popupMenu").children("a[id='appendM']").addClass("w3-hide");
        	}
        	$(this).siblings("#popupMenu").show(); // 팝업 메뉴를 보여 준다.    		
    	}
	});
	// 조직도[부서] 마우스 오른쪽 클릭시, [팝업 메뉴 호출] [끝]
	// 조직도[부서][팝업 메뉴] 마우스 포인트 아웃시, [팝업 메뉴 닫기] [시작]
    $("#popupMenu").mouseleave(function(){
			$(this).hide(); // 팝업 메뉴를 보여주지 않는다.
    });
	// 조직도[부서][팝업 메뉴] 마우스 포인트 아웃시, [팝업 메뉴 닫기] [끝]
	
	// 조직도[부서][팝업 메뉴] 메뉴 선택 시 [마우스 클릭] [시작]
	$("a[id='appendM']").click(function(){ // 최상위 부서 추가
		
		// 추가 한 후 저장 하지 않고 다른 부서를 클릭하면 없어짐.
		if(undefined!=$("#NEW").parent().html()){
			$("#NEW").parent().remove();
		}
		
		//var dept_no = $(this).parent().parent().find("#id_dept_no").val();
		//alert("dept_no:"+dept_no);
		//alert("append");
		
		var m_dept_no = "M";
		var dept_no = "NEW";
		var dept_nm = "부서명을 입력하세요.";
		var dept_manager = "";
		var dept_manager_nm = "";
		var output = '';
			output += '<ul id=\"m_'+m_dept_no+'\">';
			output += '<li id=\"'+dept_no+'\">';
			output += '<span id=\"spanDept\" class=\"w3-hover-blue\">' + dept_nm;
			output += ' [' + dept_no + ']' + '</span>';
			output += '<input type="hidden" id="m_dept_no" value=\"'+m_dept_no+'\" class=\"mgr\">';
			output += '<input type="hidden" id="id_dept_no" value=\"'+dept_no+'\" class="off ">';
			output += '<input type="hidden" id="id_dept_nm" value=\"'+dept_nm+'\" >';
			output += '<input type="hidden" id="id_manager" value=\"'+dept_manager+'\" >';
			output += '<input type="hidden" id="id_manager_nm" value=\"'+dept_manager_nm+'\" >';
			output += '</li>';
			output += '</ul>';
		
		$("#deptTree").append(output);
		
		// value 값 넣어주기
		$("#deptName0").val(""); // 부서 명
		$("#deptNo0").val(dept_no); // 부서 코드
		$("#mDeptNo0").val(m_dept_no); // 상위 부서 코드
		$("#deptManager0").val(dept_manager); // 부서장[사번]
		$("#deptManagerNm0").val(dept_manager_nm); // 부서장[이름]
		
		// placeholder 값 넣어주기
		$("#deptName0").prop("placeholder", $("#deptName0").val()); // 부서 명
		$("#deptNo0").prop("placeholder", $("#deptNo0").val()); // 부서 코드
		$("#mDeptNo0").prop("placeholder", $("#mDeptNo0").val()); // 상위 부서 코드
		$("#deptManager0").prop("placeholder", $("#deptManager0").val()); // 부서장[사번]
		$("#deptManagerNm0").prop("placeholder", $("#deptManagerNm0").val()); // 부서장[이름]
		
		// 상위 부서 select
		$("#mDeptNoSel0").val($("#mDeptNo0").val());
	});
	
	$("a[id='append']").click(function(){ // 하위 부서 추가
		
		// 추가 한 후 저장 하지 않고 다른 부서를 클릭하면 없어짐.
		if(undefined!=$("#NEW").parent().html()){
			$("#NEW").parent().remove();
		}
		
		//var dept_no = $(this).parent().parent().find("#id_dept_no").val();
		//alert("dept_no:"+dept_no);
		//alert("append");
		
		var m_dept_no = $(this).parent().parent().find("#id_dept_no").val();
		var dept_no = "NEW";
		var dept_nm = "부서명을 입력하세요.";
		var dept_manager = "";
		var dept_manager_nm = "";
		var output = '';
			output += '<ul id=\"m_'+m_dept_no+'\">';
			output += '<li id=\"'+dept_no+'\">';
			output += '<span id=\"spanDept\" class=\"w3-hover-blue\">' + dept_nm;
			output += ' [' + dept_no + ']' + '</span>';
			output += '<input type="hidden" id="m_dept_no" value=\"'+m_dept_no+'\" class=\"mgr\">';
			output += '<input type="hidden" id="id_dept_no" value=\"'+dept_no+'\" class="off ">';
			output += '<input type="hidden" id="id_dept_nm" value=\"'+dept_nm+'\" >';
			output += '<input type="hidden" id="id_manager" value=\"'+dept_manager+'\" >';
			output += '<input type="hidden" id="id_manager_nm" value=\"'+dept_manager_nm+'\" >';
			output += '</li>';
			output += '</ul>';
		
			//$(this).parent().parent().find("#id_dept_no").parent().after(output);
		//$("#id_dept_no").parent().append(output);
		
		$("#deptTree").append(output);
		
		// value 값 넣어주기
		$("#deptName0").val(""); // 부서 명		
		$("#deptNo0").val(dept_no); // 부서 코드
		$("#mDeptNo0").val(m_dept_no); // 상위 부서 코드
		$("#deptManager0").val(dept_manager); // 부서장[사번]
		$("#deptManagerNm0").val(dept_manager_nm); // 부서장[이름]
		
		// placeholder 값 넣어주기
		$("#deptName0").prop("placeholder", $("#deptName0").val()); // 부서 명
		$("#deptNo0").prop("placeholder", $("#deptNo0").val()); // 부서 코드
		$("#mDeptNo0").prop("placeholder", $("#mDeptNo0").val()); // 상위 부서 코드
		$("#deptManager0").prop("placeholder", $("#deptManager0").val()); // 부서장[사번]
		$("#deptManagerNm0").prop("placeholder", $("#deptManagerNm0").val()); // 부서장[이름]
		
		// 상위 부서 select
		$("#mDeptNoSel0").val($("#mDeptNo0").val());
	});
	
	$("a[id='remove']").click(function(){
		//var dept_no = $(this).parent().parent().find("#id_dept_no").val();
		var deptName = $(this).parent().parent().find("#id_dept_nm").val();
		if(confirm("[" + deptName + "] 부서 삭제를 선택하셨습니다.\n(주의 : 하위 부서가 있을 경우, 하위 부서들도 같이 삭제됩니다.)\n정말로 삭제 하시겠습니까?")){
		    $.post("./DeptTreeDelete.admin", {
		    	deptNo0: $("#deptNo0").val()
		    	},
				function(data, status){
					if(data=="true"){
						alert("삭제 되었습니다.");
						location.reload();
					}
					//alert("Data: " + data + "\nStatus: " + status);
				}
		    		
		    );
		}
	});
	
	//$("a[id='up']").click(function(){
	//	alert("up");
	//});	
	//$("a[id='down']").click(function(){
	//	alert("down");
	//});
	// 조직도[부서][팝업 메뉴] 메뉴 선택 시 [마우스 클릭] [끝]
	
	$("#mDeptNoSel0").on('change', function() {
		//alert(this.value); // 선택된 value를 출력한다.
		var deptNo = $("#deptNo0").val();
		if(deptNo == this.value){
			this.value = "M";
		} else {
			$("#mDeptNo0").val(this.value);
		}
	});
	
});
function validateForm() {
	var deptName = document.getElementById("deptName0");
	deptName.value = deptName.value.trim();
	
	if (deptName.value == null || deptName.value == "") {
    	alert("부서명을 입력해 주세요.");
    	deptName.focus();
        return false;
    }else {
    	return true;
    }
}
function deleteManager(){
	document.getElementById("deptManager0").value = "";
	document.getElementById("deptManagerNm0").value = "";
}
function searchManager(){
	var url = "./EmployeePop.admin?company=<%= company %>";
    window.open(url,"CLIENT_WINDOW","width=700, height=500, toolbar=no, menubar=no, location=no, scrollbars=yes, status=no,resizable=no, fullscreen=no, channelmode=no, directories=no;");
	return false;
}
function dataSave() {
	var form = document.getElementById("listForm");
	document.getElementById("deptNo0").disabled = false;
	if(true==validateForm()){
		
		form.action = "./DeptTreeSave.admin";
		form.submit();
		return true;
	}	
	return false;
}
</script>
</head>
<body class="w3-container">

<div class="w3-container">
<h2>부서 정보</h2>

<div class="w3-row-padding" style="margin:0 -16px">

<!-- 요기서 부터 왼쪽 -->
<div class="w3-col w3-container" style="width:380px;">
	<p class="w3-large w3-text-grey"><b><i class="fa fa-asterisk fa-fw w3-margin-right w3-text-blue"></i>조직도</b></p>
	<div class="w3-white w3-text-grey w3-card-4">
		<br />
		<div class="w3-display-container w3-margin-right">			
			<div id="deptTree"  class="fa" ></div><!-- style="height:772px;" -->
			<br /><br />
		</div>
	</div>
	<br />
	
</div>
<!-- 요기 까지 왼쪽 -->

<!-- 요기서 부터 오른쪽 -->
<div id="rightDiv" class="w3-col w3-container " style="width:500px">
	<p class="w3-large w3-text-grey"><b><i class="fa fa-asterisk fa-fw w3-text-blue"></i>&nbsp;부서 상세</b></p>
<div class="w3-container w3-light-grey w3-round-large">
	<br />
	<br />
	<form name="listForm" id="listForm" method="post">
	<input name="idx" id="idx" type="hidden" value="0" />
	<input name="deptManager0" id="deptManager0" type="hidden" value="" />
	<input name="mDeptNo0" id="mDeptNo0" type="hidden" value="M" />
	
	<div class="w3-container w3-padding-bottom">
		<div class="w3-quarter w3-padding-right">
			<label class="w3-right" style="font-size:17px">부서코드</label>
		</div>
		<div class="w3-half ">
			<input name="deptNo0" id="deptNo0" type="text"  value="NEW" class="w3-input w3-border-0" style="display: inline-block;width:160px;" disabled>
		</div>
	</div>
	
	<div class="w3-container w3-padding-bottom">
		<div class="w3-quarter w3-padding-right">
			<label class="w3-right" style="font-size:17px">부서명</label>
		</div>
		<div class="w3-threequarter ">
			<input name="deptName0" id="deptName0" type="text" value="" placeholder="부서명을 입력하세요." class="w3-input w3-border-0">
		</div>
	</div>
	 
	<div class="w3-container w3-padding-bottom">
		<div class="w3-quarter w3-padding-right">
			<label class="w3-right" style="font-size:17px">부서장</label>
		</div>
		<div class="w3-threequarter">
			<input name="deptManagerNm0" id="deptManagerNm0" type="text" value=""  placeholder="" class="w3-input w3-border-0 " style="display: inline-block;width:160px;" disabled>
			<button type="button" class="w3-btn w3-pale-red w3-border w3-border-blank w3-round w3-padding-4 "
				id="deleteManagerButton" onclick="return deleteManager();" style="display: inline-block;margin-bottom:4px;">삭제</button>
			<button type="button" class="w3-btn w3-pale-blue w3-border w3-border-blank w3-round w3-padding-4 "
				id="searchManagerButton" onclick="return searchManager();" style="display: inline-block;margin-bottom:4px;">추가</button>
		</div>
	</div>
	
	<div class="w3-container w3-padding-bottom">
		<div class="w3-quarter w3-padding-right">
			<label class="w3-right" style="font-size:17px">상위 부서</label>
		</div>
		<div class="w3-threequarter">
			<select name="mDeptNoSel0" id="mDeptNoSel0" class="w3-input" style="width:150px;font-size:16px;display: inline-block;" >
				<option value="M">최상위 부서</option>
<%
	String cd;
	String nm;

	for(int i = 0; i < deptListCount ; i++) {
		spring.mybatis.gw.admin.dto.SelDeptDTO data = (spring.mybatis.gw.admin.dto.SelDeptDTO)deptList.get(i);
		cd = data.getCd();
		if (cd==null) {cd = "";}
		nm = data.getNm();
		if (nm==null) {nm = "";}	
		%>
				<option value="<%= cd %>" ><%= nm %> [<%= cd %>]</option>
<% } %>
			</select>
		</div>
	</div>
	
	<p class="w3-center">
		<button type="button"  class="w3-btn w3-blue w3-border w3-border-blank w3-round " 
		id="saveButton" onclick="return dataSave();" >저장</button><!-- 저장 버튼을 누르면>> 실제 db 변경이 있음 >> 저장 후 다시 페이지 리로드 되면 >> tree 값이 변경됨 -->
	</p>
	<br />
	</form>
</div>
</div>
<!-- 요기 까지 오른쪽 -->

</div>

<br /><br />

<!-- 팝업 레이아웃 예시 참고 : w3-dropdown-hover -->

<!-- 팝업 메뉴 [시작] -->
<div id="popupMenu" class="w3-dropdown-content w3-border w3-margin-left w3-round" >
	<a id="appendM" href="#" class="w3-hover-indigo  w3-hide"><i class="fa fa-plus-circle"></i> 최상위 부서 추가</a>
	<a id="append" href="#" class="w3-hover-indigo"><i class="fa fa-plus"></i> 하위 부서 추가</a>
	<!-- 삭제를 현재 선택된 것만 삭제 할지 하위 부서도 모두 삭제할지... 나중에 추가... -->
	<!-- <a id="del" href="#" class="w3-hover-red"><i class="fa fa-remove"></i> 삭제</a>
	<a id="del" href="#" class="w3-hover-red"><i class="fa fa-trash-o"></i> 삭제</a> -->
	<a id="remove" href="#" class="w3-hover-red"><i class="fa fa-trash"></i> 삭제</a>
	<!-- 
	<a id="up" href="#" class="w3-hover-green"><i class="fa fa-sort-up"></i> 위로 이동</a>
	<a id="down" href="#" class="w3-hover-purple"><i class="fa fa-sort-down"></i> 아래로 이동</a>-->
</div>
<!-- 팝업 메뉴 [끝] -->

</div>

</body>
</html>