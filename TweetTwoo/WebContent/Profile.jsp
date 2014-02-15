<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="com.tweet.stores.*" %>
<%@ page import="com.tweet.servlets.*" %>
<%@ page import="com.tweet.libs.*" %>
<%@ page import="java.util.*" %>
<%@ page import = "java.io.*" %>

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

	<jsp:include page="Header.jsp" />
	
	<div class="main">
	<div class="timeline">
		<% 	
		List<ProfileStore> prof = (List<ProfileStore>)request.getAttribute("Profiles");
		if (prof==null){
 		%>
			<p>No profile found</p>
		<% 
		}
		else
		{
		%>


		<% 
		Iterator<ProfileStore> iterator;


		iterator = prof.iterator();     
		while (iterator.hasNext())
		{
			ProfileStore md = (ProfileStore)iterator.next();

		%>
		
		<p class="bolderFont"><%=md.getName() %></p>
		<img src="images/twitter-egg-red.jpg" alt="profilePic" width="150px" height="150px" class="imgBorder">
		<br>
		<span class="userFont">@<%=md.getUsername() %></span>
		<p class="boldishFont"><%=md.getBio() %><br> <%=md.getLocation() %>,<%=md.getCountry() %> </p>
		<p class="userFont"><%=md.getTweetCount() %>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <%=md.getFollowerCount() %>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<%=md.getFollowingCount() %><br><span class="boldishFont">Tweets &nbsp;&nbsp;&nbsp;   Followers         Following</span></p><%
		}
		}

		%>
		
		<form action="/TweetTwoo/EditProfile" method="get" >
			<input type="submit" value="Edit Profile" class="button" >
		</form>
	</div>
	
		<div class="timeline">
			<p class="bolderFont">Your Tweet's : </p>
			<%
	
			
			List<TweetStore> tweets = (List<TweetStore>)request.getAttribute("Tweets");
			if (tweets==null){
 			%>
				<p>No tweets found</p>
			<% 
			}
			else
			{
			%>


			<% 
			Iterator<TweetStore> iterator;


			iterator = tweets.iterator();     
			while (iterator.hasNext()){
			TweetStore md = (TweetStore)iterator.next();

			%>
	
	<div class="tweetDiv"> 
	 	<br/>
		<span class="regFont"><img src="images/twitter-egg-red.jpg" alt="" width="60px" height="60px" align="left" class="userimgBorder" /><span class="whiteFont" style="margin-left:1%;"><%=md.getName() %></span>&nbsp;<span class="pinkFont">@<%=md.getUsername() %><br></span><span class="tweetFont" style="margin-left:2%;"><%=md.getTweet() %></span><br><span class="timeFont" style="margin-left:20%"><%=md.getTime() %></span></span>
		<br/> <br/><br/>
	</div>
	<%

	}
	}

	%>
			
	</div>
	
	<jsp:include page="Footer.jsp" />
	
	</div>
	
	

</body>
</html>