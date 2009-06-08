<%@page import="java.util.Collection"%>
<%@page import="java.util.ArrayList"%>

<%@page import="ee.ttu.t061879.EPOOD2.data.Product"%>
<%@page import="ee.ttu.t061879.EPOOD2.data.Enterprise"%>
<%@page import="ee.ttu.t061879.EPOOD2.data.Catalog"%>

<%@page import="ee.ttu.t061879.EPOOD2.utils.Utils"%>

<jsp:include page="Head.jsp"></jsp:include>	
	<% 
	Catalog c = (Catalog)request.getAttribute("catalog");
	
	if(c != null){
		out.print("<form action='" + request.getContextPath()
		+ "/AddProductSubmit/" + c.getProductCatalog() + "/' "
		+ "method='post' style='width: 30em;'>");
	%>
		<ul>
			<li>
				<label for="nimi">Toote nimi</label>
				<% out.print("<input id='nimi' name='nimi' size='35' /><br>"); %> 
			</li>
			<li>
				<label for="kirjeldus">Toote kirjeldus</label>
				<% out.print("<textarea id='kirjeldus' name='kirjeldus' rows='2' cols='40'>");
					out.println("</textarea><br>"); %>
			</li>
			<li>
				<label for="hind">Toote hind</label>
				<% out.print("<input id='hind' name='hind' size='35' /><br>"); %>
			</li>
			<li>
				<label for="kood">Tootekood</label>
				<% out.print("<input id='kood' name='kood' size='35' /><br>"); %>
			</li>
			<li>
				<label for="tootja">Tootja</label>
				<%
					out.println("<select name='tootja' style='width:23.45em;'>");
					ArrayList<Enterprise> es = (ArrayList<Enterprise>)request.getAttribute("enterprise_list");
					for(Enterprise e : es){
							out.println("<option value='" + e.getEnterprise() + "'>" + e.getName() + "</option>");
					}
					out.println("</select>");
				%>
			</li>
			<li>
				<label for="kataloog">Kataloog</label>
				<% out.print("<input id='kataloog' name='kataloog' disabled size='35' ");
				out.println("value='" + c.getName() + "' ");
				out.println("/><br>"); %>
			</li>
			<li>
				<input value="Lisa" type="submit">
			</li>
		</ul>
	</form> 	
	<% } %>
<jsp:include page="Tail.jsp"></jsp:include>
