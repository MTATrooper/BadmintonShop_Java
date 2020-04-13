package com.shop.Admin.DAO;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.shop.models.Product;

import HibernateUtil.HibernateUtil;


public class ProductDAO implements Serializable {
	public ProductDAO() {
		super();
	}
	
	public List<Product> getListProduct()
	{
		Session session = HibernateUtil.getSessionFactory().openSession();
		List<?> lst = new ArrayList<Product>();
        try{
            
               session.beginTransaction();
               //lst = session.createCriteria(Product.class).list();
               lst = session.createQuery("from Product where active = 1").list();
               session.getTransaction().commit();
        }
        catch(Exception e){
          e.printStackTrace();
          session.getTransaction().rollback();
        }
            session.close();
            
        return (List<Product>)lst;
	}
	
	public List<Product> getListProduct(Integer idpro, Integer idcat)
	{
		Session session = HibernateUtil.getSessionFactory().openSession();
		List<?> lst = new ArrayList<Product>();
        try{
            
               session.beginTransaction();
               //lst = session.createCriteria(Product.class).list();
               lst = session.createQuery("from Product p inner join fetch p.producer pr join fetch p.category c where c.id = :CategoryId and pr.id = :ProducerId and p.active = 1")
            		   .setInteger("CategoryId", idcat).setInteger("ProducerId", idpro).list();
               session.getTransaction().commit();
        }
        catch(Exception e){
          e.printStackTrace();
          session.getTransaction().rollback();
        }
            session.close();
            
        return (List<Product>)lst;
	}
	
	public List<Product> getListStop()
	{
		Session session = HibernateUtil.getSessionFactory().openSession();
		List<?> lst = new ArrayList<Product>();
        try{
            
               session.beginTransaction();
               //lst = session.createCriteria(Product.class).list();
               lst = session.createQuery("from Product where active = 2").list();
               session.getTransaction().commit();
        }
        catch(Exception e){
          e.printStackTrace();
          session.getTransaction().rollback();
        }
            session.close();
            
        return (List<Product>)lst;
	}
	
	public List<Product> getListOut()
	{
		Session session = HibernateUtil.getSessionFactory().openSession();
		List<?> lst = new ArrayList<Product>();
        try{
            
               session.beginTransaction();
               //lst = session.createCriteria(Product.class).list();
               lst = session.createQuery("from Product where quantity <= 10").list();
               session.getTransaction().commit();
        }
        catch(Exception e){
          e.printStackTrace();
          session.getTransaction().rollback();
        }
            session.close();
            
        return (List<Product>)lst;
	}
	
	public Product getProductbyID(Integer id)
	{
		Session session = HibernateUtil.getSessionFactory().openSession();
		Product pro = session.get(Product.class, id);
		session.close();
		return pro;
	}
	
	public Integer addProduct(Product pro)
	{
		Session session = HibernateUtil.getSessionFactory().openSession();
		//Product pro = new Product(proname, price);
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

	public Integer updateProduct(Product pro)
	{
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = null;
		int results = 0;
		try {
			tx = session.beginTransaction();
			Product product = session.get(Product.class, pro.getId());
			product.setName(pro.getName());
			product.setProducer(pro.getProducer());
			product.setCategory(pro.getCategory());
			product.setImage(pro.getImage());
			product.setQuantity(pro.getQuantity());
			product.setDescription(pro.getDescription());
			product.setIsHot(pro.getIsHot());
			product.setIsNew(pro.getIsNew());
			product.setActive(pro.getActive());
			results = (Integer)session.save(product);
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

	public void deleteProduct(Integer id) {
		System.out.println(id);
		Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Product pro = session.get(Product.class, id);
            System.out.println(pro.getId());
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
	public Map<String, Integer> getDropdownProduct() {
		List<Product> producers = getListProduct();
		Map<String, Integer> dropdown = new HashMap<String, Integer>();
		for(Product p : producers)
		{
			dropdown.put(p.getName(), p.getId());
		}
		return dropdown;
	}
}
