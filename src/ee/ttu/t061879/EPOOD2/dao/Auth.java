package ee.ttu.t061879.EPOOD2.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.http.HttpServletRequest;

import ee.ttu.t061879.EPOOD2.data.User;
import ee.ttu.t061879.EPOOD2.utils.Log;

public class Auth {
	private Connection connection;
	private Statement statement;
	private String sql;
	private ResultSet set;
	private Log logger;
	
	public Auth() {
		logger = new Log();
		this.connection = DatabaseConnection.getDatabaseConnection();
	}
	
	public boolean check(String username, String password, HttpServletRequest request){
		User u = null;
		
		try{
			this.statement = connection.createStatement();
			
			sql = "SELECT first_name, last_name, username, emp_role, current_struct_unit FROM employee e JOIN emp_user u" +
					" ON(e.employee=u.employee) WHERE username = '" + username +
					"' AND passw = MD5('" + password + "');"; 
			logger.log(sql, "INFO");
			set = statement.executeQuery(sql);
			
			while(set.next()){
				u = new User();
				u.setFirstName(set.getString("first_name"));
				u.setLastName(set.getString("last_name"));
				u.setUserName(set.getString("username"));
				u.setEmpRole(set.getString("emp_role"));
				u.setCurrentStructUnit(set.getString("current_struct_unit"));
			}
			
			if(u != null){
				request.getSession().setAttribute("user", u);
				request.getSession().setAttribute("auth", 1);
			}
		}
		catch(Exception e){
			logger.log("Auth.check() " + e.getMessage(), "ERROR");
		}
		
		return (u != null);
	}
}