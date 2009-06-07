package ee.ttu.t061879.EPOOD2.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.ResourceBundle;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import ee.ttu.t061879.EPOOD2.data.Enterprise;
import ee.ttu.t061879.EPOOD2.utils.HibernateUtil;
import ee.ttu.t061879.EPOOD2.utils.Log;

public class EnterpriseDAO {
	private Session session;
	private Query q;
	private Log logger;
	
	public EnterpriseDAO() {
		logger = new Log();
		this.session = HibernateUtil.getSessionFactory().getCurrentSession();
		ResourceBundle b = ResourceBundle.getBundle("app-config");
	}
	
	public ArrayList<Enterprise> list(){
		logger.log("EnterpriseDAO.list()", "DEBUG");
		ArrayList<Enterprise> es = new ArrayList<Enterprise>();
		
		try{
			this.session.beginTransaction();
			String hql = "from Enterprise as e order by name";
			q = session.createQuery(hql);
			logger.log(q.getQueryString(), "INFO");
			
			Iterator i = q.list().iterator();
		    while(i.hasNext()){
		    	Enterprise e = (Enterprise)i.next();
		    	es.add(e);
		    }
			
		}
		catch(HibernateException e){
			logger.log("EnterpriseDAO.list() " + e.toString(), "ERROR");
		}
		catch(Exception e){
			logger.log("EnterpriseDAO.list() " + e.getClass(), "ERROR");
			e.printStackTrace();
		}
		finally{
			session.close();
		}
		
		return es;
	}
	
	public Enterprise get(int enterprise){
		logger.log("EnterpriseDAO.get()", "DEBUG");
		Enterprise en = null;
		
		try{
			this.session.beginTransaction();
			String hql = "from Enterprise as e where e.enterprise = :e_id";
			q = session.createQuery(hql);
			q.setInteger("e_id", enterprise);
			logger.log(q.getQueryString(), "INFO");
			
			en = (Enterprise)q.uniqueResult();
		}
		catch(HibernateException e){
			logger.log("EnterpriseDAO.get() " + e.toString(), "ERROR");
		}
		catch(Exception e){
			logger.log("EnterpriseDAO.get() " + e.getClass(), "ERROR");
			e.printStackTrace();
		}
		finally{
			session.close();
		}
		
		return en;
	}
}
