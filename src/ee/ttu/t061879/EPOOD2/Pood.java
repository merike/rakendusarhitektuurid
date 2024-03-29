package ee.ttu.t061879.EPOOD2;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ee.ttu.t061879.EPOOD2.dao.Auth;
import ee.ttu.t061879.EPOOD2.model.CatalogModel;
import ee.ttu.t061879.EPOOD2.model.ProductModel;
import ee.ttu.t061879.EPOOD2.utils.Log;

public class Pood extends HttpServlet{
	private Log logger;
	
	public Pood() {
		logger = new Log();
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
	throws ServletException{
		ResourceBundle b = ResourceBundle.getBundle("app-config");
		request.setAttribute("staticPath", b.getString("static_path"));
		
		request.setAttribute("title", b.getString("title"));
		try{
			request.setCharacterEncoding("UTF-8");
		}
		catch(UnsupportedEncodingException e){
			logger.log("controller: couldn't set encoding", "ERROR");
		}
		response.setCharacterEncoding("UTF-8");
		
		String mode = request.getParameter("mode");
		String submode = request.getParameter("submode");
		
		request.setAttribute("mode", mode);
		request.setAttribute("submode", submode);
		
		logger.log("modes: " + mode + " " + submode, "DEBUG");
		logger.log(" for " + request.getRequestURL(), "DEBUG");
		
		try{
			if(mode != null && mode.equalsIgnoreCase("Login")){
				logger.log("controller: login", "DEBUG");
				Auth a = new Auth();
				boolean result = a.check(request.getParameter("user"),
						request.getParameter("password"),
						request);
				
				logger.log(result + "", "DEBUG");
				
				if(result == false){
					getServletContext().getRequestDispatcher("/Login.jsp")
					.forward(request, response);
				}
				else{
					CatalogModel model = new CatalogModel();
					model.list(request, response);
					getServletContext().getRequestDispatcher("/CatalogList.jsp")
					.forward(request, response);
				}
			} // end login
			else if(mode != null && mode.equalsIgnoreCase("Logout")){
				logger.log("controller: Logout", "DEBUG");
				Auth a = new Auth();
				a.logout(request);
				getServletContext().getRequestDispatcher("/Login.jsp")
				.forward(request, response);
			}
			else if(request.getSession().getAttribute("auth") == null){
				logger.log("controller: not authenticated", "DEBUG");
				getServletContext().getRequestDispatcher("/Login.jsp")
				.forward(request, response);
			}
			else if(mode != null && mode.equalsIgnoreCase("CatalogList")
					&& submode == null){
				logger.log("controller: CatalogList", "DEBUG");
				CatalogModel model = new CatalogModel();
				model.list(request, response);
				getServletContext().getRequestDispatcher("/CatalogList.jsp")
				.forward(request, response);
			}
			else if(mode != null && mode.equalsIgnoreCase("CatalogList")
					&& submode != null){
				logger.log("controller: CatalogList remember", "DEBUG");
				CatalogModel model = new CatalogModel();
				model.rememberCatalog(request, response);
				model.list(request, response);
				getServletContext().getRequestDispatcher("/CatalogList.jsp")
				.forward(request, response);
			}
			else if(mode != null && mode.equalsIgnoreCase("DestCatalog")
					&& submode == null){
				logger.log("controller: DestCatalog", "DEBUG");
				CatalogModel model = new CatalogModel();
				model.listDest(request, response);
				getServletContext().getRequestDispatcher("/DestCatalogs.jsp")
				.forward(request, response);
			}
			else if(mode != null && mode.equalsIgnoreCase("DestCatalog")
					&& submode != null){
				logger.log("controller: DestCatalog Choose", "DEBUG");
				CatalogModel model = new CatalogModel();
				model.moveCatalogs(request, response);
				model.list(request, response);
				getServletContext().getRequestDispatcher("/CatalogList.jsp")
				.forward(request, response);
			}
			else if(mode != null && mode.equalsIgnoreCase("AddCatalog")){
				logger.log("controller: AddCatalog", "DEBUG");
				getServletContext().getRequestDispatcher("/AddCatalog.jsp")
				.forward(request, response);
			}
			else if(mode != null && mode.equalsIgnoreCase("AddCatalogSubmit")){
				logger.log("controller: AddCatalogSubmit", "DEBUG");
				CatalogModel model = new CatalogModel();
				model.add(request, response);
				
				boolean r = (Boolean)request.getAttribute("insertResult");
				logger.log("controller catalog insert result " + r, "DEBUG");
				
				if(r){
					logger.log("controller catalog insert successful", "DEBUG");
					getServletContext().getRequestDispatcher("/CatalogList.jsp")
					.forward(request, response);
				}
				else{
					logger.log("controller catalog insert unsuccessful", "DEBUG");
					getServletContext().getRequestDispatcher("/AddCatalog.jsp")
					.forward(request, response);
				}
			}
			else if(mode != null && mode.equalsIgnoreCase("EditCatalog")){
				logger.log("controller: EditCatalog", "DEBUG");
				CatalogModel model = new CatalogModel();
				model.get(request.getParameter("submode"), request);
				getServletContext().getRequestDispatcher("/EditCatalog.jsp")
				.forward(request, response);
			}
			else if(mode != null && mode.equalsIgnoreCase("EditCatalogSubmit")){
				logger.log("controller: EditCatalogSubmit", "DEBUG");
				CatalogModel model = new CatalogModel();
				model.edit(request, response);
				
				boolean r = (Boolean)request.getAttribute("editResult");
				if(r){
					logger.log("controller: catalog edit successful", "DEBUG");
					getServletContext().getRequestDispatcher("/CatalogList.jsp")
					.forward(request, response);
				}
				else{
					logger.log("controller: catalog edit unsuccessful", "DEBUG");
					getServletContext().getRequestDispatcher("/EditCatalog.jsp")
					.forward(request, response);
				}
			}
			
			else if(mode != null && mode.equalsIgnoreCase("ProductSearch")){
				logger.log("controller: ProductSearch", "DEBUG");
				if(request.getParameter("otsi") != null){
					ProductModel model = new ProductModel();
					model.search(request, response);
					if(request.getAttribute("search") != null) logger.log("luck!", "DEBUG");
				}
				getServletContext().getRequestDispatcher("/ProductSearch.jsp")
				.forward(request, response);
			}
			else if(mode != null && mode.equalsIgnoreCase("ProductList")
					&& submode != null){
				logger.log("controller: ProductList", "DEBUG");
				CatalogModel model_c = new CatalogModel();
				model_c.get(request.getParameter("submode"), request);
				ProductModel model = new ProductModel();
				model.list(request.getParameter("submode"), request);
				getServletContext().getRequestDispatcher("/ProductList.jsp")
				.forward(request, response);
			}
			else if(mode != null && mode.equalsIgnoreCase("ProductView")
					&& submode != null){
				logger.log("controller: ProductView", "DEBUG");
				ProductModel model = new ProductModel();
				model.get(request, response);
				getServletContext().getRequestDispatcher("/ProductView.jsp")
				.forward(request, response);
			}
			else if(mode != null && mode.equalsIgnoreCase("EditProductSubmit")
					&& submode != null){
				logger.log("controller: EditProductSubmit", "DEBUG");
				ProductModel model = new ProductModel();
				model.edit(request, response);
				
				boolean r = (Boolean)request.getAttribute("editResult");
				if(r){
					logger.log("controller: product edit successful", "DEBUG");
					model.get(request, response);
					getServletContext().getRequestDispatcher("/ProductView.jsp")
					.forward(request, response);
				}
				else{
					logger.log("controller: product edit unsuccessful", "DEBUG");
					model.get(request, response);
					getServletContext().getRequestDispatcher("/ProductView.jsp")
					.forward(request, response);
				}
			}
			else if(mode != null && mode.equalsIgnoreCase("AddProduct")
					&& submode != null){
				logger.log("controller: AddProduct", "DEBUG");
				ProductModel model = new ProductModel();
				model.add(request, response);
				getServletContext().getRequestDispatcher("/AddProduct.jsp")
				.forward(request, response);
			}
			else if(mode != null && mode.equalsIgnoreCase("AddProductSubmit")
					&& submode != null){
				logger.log("controller: AddProductSubmit", "DEBUG");
				ProductModel model = new ProductModel();
				model.addProduct(request, response);
				
				boolean r = (Boolean)request.getAttribute("insertResult");
				if(r){
					logger.log("controller product insert successful", "DEBUG");
					model.add(request, response);
					CatalogModel model_c = new CatalogModel();
					model_c.get(request.getParameter("submode"), request);
					model.list(request.getParameter("submode"), request);
					getServletContext().getRequestDispatcher("/ProductList.jsp")
					.forward(request, response);
				}
				else{
					logger.log("controller product insert unsuccessful", "DEBUG");
					model.add(request, response);
					getServletContext().getRequestDispatcher("/AddProduct.jsp")
					.forward(request, response);
				}
			}
			else if(mode != null && mode.equalsIgnoreCase("ProductDelete")
					&& submode != null){
				logger.log("controller: ProductDelete", "DEBUG");
				ProductModel model = new ProductModel();
				model.delete(request, response);
				getServletContext().getRequestDispatcher("/ProductList.jsp")
				.forward(request, response);
			}
			else{
				logger.log("controller: default", "DEBUG");
				CatalogModel model = new CatalogModel();
				model.list(request, response);
				getServletContext().getRequestDispatcher("/CatalogList.jsp")
				.forward(request, response);
			}
		}
		catch(Exception e){
			// FIXME
			logger.log("FIXME: Pood.doGet() " + e.getMessage(), "ERROR");
		}
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
	throws IOException, ServletException {
		doGet(request, response);
	}
}
