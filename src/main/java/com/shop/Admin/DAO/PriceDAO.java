package com.shop.Admin.DAO;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.shop.models.Price;

import HibernateUtil.HibernateUtil;

public class PriceDAO {
	
	public PriceDAO() {
		super();
	}
	
	public Integer getPriceByIdPro(Integer id) {
		List<Price> price = new ArrayList<Price>();
		Price currPrice = new Price();
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			//price = session.createQuery("from Price p inner join fetch p.product c where c.id = :ProductId order by p.beginDate desc").setInteger("ProductId", id).list();
			price = session.createQuery("from Price p inner join fetch p.product c where c.id = :ProductId and c.active = 1").setInteger("ProductId", id).list();
			Date today = new Date();
			for(Price p : price)
			{
				if(p.getBeginDate().before(today))
				{
					if(p.getEndDate() == null || p.getEndDate().after(today))
					{
						currPrice = p;
						break;
					}
				}
			}
			tx.commit();
		}
		catch(HibernateException e)
		{
			if (tx != null)
                tx.rollback();
            e.printStackTrace();
		}
		finally {
			session.close();
		}
		if(!price.isEmpty()) return currPrice.getPrice();
		else return 0;
	}
	
	public Price getLastPriceByIdPro(Integer id) {
		List<Price> price = new ArrayList<Price>();
		Price currPrice = new Price();
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			price = session.createQuery("from Price p inner join fetch p.product c where c.id = :ProductId order by p.beginDate desc").setInteger("ProductId", id).list();
			//price = session.createQuery("from Price p inner join fetch p.product c where c.id = :ProductId and c.active = 1").setInteger("ProductId", id).list();
			
			tx.commit();
		}
		catch(HibernateException e)
		{
			if (tx != null)
                tx.rollback();
            e.printStackTrace();
		}
		finally {
			session.close();
		}
		if(!price.isEmpty()) return price.get(0);
		else return null;
	}
	
	public void addPrice(Price price)
	{
		if(getLastPriceByIdPro(price.getProduct().getId()) != null)
		{
			Price last = getLastPriceByIdPro(price.getProduct().getId());
			Date lastend = last.getEndDate();
			if(lastend == null)
			{
				Price p = last;
				p.setEndDate(price.getBeginDate());
				updatePrice(p);
			}
		}
		
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			session.save(price);
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
		
	}
	public void updatePrice(Price price)
	{
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			Price pr = session.get(Price.class, price.getId());
			pr.setPrice(price.getPrice());
			pr.setBeginDate(price.getBeginDate());
			pr.setEndDate(price.getEndDate());
			session.save(pr);
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
	}
}
