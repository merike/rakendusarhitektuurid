<%@page import="java.util.Collection"%>
<%@page import="ee.ttu.t061879.EPOOD2.data.Product"%>


<%@page import="ee.ttu.t061879.EPOOD2.utils.Utils"%>
<%@page import="java.util.ArrayList"%>
<%@page import="ee.ttu.t061879.EPOOD2.data.Enterprise"%><jsp:include page="Head.jsp"></jsp:include>	
	<% 
	Product p = (Product)request.getAttribute("product"); 
	int enterprise = p.getEnterprise().getEnterprise();
	
	if(p != null){
		out.print("<form action='" + request.getContextPath()
		+ "/EditProductSubmit/" + p.getProduct() + "/' "
		+ "method='post' style='width: 30em;'>");
	%>
		<ul>
			<li>
				<label for="nimi">Toote nimi</label>
				<% out.print("<input id='nimi' name='nimi' value='" 
						+ p.getName() 
						+ "' size='35' /><br>"); %> 
			</li>
			<li>
				<label for="kirjeldus">Toote kirjeldus</label>
				<% out.print("<textarea id='kirjeldus' name='kirjeldus' rows='2' cols='40'>");
					out.print(p.getDescription());
					out.println("</textarea><br>"); %>
			</li>
			<li>
				<label for="hind">Toote hind</label>
				<% out.print("<input id='hind' name='hind' value='" 
						+ p.getPrice()
						+ "' size='35' /><br>"); %>
			</li>
			<li>
				<label for="kood">Tootekood</label>
				<% out.print("<input id='kood' name='kood' value='" 
						+ p.getCode() 
						+ "' size='35' /><br>"); %>
			</li>
			<li>
				<label for="tootja">Tootja</label>
				<%
					out.println("<select name='tootja' style='width:23.45em;'>");
					ArrayList<Enterprise> es = (ArrayList<Enterprise>)request.getAttribute("enterprise_list");
					for(Enterprise e : es){
							if(e.getEnterprise() == enterprise){
								out.println("<option selected value='" + e.getEnterprise() + "'>" + e.getName() + "</option>");
							}
							else{
								out.println("<option value='" + e.getEnterprise() + "'>" + e.getName() + "</option>");
							}
					}
					out.println("</select>");
				%>
			</li>
			<li>
				<label for="sisestaja">Sisestaja</label>
				<% out.print("<input id='sisestaja' name='sisestaja' disabled value='" 
						+ p.getCreatedBy().getFullName() 
						+ "' size='35' /><br>"); %>
			</li>
			<li>
				<label for="sisestatud">Sisestatud</label>
				<% out.print("<input id='sisestatud' name='sisestatud' disabled value='" 
						+ Utils.localDate(p.getCreated() + "")
						+ "' size='35' /><br>"); %>
			</li>
			<li>
				<label for="sisestaja">Viimane muutja</label>
				<% out.print("<input id='muutja' name='muutja' disabled value='" 
						+ p.getUpdatedBy().getFullName() 
						+ "' size='35' /><br>"); %>
			</li>
			<li>
				<label for="sisestatud">Viimati muudetud</label>
				<% out.print("<input id='muudetud' name='muudetud' disabled value='" 
						+ Utils.localDate(p.getUpdated() + "")
						+ "' size='35' /><br>"); %>
			</li>
			<li>
				<input value="Salvesta" type="submit">
			</li>
		</ul>
	</form> 	
	<% } %>
<jsp:include page="Tail.jsp"></jsp:include>
