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
<link href="${pageContext.request.contextPath}/css/stylesheet.css" rel="Stylesheet" type="text/css"></link>
<link href='http://fonts.googleapis.com/css?family=Vibur' rel='stylesheet' type='text/css'>
<link href='http://fonts.googleapis.com/css?family=Glegoo' rel='stylesheet' type='text/css'>
<link rel="shortcut icon" href="${pageContext.request.contextPath}/images/rotate100owl.png" type="image/png">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.7.2/jquery.min.js"></script>

<!-- Ajax script to enable user to delete their own tweets -->
<script>
function deleteTweet(tweetID)
{
    $.ajax({
        url:"${pageContext.request.contextPath}/Tweet/" + tweetID,
        type:"DELETE",
        cache:false,
        success: function()
        {
    
        	document.location.reload(true);
        	
        },
        error: function()
        {
        	alert("fail");
        }
        
    });
}

</script>

<title>Tweet-Twoo!</title>
</head>
<body class="general">

	<jsp:include page="Header.jsp" />
	
	<div class="main">
		<!--  This div displays the users profile -->
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
				<img src="${pageContext.request.contextPath}/images/twitter-egg-red.jpg" alt="profilePic" width="150px" height="150px" class="imgBorder">
				<br>
				<span class="userFont">@<%=md.getUsername() %></span>
				<p class="boldishFont"><%=md.getBio() %><br> <%=md.getLocation() %>,<%=md.getCountry() %> </p>
				<p class="userFont"><%=md.getTweetCount() %>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
				<%=md.getFollowerCount() %>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<%=md.getFollowingCount() %><br><span class="boldishFont">Tweets &nbsp;&nbsp;&nbsp;   Followers         Following</span></p><%
			}
			}
	
			%>
			
			<form action="${pageContext.request.contextPath}/EditProfile" method="get" >
				<input type="submit" value="Edit Profile" class="button" >
			</form>
		</div>
	
		<!--  This div area displays the users own tweets -->
		<div class="timeline">
			<p class="bolderFont">Your Tweet's : </p>
			<%
	
			
			List<TweetStore> tweets = (List<TweetStore>)request.getAttribute("Tweets");
			if (tweets==null){
			
 			%>
				<p class="whiteFont">Could not find any of your tweets</p>
			<% 
			}
			else
			{
			%>


			<% 
			Iterator<TweetStore> iterator;
			

			iterator = tweets.iterator();  
			if(!iterator.hasNext())
			{%>
				<p class="whiteFont">Could not find any of your tweets.</p>
			<% 
			}
			else
			{
			while (iterator.hasNext()){
			TweetStore md = (TweetStore)iterator.next();

			%>
	
				<div class="tweetDiv"> 
				 	<br/>
					<span class="regFont"><img src="${pageContext.request.contextPath}/images/twitter-egg-red.jpg" alt="" width="60px" height="60px" align="left" class="userimgBorder" />
					<span class="whiteFont" style="margin-left:1%;"><%=md.getName() %></span>&nbsp;<span class="pinkFont">@<%=md.getUsername() %><br></span>
					<span class="tweetFont" style="margin-left:2%;"><%=md.getTweet() %></span><br>
					<span class="timeFont" style="margin-left:20%"><%=md.getTime() %></span>
					<input type="image" class="images" onclick="deleteTweet(<%=md.getTweetid() %>)" src="${pageContext.request.contextPath}/images/trash-2-512.png" name="image" width="20" height="15" style="margin-left:95%"/></span>
					<br/> <br/><br/>
				</div>
			<%
			}
			}
			}
		
			%>
			
	</div>
	
	<jsp:include page="Footer.jsp" />
	
	</div>
	
	

</body>
</html>