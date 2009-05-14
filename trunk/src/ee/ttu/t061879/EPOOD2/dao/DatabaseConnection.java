package ee.ttu.t061879.EPOOD2.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import ee.ttu.t061879.EPOOD2.utils.Log;


public class DatabaseConnection {
	private static Log logger = new Log();
	
	/**
	 * 
	 * @return new connection
	 */
	public static Connection getDatabaseConnection() {
		ResourceBundle b = ResourceBundle.getBundle("database-config");
		String driver = b.getString("driver");
		String url = b.getString("url");
		String username = b.getString("user");
		String password = b.getString("password");
		
		try{
			Class.forName(driver);
			Connection connection = DriverManager.getConnection(url, username, password);
			return connection;
		}
		catch(ClassNotFoundException e){
			logger.log("DatabaseConnection.getDatabaseConnection() "
					+ e.getMessage(), "ERROR");
			System.out.println(":(");
		}
		catch(SQLException e){
			logger.log("DatabaseConnection.getDatabaseConnection() "
					+ e.getMessage(), "ERROR");
		}
		catch(MissingResourceException e){
			logger.log("DatabaseConnection.getDatabaseConnection() "
					+ e.getMessage(), "ERROR");
		}
		
		return null;
	}

	public static void close(Connection c){
		try{
			if(c != null) c.close();
		}
		catch(SQLException e){
			logger.log("DatabaseConnection.close() "
					+ e.getMessage(), "ERROR");
		}
	}
}
