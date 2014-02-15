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
<body class="general">
	<div class="header">
		<ul class="headerBar">
			<li class="link2"><a href="" class="tweetTwoo">Tweet-Twoo!</a></li>
			<li class="owlLink"><img src="images/rotate100owl.png" width="30px" height="30px" /></li>
			<!--  Owl logo found online and is free to use under public domain - site can be found here http://www.clipartlord.com/free-cute-cartoon-owls-perched-branch-clip-art/ -->
			<li class="link"><a href="/TweetTwoo/Tweet" >Home</a></li>
			<li class="link"><a href="/TweetTwoo/Profile">Profile</a></li>
			<li class="link"><a href="/TweetTwoo/Following">Following</a></li>
			<li class="link"><a href="/TweetTwoo/Follower">Followers</a></li>
			<li class="link"><a href="/TweetTwoo/Suggestions">Suggestions</a></li>
			<li class="link"><a href="/TweetTwoo/Logout">Log Out</a></li>
		</ul>
	</div>
	
	<div class="main">
	<div class="timeline">
		<p class="bolderFont">Who you are following :</p>
	<% 	List<ProfileStore> following = (List<ProfileStore>)request.getAttribute("Following");
	if (following==null){
	 %>
		<p>You are not following anyone</p>
	<% 
	}
	else
	{
	%>


	<% 
	Iterator<ProfileStore> iterator;


	iterator = following.iterator();     
	while (iterator.hasNext()){
	ProfileStore md = (ProfileStore)iterator.next();

	%>
	<form action="/TweetTwoo/Following" method="post">
	<div class="tweetDiv"> 
	 <br/>
	<span class="regFont"><img src="images/twitter-egg-red.jpg" alt="" width="60px" height="60px" align="left" class="userimgBorder" />
	<span class="whiteFont" style="margin-left:1%;"><%=md.getName() %></span>&nbsp;
	<span class="pinkFont">@<%=md.getUsername() %><br></span><input type="hidden" name="userid" value="<%=md.getUserid() %>"/>
	<span class="tweetFont" style="margin-left:2%;"><%=md.getBio() %><input type="submit" value="Unfollow" class="button" style="margin-left:70%"></span><br>
	<span class="tweetFont" style="margin-left:2%;"><%=md.getLocation() %>,<%=md.getCountry() %></span><br>
	<br/> <br/><br/>
	</div>
	</form>
	<%

	}
	}

	%>
	</div>
	
	<div class="footer">
	<center>
 	Copyright © Kari McMahon 2014
 	</center>
	</div>
	
	</div>
</body>
</html>