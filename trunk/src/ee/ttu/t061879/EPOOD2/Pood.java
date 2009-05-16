package ee.ttu.t061879.EPOOD2;

import java.io.IOException;

import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ee.ttu.t061879.EPOOD2.dao.Auth;
import ee.ttu.t061879.EPOOD2.model.CatalogModel;
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
		
		try{
			request.setCharacterEncoding("UTF-8");
		}catch(Exception e){ logger.log(e.getMessage(), "ERROR");}
		
		request.setAttribute("title", b.getString("title"));
		
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
			else if(request.getSession().getAttribute("auth") == null){
				logger.log("controller: not authenticated", "DEBUG");
				getServletContext().getRequestDispatcher("/Login.jsp")
				.forward(request, response);
			}
			else if(mode != null && mode.equalsIgnoreCase("ListCatalogs")){
				logger.log("controller: ListCatalogs", "DEBUG");
				CatalogModel model = new CatalogModel();
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
					model.list(request, response);
					getServletContext().getRequestDispatcher("/CatalogList.jsp")
					.forward(request, response);
				}
				else getServletContext().getRequestDispatcher("/AddCatalog.jsp")
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
