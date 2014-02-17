<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

</head>
<body>
	<div class="header">
		<ul class="headerBar">
			<li class="link2"><a href="" class="tweetTwoo">Tweet-Twoo!</a></li>
			<li class="owlLink"><img src="images/rotate100owl.png" width="30px" height="30px" /></li>
			<!--  Owl logo found online and is free to use under public domain - site can be found here http://www.clipartlord.com/free-cute-cartoon-owls-perched-branch-clip-art/ -->
			<li class="link"><a href="${pageContext.request.contextPath}/Tweet" >Home</a></li>
			<li class="link"><a href="${pageContext.request.contextPath}/Profile">Profile</a></li>
			<li class="link"><a href="${pageContext.request.contextPath}/Following">Following</a></li>
			<li class="link"><a href="${pageContext.request.contextPath}/Follower">Followers</a></li>
			<li class="link"><a href="${pageContext.request.contextPath}/Suggestions">Suggestions</a></li>
			<li class="link"><a href="${pageContext.request.contextPath}/Search.jsp">Search</a></li>
			<li class="link"><a href="${pageContext.request.contextPath}/Logout">Log Out</a></li>
		</ul>
	</div>
</body>
</html>