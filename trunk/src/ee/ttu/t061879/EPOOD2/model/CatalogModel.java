package ee.ttu.t061879.EPOOD2.model;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ee.ttu.t061879.EPOOD2.dao.CatalogDAO;
import ee.ttu.t061879.EPOOD2.data.Catalog;
import ee.ttu.t061879.EPOOD2.data.User;
import ee.ttu.t061879.EPOOD2.utils.Log;

public class CatalogModel {
	private Log logger;
	
	public CatalogModel() {
		logger = new Log();
	}
		
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
		
		int upperCatalog = 0;
		if(request.getParameter("submode") != null){
			try{
				upperCatalog = Integer.parseInt(request.getParameter("submode"));
			}
			catch(Exception e){}
			if(upperCatalog != 0){
				c.setUpperCatalog(upperCatalog);
			}
		}
		
		CatalogDAO dao = new CatalogDAO();
		boolean result = dao.addCatalog(c, u.getEmpUser());
		
		if(result == true){
			request.setAttribute("insertResult", true);
			request.setAttribute("info", "Kataloogi sisestamine õnnestus.");
			this.list(request, response);
		}
		else{
			request.setAttribute("insertResult", false);
			request.setAttribute("info", "Kataloogi sisestamine ebaõnnestus!");
			request.setAttribute("catalog", c);
		}
	}
	
	public void get(HttpServletRequest request, HttpServletResponse response){
		try{
			int product_catalog = Integer.parseInt(request.getParameter("submode")); 
			CatalogDAO dao = new CatalogDAO();
			request.setAttribute("catalog", dao.getCatalog(product_catalog));
		}
		catch (Exception e) {
			logger.log("CatalogModel.get() ", "ERROR");
		}
	}
	
	public void edit(HttpServletRequest request, HttpServletResponse response){
		String desc = request.getParameter("kirjeldus");
		String name = request.getParameter("nimi");
		int productCatalog;
		boolean result = false;
		Catalog c = new Catalog();
		User u;
		
		try{
			productCatalog = Integer.parseInt(request.getParameter("submode")); 
			u = (User)(request.getSession().getAttribute("user"));
			
			c.setProductCatalog(productCatalog);
			c.setDescription(desc);
			c.setName(name);
		
			CatalogDAO dao = new CatalogDAO();
			result = dao.editCatalog(c, u.getEmpUser());
		}
		catch (Exception e) {
			logger.log("CatalogModel.edit() ", "ERROR");
		}
		
		if(result == true){
			request.setAttribute("editResult", true);
			request.setAttribute("info", "Kataloogi muutmine õnnestus.");
			this.list(request, response);
		}
		else{
			request.setAttribute("editResult", false);
			request.setAttribute("info", "Kataloogi muutmine ebaõnnestus!");
			request.setAttribute("catalog", c);
		}
	}
}
