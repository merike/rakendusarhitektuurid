package ee.ttu.t061879.EPOOD2.model;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ee.ttu.t061879.EPOOD2.dao.ProductDAO;
import ee.ttu.t061879.EPOOD2.utils.Log;

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
}
