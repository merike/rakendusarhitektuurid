<%@page import="java.util.Collection"%>
<%@page import="ee.ttu.t061879.EPOOD2.data.Catalog"%>

<jsp:include page="Head.jsp"></jsp:include>	
	<h1>Kataloogid</h1> 
	
	<%
	Collection<Catalog> list = 
		(Collection<Catalog>)request.getAttribute("CatalogList");
	out.println("<ul>");
	
	for(Catalog top : list){
		out.println("<li>");
		out.println(top.getName()
				+ " <a href='" + request.getContextPath()
				+ "/EditCatalog/" + top.getProductCatalog() + "" 
				+ "/'>Muuda</a>");
		
		Collection<Catalog> subs = top.getSubCatalogs();
		out.println("<ul>");
		for(Catalog sub : subs){
			out.println("<li>" 
			+ sub.getName()
			+ " <a href='" + request.getContextPath()
			+ "/EditCatalog/" + sub.getProductCatalog() + "" 
			+ "/'>Muuda</a>"
			+ "</li>");
		}
		
		out.println("<li><a href='" + request.getContextPath()
				+ "/AddCatalog/" + top.getProductCatalog() 
				+ "/'>Lisa kataloog</a></li>");
		out.println("</ul>");
		out.println("</li>");
	}
	
	out.println("<li><a href='" 
			+ request.getContextPath() 
			+ "/AddCatalog/'>Lisa kataloog</a></li>");
	out.println("</ul>");
%>
	
<jsp:include page="Tail.jsp"></jsp:include>
