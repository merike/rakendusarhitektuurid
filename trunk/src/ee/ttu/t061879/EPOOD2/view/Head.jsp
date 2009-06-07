<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
 "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="et" lang="et">
	<head>
		<title><% out.println(request.getAttribute("title")); %></title>
		<link rel="stylesheet" type="text/css" href="<% out.print(request.getAttribute("staticPath")); %>style.css" />
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	</head>
	<body>
	<div id="container">
		<div id="sidebar">
			<jsp:include page="UserInfo.jsp"></jsp:include>
			<jsp:include page="RememberedCatalogs.jsp"></jsp:include>
		</div>
		
		<div id="content">
			<% out.println(request.getAttribute("mode"));
				out.println(request.getAttribute("submode")); %>
			<jsp:include page="Menu.jsp"></jsp:include>
			<%
				if(request.getAttribute("info") != null){
					out.println("<p>" + request.getAttribute("info") + "</p>");
				}
			
			
			%>