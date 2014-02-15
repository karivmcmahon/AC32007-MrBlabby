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
<link href="css/stylesheet.css" rel="Stylesheet" type="text/css"></link>
<link href='http://fonts.googleapis.com/css?family=Vibur' rel='stylesheet' type='text/css'>
<link href='http://fonts.googleapis.com/css?family=Glegoo' rel='stylesheet' type='text/css'>
<link rel="shortcut icon" href="images/rotate100owl.png" type="image/png">
<title>Tweet-Twoo!</title>
</head>
<body class="signUp">



<div class="header">
<ul class="headerBar">
	<li class="link1"><a href="" class="tweetTwoo">Tweet-Twoo!</a></li>
	<!--  Owl logo found online and is free to use under public domain - site can be found here http://www.clipartlord.com/free-cute-cartoon-owls-perched-branch-clip-art/ -->
	<li class="link"><img src="images/rotate100owl.png" width="40px" height="40px"/></li>
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
<form action="/TweetTwoo/Register"  method="post" class="regFont2">

Name: <input type="text" name="name"><br>
Username: <input type="text" name="newUsername"><br>
Password: <input type="password" name="newPassword"><br>
Email: <input type="text" name="newEmail"><br>
<input type="submit" value="Sign Up!" class="button">
</form>
</div>

<div class="signInRight">

 <p class="boldFont">Sign In </p>
 <form action="/TweetTwoo/Login" method="post" class="regFont2">
Username: <input type="text" name="username"><br>
Password: <input type="password" name="password"><br>
<input type="submit" value="Sign In!" class="button">
</form>
</div>

<jsp:include page="Footer.jsp" />


</div>



</body>
</html>