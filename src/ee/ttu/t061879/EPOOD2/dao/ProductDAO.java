package ee.ttu.t061879.EPOOD2.dao;

import java.sql.SQLException;
import java.util.ResourceBundle;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import ee.ttu.t061879.EPOOD2.data.Product;
import ee.ttu.t061879.EPOOD2.utils.HibernateUtil;
import ee.ttu.t061879.EPOOD2.utils.Log;

public class ProductDAO {
	private Session session;
	private Query q;
	private int structUnit;
	private Log logger;
	
	public ProductDAO() {
		logger = new Log();
		this.session = HibernateUtil.getSessionFactory().getCurrentSession();
		ResourceBundle b = ResourceBundle.getBundle("app-config");
		this.structUnit = Integer.parseInt(b.getString("struct_unit"));
	}
	
	public Product getProduct(int product){
		logger.log("ProductDAO.getProduct() " + product, "DEBUG");
		
		try{
			this.session.beginTransaction();
			logger.log("1:" + product, "DEBUG");
			q = session.createQuery("FROM Product AS P WHERE P.product = :p_id");
			logger.log("2:" + product, "DEBUG");
			q.setInteger("p_id", product);
			
			logger.log(q.getQueryString(), "INFO");
			
			Product p = (Product)q.uniqueResult();
			logger.log("3:" + product, "DEBUG");
			if(p == null) logger.log("null", "DEBUG");
			return p;
		}
		catch(HibernateException e){
			logger.log("ProductDAO.getProduct() " + e.toString(), "ERROR");
		}
		catch(Exception e){
			logger.log("ProductDAO.getProduct() " + e.getClass(), "ERROR");
			e.printStackTrace();
		}
		finally{
			session.close();
		}
		
		return null;
	}
}
