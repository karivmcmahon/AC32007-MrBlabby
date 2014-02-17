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
<script>
function deleteAccount(userID)
{
    $.ajax({
        url:"${pageContext.request.contextPath}/EditProfile/" + userID,
        type:"DELETE",
        cache:false
    }).done(function() {
        //Refresh page
        document.location.reload(true);
    });
}

</script>
<title>Tweet-Twoo!</title>
</head>
<body class="general">

<jsp:include page="Header.jsp" />
	
	<div class="main">
	<div class="timeline">
		
		
		<p class="bolderFont">Edit your profile</p>
		<form action="${pageContext.request.contextPath}/EditProfile/" method="post" class="regFont">
		Name: <input type="text" name="name" value="${Profile.getName()}"><br>
		Username: <input type="text" name="username" value="${Profile.getUsername()}"><br>
		Password: <input type="password" name="password" value="${Profile.getPassword()}"><br>
		Email: <input type="text" name="email" value="${Profile.getEmail() }"><br>
		Bio : <input type="text" name="bio" value="${Profile.getBio() }"><br>
		Location: <input type="text" name="location" value="${Profile.getLocation()}"><br>
		Country:  <input type="text" name="country" value="${Profile.getCountry() }"><br>
		<input type="submit" value="Update" class="button">
		</form>
		<input type="button" value="Delete Account" name="delete" class="button" onclick="deleteAccount(${Profile.getUserid()})" />
	</div>
	
	
	<jsp:include page="Footer.jsp" />
	
	</div>

</body>
</html>