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
<link rel="shortcut icon" href="${pageContext.request.contextPath}/images/rotate100owl.png" type="image/png">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.7.2/jquery.min.js"></script>
<title>Tweet-Twoo!</title>

<script>
function deleteAccount(userID)
{
    $.ajax({
        url:"${pageContext.request.contextPath}/User/" + userID,
        type:"DELETE",
        cache:false
    }).done(function() {
        //Refresh page
        document.location.reload(true);
    });
}

</script>
</head>
<body class="general">

<jsp:include page="Header.jsp" />
	
	<div class=main>
	
	
	<div class="timeline">
	<p class="bolderFont">Search for a user by name:</p>
	<form action="${pageContext.request.contextPath}/Search"  method="post" class="regFont">
		<input type="text" name="searchbox"/>
		<input type="submit" value="Search" class="button">
		
	</form>
	</div>
	
	<div class="timeline">
	<p class="bolderFont">User's : </p>
	<% 	List<ProfileStore> u = (List<ProfileStore>)request.getAttribute("Profiles");
	if (u==null){
	 %>
		<p class="whiteFont">No users were found please search again</p>
	<% 
	}
	else
	{
	%>


	<% 
	Iterator<ProfileStore> iterator;


	iterator = u.iterator();  
	if(!iterator.hasNext())
	{%>
		<p class="whiteFont">No users were found please search again</p>
	<% 
	}
	else
	{
	while (iterator.hasNext()){
	ProfileStore md = (ProfileStore)iterator.next();

	%>
	
	<div class="tweetDiv"> 
	 <br/>
	<span class="regFont"><img src="${pageContext.request.contextPath}/images/twitter-egg-red.jpg" alt="" width="60px" height="60px" align="left" class="userimgBorder" />
	<span class="whiteFont" style="margin-left:1%;"><%=md.getName() %></span>&nbsp;
	<span class="pinkFont">@<%=md.getUsername() %><br></span>
	<span class="tweetFont" style="margin-left:2%;"><%=md.getBio() %></span><br>
	<span class="tweetFont" style="margin-left:2%;float:left;"><%=md.getLocation() %>,<%=md.getCountry() %></span><br>
	<%if(md.getFollowing() == true)
		{
	%>	<form action="${pageContext.request.contextPath}/Following" method="post">
		<input type="submit" value="Unfollow" class="button" style="margin-left:80%;white-space:nowrap;">
		<input type="hidden" name="userid" value="<%=md.getUserid() %>"/>
		</form>
		<%
		}
	else
	{
		%>
		<form action="${pageContext.request.contextPath}/Follower" method="post">
		<input type="submit" value="Follow" class="button" style="margin-left:80%;white-space:nowrap;">
		<input type="hidden" name="userid" value="<%=md.getUserid() %>"/>
		</form>
		<%
	}
		%>
		<%
		UserStore us = new UserStore();
		us = (UserStore)request.getSession().getAttribute("currentSeshUser");
		if(us.getPermission() == 2 && md.getPermission() == 1 || us.getPermission() == 3 && md.getPermission() == 1)
		{
		%>

			<input type="button" value="Delete" class="button" style="margin-left:80%;white-space:nowrap;" onclick="deleteAccount(<%= md.getUserid()%>)">
			

		<%
		}
		else if(us.getPermission() == 3 && md.getPermission() == 2)
		{
		%>
			<input type="button" value="Delete" class="button" style="margin-left:80%;white-space:nowrap;" onclick="deleteAccount(<%= md.getUserid()%>)">
		<%
		}
		%>
		
	
	
	<br/> <br/><br/>
	
	</div>
	
	<%
	}
	}
	}

	%>
	
	</div>
	
	<jsp:include page="Footer.jsp" />

</body>
</html>