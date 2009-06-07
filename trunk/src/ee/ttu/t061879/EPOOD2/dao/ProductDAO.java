package ee.ttu.t061879.EPOOD2.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.ResourceBundle;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import ee.ttu.t061879.EPOOD2.data.Employee;
import ee.ttu.t061879.EPOOD2.data.Product;
import ee.ttu.t061879.EPOOD2.utils.HibernateUtil;
import ee.ttu.t061879.EPOOD2.utils.Log;
import ee.ttu.t061879.EPOOD2.utils.Utils;
import ee.ttu.t061879.EPOOD2.validate.ProductSearch;

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
	
	public ArrayList<Product> productSearch(ProductSearch s){
		logger.log("ProductDAO.productSearch()", "DEBUG");
		ArrayList<Product> ps = new ArrayList<Product>();
		
		try{
			this.session.beginTransaction();
			String hql = "from Product as p left join p.createdBy as creator left join p.updatedBy as updater ";
			
			hql += "WHERE 1=1 ";
			if(s.getName().length() > 0) hql += "AND upper(name) LIKE upper('" + s.getName() + "%') ";
			if(s.getDesc().length() > 0) hql += "AND upper(description) LIKE upper('" + s.getDesc() + "%') ";
			if(s.getMinPrice().length() > 0) hql += "AND price > " + Utils.number(s.getMinPrice()) + " ";
			if(s.getMaxPrice().length() > 0) hql += "AND price < " + Utils.number(s.getMaxPrice()) + " ";
			if(s.getMinAdded().length() > 0) hql += "AND p.created > '" + Utils.isoDate(s.getMinAdded()) + "' ";
			if(s.getMaxAdded().length() > 0) hql += "AND p.created < '" + Utils.isoDate(s.getMaxAdded()) + "' ";
			if(s.getMinChanged().length() > 0) hql += "AND p.updated > '" + Utils.isoDate(s.getMinChanged()) + "' ";
			if(s.getMaxChanged().length() > 0) hql += "AND p.updated < '" + Utils.isoDate(s.getMaxChanged()) + "' ";
			if(s.getAddedBy().length() > 0) 
				hql += "AND upper(creator.firstName || ' ' || creator.lastName) LIKE upper('" + s.getAddedBy() + "%') ";
			if(s.getChangedBy().length() > 0) 
				hql += "AND upper(updater.firstName || ' ' || updater.lastName) LIKE upper('" + s.getChangedBy() + "%') ";
			if(s.getCatalogs().size() > 0){
				hql += "AND p.catalog in(";
				
				String list = s.getCatalogs().toString();
				list = list.substring(1, list.length() - 1);
				hql += list;
				
				hql += ")";
			}
			
			q = session.createQuery(hql);
			logger.log(q.getQueryString(), "INFO");
			
			Iterator i = q.list().iterator();
		    while(i.hasNext()){
		    	Object[] o = (Object[])i.next();
		    	Product p = (Product) o[0];
		    	Employee c = (Employee) o[1];
		    	Employee u = (Employee) o[2];
		    	
		    	Product pr = new Product();
		    	pr.setProduct(p.getProduct());
		    	pr.setName(p.getName());
		    	pr.setDescription(p.getDescription());
		    	pr.setPrice(p.getPrice());
		    	pr.setCreated(p.getCreated());
		    	pr.setUpdated(p.getUpdated());
		    	pr.setCreatedBy(c);
		    	pr.setUpdatedBy(u);
		    	
		    	ps.add(pr);
		    }
			
			return ps;
		}
		catch(HibernateException e){
			logger.log("ProductDAO.productSearch() " + e.toString(), "ERROR");
		}
		catch(Exception e){
			logger.log("ProductDAO.productSearch() " + e.getClass(), "ERROR");
			e.printStackTrace();
		}
		finally{
			session.close();
		}
		
		return ps;
	}
}
