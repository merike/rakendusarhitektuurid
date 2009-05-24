package ee.ttu.t061879.EPOOD2.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
	
	public void rememberCatalog(HttpServletRequest request, HttpServletResponse response){
		Catalog c;
		
		HttpSession s = request.getSession();
		
		try{
			int productCatalog = Integer.parseInt(request.getParameter("submode")); 
			
			CatalogDAO dao = new CatalogDAO();
			c = dao.getCatalog(productCatalog);
			
			ArrayList<Catalog> rememberedCatalogs = (ArrayList<Catalog>)s.getAttribute("rememberedCatalogs"); 
			if(productCatalog == 0){
				rememberedCatalogs.clear();
			}
			else if(rememberedCatalogs != null && rememberedCatalogs.contains(c)){
				logger.log("CatalogModel.rememberCatalog() juba olemas", "DEBUG");
				return;
			}
			else if(rememberedCatalogs != null){
				rememberedCatalogs.add(c);
				logger.log("CatalogModel.rememberCatalog() lisatud", "DEBUG");
			}
			else{
				rememberedCatalogs = new ArrayList<Catalog>();
				rememberedCatalogs.add(c);
				s.setAttribute("rememberedCatalogs", rememberedCatalogs);
				logger.log("CatalogModel.rememberCatalog() esimene", "DEBUG");
			}
		}
		catch (Exception e) {
			logger.log("CatalogModel.rememberCatalog() " + e.getMessage(), "ERROR");
		}
	}
	
	public void listDest(HttpServletRequest request, HttpServletResponse response){
		ArrayList<Catalog> c = new ArrayList<Catalog>();
		
		HttpSession s = request.getSession();
		ArrayList<Catalog> rememberedCatalogs = (ArrayList<Catalog>)s.getAttribute("rememberedCatalogs");
		if(rememberedCatalogs == null || rememberedCatalogs.size() == 0) return;
		
		try{
			CatalogDAO dao = new CatalogDAO();
			Collection<Catalog> all = dao.getCatalogs();
			
			for(Catalog upper : all){
				Collection<Catalog> subs = upper.getSubCatalogs();
				
				int points = 0;
				for(Catalog sub : subs){
					// not fitting upper catalog
					for(Catalog rem : rememberedCatalogs){
						if(rem.getName().equalsIgnoreCase(sub.getName())) points++;
					}
				}
				if(points == 0) c.add(upper);
			}
			request.setAttribute("destCatalogs", c);
		}
		catch (Exception e) {
			logger.log("CatalogModel.listDest() " + e.getMessage(), "ERROR");
		}
	}
}
