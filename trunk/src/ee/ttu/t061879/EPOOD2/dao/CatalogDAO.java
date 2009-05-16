package ee.ttu.t061879.EPOOD2.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Collection;
import java.util.ResourceBundle;

import ee.ttu.t061879.EPOOD2.data.Catalog;
import ee.ttu.t061879.EPOOD2.utils.Log;

public class CatalogDAO {
	private Connection connection;
	private Statement statement;
	private String sql;
	private int structUnit;
	private ResultSet set;
	private Log logger;
	
	public CatalogDAO() {
		logger = new Log();
		this.connection = DatabaseConnection.getDatabaseConnection();
		ResourceBundle b = ResourceBundle.getBundle("app-config");
		this.structUnit = Integer.parseInt(b.getString("struct_unit"));
	}
	
	public Collection<Catalog> getCatalogs(){
		ArrayList<Catalog> catalogs = new ArrayList<Catalog>();
		
		try{
			this.statement = connection.createStatement();
			
			// top-level catalogs
			sql = "SELECT product_catalog, name, description " +
					"FROM product_catalog " +
					"WHERE struct_unit = " + this.structUnit +
					" AND upper_catalog IS NULL ORDER BY name;"; 
			logger.log(sql, "INFO");
			set = statement.executeQuery(sql);
			
			ArrayList<Integer> topLevelCatalogs = new ArrayList<Integer>();
			
			while(set.next()){
				topLevelCatalogs.add(new Integer(set.getInt("product_catalog")));
				
				Catalog c = new Catalog();
				c.setProductCatalog(set.getInt("product_catalog"));
				c.setName(set.getString("name"));
				c.setDescription(set.getString("description"));
				catalogs.add(c);
			}
			
			// no top-level catalogs at all
			if(catalogs.size() == 0) return catalogs;
			
			String tlcs = "";
			for(Integer i : topLevelCatalogs) tlcs += "," + i.toString();
			tlcs = tlcs.substring(1);
			
			// second-level catalogs
			sql = "SELECT product_catalog, upper_catalog, name, description " +
					"FROM product_catalog " +
					"WHERE struct_unit = " + this.structUnit +
					" AND upper_catalog IN(" + tlcs + ") ORDER BY name;";
			logger.log(sql, "INFO");
			set = statement.executeQuery(sql);
						
			while(set.next()){
				logger.log("subcatalog", "DEBUG");
				Catalog c = new Catalog();
				c.setUpperCatalog(set.getInt("upper_catalog"));
				c.setName(set.getString("name"));
				c.setDescription(set.getString("description"));
				
				for(int i = 0; i < topLevelCatalogs.size(); i++){
					logger.log(catalogs.get(i).getProductCatalog() + " " + c.getUpperCatalog(), "DEBUG");
					if(catalogs.get(i).getProductCatalog() == c.getUpperCatalog()){
						catalogs.get(i).addSubCatalog(c);
						logger.log("Adding subcatalog", "DEBUG");
						break;
					}
				}
			}
		}
		catch (Exception e) {
			logger.log("CatalogDAO.getCatalogs() "
					+ e.getMessage(), "ERROR");
		}
		
		return catalogs;
	}
	
	public boolean addCatalog(Catalog c, int user){
		boolean result = false;
		
		try{
			this.statement = connection.createStatement();
			
			sql = "{call add_top_level_catalog(?, ?, ?, ?, ?)}";
			CallableStatement cs = connection.prepareCall(sql);
			
			cs.setString(1, c.getName());
			cs.setString(2, c.getDescription());
			cs.setInt(3, user);
			cs.setInt(4, c.getStructUnit());
			cs.registerOutParameter(5, Types.INTEGER);
			
			cs.executeUpdate();
			
			if(cs.getInt(5) != 0) result = true;
		}
		catch(Exception e){
			logger.log("CatalogDAO.addCatalog() " + e.getMessage(), "ERROR");
		}
		
		return result;
	}
}
