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
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.7.2/jquery.min.js"></script>
<!-- Ajax script to refresh followers every minute, fade out is fast to show refresh occuring -->
<script>
var auto_refresh = setInterval(
function()
{
 $.ajaxSetup({ cache: false });
$('#loaddiv').fadeOut('fast').load('/TweetTwoo/Follower #loaddiv').fadeIn("slow");
}, 60000);
</script>
<title>Tweet-Twoo!</title>
</head>
<body class="general">
 
 <jsp:include page="Header.jsp" />
		
	
	<div class="main">
		<div class="timeline">
				<p class="bolderFont">Your followers :</p>
				<div id="loaddiv">
			<% 	List<ProfileStore> followers = (List<ProfileStore>)request.getAttribute("Followers");
			if (followers==null){
			 %>
				<p>You have no followers</p>
			<% 
			}
			else
			{
			%>
		
		
			<% 
			Iterator<ProfileStore> iterator;
		
		
			iterator = followers.iterator();     
			while (iterator.hasNext()){
			ProfileStore md = (ProfileStore)iterator.next();
		
			%>
			<div class="tweetDiv"> 
			 <br/>
			<span class="regFont"><img src="images/twitter-egg-red.jpg" alt="" width="60px" height="60px" align="left" class="userimgBorder" />
			<span class="whiteFont" style="margin-left:1%;"><%=md.getName() %></span>&nbsp;
			<span class="pinkFont">@<%=md.getUsername() %><br></span>
			<span class="tweetFont" style="margin-left:2%;"><%=md.getBio() %></span><br>
			<span class="tweetFont" style="margin-left:2%;"><%=md.getLocation() %>,<%=md.getCountry() %></span><br>
			<br/> <br/><br/>
			</div>
			<%
		
			}
			}
		
			%>
		</div>
		</div>
	<jsp:include page="Footer.jsp" />
	
	</div>

</body>
</html>