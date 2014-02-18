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
<!-- Ajax script to refresh following every minute, fade out is fast to show refresh occuring -->
<script>
var auto_refresh = setInterval(
function()
{
 $.ajaxSetup({ cache: false });
$('#loaddiv').fadeOut('fast').load('/TweetTwoo/Suggestions #loaddiv').fadeIn("slow");
}, 60000);
</script>
<title>Tweet-Twoo!</title>
</head>
<body class="general">

<jsp:include page="Header.jsp" />
	
	<div class="main">
	<div class="timeline">
		<p class="bolderFont">Suggestions based on who you follow:</p>
		<div id="loaddiv">
		<% 	List<ProfileStore> suggestions = (List<ProfileStore>)request.getAttribute("Suggestions");
			if (suggestions==null){
			 %>
				<p class="whiteFont">No suggestions for you were found</p>
			<% 
			}
			else
			{
			%>
		
		
			<% 
			Iterator<ProfileStore> iterator;
		
			iterator = suggestions.iterator(); 
			if(!iterator.hasNext())
			{%>
				<p class="whiteFont">No suggestions for you were found</p>
			<% 
			}
			else
			{
			while (iterator.hasNext()){
			ProfileStore md = (ProfileStore)iterator.next();
		
			%>
			<form action="/TweetTwoo/Follower" method="post">
			<div class="tweetDiv"> 
			 <br/>
			<span class="regFont"><img src="images/twitter-egg-red.jpg" alt="" width="60px" height="60px" align="left" class="userimgBorder" />
			<span class="whiteFont" style="margin-left:1%;"><%=md.getName() %></span>&nbsp;
			<span class="pinkFont" >@<%=md.getUsername() %><br></span><input type="hidden" name="userid" value="<%=md.getUserid() %>"/>
			<span class="tweetFont" style="margin-left:2%;"><%=md.getBio() %><input type="submit" value="Follow" class="button" style="margin-left:70%"></span><br>
			<span class="tweetFont" style="margin-left:2%;"><%=md.getLocation() %>,<%=md.getCountry() %></span><br>
			<br/> <br/><br/>
			</div>
			</form>
			<%
			}
			}
			}
		
			%>
		</div>
		</div>
	</div>
	
	<jsp:include page="Footer.jsp" />
	
	</div>

</body>
</html>