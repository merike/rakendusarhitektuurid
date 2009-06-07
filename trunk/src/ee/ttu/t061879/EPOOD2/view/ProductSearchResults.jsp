<%@page import="ee.ttu.t061879.EPOOD2.data.Product"%>
<%@page import="ee.ttu.t061879.EPOOD2.utils.Utils"%>
<%@page import="java.util.ArrayList"%>

<% 
if(request.getAttribute("product_search_results") != null){
	ArrayList<Product> ps = (ArrayList<Product>)request.getAttribute("product_search_results");
	
	if(ps.size() == 0){
		out.println("Ei leitud sobivaid tooteid.");
	}
	else{
		out.println("Sobivad tooted:");
		out.println("<table><thead><tr><td>Nimi</td><td>Kirjeldus</td><td>Hind</td>");
		out.println("<td>Sisestaja</td><td>Sisestatud</td><td>Muutja</td><td>Muudetud</td></tr></thead>");
		out.println("<tbody>");
		int i = 1;
		for(Product p : ps){
			if(i % 2 == 0) out.println("<tr class='even'>");
			else out.println("<tr class='odd'>");
			out.println("<td>" + p.getName() + "</td>");
			out.println("<td>" + p.getDescription() + "</td>");
			out.println("<td>" + Utils.number(p.getPrice()) + "</td>");
			out.println("<td>" + p.getCreatedBy().getFullName() + "</td>");
			out.println("<td>" + Utils.localDate(p.getCreated() + "") + "</td>");
			out.println("<td>" + p.getUpdatedBy().getFullName() + "</td>");
			out.println("<td>" + Utils.localDate(p.getUpdated() + "") + "</td>");
			out.println("</tr>");
			i++;
		}
		out.println("</tbody></table>");
	}
}
%>
