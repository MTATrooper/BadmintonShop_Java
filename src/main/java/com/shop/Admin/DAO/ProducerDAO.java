package com.shop.Admin.DAO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.shop.models.Producer;

import HibernateUtil.HibernateUtil;


public class ProducerDAO {
	
	public ProducerDAO(){
		super();
	}
	public List<Producer> getListProducer() {
		Session session = HibernateUtil.getSessionFactory().openSession();
		List<?> lst = new ArrayList<Producer>();
        try{
            
               session.beginTransaction();
               lst = session.createCriteria(Producer.class).list();
               session.getTransaction().commit();
        }
        catch(Exception e){
          e.printStackTrace();
          session.getTransaction().rollback();
        }
            session.close();
            
        return (List<Producer>)lst;
	}

	public Producer getProducerbyID(Integer id) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Producer pro = session.get(Producer.class, id);
		session.close();
		return pro;
	}

	public Integer addProducer(Producer pro) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = null;
		int results = 0;
		try {
			tx = session.beginTransaction();
			results = (Integer)session.save(pro);
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

	public Integer updateProducer(Producer pro) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = null;
		int results = 0;
		try {
			tx = session.beginTransaction();
			Producer producer = session.get(Producer.class, pro.getId());
			producer.setName(pro.getName());
			producer.setLogo(pro.getLogo());
			results = (Integer)session.save(producer);
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

	public void deleteProducer(Integer id) {
		//System.out.println(id);
		Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Producer pro = session.get(Producer.class, id);
            //System.out.println(pro.getId());
            session.delete(pro);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null)
                tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }

	}

	public Map<String, Integer> getDropdownProducer() {
		List<Producer> producers = getListProducer();
		Map<String, Integer> dropdown = new HashMap<String, Integer>();
		for(Producer p : producers)
		{
			dropdown.put(p.getName(), p.getId());
		}
		return dropdown;
	}

}
