<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="com.tweet.stores.*" %>
<%@ page import="com.tweet.servlets.*" %>
<%@ page import="com.tweet.libs.*" %>
<%@ page import="java.util.*" %>
<%@ page import = "java.lang.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link href='http://fonts.googleapis.com/css?family=Vibur' rel='stylesheet' type='text/css'>
<link href='http://fonts.googleapis.com/css?family=Glegoo' rel='stylesheet' type='text/css'>
<link href="${pageContext.request.contextPath}/css/stylesheet.css" rel="Stylesheet" type="text/css"></link>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.7.2/jquery.min.js"></script>
<!-- Ajax script to refresh tweet timeline every minute, fade out is fast to show refresh occuring -->
<script>
var auto_refresh = setInterval(
function()
{
 $.ajaxSetup({ cache: false });
$('#loaddiv').fadeOut('fast').load('/TweetTwoo/Tweet #loaddiv').fadeIn("slow");
}, 60000);
</script>
<link rel="shortcut icon" href="${pageContext.request.contextPath}/images/rotate100owl.png" type="image/png">
<title>Tweet-Twoo!</title>
</head>

<body class="general">


	<jsp:include page="Header.jsp" />
	
	<div class=main>
	
	
	<div class="timeline">
	<p class="bolderFont">What's Happening ?
	<br><span class="smallFont">Tell the world.<br>Post a Tweet : </span></p>
	<form action="/TweetTwoo/Tweet"  method="post" class="regFont">
	
		<textarea  name="postTweet" rows="6" cols="45" class="textarea">
		</textarea><br>
		<input type="submit" value="Post It" class="button">
		
	</form>
	</div>
	
	<div class="timeline">
	<p class="bolderFont">Your Friends Tweet's : </p>
	<div id="loaddiv">
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
	<span class="regFont"><img src="${pageContext.request.contextPath}/images/twitter-egg-red.jpg" alt="" width="60px" height="60px" align="left" class="userimgBorder" /><span class="whiteFont" style="margin-left:1%;"><%=md.getName() %></span>&nbsp;
	<span class="pinkFont">@<%=md.getUsername() %>
	<br></span><span class="tweetFont" style="margin-left:2%;"><%=md.getTweet() %></span>
	<% 
	
	if(md.getUserid() == 1)
	{
	%>
	 <input type="button" name="deleteTweet" value="delete" class="button" style="margin-left:60%;">
	 <%} %>
	<br><span class="timeFont" style="margin-left:20%"><%=md.getTime() %></span></span>
	<br/> <br/><br/>
	</div>
	
	<%

	}
	}

	%>
	</div>
	</div>
	</div>
	
	<jsp:include page="Footer.jsp" />
	
	
</body>
</html>