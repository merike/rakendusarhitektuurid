<%@page import="java.util.Collection"%>
<%@page import="ee.ttu.t061879.EPOOD2.data.Product"%>

<jsp:include page="Head.jsp"></jsp:include>	
	<% Product p = (Product)request.getAttribute("product"); %>
	
	<% 
		if(p != null){
			out.print("<form action='" + request.getContextPath()
			+ "/EditProductSubmit/" + p.getProduct() + "/' "
			+ "method='post' style='width: 22em;'>");
	%>
		<ul>
			<li>
				<label for="nimi">Toote nimi</label>
				<% out.print("<input id='nimi' name='nimi' value='" 
						+ p.getName() 
						+ "' size='15' /><br>"); %> 
			</li>
			<li>
				<label for="kirjeldus">Toote kirjeldus</label>
				<% out.print("<textarea id='kirjeldus' name='kirjeldus' rows='2' cols='19'>");
					out.print(p.getDescription());
					out.println("</textarea><br>"); %>
			</li>
			<li>
				<input value="Muuda" type="submit">
			</li>
		</ul>
	</form> 	
	<% } %>
<jsp:include page="Tail.jsp"></jsp:include>
