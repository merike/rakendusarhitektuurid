<%@page import="java.util.Collection"%>
<%@page import="ee.ttu.t061879.EPOOD2.data.Catalog"%>

<jsp:include page="Head.jsp"></jsp:include>	
	
	<form action="<% out.print(request.getContextPath()); %>/AddCatalogSubmit/" method="post" style="width: 22em;">
		<%
			if(request.getParameter("submode") != null){
				try{
					int subCatalog = Integer.parseInt(request.getParameter("submode"));
					out.println("<input type='hidden' name='submode' value='" + subCatalog + "'/>");
				}
				catch(Exception e){}
			}
		%>
		<ul>
			<li>
				<label for="nimi">Kataloogi nimi</label>
				<input id="nimi" name="nimi" size="15" /><br>
			</li>
			<li>
				<label for="kirjeldus">Kataloogi kirjeldus</label>
				<textarea id="kirjeldus" name="kirjeldus" rows="2" cols="19"></textarea><br>
			</li>
			<li>
				<input value="Lisa" type="submit">
			</li>
		</ul>
	</form>
 	
<jsp:include page="Tail.jsp"></jsp:include>
