package ee.ttu.t061879.EPOOD2.model;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ee.ttu.t061879.EPOOD2.dao.CatalogDAO;
import ee.ttu.t061879.EPOOD2.data.Catalog;
import ee.ttu.t061879.EPOOD2.data.User;

public class CatalogModel {
	public void list(HttpServletRequest request, HttpServletResponse response){
		CatalogDAO dao = new CatalogDAO();
		request.setAttribute("CatalogList", dao.getCatalogs());
	}
	
	public void add(HttpServletRequest request, HttpServletResponse response){
		String desc = request.getParameter("kirjeldus");
		String name = request.getParameter("nimi");
		
		Catalog c = new Catalog();
		User u = (User)(request.getSession().getAttribute("user"));
		
		c.setDescription(desc);
		c.setName(name);
		c.setStructUnit(u.getCurrentStructUnit());
		
		CatalogDAO dao = new CatalogDAO();
		boolean result = dao.addCatalog(c, u.getEmpUser());
		
		if(result == true){
			request.setAttribute("insertResult", true);
			request.setAttribute("info", "Kataloogi sisestamine õnnestus");
		}
		else{
			request.setAttribute("insertResult", false);
			request.setAttribute("catalog", c);
		}
	}
}
