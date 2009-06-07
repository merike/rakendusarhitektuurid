<%@page import="java.util.Collection"%>
<%@page import="ee.ttu.t061879.EPOOD2.data.Catalog"%>
<%@page import="ee.ttu.t061879.EPOOD2.data.Product"%>
<%@page import="ee.ttu.t061879.EPOOD2.utils.Utils"%>

<jsp:include page="Head.jsp"></jsp:include>	
	<%
	Collection<Product> list = (Collection<Product>)request.getAttribute("ProductList");
	Catalog c = (Catalog)request.getAttribute("catalog");
	
	out.println("<h1>Tooted kataloogis ");
	if(c != null){
		out.println(c.getName());
	}	
	out.println("</h1>");
	
	if(list.size() == 0){
		out.println("<p>Kataloog on tühi.</p>");
	}
	else{
		out.println("<table><thead><tr><td>Nimi</td><td>Kirjeldus</td><td>Hind</td>");
		out.println("<td>Sisestaja</td><td>Sisestatud</td><td>Muutja</td><td>Muudetud</td><td></td></tr></thead>");
		out.println("<tbody>");
		int i = 1;
		for(Product p : list){
			if(i % 2 == 0) out.println("<tr class='even'>");
			else out.println("<tr class='odd'>");
			out.println("<td>" + p.getName() + "</td>");
			out.println("<td>" + p.getDescription() + "</td>");
			out.println("<td>" + Utils.number(p.getPrice()) + "</td>");
			out.println("<td>" + p.getCreatedBy().getFullName() + "</td>");
			out.println("<td>" + Utils.localDate(p.getCreated() + "") + "</td>");
			out.println("<td>" + p.getUpdatedBy().getFullName() + "</td>");
			out.println("<td>" + Utils.localDate(p.getUpdated() + "") + "</td>");
			out.println("<td><a href='" + request.getContextPath() + "/ProductView/" + p.getProduct() 
					+ "/'>Muuda</a></td>");
			out.println("</tr>");
			i++;
		}
		out.println("</tbody></table>");
	}
	
	out.println("<p><a href='"	+ request.getContextPath() + "/AddProduct/" + c.getProductCatalog() 
			+ "/'>Lisa toode</a></p>");
%>
	
<jsp:include page="Tail.jsp"></jsp:include>
