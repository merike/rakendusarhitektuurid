<%@page import="java.util.Collection"%>
<%@page import="ee.ttu.t061879.EPOOD2.data.Catalog"%>

<jsp:include page="Head.jsp"></jsp:include>	
	<% Catalog c = (Catalog)request.getAttribute("catalog"); %>
	
	<% out.print("<form action='" + request.getContextPath()
			+ "/EditCatalogSubmit/" + c.getProductCatalog() + "/' "
			+ "method='post' style='width: 22em;'>");
	%>
		<ul>
			<li>
				<label for="nimi">Kataloogi nimi</label>
				<% out.print("<input id='nimi' name='nimi' value='" 
						+ c.getName() 
						+ "' size='15' /><br>"); %> 
			</li>
			<li>
				<label for="kirjeldus">Kataloogi kirjeldus</label>
				<% out.print("<textarea id='kirjeldus' name='kirjeldus' rows='2' cols='19'>");
					out.print(c.getDescription());
					out.println("</textarea><br>"); %>
			</li>
			<li>
				<input value="Muuda" type="submit">
			</li>
		</ul>
	</form>
 	
<jsp:include page="Tail.jsp"></jsp:include>
