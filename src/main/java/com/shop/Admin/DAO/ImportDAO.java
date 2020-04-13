package com.shop.Admin.DAO;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.shop.models.ImportProduct;

import HibernateUtil.HibernateUtil;

public class ImportDAO {
	public ImportDAO()
	{
		super();
	}
	
	public List<ImportProduct> getListImportProduct() {
		Session session = HibernateUtil.getSessionFactory().openSession();
		List<?> lst = new ArrayList<ImportProduct>();
        try{
            
               session.beginTransaction();
               lst = session.createCriteria(ImportProduct.class).list();
               session.getTransaction().commit();
        }
        catch(Exception e){
          e.printStackTrace();
          session.getTransaction().rollback();
        }
            session.close();
            
        return (List<ImportProduct>)lst;
	}
	public ImportProduct getImportProductbyID(Integer id) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		ImportProduct pro = session.get(ImportProduct.class, id);
		session.close();
		return pro;
	}
	
	public List<ImportProduct> getImportProductbyDate(Date date) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		List<?> lst = new ArrayList<ImportProduct>();
        try{
            
               session.beginTransaction();
               lst = session.createQuery("from ImportProduct where importDate = :date").setDate("date", date).list();
               session.getTransaction().commit();
        }
        catch(Exception e){
          e.printStackTrace();
          session.getTransaction().rollback();
        }
            session.close();
            
        return (List<ImportProduct>)lst;
	}

	public Integer addImportProduct(ImportProduct cat) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = null;
		int results = 0;
		try {
			tx = session.beginTransaction();
			results = (Integer)session.save(cat);
			tx.commit();
		}
		catch(HibernateException e) {
			if (tx != null)
                tx.rollback();
            e.printStackTrace();
		}
		finally {
			session.close();
		}
		return results;
	}

}
