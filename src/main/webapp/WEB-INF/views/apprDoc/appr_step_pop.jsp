<%--
 //******************************************************************************
//  프로그램ID : appr_step_pop.jsp
//  프로그램명 : 부서 결재 라인 popup
//  관련 DB 테이블 : 
//  기타 DB 테이블 : 
//  작  성  자 : 조 수 정 (Sujung Jo)
//  작  성  일 : 2016.10.28.
//******************************************************************************
 --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%
	//String sessionCompany = (String) request.getSession().getAttribute("company"); // session company
	String sessionCompany = "C001";
	String company = sessionCompany;
//	if(request.getParameter("company") != null) {
//		company = request.getParameter("company");
//	}
	
	String xmlStr = (String) request.getAttribute("xmlStr");
	
	List list = (List) request.getAttribute("empList");
	int listCount = list.size();
	
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="http://www.w3schools.com/lib/w3.css">
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
.odd {background-color:#fff}
.even{background-color:#f1f1f1}
</style>
<script src="./resources/js/jquery-3.1.0.js"></script>
<script type="text/javascript">
var xml = '<%= xmlStr %>';

$(function(){

	var xmlDOC = $.parseXML(xml);
	
	$(xmlDOC).find('dept').each(function(){
		var m_dept_no = $(this).find('m_dept_no').text();
		var dept_no = $(this).find('dept_no').text();
		var output = '';
		if(m_dept_no=='M'){
			output += '<ul id=\"m_'+m_dept_no+'\">';
			output += '<li id=\"'+dept_no+'\">';
			output += '<span id=\"spanDept\" class=\"w3-hover-blue\">' + $(this).find('dept_nm').text();
			output += ' [' + $(this).find('dept_no').text() + ']' + '</span>';
			output += '<input type="hidden" id="m_dept_no" value=\"'+m_dept_no+'\" class=\"mgr\">';
			output += '<input type="hidden" id="id_dept_no" value=\"'+dept_no+'\" class="off ">';
			output += '</li>';
			output += '</ul>';
		}
		
		$("#deptTree").append(output);
	});
	
	var temp = '';
	$(xmlDOC).find('dept').each(function(){
		var m_dept_no = $(this).find('m_dept_no').text();
		var dept_no = $(this).find('dept_no').text();
		var output = '';
		if(m_dept_no!='M'){
			
			if(temp == m_dept_no){
				output += '<li id=\"'+dept_no+'\">';
				output += '<span id=\"spanDept\" class=\"w3-hover-blue\">' + $(this).find('dept_nm').text();
				output += ' [' + $(this).find('dept_no').text() + ']' + '</span>';
				output += '<input type="hidden" id="m_dept_no" value=\"'+m_dept_no+'\" class=\"mgr\">';
				output += '<input type="hidden" id="id_dept_no" value=\"'+dept_no+'\" class="off ">';
				output += '</li>';			
				$("#m_"+m_dept_no).append(output);
			}else{
				output += '<ul id=\"m_'+m_dept_no+'\" >';
				output += '<li id=\"'+dept_no+'\">';
				output += '<span id=\"spanDept\" class=\"w3-hover-blue\">' + $(this).find('dept_nm').text();
				output += ' [' + $(this).find('dept_no').text() + ']' + '</span>';
				output += '<input type="hidden" id="m_dept_no" value=\"'+m_dept_no+'\" class=\"mgr\">';
				output += '<input type="hidden" id="id_dept_no" value=\"'+dept_no+'\" class="off ">';
				output += '</li>';
				output += '</ul>';
				$("#"+m_dept_no).append(output);
			}
			temp = m_dept_no;	
			
		}	
	});

	$(".mgr").each(function(){
		var val = $(this).val();
		$("input[type=hidden]").each(function(){
			var val2 = $(this).val();
			if(val == val2 && $(this).is("#id_dept_no")){			
				//$(this).css('color','hotpink');
				$(this).removeAttr("class");
				$("#"+val).attr("class","on");
			}
		});
	});
	
	$(".off").each(function(){
		var val = $(this).val();
		$("#"+val).attr("class","off");		
	});
	
	// 조직도 선택시, [시작]
	//dblclick 시작
	$("span[id='spanDept']").dblclick(function(){
		var val = $(this).text();
		//$("#tableTitle").text(val.substr(0, val.lastIndexOf("["))+"사원"); // 사원 리스트 테이블의 타이틀이 변경된다.(부서명)
		var len = val.lastIndexOf("]") - val.lastIndexOf("[");
		var val = val.substr(val.lastIndexOf("[")+1, len-1);		
		//$("#clickedDept").prop("value",val); // 사원 리스트 조회시 참고할 hidden에 값을 넣어준다.(부서번호)
		
 		if($("#"+val).hasClass("off")){ 			
			$("#"+val+":has(ul)>ul").show(); 			
			$("#"+val+":has(ul)").attr('class','on');	
		} else {
			$("#"+val+":has(ul)>ul").hide();
			$("#"+val).attr('class','off');
		}		
	});
	//dblclick 끝
	//click 시작
	$("span[id='spanDept']").click(function(){
		var val = $(this).text();
		$("#tableTitle").text(val.substr(0, val.lastIndexOf("["))+"사원"); // 사원 리스트 테이블의 타이틀이 변경된다.(부서명)
		var len = val.lastIndexOf("]") - val.lastIndexOf("[");
		var val = val.substr(val.lastIndexOf("[")+1, len-1);		
		$("#clickedDept").prop("value",val); // 사원 리스트 조회시 참고할 hidden에 값을 넣어준다.(부서번호)
		/*
 		if($("#"+val).hasClass("off")){ 			
			$("#"+val+":has(ul)>ul").show(); 			
			$("#"+val+":has(ul)").attr('class','on');	
		} else {
			$("#"+val+":has(ul)>ul").hide();
			$("#"+val).attr('class','off');
		}*/
		
		// 선택된 부서 사원 검색 [시작]
		var cnt = 0;
		$("#myTable tr").each(function(){			
			var td = $(this).children("td:eq(1)").html();
			var deptCd = $(this).children("td:eq(1)").children("input[type=hidden]").val();
			if(td!=undefined){
				if(val != deptCd){					
					$(this).removeClass("rowon");
					$(this).removeClass("rowoff");
					
					$(this).addClass("rowoff");
				} else { // 선택되어진 dept
					cnt++;
					$(this).removeClass("rowon");
					$(this).removeClass("rowoff");
					
					$(this).addClass("rowon");
				}
			}
		});		
		$("#myTable [class*=nodata]").each(function(){
			if(cnt==0){
				$(".nodata").removeClass("rowoff");
				$(".nodata").addClass("rowon");
			} else {
				$(".nodata").removeClass("rowon");
				$(".nodata").addClass("rowoff");
			}
		});
		$('input[type=checkbox]').prop("checked", "");
		// 선택된 부서 사원 검색 [끝]
		
		//.w3-table-all tr:nth-child(odd){background-color:#fff}
		//.w3-table-all tr:nth-child(even){background-color:#f1f1f1}	
		$(".rowon").each(function(idx){			
			if(idx%2!=0){
				$(this).removeClass("odd");
				$(this).addClass("even");
			}else{
				$(this).removeClass("even");
				$(this).addClass("odd");
			}
		});
	});
	//click 끝
	// 조직도 선택시, [끝]
	
	// 전체조회
	$("#empListReset").click(function(){
		$("#clickedDept").prop("value","");
		$("#tableTitle").text("사원");
		$("#myTable tr[class*=rowoff]").each(function(){
			if(false == $(this).hasClass("nodata")){
				$(this).removeClass("rowoff");
				$(this).addClass("rowon");
			}
		});
		$("#myTable [class*=nodata]").removeClass("rowon");
		$('input[type=checkbox]').prop("checked", "");
		
		//.w3-table-all tr:nth-child(odd){background-color:#fff}
		//.w3-table-all tr:nth-child(even){background-color:#f1f1f1}	
		var cnt=0;
		$(".rowon").each(function(idx){
			cnt++;
			if(idx%2!=0){
				$(this).removeClass("odd");
				$(this).addClass("even");
			}else{
				$(this).removeClass("even");
				$(this).addClass("odd");
			}
		});
		$("#myTable [class*=nodata]").each(function(){
			if(cnt==0){
				$(".nodata").removeClass("rowoff");
				$(".nodata").addClass("rowon");
			} else {
				$(".nodata").removeClass("rowon");
				$(".nodata").addClass("rowoff");
			}
		});
	});
	
	/* 체크 박스 전체 선택/해제 */
	$('#checkall').click(function(){			
		var chk = $(this).prop('checked');
		$('input[type=checkbox]').prop("checked", chk);
	});
	
	/* 사원 추가 button 시작 */
	/* 결재자 button */
	$("#btn1").click(function(){
		$("input[type=checkbox]:checked").each(function() {
			var trObj = $(this).parent().parent("tr[class*=rowon]"); // 보여지는 tr만 선택되도록...			
			var id = $(this).attr('id');
            if(id!="checkall"){
            	var idx = id.substring(3, id.length);
            	var txt = $("#txt"+idx).val();
            	var empNo = $("#empNo"+idx).val();
            	
    			var selectObj = $("#sel1");
    			var newObj = new Option(txt,empNo);    			
    			var isList = false;
    			
    			// 합의자랑 중복체크
    			for(var i=0; i<$("#sel2").children().length ; i++) {
    				var val = $('#sel2 option:eq('+i+')').val();
    				if(val == empNo){ // 중복되게 넣지 않기 위해 비교한다.
    					isList = true;
    					//alert(""+i+":"+$('#sel1 option:eq('+i+')').val());    		    					
    				}
    			}
    			
    			for(var i=0; i<selectObj.children().length ; i++) {
    				var val = $('#sel1 option:eq('+i+')').val();
    				if(val == empNo){ // 중복되게 넣지 않기 위해 비교한다.
    					isList = true;
    					//alert(""+i+":"+$('#sel1 option:eq('+i+')').val());    		    					
    				}
    			}
    			if(isList == false){ // 중복된 값이 없으면 select 에 추가해 넣는다.
    				if(trObj.val()!=undefined){
    					selectObj.get(0).options[selectObj.children().length] = newObj;
    				}
    			}
            }
        });
	});
	/* 합의자 button */
	$("#btn2").click(function(){
		$("input[type=checkbox]:checked").each(function() {
			var trObj = $(this).parent().parent("tr[class*=rowon]"); // 보여지는 tr만 선택되도록...
			var id = $(this).attr('id');
            if(id!="checkall"){
            	var idx = id.substring(3, id.length);
            	var txt = $("#txt"+idx).val();
            	var empNo = $("#empNo"+idx).val();
            	
    			var selectObj = $("#sel2");
    			var newObj = new Option(txt,empNo);    			
    			var isList = false;
    			
    			// 결재자랑 중복체크
    			for(var i=0; i<$("#sel1").children().length ; i++) {
    				var val = $('#sel1 option:eq('+i+')').val();
    				if(val == empNo){ // 중복되게 넣지 않기 위해 비교한다.
    					isList = true;
    					//alert(""+i+":"+$('#sel1 option:eq('+i+')').val());    		    					
    				}
    			}
    			
    			for(var i=0; i<selectObj.children().length ; i++) {
    				var val = $('#sel2 option:eq('+i+')').val();
    				if(val == empNo){ // 중복되게 넣지 않기 위해 비교한다.
    					isList = true;
    					//alert(""+i+":"+$('#sel1 option:eq('+i+')').val());    		    					
    				}
    			}
    			if(isList == false){ // 중복된 값이 없으면 select 에 추가해 넣는다.
    				if(trObj.val()!=undefined){
    					selectObj.get(0).options[selectObj.children().length] = newObj;
    				}
    			}
            }
        });
	});
	/* 시행자 button */
	$("#btn3").click(function(){
		$("input[type=checkbox]:checked").each(function() {
			var trObj = $(this).parent().parent("tr[class*=rowon]"); // 보여지는 tr만 선택되도록...
			var id = $(this).attr('id');
            if(id!="checkall"){
            	var idx = id.substring(3, id.length);
            	var txt = $("#txt"+idx).val();
            	var empNo = $("#empNo"+idx).val();
            	
    			var selectObj = $("#sel3");
    			var newObj = new Option(txt,empNo);    			
    			var isList = false;
    			for(var i=0; i<selectObj.children().length ; i++) {
    				var val = $('#sel3 option:eq('+i+')').val();
    				if(val == empNo){ // 중복되게 넣지 않기 위해 비교한다.
    					isList = true;
    					//alert(""+i+":"+$('#sel1 option:eq('+i+')').val());    		    					
    				}
    			}
    			if(isList == false){ // 중복된 값이 없으면 select 에 추가해 넣는다.
    				if(trObj.val()!=undefined){
    					selectObj.get(0).options[selectObj.children().length] = newObj;
    				}
    			}
            }
        });
	});
	/* 수신참조자 button */
	$("#btn4").click(function(){
		$("input[type=checkbox]:checked").each(function() {
			var trObj = $(this).parent().parent("tr[class*=rowon]"); // 보여지는 tr만 선택되도록...
			var id = $(this).attr('id');
            if(id!="checkall"){
            	var idx = id.substring(3, id.length);
            	var txt = $("#txt"+idx).val();
            	var empNo = $("#empNo"+idx).val();
            	
    			var selectObj = $("#sel4");
    			var newObj = new Option(txt,empNo);    			
    			var isList = false;
    			for(var i=0; i<selectObj.children().length ; i++) {
    				var val = $('#sel4 option:eq('+i+')').val();
    				if(val == empNo){ // 중복되게 넣지 않기 위해 비교한다.
    					isList = true;
    					//alert(""+i+":"+$('#sel1 option:eq('+i+')').val());    		    					
    				}
    			}
    			if(isList == false){ // 중복된 값이 없으면 select 에 추가해 넣는다.
    				if(trObj.val()!=undefined){
    					selectObj.get(0).options[selectObj.children().length] = newObj;
    				}
    			}
            }
        });
	});
	/* 사원 추가 button 끝 */
	
	/* 결재자 select 변경 button 시작 */
	$("#del1").click(function(){
		$("#sel1 option:selected").each(function(){
			$(this).remove();
		});
	});
	
	$("#up1").click(function(){
		$("#sel1 option:selected").each(function(){
			var selectObj = $(this);
			if(selectObj.index() == 0 ){
				return false;
			}
			var targetObj = $('#sel1 option:eq('+(selectObj.index()-1)+')');
			targetObj.before(selectObj);
		});
	});
	
	$("#down1").click(function(){
		$("#sel1 option:selected").each(function(){
			var selectObj = $(this);
			if(selectObj.index() == $('#sel1').children().length ){
				return false;
			}
			var targetObj = $('#sel1 option:eq('+(selectObj.index()+1)+')');
			targetObj.after(selectObj);
		});
	});
	/* 결재자 select 변경 button 끝 */
	
	/* 합의자 select 변경 button 시작 */
	$("#del2").click(function(){
		$("#sel2 option:selected").each(function(){
			$(this).remove();
		});
	});
	
	$("#up2").click(function(){
		$("#sel2 option:selected").each(function(){
			var selectObj = $(this);
			if(selectObj.index() == 0 ){
				return false;
			}
			var targetObj = $('#sel2 option:eq('+(selectObj.index()-1)+')');
			targetObj.before(selectObj);
		});
	});
	
	$("#down2").click(function(){
		$("#sel2 option:selected").each(function(){
			var selectObj = $(this);
			if(selectObj.index() == $('#sel2').children().length ){
				return false;
			}
			var targetObj = $('#sel2 option:eq('+(selectObj.index()+1)+')');
			targetObj.after(selectObj);
		});
	});
	/* 합의자 select 변경 button 끝 */
	
	/* 시행자 select 변경 button 시작 */
	$("#del3").click(function(){
		$("#sel3 option:selected").each(function(){
			$(this).remove();
		});
	});
	
	$("#up3").click(function(){
		$("#sel3 option:selected").each(function(){
			var selectObj = $(this);
			if(selectObj.index() == 0 ){
				return false;
			}
			var targetObj = $('#sel3 option:eq('+(selectObj.index()-1)+')');
			targetObj.before(selectObj);
		});
	});
	
	$("#down3").click(function(){
		$("#sel3 option:selected").each(function(){
			var selectObj = $(this);
			if(selectObj.index() == $('#sel3').children().length ){
				return false;
			}
			var targetObj = $('#sel3 option:eq('+(selectObj.index()+1)+')');
			targetObj.after(selectObj);
		});
	});
	/* 시행자 select 변경 button 끝 */
	
	/* 수신참조자 select 변경 button 시작 */
	$("#del4").click(function(){
		$("#sel4 option:selected").each(function(){
			$(this).remove();
		});
	});
	
	$("#up4").click(function(){
		$("#sel4 option:selected").each(function(){
			var selectObj = $(this);
			if(selectObj.index() == 0 ){
				return false;
			}
			var targetObj = $('#sel4 option:eq('+(selectObj.index()-1)+')');
			targetObj.before(selectObj);
		});
	});
	
	$("#down4").click(function(){
		$("#sel4 option:selected").each(function(){
			var selectObj = $(this);
			if(selectObj.index() == $('#sel4').children().length ){
				return false;
			}
			var targetObj = $('#sel4 option:eq('+(selectObj.index()+1)+')');
			targetObj.after(selectObj);
		});
	});
	/* 수신참조자 select 변경 button 끝 */
	
	/* 전송 버튼 클릭 시작 */
	$("#send").click(function(){
		
		// 결재자		
		$(opener.document).find("#apprEmp").children().remove();
		$(opener.document).find("#apprEmpCnt").val(0);
		$("#sel1 option").each(function(){
			var optionObj = $(this);
			var objIndex = optionObj.index();
			var objVal = optionObj.val();
			var objText = optionObj.text();
			
			var output = "<input id= 'apprEmpNo" + objIndex + "' "+"name='apprEmpNo" + objIndex + "' "+"type='hidden' value='" + objVal + "'>";
				output += "<span id='apprEmpNm" + objIndex + "' "+"class='w3-large'>" + objText + "&nbsp;&nbsp;</span>";
			
			$(opener.document).find("#apprEmp").append(output);
			
			var cnt = $(opener.document).find("#apprEmpCnt").val();			
			$(opener.document).find("#apprEmpCnt").val(++cnt);
		});
		
		// 합의자
		$(opener.document).find("#agreeEmp").children().remove();
		$(opener.document).find("#agreeEmpCnt").val(0);
		$("#sel2 option").each(function(){
			var optionObj = $(this);
			var objIndex = optionObj.index();
			var objVal = optionObj.val();
			var objText = optionObj.text();
			
			var output = "<input id= 'agreeEmpNo" + objIndex + "' "+"name='agreeEmpNo" + objIndex + "' "+"type='hidden' value='" + objVal + "'>";
				output += "<span id='agreeEmpNm" + objIndex + "' "+"class='w3-large'>" + objText + "&nbsp;&nbsp;</span>";
			
			$(opener.document).find("#agreeEmp").append(output);
			
			var cnt = $(opener.document).find("#agreeEmpCnt").val();			
			$(opener.document).find("#agreeEmpCnt").val(++cnt);
		});
		
		// 시행자
		$(opener.document).find("#enforceEmp").children().remove();
		$(opener.document).find("#enforceEmpCnt").val(0);
		$("#sel3 option").each(function(){
			var optionObj = $(this);
			var objIndex = optionObj.index();
			var objVal = optionObj.val();
			var objText = optionObj.text();
			
			var output = "<input id= 'enforceEmpNo" + objIndex + "' "+"name='enforceEmpNo" + objIndex + "' "+"type='hidden' value='" + objVal + "'>";
				output += "<span id='enforceEmpNm" + objIndex + "' "+"class='w3-large'>" + objText + "&nbsp;&nbsp;</span>";
			
			$(opener.document).find("#enforceEmp").append(output);
			
			var cnt = $(opener.document).find("#enforceEmpCnt").val();			
			$(opener.document).find("#enforceEmpCnt").val(++cnt);
		});
		
		// 수신참조자
		$(opener.document).find("#referEmp").children().remove();
		$(opener.document).find("#referEmpCnt").val(0);
		$("#sel4 option").each(function(){
			var optionObj = $(this);
			var objIndex = optionObj.index();
			var objVal = optionObj.val();
			var objText = optionObj.text();
			
			var output = "<input id= 'referEmpNo" + objIndex + "' "+"name='referEmpNo" + objIndex + "' "+"type='hidden' value='" + objVal + "'>";
				output += "<span id='referEmpNm" + objIndex + "' "+"class='w3-large'>" + objText + "&nbsp;&nbsp;</span>";
			
			$(opener.document).find("#referEmp").append(output);
			
			var cnt = $(opener.document).find("#referEmpCnt").val();			
			$(opener.document).find("#referEmpCnt").val(++cnt);
		});
		
		window.close();
		
	});
	/* 전송 버튼 클릭 끝 */
	
});
</script>
</head>
<body class="w3-light-grey">

<!-- Page Container -->
<div class="w3-container w3-content w3-margin-top" style="max-width:940px;">

  <!-- The Grid -->
  <div class="w3-row-padding" style="margin:0 -16px">
  
    <!-- Left Column -->
    <div class="w3-half" style="width:380px;">
    
      <div class="w3-white w3-text-grey w3-card-4">
        <div class="w3-display-container w3-margin-left w3-margin-right w3-padding-top">
        	
			<p class="w3-large"><b><i class="fa fa-asterisk fa-fw w3-margin-right w3-text-blue"></i>조직도</b></p>
			<div id="deptTree"  class="fa" style="height:772px;"></div>
			<br/><br/>
    	</div>
      </div><br/>

    <!-- End Left Column -->
    </div>

    <!-- Right Column -->
    <div class="w3-rest">
          <div class="w3-container w3-card-2 w3-white w3-margin-bottom w3-padding-top">
          
			<p class="w3-large w3-text-grey"><b><i class="fa fa-asterisk fa-fw w3-margin-right w3-text-blue"></i>
			<span id="tableTitle" >사원</span></b>&nbsp;<input id="clickedDept" type="hidden" value="" />
			<span class="w3-right"><input id="empListReset" type="button" class="w3-btn w3-small w3-white w3-border w3-border-blue" value="전체 조회"/></span>
			</p>
			
			<div style="height:270px;overflow-x: hidden;overflow-y: true;">
			<table id="myTable" class="w3-table w3-hoverable w3-bordered"><!-- 필터 적용후 w3-table >> w3-table-all 로 변경 -->
				<thead>
					<tr class="w3-light-grey" >
						<th style="width:50px;padding-bottom:2px;"><input id="checkall" class="w3-check" type="checkbox" style="top:0px;"></th>
						<th style="padding-bottom:2px;">부서</th>
						<th style="width:70px;padding-bottom:2px;">직급</th>
						<th style="padding-bottom:2px;">이름</th>
					</tr>
				</thead>
				<tr class="nodata rowoff"><td colspan="4" class="w3-center">조회된 자료가 없습니다.</td></tr>				
<%
	String	dept_no;		//	부서코드
	String	dept_nm;		// 부서명**
	String	emp_role_cd;//	직급코드
	String	emp_role_nm;//직급명**
	String	emp_nm;		//	이름
	String	emp_no;		//	사번
	
	for(int i = 0, j = 1; i < listCount ; i++, j++) {
		spring.mybatis.gw.admin.dto.EmployeeDTO data = (spring.mybatis.gw.admin.dto.EmployeeDTO)list.get(i);
		
		dept_no = data.getDept_no();
		if (dept_no==null) {dept_no = "";}
		dept_nm = data.getDept_nm();
		if (dept_nm==null) {dept_nm = "";}
		emp_role_cd = data.getEmp_role_cd();
		if (emp_role_cd==null) {emp_role_cd = "";}
		emp_role_nm = data.getEmp_role_nm();
		if (emp_role_nm==null) {emp_role_nm = "";}		
		emp_nm = data.getEmp_nm();
		if (emp_nm==null) {emp_nm = "";}	
		emp_no = data.getEmp_no();
		if (emp_no==null) {emp_no = "";}
		
%>
				<tr class="rowon <%= j%2==0? "even" : "odd" %>">
					<td style="padding-bottom:2px;"><input id="chk<%= j %>" class="w3-check" type="checkbox" style="top:0px;"/></td>
					<td style="padding-bottom:2px;"><%= dept_nm %><input id="deptCd" type="hidden" value="<%= dept_no %>" /></td>
					<td style="padding-bottom:2px;"><%= emp_role_nm %></td>
					<td style="padding-bottom:2px;"><%= emp_nm %>(<%= emp_no %>)<input id="txt<%= j %>" type="hidden" value="<%= emp_nm %>(<%= emp_no %>)" />
					<input id="empNo<%= j %>" type="hidden" value="<%= emp_no %>" /></td>
				</tr>
<% } %>
			</table>
			</div>
			
			<br />
			</div>
			
           <!-- 결재자 합의자 시행자 수신참조자 -->
		<div class="w3-container w3-center w3-margin-top w3-margin-bottom">
			<button id="btn1">결재자 <i class="fa fa-chevron-down"></i></button>
			&nbsp;&nbsp;
			<button id="btn2">합의자 <i class="fa fa-chevron-down"></i></button>
			&nbsp;&nbsp;
			<button id="btn3">시행자 <i class="fa fa-chevron-down"></i></button>
			&nbsp;&nbsp;
			<button id="btn4">수신참조자 <i class="fa fa-chevron-down"></i></button>
		</div>
		
		<div class="w3-container w3-card-2 w3-white w3-margin-bottom">
			
			<div class="w3-row">
			<div class="w3-col w3-padding-left w3-padding-right" style="width:20px;">&nbsp;</div>
			
			<div class="w3-col w3-padding-left w3-padding-right" style="width:220px;">
				<h5 class="w3-opacity"><b>결재자</b></h5>
				<div class="w3-row">
					<div class="w3-col" style="width:170px">
						<select id="sel1" size="6" style="width:164px;" >
						<!-- 
							<option value="1">사원1(사번1)</option>	
							<option value="2">사원2(사번2)</option>
							<option value="3">사원3(사번3)</option> -->
						</select>
						<br/><br/>
					</div>
					<div class="w3-rest">			
					 	<i id="up1" class="fa fa-chevron-circle-up" style="font-size:20px"></i><br /><br />
					 	<i id="down1" class="fa fa-chevron-circle-down" style="font-size:20px"></i><br /><br />
					 	<i id="del1" class=" fa fa-times-circle" style="font-size:20px"></i>
					 </div>
				 </div>
			 </div>
			 
			<div class="w3-col w3-padding-left w3-padding-right" style="width:220px;">
				<h5 class="w3-opacity"><b>합의자</b></h5>
				<div class="w3-row">
					<div class="w3-col" style="width:170px">
						<select id="sel2" size="6" style="width:164px;" >
							
						</select>
						<br/><br/>
					</div>
					<div class="w3-rest">			
					 	<i id="up2" class="fa fa-chevron-circle-up" style="font-size:20px"></i><br /><br />
					 	<i id="down2" class="fa fa-chevron-circle-down" style="font-size:20px"></i><br /><br />
					 	<i id="del2" class=" fa fa-times-circle" style="font-size:20px"></i>
					 </div>
				 </div>
			 </div>
			 
			 </div>
			 
			 
			<div class="w3-row">
			<div class="w3-col w3-padding-left w3-padding-right" style="width:20px;">&nbsp;</div>
			 
			<div class="w3-col w3-padding-left w3-padding-right" style="width:220px;">
				<h5 class="w3-opacity"><b>시행자</b></h5>
				<div class="w3-row">
					<div class="w3-col" style="width:170px">
						<select id="sel3" size="6" style="width:164px;" >
							
						</select>
						<br/><br/>
					</div>
					<div class="w3-rest">			
					 	<i id="up3" class="fa fa-chevron-circle-up" style="font-size:20px"></i><br /><br />
					 	<i id="down3" class="fa fa-chevron-circle-down" style="font-size:20px"></i><br /><br />
					 	<i id="del3" class=" fa fa-times-circle" style="font-size:20px"></i>
					 </div>
				 </div>
			 </div>
			 
			<div class="w3-col w3-padding-left w3-padding-right" style="width:220px;">
				<h5 class="w3-opacity"><b>수신참조자</b></h5>
				<div class="w3-row">
					<div class="w3-col" style="width:170px">
						<select id="sel4" size="6" style="width:164px;" >
							
						</select>
						<br/><br/>
					</div>
					<div class="w3-rest">			
					 	<i id="up4" class="fa fa-chevron-circle-up" style="font-size:20px"></i><br /><br />
					 	<i id="down4" class="fa fa-chevron-circle-down" style="font-size:20px"></i><br /><br />
					 	<i id="del4" class=" fa fa-times-circle" style="font-size:20px"></i>
					 </div>
				 </div>
			 </div>
			 
			 </div>
			
		</div>
		
		<p class="w3-center"><button id="send">전송 <i class="fa fa-check"></i></button></p>
		

    <!-- End Right Column -->
    </div>
    
  <!-- End Grid -->
  </div>
  
  <!-- End Page Container -->
</div>

</body>
</html>