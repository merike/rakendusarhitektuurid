package ee.ttu.t061879.EPOOD2.model;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ee.ttu.t061879.EPOOD2.dao.ProductDAO;
import ee.ttu.t061879.EPOOD2.data.Catalog;
import ee.ttu.t061879.EPOOD2.utils.Log;
import ee.ttu.t061879.EPOOD2.validate.ProductSearch;
import ee.ttu.t061879.EPOOD2.validate.ProductSearchValidator;

public class ProductModel {
	private Log logger;
	
	public ProductModel() {
		logger = new Log();
	}
	
	public void get(HttpServletRequest request, HttpServletResponse response){
		try{
			int product = Integer.parseInt(request.getParameter("submode")); 
			ProductDAO dao = new ProductDAO();
			request.setAttribute("product", dao.getProduct(product));
		}
		catch (Exception e) {
			logger.log("ProductModel.get() " + e.getMessage(), "ERROR");
		}
	}
	
	public void search(HttpServletRequest request, HttpServletResponse response){
		ProductSearch s = new ProductSearch();
		
		ArrayList<Catalog> cs = (ArrayList<Catalog>)request.getSession().getAttribute("rememberedCatalogs");
		if(cs != null){
			for(Catalog cat : cs) s.addCatalog(cat.getProductCatalog()); 
		}
		
		if(request.getParameter("nimi") != null) s.setName(request.getParameter("nimi"));
		if(request.getParameter("kirjeldus") != null) s.setDesc(request.getParameter("kirjeldus"));
		if(request.getParameter("v_hind") != null) s.setMinPrice(request.getParameter("v_hind"));
		if(request.getParameter("s_hind") != null) s.setMaxPrice(request.getParameter("s_hind"));
		if(request.getParameter("sisestaja") != null) s.setAddedBy(request.getParameter("sisestaja"));
		if(request.getParameter("sisest_algus") != null) s.setMinAdded(request.getParameter("sisest_algus"));
		if(request.getParameter("sisest_lopp") != null) s.setMaxAdded(request.getParameter("sisest_lopp"));
		if(request.getParameter("muutja") != null) s.setChangedBy(request.getParameter("muutja"));
		if(request.getParameter("muutm_algus") != null) s.setMinChanged(request.getParameter("muutm_algus"));
		if(request.getParameter("muutm_lopp") != null) s.setMaxChanged(request.getParameter("muutm_lopp"));
		
		ProductSearchValidator v = new ProductSearchValidator();
		v.validate(s);
		boolean result = s.isOk();  
		logger.log("validation result " + result, "DEBUG");
		
		try{
			if(result){
				ProductDAO dao = new ProductDAO();
				request.setAttribute("product_search_results", dao.productSearch(s));
			}
			request.setAttribute("search", s);
		}
		catch (Exception e) {
			logger.log("ProductModel.search() " + e.getMessage(), "ERROR");
		}
	}
	
	public void list(HttpServletRequest request, HttpServletResponse response){
		ProductSearch s = new ProductSearch();
		
		ArrayList<Catalog> cs = (ArrayList<Catalog>)request.getSession().getAttribute("rememberedCatalogs");
		int catalog = Integer.parseInt(request.getParameter("submode"));
		s.addCatalog(catalog); 
				
		try{
			ProductDAO dao = new ProductDAO();
			request.setAttribute("ProductList", dao.productSearch(s));
		}
		catch (Exception e) {
			logger.log("ProductModel.list() " + e.getMessage(), "ERROR");
		}
	}
}
