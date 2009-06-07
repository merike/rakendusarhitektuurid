<%@page import="java.util.Collection"%>
<%@page import="ee.ttu.t061879.EPOOD2.data.Catalog"%>
<%@page import="ee.ttu.t061879.EPOOD2.utils.Utils"%>

<%@page import="ee.ttu.t061879.EPOOD2.validate.ProductSearch"%><jsp:include page="Head.jsp"></jsp:include>	
	<% out.print("<form action='" + request.getContextPath()
			+ "/ProductSearch/' method='post' style='width: 25em;'>");
	   ProductSearch s = (ProductSearch)request.getAttribute("search");
	%>
		<ul>
			<li>
				<% if(s != null && !s.isNameOk()) out.println(Utils.formError(s.getNameError())); %>
				<label for="nimi">Kauba nimi</label>
				<% out.print("<input id='nimi' name='nimi' size='20' ");
				if(s != null && s.getName().length() > 0) out.println("value='" + s.getName() + "' ");
				out.println("/><br>"); %> 
			</li>
			<li>
				<% if(s != null && !s.isDescOk()) out.println(Utils.formError(s.getDescError())); %>
				<label for="kirjeldus">Kauba kirjeldus</label>
				<% out.print("<input id='kirjeldus' name='kirjeldus' size='20' ");
				if(s != null && s.getDesc().length() > 0) out.println("value='" + s.getDesc() + "' ");
				out.println("/>"); %>
			</li>
			<li>
				<% if(s != null && !s.isMinPriceOk()) out.println(Utils.formError(s.getMinPriceError())); %>
				<% if(s != null && !s.isMaxPriceOk()) out.println(Utils.formError(s.getMaxPriceError())); %>
				<label for="v_hind">Hinnavahemik</label>
				<% out.print("<input id='s_hind' name='s_hind' size='8' ");
				if(s != null && s.getMaxPrice().length() > 0) out.println("value='" + s.getMaxPrice() + "' ");
				out.println("/>"); %>
				<% out.print("<input id='v_hind' name='v_hind' size='8' style='clear:none;' ");
				if(s != null && s.getMinPrice().length() > 0) out.println("value='" + s.getMinPrice() + "' ");
				out.println("/>"); %>  
			</li>
			<li>
				<% if(s != null && !s.isAddedByOk()) out.println(Utils.formError(s.getAddedByError())); %>
				<label for="sisestaja">Sisestaja</label>
				<% out.print("<input id='sisestaja' name='sisestaja' size='20' ");
				if(s != null && s.getAddedBy().length() > 0) out.println("value='" + s.getAddedBy() + "' ");
				out.println("/>"); %>
			</li>
			<li>
				<% if(s != null && !s.isMinAddedOk()) out.println(Utils.formError(s.getMinAddedError())); %>
				<% if(s != null && !s.isMaxAddedOk()) out.println(Utils.formError(s.getMaxAddedError())); %>
				<label for="sisest_algus">Sisestamise periood</label>
				<% out.print("<input id='sisest_lopp' name='sisest_lopp' size='8' ");
				if(s != null && s.getMaxAdded().length() > 0) out.println("value='" + s.getMaxAdded() + "' ");
				out.println("/>"); %>
				<% out.print("<input id='sisest_algus' name='sisest_algus' size='8' style='clear:none;' ");
				if(s != null && s.getMinAdded().length() > 0) out.println("value='" + s.getMinAdded() + "' ");
				out.println("/>"); %>
			</li>
			<li>
				<% if(s != null && !s.isChangedByOk()) out.println(Utils.formError(s.getChangedByError())); %>
				<label for="muutja">Muutja</label>
				<% out.print("<input id='muutja' name='muutja' size='20' ");
				if(s != null && s.getChangedBy().length() > 0) out.println("value='" + s.getChangedBy() + "' ");
				out.println("/>"); %>
			</li>
			<li>
				<% if(s != null && !s.isMinChangedOk()) out.println(Utils.formError(s.getMinChangedError())); %>
				<% if(s != null && !s.isMaxChangedOk()) out.println(Utils.formError(s.getMaxChangedError())); %>
				<label for="muutm_algus">Muutmise periood</label>
				<% out.print("<input id='muutm_lopp' name='muutm_lopp' size='8' ");
				if(s != null && s.getMaxChanged().length() > 0) out.println("value='" + s.getMaxChanged() + "' ");
				out.println("/>"); %>
				<% out.print("<input id='muutm_algus' name='muutm_algus' size='8' style='clear:none;' ");
				if(s != null && s.getMinChanged().length() > 0) out.println("value='" + s.getMinChanged() + "' ");
				out.println("/>"); %>
			</li>
			
			<li>
				<input name="otsi" value="Otsi" type="submit">
			</li>
		</ul>
	<% out.print("</form>"); %>
<jsp:include page="ProductSearchResults.jsp"></jsp:include>
 	
<jsp:include page="Tail.jsp"></jsp:include>
