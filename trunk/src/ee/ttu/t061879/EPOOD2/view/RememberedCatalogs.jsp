<%
ArrayList<Catalog> c = (ArrayList<Catalog>)request.getSession().getAttribute("rememberedCatalogs");
if(c != null && c.size() > 0){ %>

	
<%@page import="java.util.ArrayList"%>
<%@page import="ee.ttu.t061879.EPOOD2.data.Catalog"%><div id="rememberedCatalogs">

<%
	out.print("<h3>Kataloogid puhvris:</h3>");
	for(Catalog cat : c){
		out.print("<span title='" + cat.getDescription() + "'>" + cat.getName() + "</span><br />");
	}
	
	out.println("<a href='" + request.getContextPath()
			+ "/CatalogList/0/" 
			+ "'>Tühjenda</a>");
	out.println("<a href='" + request.getContextPath()
			+ "/DestCatalog/" 
			+ "'>Vali sihtkataloog</a>");
}
%>
</div>