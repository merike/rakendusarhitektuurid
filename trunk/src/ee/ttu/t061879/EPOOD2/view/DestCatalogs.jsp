<%@page import="java.util.Collection"%>
<%@page import="ee.ttu.t061879.EPOOD2.data.Catalog"%>

<jsp:include page="Head.jsp"></jsp:include>	
	<h1>Kataloogid</h1> 
	
	<%
	Collection<Catalog> list = 
		(Collection<Catalog>)request.getAttribute("destCatalogs");
	if(list != null && list.size() != 0){
		out.println("<ul>");
		
		for(Catalog top : list){
			out.println("<li><span title='" + top.getDescription() + "'>");
			out.println(top.getName()
					+ "</span> <a href='" + request.getContextPath()
					+ "/DestCatalog/" + top.getProductCatalog() + "/" 
					+ "'>Tõsta alamkataloogideks</a>");
			out.println("</li>");
		}
		
		out.println("</ul>");
	}
%>
	
<jsp:include page="Tail.jsp"></jsp:include>
