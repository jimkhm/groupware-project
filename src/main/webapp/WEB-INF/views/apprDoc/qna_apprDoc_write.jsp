<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ page import="java.util.*, java.text.*" %>
<%
//WriterInfo_Bean empInfo = (WriterInfo_Bean)request.getAttribute("empData");
	//request 영역에 저장된 데이터를 꺼냄
//CompanyDTO adminInfo = (CompanyDTO)request.getAttribute("adminInfo");

Date date = new Date();
SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy-MM-dd");
String strdate = simpleDate.format(date);	
String id = (String)session.getAttribute("id");
String empnm = (String) request.getSession().getAttribute("empnm");
	
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
	<title>전자 결재 등록</title>

<link rel="stylesheet" href="http://www.w3schools.com/lib/w3.css">
<link rel="stylesheet" href="http://www.w3schools.com/lib/w3-theme-green.css">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.6.3/css/font-awesome.min.css">
<script src = "./resources/js/jquery-3.1.0.js"></script>
	<script language="javascript">
						
		function search(){			
			var url = "./EmployeeStepPop.admin";
		    window.open(url,"CLIENT_WINDOW","width=1000, height=800, toolbar=no, menubar=no, location=no, scrollbars=yes, status=no,resizable=no, fullscreen=no, channelmode=no, directories=no;");
			return false;
		}
		
		function validateForm(formName) {
			//alert("submit 직전1");
					
			// 앞 뒤 공백 제거
		    document.forms[formName]["appr_title"].value = document.forms[formName]["appr_title"].value.trim();
		   	document.forms[formName]["apprContent"].value = document.forms[formName]["apprContent"].value.trim();
		    document.forms[formName]["apprDate"].value = document.forms[formName]["apprDate"].value.trim();
		    document.forms[formName]["finalDate"].value = document.forms[formName]["finalDate"].value.trim();
		    
		    if ($('#apprEmpNo0').length) {
		    	document.forms[formName]["apprEmpNo0"].value = document.forms[formName]["apprEmpNo0"].value.trim(); 
		    }
		    
		    //if(dcument.getElementById('apprEmpNo0')){
		    //document.forms[formName]["apprEmpNo0"].value = document.forms[formName]["apprEmpNo0"].value.trim(); 
		    	
		   // }
		    
		    //alert("submit 직전2");
			
		    var check1 = document.forms[formName]["appr_title"].value; // *제목
		    var check2 = document.forms[formName]["apprContent"].value; // *내용
		    var check3 = document.forms[formName]["apprDate"].value; // *결재일
		    var check4 = document.forms[formName]["finalDate"].value; // *마감일
		    var check5 = null;
		    if ($('#apprEmpNo0').length) {
		    	check5 = document.forms[formName]["apprEmpNo0"].value; // 결재자 
		    }
		    		  
		   
		    
		    if (check1 == null || check1 == "") {
		    	alert("제목을 입력해 주세요.");
		        document.forms[formName]["appr_title"].focus();
		        return false;
		    } else if (check2 == null || check2 == "") {
		    	alert("내용을 입력해 주세요.");
		    	document.forms[formName]["apprContent"].focus();
		    	return false;
		    } else if (check3 == null || check3 == "") {
		    	alert("결재일를 입력해 주세요.");
		    	document.forms[formName]["apprDate"].focus();
		    	return false;
		    }else if (check4 == null || check4 == "") {
		    	alert("마감일을 입력해 주세요.");
		    	document.forms[formName]["finalDate"].focus();
		    	return false;
		    }else if (check5 == null || check5 == "") {
		    	alert("결재자를 입력해 주세요.");		    	
		    	return false;
		    }else {
		    	
		    	 if(true == validateDate()){
		    		
			    	 boardform.submit();	 
		    	 }
		    	 
		    	return false;
		    }		    
		}
		
		//상신일, 결재일, 마감일 비교
		function validateDate(){					
			
			var dateUp = $('#dateUp').val();
			var apprDate = $('#apprDate').val();
			var finalDate = $('#finalDate').val();
			 
			var upDateArray = dateUp.split('-'); 
			var apprDateArray = apprDate.split('-');
			var finalDateArray = finalDate.split('-');
					
			var date_up = new Date(upDateArray[0], upDateArray[1], upDateArray[2]);
			var appr_date = new Date(apprDateArray[0], apprDateArray[1], apprDateArray[2]);
			var final_date = new Date(finalDateArray[0], finalDateArray[1], finalDateArray[2]);
						
			if(date_up.getTime() > appr_date.getTime()) {
	            alert("결재일은 상신일 이후로 설정해야 합니다. .");
	            return false;
	        }  	                
	        
	        if(appr_date.getTime() > final_date.getTime()) {
	            alert("마감일은 종료일 이후로 설정해야 합니다.");
	            return false;
	        }
	        
	        return true;
	        
		}
		
		function addForm(formName){
			
			validateForm(formName);
			
		}
		
		function chkword(obj, maxByte) {
			 
	        var strValue = obj.value;
	        var strLen = strValue.length;
	        var totalByte = 0;
	        var len = 0;
	        var oneChar = "";
	        var str2 = "";
	 
	        for (var i = 0; i < strLen; i++) {
	            oneChar = strValue.charAt(i);
	            if (escape(oneChar).length > 4) {
	                totalByte += 3;
	            } else {
	                totalByte++;
	            }
	 
	            // 입력한 문자 길이보다 넘치면 잘라내기 위해 저장
	            if (totalByte <= maxByte) {
	                len = i + 1;
	            }
	        }
	 
	        // 넘어가는 글자는 자른다.
	        if (totalByte > maxByte) {
	            alert(maxByte + "Byte를 초과 입력 할 수 없습니다.");
	            str2 = strValue.substr(0, len);
	            obj.value = str2;
	            chkword(obj, 4000);
	        }
	    }
		
		$(function(){
			$("#addFile").click(function(){
				var cnt = $("#fileaddcnt").val();
				cnt++;
				$("#fileaddcnt").val(cnt);
				var str = "";
				str = "<input name=\"uploadFile" + cnt + "\" type=\"file\"/> <br />";		
				$("#divFile").append(str);
			});
		});
		
		
		/*
		function inRowLast(apprLine, empno, name){	
			
			alert("* : empno:"+empno+",name:"+name);
			
			var apprEmpCnt = document.getElementById("apprEmpCnt").value;
			var agreeEmpCnt = document.getElementById("agreeEmpCnt").value;
			var enforceEmpCnt = document.getElementById("enforceEmpCnt").value;
			var referEmpCnt = document.getElementById("referEmpCnt").value;
						
			if(apprLine == "A"){
				
				alert("A : empno:"+empno+",name:"+name);
				
				var addInput = document.createElement("span");
				var addSpan = document.createElement("span");
				
				addInput.innerHTML = "<input id= 'apprEmpNo"+apprEmpCnt+"' "+"name='apprEmpNo"+apprEmpCnt+"' "+"type='hidden' value='"+empno+"'>";
				addSpan.innerHTML ="<span id='apprEmpNm"+apprEmpCnt+"' "+"class='w3-large'>"+name+"</span>&nbsp;&nbsp;";
				
				document.getElementById("apprEmp").appendChild(addInput);
				document.getElementById("apprEmp").appendChild(addSpan);
									

			}else if(apprLine == "B"){
				alert("B : empno:"+empno+",name:"+name);  
				var addInput = document.createElement("span");
				var addSpan = document.createElement("span");
				
				addInput.innerHTML = "<input id= 'apprEmpNo"+agreeEmpCnt+"' "+"name='apprEmpNo"+agreeEmpCnt+"' "+"type='hidden' value='"+empno+"'>";
				addSpan.innerHTML = "<span id='apprEmpNm"+agreeEmpCnt+"' "+"class='w3-large'>"+name+"</span>&nbsp;&nbsp;";
				
				document.getElementById("agreeEmp").appendChild(addInput);
				document.getElementById("agreeEmp").appendChild(addSpan);
											
																						
			}else if(apprLine == "C"){
				alert("C : empno:"+empno+",name:"+name);
				
				var addInput = document.createElement("span");
				var addSpan = document.createElement("span");
				
				addInput.innerHTML = "<input id= 'apprEmpNo"+enforceEmpCnt+"' "+"name='apprEmpNo"+enforceEmpCnt+"' "+"type='hidden' value='"+empno+"'>";
				addSpan.innerHTML ="<span id='apprEmpNm"+enforceEmpCnt+"' "+"class='w3-large'>"+name+"</span>&nbsp;&nbsp;";	
				
				document.getElementById("enforceEmp").appendChild(addInput);
				document.getElementById("enforceEmp").appendChild(addSpan);				
				
																						
			}else if(apprLine == "D"){
				alert("D : empno:"+empno+",name:"+name);
				
				var addInput = document.createElement("span");
				var addSpan = document.createElement("span");
				
				addInput.innerHTML = "<input id= 'apprEmpNo"+referEmpCnt+"' "+"name='apprEmpNo"+referEmpCnt+"' "+"type='hidden' value='"+empno+"'>";
				addSpan.innerHTML ="<span id='apprEmpNm"+referEmpCnt+"' "+"class='w3-large'>"+name+"</span>&nbsp;&nbsp;";	
				
				document.getElementById("referEmp").appendChild(addInput);
				document.getElementById("referEmp").appendChild(addSpan);
				
			}		
		
	}*/
				
	</script>
</head>
<body class="w3-container">

	<div class="w3-container">

		<h2>전자 결재 등록</h2>
		
		<!-- ./ApprDocAddAction.appr -->

		<form name="boardform" action="./ApprDocWrite.appr" method="post" enctype="multipart/form-data" 
		class="w3-container w3-card-4" style="width:770px;">
		<input id="fileaddcnt" name="fileaddcnt" type="hidden" value="0" />
		<!-- boardform 이름으로 form안에 있는 것을 부를거다-->
		<!--multipart/form-data로 해야 파일까지 전송한다.  -->
		<!-- application/x-www-form-urlencoded : 이것이 디폴트이다. -->
		
		
		
		<!-- 이것은 자동이동을 막기위함이다. -->
		<div style="display:none">
			<input type="submit" onclick="return false;" />
			<input type="text"/>
		</div>
			
		<br/>
		<input id="apprEmpCnt" name="apprEmpCnt" type="hidden"  value="0">
		<input id="agreeEmpCnt" name="agreeEmpCnt" type="hidden" value="0" >
		<input id="enforceEmpCnt" name="enforceEmpCnt" type="hidden" value="0" >
		<input id="referEmpCnt" name="referEmpCnt" type="hidden" value="0" >
		
		<!-- <div>
			<button type="button" class="w3-btn w3-blue w3-border w3-border-blank w3-round "
				id="searchButton" onclick="return search();" style="display: inline-block;">결재라인 등록</button>
		</div> -->
		<div >
			<button type="button" class="w3-btn w3-blue w3-border w3-border-blank w3-round "
				id="searchButton" onclick="return search();" style="display: inline-block;"  >결재라인 등록</button>				
			<span class="w3-right"><button onclick="javascript:history.go(-1);" class="w3-btn w3-blue w3-border w3-round " >뒤로</button>&nbsp;&nbsp;	
			<button onclick="return validateForm('boardform');" class="w3-btn w3-blue w3-border w3-round " >등록</button>
			</span>
		</div>				
		
		<br/>
		<br/>
		
		<div >
			<label class="w3-text-blue "><b>제목</b></label><br />
			<div class="w3-container w3-margin-left w3-margin-right w3-margin-4">
			<input name="apprTitle" id="appr_title" class="w3-input w3-border w3-threequarter " type="text" value="" 
			placeholder="이곳에 제목을 입력하세요." maxlength="100" onkeyup="chkword(this, 200)" /> 
			</div>
		</div>
		<br/>
		
			
		<div >
			<label class="w3-text-blue w3-large"><b>내용</b></label><br />
			<div class="w3-container w3-margin-left w3-margin-right w3-margin-top">
				<textarea name="apprContent"   cols="60" rows="8" class="w3-input w3-border w3-threequarter "
				placeholder="이곳에 내용을 입력하세요." onkeyup="chkword(this, 2000)" ></textarea>
			</div>
		</div>
		<br/>
		
		<div class="w3-row">
		
			<div class="w3-col s4 w3-center">
				<label class="w3-text-blue w3-large"><b>상신일</b></label><br />
				<div class="w3-container w3-margin-left w3-margin-right w3-margin-4"><span class="w3-large" >
				<%=strdate %><input id="dateUp" name="dateUp" type="hidden" value="<%=strdate %>"></span>
				</div>
			</div>
			
			<div class="w3-col s4 w3-center">
				<label class="w3-text-blue w3-large"><b>결재일</b></label><br />
				<div class="w3-container w3-margin-left w3-margin-right w3-margin-4">
					<input  id="apprDate" name="apprDate" type="date" value=""/>
				</div>
			</div>
			
			<div class="w3-col s4 w3-center">
				<label class="w3-text-blue w3-large"><b>마감일</b></label><br />
				<div class="w3-container w3-margin-left w3-margin-right w3-margin-4">
					<input id="finalDate" name="finalDate" type="date" value=""/>
				</div>
			</div>
		
		</div>
		<br/>
	
	
		<div >
			<label class="w3-text-blue w3-large"><b>기안자</b></label><br />
			<div class="w3-container w3-margin-left w3-margin-right w3-margin-top"><span class="w3-large"><%=empnm %></span></div>
		</div>
		<br/>		
		<div >
			<label class="w3-text-blue w3-large"><b>결재자</b></label><br />
			<div  id= "apprEmp" class="w3-container w3-margin-left w3-margin-right w3-margin-4">				
				<!-- <input id="apprEmpNo0" name="apprEmpNo0" type="hidden" value=""> -->
				
				<!-- 결재자 javascript 동적 생성 함수 필요 -->
				<!-- 
				<input id="apprEmpNo0" name="apprEmpNo0" type="hidden" value="E0001">
				<span id="apprEmpNm0" class="w3-large">홍길동</span>&nbsp;&nbsp;
				
				<input id="apprEmpNo1" name="apprEmpNo1" type="hidden" value="E0002">
				<span id="apprEmpNm1" class="w3-large">유관순</span>&nbsp;&nbsp;	
			 -->
			</div>
		</div>
		<br/>
		
		<div >
			<label class="w3-text-blue w3-large"><b>합의자</b></label><br />
			<div id= "agreeEmp"  class="w3-container w3-margin-left w3-margin-right w3-margin-4">
			
				<!-- 합의자 javascript 동적 생성 함수 필요 -->
				<!-- 
				<input id="agreeEmpNo0" name="agreeEmpNo0" type="hidden" value="E0003">
				<span id="agreeEmpNm0" class="w3-large">이순신</span>&nbsp;&nbsp;
			 -->
			</div>
		</div>
		<br/>
		
		<div >
			<label class="w3-text-blue w3-large"><b>시행자</b></label><br />
			<div id="enforceEmp" class="w3-container w3-margin-left w3-margin-right w3-margin-4">
			
				<!-- 시행자 javascript 동적 생성 함수 필요 -->
				<!-- 
				<input id="enforceEmpNo0" name="enforceEmpNo0" type="hidden" value="E0005">
				<span id="enforceEmpNm0" class="w3-large">이순신2</span>&nbsp;&nbsp;
			 -->
			</div>
		</div>
		<br/>
		
		<div >
			<label class="w3-text-blue w3-large"><b>수신참조자</b></label><br />
			<div id ="referEmp" class="w3-container w3-margin-left w3-margin-right w3-margin-4">
			
				<!-- 수신참조자 javascript 동적 생성 함수 필요 -->
				<!-- 
				<input id="referEmpNo0" name="referEmpNo0" type="hidden" value="E0004">
				<span id="referEmpNm0" class="w3-large">이순신</span>&nbsp;&nbsp;
			 -->
			</div>
		</div>
		<br/>
		
		<div >
			<label class="w3-text-blue w3-large"><b>파일 첨부</b></label><br />
			<div id="divFile">
					<input name="uploadFile0" type="file"/>
					<button type="button" class="w3-btn w3-blue w3-border w3-border-blank w3-round-xlarge w3-small "
						id="addFile" >&nbsp;추가&nbsp;</button><br />
			</div>		
		</div>
		<br/>
				
		<br/><br/>
</form>
</div>		
		
</body>
</html>

    