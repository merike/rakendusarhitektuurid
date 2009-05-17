<%@page import="java.util.Collection"%>
<%@page import="ee.ttu.t061879.EPOOD2.data.Catalog"%>

<jsp:include page="Head.jsp"></jsp:include>
	
	<form action="<% out.print(request.getContextPath()); %>/Login/" method="post" style="width:19em;">
		<ul>
			<li>
				<label for="user">Kasutajanimi</label>
				<input id="user" name="user" size="15" /><br />
			</li>
			<li>
				<label for="password">Salasõna</label>
				<input type="password" id="password" name="password" size="15" /><br />
			</li>
			<li>
				<input type="submit" value="Logi sisse">
			</li>
		</ul>
	</form>
	
<jsp:include page="Tail.jsp"></jsp:include>
