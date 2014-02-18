<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
   <%@ page import="com.tweet.stores.*" %>
<%@ page import="com.tweet.servlets.*" %>
<%@ page import="com.tweet.libs.*" %>
<%@ page import="java.util.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link href="${pageContext.request.contextPath}/css/stylesheet.css" rel="Stylesheet" type="text/css"></link>
<link href='http://fonts.googleapis.com/css?family=Vibur' rel='stylesheet' type='text/css'>
<link href='http://fonts.googleapis.com/css?family=Glegoo' rel='stylesheet' type='text/css'>
<link rel="shortcut icon" href="${pageContext.request.contextPath}/images/rotate100owl.png" type="image/png">
<title>Tweet-Twoo!</title>
</head>
<script>
function validateRegister()
{
	var nameV=document.forms["registerForm"]["name"].value;
	var usernameV =document.forms["registerForm"]["newUsername"].value;
	var emailV =document.forms["registerForm"]["newEmail"].value;
	var passwordV =document.forms["registerForm"]["newPassword"].value;
	if (nameV==null || nameV=="")
	{
	  	alert("Name must be filled out to register");
	  	return false;
	}
	if (usernameV==null || usernameV=="")
	{
	  	alert("Username must be filled out to register");
	  	return false;
	}
	if (emailV==null || emailV=="")
	{
	  	alert("Email must be filled out to register");
	  	return false;
	}
	if (passwordV==null || passwordV=="")
	{
	  	alert("Password must be filled out to register");
	  	return false;
	}

}

function validateLogin()
{
	var un=document.forms["loginForm"]["username"].value;
	var pw =document.forms["loginForm"]["password"].value;
	if (un==null || un=="")
	{
	  	alert("Username must be filled out to login");
	  	return false;
	}
	if (pw==null || pw=="")
	{
	  	alert("Password must be filled out to login");
	  	return false;
	}
}
</script>
<body class="signUp">



<div class="header">
<ul class="headerBar">
	<li class="link1"><a href="" class="tweetTwoo">Tweet-Twoo!</a></li>
	<!--  Owl logo found online and is free to use under public domain - site can be found here http://www.clipartlord.com/free-cute-cartoon-owls-perched-branch-clip-art/ -->
	<li class="link"><img src="${pageContext.request.contextPath}/images/rotate100owl.png" width="40px" height="40px"/></li>
</ul>

</div>

<div class="main">



<div class="left">
<p class="font"> Welcome to  <span class="tweetTwoo">Tweet-Twoo!</span>
<br> <span class="italics">Discover The World</span>
</p>
</div>

<div class="right">
<p class = "regFont"><span class="boldFont">New ? </span> Sign Up </p>
<form action="${pageContext.request.contextPath}/Register"  method="post" class="regFont2" name="registerForm" onsubmit="return validateRegister()">

Name: <input type="text" name="name"><br>
Username: <input type="text" name="newUsername"><br>
Password: <input type="password" name="newPassword"><br>
Email: <input type="text" name="newEmail"><br>
<% UserStore u = new UserStore();
   u = (UserStore)request.getAttribute("Users");
   if(u != null)
	   
   {%>
   <p class="errorFont"><%=u.getError()%></p>
   <%} %>
<input type="submit" value="Sign Up!" class="button">
</form>
</div>

<div class="signInRight">

 <p class="boldFont">Sign In </p>
 <form action="${pageContext.request.contextPath}/Login" method="post" class="regFont2" name="loginForm" onsubmit="return validateLogin()">
Username: <input type="text" name="username"><br>
Password: <input type="password" name="password"><br>
<input type="submit" value="Sign In!" class="button">
<% 
   u = (UserStore)request.getAttribute("Users");
   if(u != null)
	   
   {%>
   <p class="errorFont"><%=u.getError()%></p>
   <%} %>
</form>
</div>

<jsp:include page="Footer.jsp" />


</div>



</body>
</html>