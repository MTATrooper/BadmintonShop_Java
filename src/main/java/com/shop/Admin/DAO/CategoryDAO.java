package com.shop.Admin.DAO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.shop.models.Category;

import HibernateUtil.HibernateUtil;

public class CategoryDAO {

	public CategoryDAO()
	{
		super();
	}
	public List<Category> getListCategory() {
		Session session = HibernateUtil.getSessionFactory().openSession();
		List<?> lst = new ArrayList<Category>();
        try{
            
               session.beginTransaction();
               lst = session.createCriteria(Category.class).list();
               session.getTransaction().commit();
        }
        catch(Exception e){
          e.printStackTrace();
          session.getTransaction().rollback();
        }
            session.close();
            
        return (List<Category>)lst;
	}

	public Category getCategorybyID(Integer id) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Category pro = session.get(Category.class, id);
		session.close();
		return pro;
	}

	public Integer addCategory(Category cat) {
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

	public Integer updateCategory(Category cat) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = null;
		int results = 0;
		try {
			tx = session.beginTransaction();
			Category producer = session.get(Category.class, cat.getId());
			producer.setName(cat.getName());
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

	public void deleteCategory(Integer id) {
		Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Category pro = session.get(Category.class, id);
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

	public Map<String, Integer> getDropdownCategory() {
		List<Category> producers = getListCategory();
		Map<String, Integer> dropdown = new HashMap<String, Integer>();
		for(Category p : producers)
		{
			dropdown.put(p.getName(), p.getId());
		}
		return dropdown;
	}

}
