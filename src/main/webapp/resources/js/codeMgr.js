// 상세 정보 보기
function detailView(i){
	var objParams ={ val : $('#m_m_'+i).text() };
	var values = [];
	var result = "null";
	$.post(
	 	"./CodeDetail.cd",
	 	objParams,
	 	function(data) {
	 		values = JSON.parse(data);
	 		if(values){
	 		    var x = document.getElementById("m_List_"+i);
	 			if (x.className.indexOf("w3-show") == -1) {
	 				$('#btn_M_'+i).removeClass("w3-white").addClass("w3-blue");
	 		        x.className += " w3-show";
	 		        var j;
	 		        var  out = "<table id=\"d_ListTable_"+objParams.val+"\" class=\"w3-table w3-centered w3-hoverable w3-bordered\">";
	 		        if(values[0].result == result){
	 		        		out += "<tr class=\"w3-pale-blue\">";
	 		        		out += "	<th id=\"d_m_"+values[0].cd_m+"\" colspan=\"4\">"+values[0].cd_m+"</td>";
	 		        		out += "	<th>Detail View</th>"	
	 		        		out += "	<th><button onclick=\"insert_Dcode('"+values[0].cd_m+"')\"  class=\"w3-btn fa fa-plus w3-white\"></button></td>";	
	 		        		out += "</tr>";
	 		        		out += "<tr>"
	 		        		out += "	<td>코드 (마스터)</td>"
	 		        		out += "	<td>코드 (디테일)</td>"
	 		        		out += "	<td>코드명</td>"
	 		        		out += "	<td>조회 순서</td>"
	 		        		out += "	<td>코드 설명</td>"
	 		        		out += "</tr>"
	 		        }else{
		 		        	out += "<tr class=\"w3-pale-blue\">";
	 		        		out += "	<th id=\"d_m_"+values[0].cd_m+"\" colspan=\"3\">"+values[0].cd_m+"</td>";
	 		        		out += "	<th>Detail View</th>";	
	 		        		out += "	<th><button onclick=\"insert_Dcode('"+values[0].cd_m+"')\" class=\"w3-btn fa fa-plus w3-white\"></button></td>"	
	 		        		out += "</tr>"
	 		        		out += "<tr>"
	 		        		out += "	<td>코드 (마스터)</td>"
	 		        		out += "	<td>코드 (디테일)</td>"
	 		        		out += "	<td>코드명</td>"
	 		        		out += "	<td>조회 순서</td>"
	 		        		out += "	<td>코드 설명</td>"
	 		        		out += "</tr>"
		 		        for(j=0 ; j < values.length ; j++){
		 		        	out += "<tr id=\"d_list_"+values[j].cd_m+"_"+values[j].cd_d+"\">";
		 		        	out += "	<td>"+values[j].cd_m+"</td>";
		 		        	out += "	<td>"+values[j].cd_d+"</td>";
		 		        	out += "	<td>"+values[j].cd_d_nm+"</td>";
		 		        	out += "	<td>"+values[j].q_ord+"</td>";
		 		        	out += "	<td>"+values[j].remark+"</td>";
		 		        	out += "	<td><button onclick=\"update_D('"+values[j].cd_m+"', '"+values[j].cd_d+"')\" class=\"w3-btn fa fa-pencil w3-xlarge w3-white \"></button><button onclick=\"delete_D('"+values[j].cd_m+"', '"+values[j].cd_d+"')\" class=\"w3-btn fa fa-trash w3-xlarge w3-white\"></button></td>";
		 		        	out += "</tr>";
		 		    }
	 		     }
	 		        out += "</table>";
	 		        document.getElementById("m_List_"+i).innerHTML = out;
	 		    } else {
	 		    	$('#btn_M_'+i).removeClass("w3-blue").addClass("w3-white");
	 		        x.className = x.className.replace(" w3-show", "");
	 		    }
	 		} else {
	 			alert("실패");
	 		}
	 	}                   
	);
}

// 마스터 코드 추가
function insert_Mcode(){
	var newMcode = { cd_m : $("#cd_m").val(), cd_nm : $("#cd_nm").val(), remark : $("#remark").val() };
	if(!newMcode.cd_m){
		alert("코드가 빠졌습니다.");
		return;
	}
	if(!newMcode.cd_nm){
		alert("코드명이 빠졌습니다.");
		return;
	}
	if(!newMcode.remark){
		alert("코드 설명이 빠졌습니다.");
		return;
	}
	$("#cd_m").val('');
	$("#cd_nm").val('');
	$("#remark").val('');
	var result;
	$.post(
			"./insert_Mcode.cd",
			newMcode,
		 	function(data) {
				result = JSON.parse(data);
				alert(result.result);
				if(result.result == "ok"){
					/*var out = "<div class=\"w3-accordion w3-row\">" ;
					out += "<button id=\"btn_M_"+result.cd_m+"\" onclick=\"detailView('"+result.cd_m+"')\" class=\"w3-threequarter w3-btn w3-white w3-hover-blue\">";
					out += "<span class=\"w3-third\" id=\"m_m_"+result.cd_m+"\">"+result.cd_m+"</span>";
					out += "<span class=\"w3-third\" id=\"m_nm_"+result.cd_nm+"\">"+result.cd_nm+"</span>";
					out += "<span class=\"w3-third\" id=\"m_re_"+result.remark+"\">"+result.remark+"</span></button>";
					out += "<button id=\"btn_M_U_"+result.cd_m+"\" class=\"w3-btn w3-ripple w3-blue\" onclick=\"update_Mcode('"+result.cd_m+"', '"+result.cd_nm+"', '"+result.remark+"')\">수정</button>";
					out += "<button id=\"btn_M_D_"+result.cd_m+"\" class=\"w3-btn w3-ripple w3-blue\" onclick=\"deleteMcode('"+result.cd_m+"')\">삭제</button>"	;
					out += "<div id=\"m_List_"+result.cd_m+"\" class=\"w3-accordion-content w3-container\">";
					out += "</div></div>";
					$("#mList").on().append(out);
					
					
					document.getElementById('id01').style.display='none';*/
					document.location.href = "./CodeMgr.cd";
				}else{
					alert("실패");
				}
			}
	);
}

//마스터 코드 삭제
function delete_Mcode(cd_m){
	if (confirm("삭제 하시겠습니까?")) {
		document.location.href = "./delete_Mcode.cd?cd_m="+cd_m;
	}
	return false;
	
}

//마스터 코드 수정 폼
function update_Mcode(cd_m, cd_nm, remark){
	document.getElementById('updateM').style.display='block'
	$('#originCd_m').val(cd_m);
	$('#originCd_nm').val(cd_nm);
	$('#originRemark').val(remark);
	$('#updateCd_m').val(cd_m).removeClass("w3-pale-green w3-pale-red");
	$('#updateCd_nm').val(cd_nm).removeClass("w3-pale-green w3-pale-red");	
	$('#updateRemark').val(remark).removeClass("w3-pale-green w3-pale-red");
}
//마스터 코드 수정 확정
function updateM(){
	var origin = { ocd_m : $("#originCd_m").val(), ocd_nm : $("#originCd_nm").val(), oremark : $("#originRemark").val() };
	var newMcode = { ocd_m : $("#originCd_m").val(), cd_m : $("#updateCd_m").val(), cd_nm : $("#updateCd_nm").val(), remark : $("#updateRemark").val() };
	var result;
	$.post(
			"./update_Mcode.cd",
			newMcode,
		 	function(data) {
				result = JSON.parse(data);
				alert(result.result);
				if(result.result == "ok"){
					document.location.href = "./CodeMgr.cd";
					//마스터 코드 부분
					//아래 코드 사용시 디테일 뷰 갱신에 문제가 있음 위에 방법으로 대체함.
					/*var cd_m = $('#m_m_'+origin.ocd_m);
					var cd_nm = $('#m_nm_'+origin.ocd_nm);
					var remark = $('#m_re_'+origin.oremark);
					cd_m.text(result.cd_m);
					cd_nm.text(result.cd_nm);
					remark.text(result.remark);
					cd_m.attr("id", 'm_'+result.cd_m);
					cd_nm.attr("id", 'm_'+result.cd_nm);
					remark.attr("id", 'm_'+result.remark);
					$('#btn_M_'+origin.ocd_m).attr("id", 'btn_M_'+result.cd_m).attr("onclick", "detailView('"+result.cd_m+"')");
					$('#btn_M_U_'+origin.ocd_m).attr("id", 'btn_M_U_'+result.cd_m).attr("onclick", "update_Mcode('"+result.cd_m+"', '"+result.cd_nm+"', '"+result.remark+"')");
					$('#btn_M_D_'+origin.ocd_m).attr("id", 'btn_M_D_'+result.cd_m).attr("onclick", "delete_Mcode('"+result.cd_m+"')");
					$('#m_List_'+origin.ocd_m).attr("id", 'm_List_'+result.cd_m);
					$('#d_List_'+origin.ocd_m).attr("id", 'd_List_'+result.cd_m);*/
					
					//디테일 코드 부분
					//var d_cd_m = $('#d_'+origin.ocd_m);
					//d_cd_m = $('#d_'+result.cd_m);
					
					//창 끄기
					document.getElementById('updateM').style.display='none';
				}else{
					alert("실패");
				}
			}
	);
}

//디테일 코드 추가
function insert_Dcode(cd_m){
	var out = "<tr>";
	out += "	<td><input class=\"w3-input w3-border\" name=\"i_cd_m\" type=\"text\" value=\""+cd_m+"\" disabled/></td>";
 	out += "	<td><input class=\"w3-input w3-border\" name=\"i_cd_d\" type=\"text\"/></td>";
 	out += "	<td><input class=\"w3-input w3-border\" name=\"i_cd_d_nm\" type=\"text\"/></td>";
 	out += "	<td><input class=\"w3-input w3-border\" name=\"i_q_ord\" type=\"text\"/></td>";
 	out += "	<td><input class=\"w3-input w3-border\" name=\"i_remark\" type=\"text\"/></td>";
 	out += "	<td><button onclick=\"insert_D(this)\" class=\"w3-btn fa fa-plus w3-xlarge w3-white\"/><button onclick=\"cancel_D(this, '"+cd_m+"')\" class=\"w3-btn fa fa-trash w3-xlarge w3-white\"></button></td>";
 	out += "</tr>"
	$('#d_ListTable_'+cd_m).append(out);
}
//디테일 코드 추가 확정
function insert_D(a){
	var i_cd_m = $(a).parent().parent().children("td").children("input[name=i_cd_m]").val();
	var i_cd_d = $(a).parent().parent().children("td").children("input[name=i_cd_d]").val();
	var i_cd_d_nm = $(a).parent().parent().children("td").children("input[name=i_cd_d_nm]").val();
	var i_q_ord = $(a).parent().parent().children("td").children("input[name=i_q_ord]").val();
	var i_remark = $(a).parent().parent().children("td").children("input[name=i_remark]").val();
	if(!i_cd_d){
		alert("코드가 빠졌습니다.");
		return;
	}
	if(!i_cd_d_nm){
		alert("코드명이 빠졌습니다.");
		return;
	}
	if(!i_q_ord){
		alert("조회 순서가 빠졌습니다.");
		return;
	}
	if(!i_remark){
		alert("코드 설명이 빠졌습니다.");
		return;
	}
	var newIcode = { cd_m : i_cd_m, cd_d : i_cd_d, cd_d_nm : i_cd_d_nm, q_ord : i_q_ord, remark : i_remark };
	
	$.post(
			"./insert_Icode.cd",
			newIcode,
		 	function(data) {
				result = JSON.parse(data);
				if(result.result == "ok"){
					var out = "<tr id=\"d_list_"+result.cd_m+"_"+result.cd_d+"\">";
 		        	out += "	<td>"+result.cd_m+"</td>";
 		        	out += "	<td>"+result.cd_d+"</td>";
 		        	out += "	<td>"+result.cd_d_nm+"</td>";
 		        	out += "	<td>"+result.q_ord+"</td>";
 		        	out += "	<td>"+result.remark+"</td>";
 		        	out += "	<td><button onclick=\"update_D('"+result.cd_m+"', '"+result.cd_d+"')\" class=\"w3-btn fa fa-pencil w3-xlarge w3-white \"><button onclick=\"delete_D('"+result.cd_m+"', '"+result.cd_d+"')\" class=\"w3-btn fa fa-trash w3-xlarge w3-white\"/></td>";
 		        	out += "</tr>";
 		        	/*$('#d_ListTable_'+i_cd_m+' tr:last').replaceWith(out);*/
 		        	$(a).parent().parent().replaceWith(out);
				}else{
					alert("실패");
				}
			}
	);
}
//디테일 코드 추가 취소
function cancel_D(a, cd_m){
	$(a).parent().parent().remove();
}

//디테일 코드 수정
var chk_cd_d;
var chk_q_ord;
function update_D(cd_m, cd_d){
	var cd_m = $('#d_list_'+cd_m+'_'+cd_d+' > td:nth-child(1)').text();
	var cd_d = $('#d_list_'+cd_m+'_'+cd_d+' > td:nth-child(2)').text();
	chk_cd_d = $('#d_list_'+cd_m+'_'+cd_d+' > td:nth-child(2)').text();
	var cd_d_nm = $('#d_list_'+cd_m+'_'+cd_d+' > td:nth-child(3)').text();
	var q_ord = $('#d_list_'+cd_m+'_'+cd_d+' > td:nth-child(4)').text();
	chk_q_ord = $('#d_list_'+cd_m+'_'+cd_d+' > td:nth-child(4)').text();
	var remark = $('#d_list_'+cd_m+'_'+cd_d+' > td:nth-child(5)').text();
	
	var out = "<tr id=\"d_list_"+cd_m+'_'+cd_d+"\">";
	out += "	<td><input class=\"w3-input w3-border\" name=\"u_cd_m\" type=\"text\" value=\""+cd_m+"\" disabled/></td>";
 	out += "	<td><input class=\"w3-input w3-border\" name=\"u_cd_d\" type=\"text\" value=\""+cd_d+"\"/></td>";
 	out += "	<td><input class=\"w3-input w3-border\" name=\"u_cd_d_nm\" type=\"text\" value=\""+cd_d_nm+"\"/></td>";
 	out += "	<td><input class=\"w3-input w3-border\" name=\"u_q_ord\" type=\"text\" value=\""+q_ord+"\"/></td>";
 	out += "	<td><input class=\"w3-input w3-border\" name=\"u_remark\" type=\"text\" value=\""+remark+"\"/></td>";
 	out += "	<td><button onclick=\"update_UD('"+cd_m+"', '"+cd_d+"')\" class=\"w3-btn fa fa-check w3-xlarge w3-white\" /><button onclick=\"cancel_UD('"+cd_m+"', '"+cd_d+"', '"+cd_d_nm+"', '"+q_ord+"', '"+remark+"')\" class=\"w3-btn fa fa-close w3-xlarge w3-white\" /></td>";
 	out += "</tr>";
 	$('#d_list_'+cd_m+'_'+cd_d).replaceWith(out);
}

//디테일 코드 수정 완료
function update_UD(cd_m, cd_d){
	var ocd_m = cd_m
	var ocd_d = cd_d
	var ucd_m = $('#d_list_'+cd_m+'_'+cd_d+' input[name=u_cd_m]').val();
	var ucd_d = $('#d_list_'+cd_m+'_'+cd_d+' input[name=u_cd_d]').val();
	var ucd_d_nm = $('#d_list_'+cd_m+'_'+cd_d+' input[name=u_cd_d_nm]').val();
	var uq_ord = $('#d_list_'+cd_m+'_'+cd_d+' input[name=u_q_ord]').val();
	var uremark = $('#d_list_'+cd_m+'_'+cd_d+' input[name=u_remark]').val();
	var upDcode = { originCd_m : ocd_m, originCd_d : ocd_d, upCd_m : ucd_m, upCd_d : ucd_d, upCd_d_nm : ucd_d_nm, upQ_ord : uq_ord, upRemark : uremark};
	var chkCd_d = $('#d_list_'+cd_m+'_'+cd_d+' input[name=u_cd_d]').hasClass("w3-pale-red");
	var chkQ_ord = $('#d_list_'+cd_m+'_'+cd_d+' input[name=u_q_ord]').hasClass("w3-pale-red");
	if(chkCd_d || chkQ_ord){
		alert("입력을 확인하세요.");
		return;
	}else{
		$.post(
				"./update_Dcode.cd",
				upDcode,
			 	function(data) {
					result = JSON.parse(data);
					if(result.result == "ok"){
						var out = "<tr id=\"d_list_"+result.cd_m+"_"+result.cd_d+"\">";
	 		        	out += "	<td>"+result.cd_m+"</td>";
	 		        	out += "	<td>"+result.cd_d+"</td>";
	 		        	out += "	<td>"+result.cd_d_nm+"</td>";
	 		        	out += "	<td>"+result.q_ord+"</td>";
	 		        	out += "	<td>"+result.remark+"</td>";
	 		        	out += "	<td><button onclick=\"update_D('"+result.cd_m+"', '"+result.cd_d+"')\" class=\"w3-btn fa fa-pencil w3-xlarge w3-white\" ><button onclick=\"delete_D('"+result.cd_m+"', '"+result.cd_d+"')\" class=\"w3-btn fa fa-trash w3-xlarge w3-white\"></td>";
	 		        	out += "</tr>";
	 		        	$('#d_list_'+ocd_m+'_'+ocd_d).replaceWith(out);
					}else{
						alert("실패");
					}
				}
		);
	}
}

//디테일 코드 수정 취소
function cancel_UD(cd_m, cd_d, cd_d_nm, q_ord, remark){
	var ocd_m = cd_m;
	var ocd_d = cd_d;
	var ocd_d_nm =cd_d_nm;
	var oq_ord = q_ord;
	var oremark = remark;
	
	var out = "<tr id=\"d_list_"+ocd_m+'_'+ocd_d+"\">";
 	out += "	<td>"+ocd_m+"</td>";
 	out += "	<td>"+ocd_d+"</td>";
 	out += "	<td>"+ocd_d_nm+"</td>";
 	out += "	<td>"+oq_ord+"</td>";
 	out += "	<td>"+oremark+"</td>";
 	out += "	<td><button onclick=\"update_D('"+ocd_m+"', '"+ocd_d+"')\">수정</button><button onclick=\"delete_D('"+ocd_m+"', '"+ocd_d+"')\">삭제</button></td>";
 	out += "</tr>";
	
	$('#d_list_'+ocd_m+'_'+ocd_d).replaceWith(out);
}
//디테일 코드 삭제
function delete_D(cd_m, cd_d){
	if (confirm("삭제 하시겠습니까?")) {
		var delDcode = { delCd_m : cd_m, delCd_d : cd_d};
		$.post(
				"./delete_Dcode.cd",
				delDcode,
			 	function(data) {
					result = JSON.parse(data);
					if(result.result == "ok"){
						$('#d_list_'+cd_m+'_'+cd_d).remove();
					}else{
						alert("실패");
					}
				}
		);
	}
	return false;
}


//input에서 key눌렸다 뗄 때 발생하는 함수
$().ready(function(){
	//마스터 코드 등록 부분
	$('body').on({
		keyup:function(){
			var chk = check("cd_m", this, $(this).val());
			if(chk != false){
				var selector = $(this);
				var checkMcode = { i_cd_m : $(this).val() };
				$(this).removeClass("w3-pale-red");
				$.post(
						"./Check_Mcode.cd",
						checkMcode,
					 	function(data) {
							var result = JSON.parse(data);
							if(result.result){
								$(selector).addClass("w3-pale-green");
							}else{
								$(selector).removeClass("w3-pale-green").addClass("w3-pale-red");
							}
						}
				);
			}
	}}, '#cd_m');
	$('body').on({
		keyup:function(){
			var chk = check("cd_d_nm", this, $(this).val());
			if(chk != false){
				$(this).removeClass("w3-pale-red");
			}
	}}, '#cd_nm');
	$('body').on({
		keyup:function(){
			var chk = check("remark", this, $(this).val());
			if(chk != false){
				$(this).removeClass("w3-pale-red");
			}
	}}, '#remark');
	//마스터 코드 수정 부분
	$('body').on({
		keyup:function(){
			if($('#originCd_m').val() == $(this).val()){
				$(this).removeClass("w3-pale-red").addClass("w3-pale-green");
			//cd_m 수정 o
			}else{
				var chk = check("cd_m", this, $(this).val());
				if(chk != false){
					var selector = $(this);
					var checkMcode = { i_cd_m : $(this).val() };
					$(this).removeClass("w3-pale-red");
					$.post(
							"./Check_Mcode.cd",
							checkMcode,
						 	function(data) {
								var result = JSON.parse(data);
								if(result.result){
									$(selector).addClass("w3-pale-green");
								}else{
									$(selector).removeClass("w3-pale-green").addClass("w3-pale-red");
								}
							}
					);
				}
			}
	}}, '#updateCd_m');
	$('body').on({
		keyup:function(){
			var chk = check("cd_d_nm", this, $(this).val());
			if(chk != false){
				$(this).removeClass("w3-pale-red");
			}
	}}, '#updateCd_nm');
	$('body').on({
		keyup:function(){
			var chk = check("remark", this, $(this).val());
			if(chk != false){
				$(this).removeClass("w3-pale-red");
			}
	}}, '#updateRemark');
	
	
	//디테일 코드 추가 부분
	$('body').on({
		keyup:function(){
			var chk = check("cd_d", this, $(this).val());
			if(chk != false){
				var curr = $(this).parent().parent().children("td").children().val();
				var selector = $(this);
				var checkDcode = { i_cd_m : curr, i_cd_d : $(this).val() };
				$(this).removeClass("w3-pale-red");
				$.post(
						"./Check_Dcode.cd",
						checkDcode,
					 	function(data) {
							var result = JSON.parse(data);
							if(result.result){
								$(selector).addClass("w3-pale-green");
							}else{
								$(selector).removeClass("w3-pale-green").addClass("w3-pale-red");
							}
						}
				);
			}
	}}, 'input[name=i_cd_d]');
	$('body').on({
		keyup:function(){
			var chk = check("cd_d_nm", this, $(this).val());
			if(chk != false){
				$(this).removeClass("w3-pale-red");
			}
	}}, 'input[name=i_cd_d_nm]');
	$('body').on({
		keyup:function(){			
			var chk = check("q_ord", this, $(this).val());
			if(chk != false){
				var curr = $(this).parent().parent().children("td").children().val();
				var selector = $(this);
				var checkDcode = { i_cd_m : curr, i_q_ord : $(this).val() };
				$(this).removeClass("w3-pale-red");
				$.post(
						"./Check_Dcode.cd",
						checkDcode,
					 	function(data) {
							var result = JSON.parse(data);
							if(result.result){
								$(selector).addClass("w3-pale-green");
							}else{
								$(selector).removeClass("w3-pale-green").addClass("w3-pale-red");
							}
						}
				);
			}
	}}, 'input[name=i_q_ord]');
	$('body').on({
		keyup:function(){
			var chk = check("remark", this, $(this).val());
			if(chk != false){
				$(this).removeClass("w3-pale-red");
			}
	}}, 'input[name=i_remark]');
	
	
	//디테일 코드 수정 부분
	$('body').on({
		keyup:function(){
			//cd_d 수정 x
			if(chk_cd_d == $(this).val()){
				$(this).removeClass("w3-pale-red").addClass("w3-pale-green");
			//cd_d 수정 o
			}else if(chk_cd_d != $(this).val()){
				var chk = check("cd_d", this, $(this).val());
				if(chk != false){
					var curr = $(this).parent().parent().children("td").children().val();
					var selector = $(this);
					var checkDcode = { i_cd_m : curr, i_cd_d : $(this).val() };
					$(this).removeClass("w3-pale-red");
					$.post(
							"./Check_Dcode.cd",
							checkDcode,
						 	function(data) {
								var result = JSON.parse(data);
								if(result.result){
									$(selector).addClass("w3-pale-green");
								}else{
									$(selector).removeClass("w3-pale-green").addClass("w3-pale-red");
								}
							}
					);
				}
			}
	}}, 'input[name=u_cd_d]');
	$('body').on({
		keyup:function(){
			var chk = check("cd_d_nm", this, $(this).val());
			if(chk != false){
				$(this).removeClass("w3-pale-red");
			}
	}}, 'input[name=u_cd_d_nm]');
	//q_ord 체크
	$('body').on({
		keyup:function(){			
			//q_ord 수정 x
			if(chk_q_ord == $(this).val()){
				$(this).removeClass("w3-pale-red").addClass("w3-pale-green");
			//q_ord 수정 o
			}else if(chk_q_ord != $(this).val()){
				var chk = check("q_ord", this, $(this).val());
				if(chk != false){
					var curr = $(this).parent().parent().children("td").children().val();
					var selector = $(this);
					var checkDcode = { i_cd_m : curr, i_q_ord : $(this).val() };
					$(this).removeClass("w3-pale-red");
					$.post(
							"./Check_Dcode.cd",
							checkDcode,
						 	function(data) {
								var result = JSON.parse(data);
								if(result.result){
									$(selector).addClass("w3-pale-green");
								}else{
									$(selector).removeClass("w3-pale-green").addClass("w3-pale-red");
								}
							}
					);
				}
			}
	}}, 'input[name=u_q_ord]');
	$('body').on({
		keyup:function(){
			var chk = check("remark", this, $(this).val());
			if(chk != false){
				$(this).removeClass("w3-pale-red");
			}
	}}, 'input[name=u_remark]');
});




//Code 체크
function check(chkName, here, checkDcode){
	var str = checkDcode;
	if(chkName == "cd_m"){
		var input_pattern = /^[\w]{1,10}$/;
		if( !input_pattern.test(str) ){
			$(here).removeClass("w3-pale-green")
			$(here).addClass("w3-pale-red");
		    return false;
		}
	}
	if(chkName == "cd_d"){
		var input_pattern = /^[\w]{1,10}$/;
		if( !input_pattern.test(str) ){
			$(here).removeClass("w3-pale-green")
			$(here).addClass("w3-pale-red");
		    return false;
		}
	}else if(chkName == "q_ord"){
		var pattern = /^[0-9]{1,4}$/;
		if(!pattern.test(str)){
			$(here).removeClass("w3-pale-green")
			$(here).addClass("w3-pale-red");
		    return false;
		}
	}else if(chkName == "cd_d_nm"){
		//공백금지
		var blank_pattern = /^\s+|\s+$/g;
		if( str.replace( blank_pattern, '' ) == "" ){
			$(here).addClass("w3-pale-red");
		    return false;
		}
		//바이트 체크 정규식
		stringByteLength = str.replace(/[\0-\x7f]|([0-\u07ff]|(.))/g,"$&$1$2").length;
		if(stringByteLength > 100){
			$(here).addClass("w3-pale-red");
		    return false;
		}
	}else if(chkName == "remark"){
		//공백금지
		var blank_pattern = /^\s+|\s+$/g;
		if( str.replace( blank_pattern, '' ) == "" ){
			$(here).addClass("w3-pale-red");
		    return false;
		}
		stringByteLength = str.replace(/[\0-\x7f]|([0-\u07ff]|(.))/g,"$&$1$2").length;
		if(stringByteLength > 200){
			$(here).addClass("w3-pale-red");
		    return false;
		}
	}
}


