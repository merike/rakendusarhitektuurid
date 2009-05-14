<%@page import="ee.ttu.t061879.EPOOD2.data.User"%>

<%
User u = (User)request.getSession().getAttribute("user");
if(u != null){ %>

	<div id="userinfo">

<%
	out.print("<h3>" + u.getFirstName() + " " + u.getFirstName() + "</h3>");
	out.print("struktuuriüksus: " + u.getCurrentStructUnit() + "<br />");
	out.print("roll: " + u.getEmpRole() + "<br />");
	out.print("kasutajanimi: " + u.getUserName() + "<br />");
	out.print(request.getSession().getAttribute("auth"));
}
%>
</div>