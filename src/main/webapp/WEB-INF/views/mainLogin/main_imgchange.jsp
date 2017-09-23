<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>프로필 사진 변경</title>
<link rel="stylesheet" href="http://www.w3schools.com/lib/w3.css">
<link rel="stylesheet" href="http://www.w3schools.com/lib/w3-theme-green.css">
<script type="text/javascript">

</script>
</head>
<body class="w3-container w3-light-grey">
<br /><br />
<br /><br />
<form name="infoForm" id="infoForm" action="./MainImgSave.em" class="w3-container w3-card-4 w3-light-grey" 
	enctype="multipart/form-data" method="post">
	<br />
	<span onclick="javascript:self.close();"
	class="w3-closebtn w3-hover-red w3-container w3-padding-8 w3-display-topright" >×</span>
	
	<h2>&nbsp;프로필 사진 변경</h2>
	<p>&nbsp;변경할 프로필 사진을 선택해주세요.</p>

	<input type="file" name="img" class="w3-input w3-border w3-round" />
	<br /><br />
	<input type="submit" value="이미지 업로드" class="w3-right w3-btn"/>
	<br /><br /><br />
	
</form>
   
</body>
</html>







