<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String company = (String)request.getSession().getAttribute("company");
	String id = (String)request.getSession().getAttribute("id");
	String pw = (String)request.getSession().getAttribute("pw");
	String gubun = (String) request.getSession().getAttribute("gubun"); /**/
	String empno = (String) request.getSession().getAttribute("empno");
	String empnm = (String) request.getSession().getAttribute("empnm");
	String deptno = (String) request.getSession().getAttribute("deptno");
	String deptnm = (String) request.getSession().getAttribute("deptnm");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script type="text/javascript">
function pwsafety(){
var strength = {
        0: "Worst ☹",
        1: "Bad ☹",
        2: "Weak ☹",
        3: "Good ☺",
        4: "Strong ☻"
}

var password = document.getElementById('password');
var meter = document.getElementById('password-strength-meter');
var text = document.getElementById('password-strength-text');

password.addEventListener('input', function()
{
    var val = password.value;
    var result = zxcvbn(val);
    
    // Update the password strength meter
    meter.value = result.score;
   
    // Update the text indicator
    if(val !== "") {
        text.innerHTML = "Strength: " + "<strong>" + strength[result.score] + "</strong>" + "<span class='feedback'>" + result.feedback.warning + " " + result.feedback.suggestions + "</span"; 
    }
    else {
        text.innerHTML = "";
    }
});

}
</script>
<style type="text/css">
* {
    box-sizing: border-box;
}

body {
    padding: 2.5em 2em 0em;
    background: #f5f7f9;
        font-size: 1.5em;
        color: #346;
        font-family: Signika, -apple-system, sans-serif;
}

section {
    margin: 0em auto 0;
    width: 100%;
    max-width: 800px;
}

input {
    display: block;
    margin: .5em auto 0em;
    padding: 0.5em 1em 0.5em 0.7em;
    width: 100%;
    border: none;
    background: rgba(0,0,0,0.05);
    color: rgba(0,0,0,0.8);
    font-size: 2em;
    line-height: 0;
        transition: all .5s linear;
}

input:hover, input:focus {
    outline: 0;
    transition: all .5s linear;
    box-shadow: inset 0px 0px 10px #ccc;
}

meter {
    /* Reset the default appearance */
    -webkit-appearance: none;
       -moz-appearance: none;
            appearance: none;
            
    margin: 0 auto 1em;
    width: 100%;
    height: .5em;
    
    /* Applicable only to Firefox */
    background: none;
    background-color: rgba(0,0,0,0.1);
}

meter::-webkit-meter-bar {
    background: none;
    background-color: rgba(0,0,0,0.1);
}

meter[value="1"]::-webkit-meter-optimum-value { background: red; }
meter[value="2"]::-webkit-meter-optimum-value { background: yellow; }
meter[value="3"]::-webkit-meter-optimum-value { background: orange; }
meter[value="4"]::-webkit-meter-optimum-value { background: green; }

meter[value="1"]::-moz-meter-bar { background: red; }
meter[value="2"]::-moz-meter-bar { background: yellow; }
meter[value="3"]::-moz-meter-bar { background: orange; }
meter[value="4"]::-moz-meter-bar { background: green; }

.feedback {
    color: #9ab;
    font-size: 90%;
    padding: 0 .25em;
    font-family: Courgette, cursive;
    margin-top: 1em;
}
</style>
</head>
<body>
	<%=company %>
	<%=id%>
	<%=pw %>
	<%=gubun %>
	<%=empno %>
	<%=empnm %>
	<%=deptno %>
	<%=deptnm %>    
	<section>
        <label for="password">Enter password</label>
        <input type="password" name="password" id="password" onkeyup="return pwsafety();'">
    
        <meter max="4" id="password-strength-meter"></meter>
        <p id="password-strength-text"></p>
</section>
</body>
</html>