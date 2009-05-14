package ee.ttu.t061879.EPOOD2.model;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ee.ttu.t061879.EPOOD2.dao.CatalogDAO;

public class CatalogModel {
	public void list(HttpServletRequest request, HttpServletResponse response){
		CatalogDAO dao = new CatalogDAO();
		request.setAttribute("CatalogList", dao.getCatalogs());
	}
	
	public void add(HttpServletRequest request, HttpServletResponse response){
		
	}
}
