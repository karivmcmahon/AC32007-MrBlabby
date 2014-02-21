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
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.7.2/jquery.min.js"></script>

<!-- Ajax script to refresh following every minute, fade out is fast to show refresh occuring -->
<script>
var auto_refresh = setInterval(
function()
{
 $.ajaxSetup({ cache: false });
$('#loaddiv').fadeOut('fast').load('${pageContext.request.contextPath}/Following #loaddiv').fadeIn("slow");
}, 60000);
</script>

<title>Tweet-Twoo!</title>
</head>
<body class="general">
	
	<jsp:include page="Header.jsp" />
	
	<div class="main">
	
		<!-- Div displays who the user is following and gives them the oppurtunity to unfollow them -->
		<div class="timeline">
			<p class="bolderFont">Who you are following :</p>
			<div id="loaddiv">
				<% 	List<ProfileStore> following = (List<ProfileStore>)request.getAttribute("Following");
				if (following==null){
				 %>
					<p class="whiteFont">You are not following anyone</p>
				<% 
				}
				else
				{
				%>
		
		
				<% 
				Iterator<ProfileStore> iterator;
		
		
				iterator = following.iterator(); 
				if(!iterator.hasNext())
				{%>
					<p class="whiteFont">You are not following anyone</p>
				<% 
				}
				else
				{
				while (iterator.hasNext()){
				ProfileStore md = (ProfileStore)iterator.next();
		
				%>
				<form action="${pageContext.request.contextPath}/Following" method="post">
					<div class="tweetDiv"> 
			 		<br/>
						<span class="regFont"><img src="${pageContext.request.contextPath}/images/twitter-egg-red.jpg" alt="" width="60px" height="60px" align="left" class="userimgBorder" />
						<span class="whiteFont" style="margin-left:1%;"><%=md.getName() %></span>&nbsp;
						<span class="pinkFont">@<%=md.getUsername() %><br></span><input type="hidden" name="userid" value="<%=md.getUserid() %>"/>
						<span class="tweetFont" style="margin-left:2%;"><%=md.getBio() %>
						<input type="submit" value="Unfollow" class="button" style="margin-left:70%">
						</span><br>
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
	
	<jsp:include page="Footer.jsp" />
	
	</div>
</body>
</html>