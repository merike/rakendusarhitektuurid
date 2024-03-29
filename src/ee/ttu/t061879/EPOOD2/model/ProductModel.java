package ee.ttu.t061879.EPOOD2.model;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ee.ttu.t061879.EPOOD2.dao.CatalogDAO;
import ee.ttu.t061879.EPOOD2.dao.EnterpriseDAO;
import ee.ttu.t061879.EPOOD2.dao.ProductDAO;
import ee.ttu.t061879.EPOOD2.data.Catalog;
import ee.ttu.t061879.EPOOD2.data.Enterprise;
import ee.ttu.t061879.EPOOD2.data.Product;
import ee.ttu.t061879.EPOOD2.data.User;
import ee.ttu.t061879.EPOOD2.utils.Log;
import ee.ttu.t061879.EPOOD2.utils.Utils;
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
			EnterpriseDAO edao = new EnterpriseDAO();
			request.setAttribute("enterprise_list", edao.list());
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
	
	public void list(String catalog, HttpServletRequest request){
		ProductSearch s = new ProductSearch();
		
		ArrayList<Catalog> cs = (ArrayList<Catalog>)request.getSession().getAttribute("rememberedCatalogs");
				
		try{
			s.addCatalog(Integer.parseInt(catalog));
			ProductDAO dao = new ProductDAO();
			request.setAttribute("ProductList", dao.productSearch(s));
		}
		catch (Exception e) {
			logger.log("ProductModel.list() " + e.getMessage(), "ERROR");
		}
	}
	
	public void add(HttpServletRequest request, HttpServletResponse response){
		try{
			int catalog;
			catalog = Integer.parseInt((String)request.getAttribute("submode"));
			EnterpriseDAO dao = new EnterpriseDAO();
			CatalogDAO cdao = new CatalogDAO();
			request.setAttribute("catalog", cdao.getCatalog(catalog));
			request.setAttribute("enterprise_list", dao.list());
		}
		catch (Exception e) {
			logger.log("ProductModel.add() " + e.getMessage(), "ERROR");
		}
	}
	
	public void edit(HttpServletRequest request, HttpServletResponse response){
		String desc = request.getParameter("kirjeldus");
		String name = request.getParameter("nimi");
		String code = request.getParameter("kood");
		double price;
		int enterprise;
		int product;
		boolean result = false;
		Product p = new Product();
		User u;
		
		try{
			price = Utils.number(request.getParameter("hind"));
			enterprise = Integer.parseInt(request.getParameter("tootja"));
			product = Integer.parseInt(request.getParameter("submode"));
			u = (User)(request.getSession().getAttribute("user"));
			
			p.setProduct(product);
			p.setName(name);
			p.setDescription(desc);
			p.setCode(code);
			p.setPrice(price);
			
			EnterpriseDAO edao = new EnterpriseDAO();
			p.setEnterprise(edao.get(enterprise));
		
			ProductDAO dao = new ProductDAO();
			result = dao.editProduct(p, u.getEmpUser());
		}
		catch (Exception e) {
			logger.log("ProductModel.edit() " + e.getMessage(), "ERROR");
		}
		
		if(result == true){
			request.setAttribute("editResult", true);
			request.setAttribute("info", "Toote salvestamine õnnestus.");
			this.get(request, response);
		}
		else{
			request.setAttribute("editResult", false);
			request.setAttribute("info", "Toote salvestamine ebaõnnestus!");
			request.setAttribute("product", p);
		}
	}
	
	public void addProduct(HttpServletRequest request, HttpServletResponse response){
		String desc = request.getParameter("kirjeldus");
		String name = request.getParameter("nimi");
		String code = request.getParameter("kood");
		int enterprise;
		double price;
		
		Product p = new Product();
		User u = (User)(request.getSession().getAttribute("user"));
		
		p.setName(name);
		p.setDescription(desc);
		p.setCode(code);
		
		int catalog;
	
		try{
			// assume worst
			request.setAttribute("insertResult", false);
			request.setAttribute("info", "Toote sisestamine ebaõnnestus!");
			
			enterprise = Integer.parseInt(request.getParameter("tootja"));
			catalog = Integer.parseInt(request.getParameter("submode"));
			p.setCatalog(catalog);
			price = Utils.number((String)request.getParameter("hind"));
			p.setPrice(price);
			
			EnterpriseDAO edao = new EnterpriseDAO();
			p.setEnterprise(edao.get(enterprise));
			
			ProductDAO dao = new ProductDAO();
			boolean result = dao.addProduct(p, u);
			
			if(result == true){
				request.setAttribute("insertResult", true);
				request.setAttribute("info", "Toote sisestamine õnnestus.");
				this.list(catalog + "", request);
			}
			else{
				request.setAttribute("product", p);
			}
		}
		catch(Exception e){
			logger.log("ProductModel.addProduct() " + e.getMessage(), "ERROR");
		}	
	}
	
	public void delete(HttpServletRequest request, HttpServletResponse response){
		int product;
		Product p;
		
		try{
			// assume worst
			request.setAttribute("insertResult", false);
			request.setAttribute("info", "Toote kustutamine ebaõnnestus!");
			
			product = Integer.parseInt(request.getParameter("submode"));
			
			ProductDAO dao = new ProductDAO();
			p = dao.getProduct(product);
			this.list(p.getCatalog() + "", request);
			boolean result = dao.delete(product);
			
			CatalogDAO cdao = new CatalogDAO();
			request.setAttribute("catalog", cdao.getCatalog(p.getCatalog()));
			
			if(result == true){
				request.setAttribute("insertResult", true);
				request.setAttribute("info", "Toote kustutamine õnnestus.");
				this.list(p.getCatalog() + "", request);
				logger.log("controller: product delete successful", "DEBUG");
			}
			else{
				logger.log("controller: product delete unsuccessful", "DEBUG");
			}
		}
		catch(Exception e){
			logger.log("ProductModel.delete() " + e.getMessage(), "ERROR");
		}	
	}
}
