package com.shop.Admin.DAO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.shop.models.Account;

import HibernateUtil.HibernateUtil;

public class AccountDAO {
	public AccountDAO()
	{
		super();
	}
	
	public List<Account> getListAccount() {
		Session session = HibernateUtil.getSessionFactory().openSession();
		List<?> lst = new ArrayList<Account>();
        try{
            
               session.beginTransaction();
               lst = session.createCriteria(Account.class).list();
               session.getTransaction().commit();
        }
        catch(Exception e){
          e.printStackTrace();
          session.getTransaction().rollback();
        }
            session.close();
            
        return (List<Account>)lst;
	}
	public Account getAccountbyID(Integer id) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Account pro = session.get(Account.class, id);
		session.close();
		return pro;
	}

	public Integer addAccount(Account cat) {
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

	public Integer updateAccount(Account cat) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = null;
		int results = 0;
		try {
			tx = session.beginTransaction();
			Account producer = session.get(Account.class, cat.getId());
			producer.setActive(cat.getActive());
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

	public void deleteAccount(Integer id) {
		Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Account pro = session.get(Account.class, id);
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

	public Map<String, Integer> getDropdownAccount() {
		List<Account> producers = getListAccount();
		Map<String, Integer> dropdown = new HashMap<String, Integer>();
		for(Account p : producers)
		{
			//dropdown.put(p.getName(), p.getId());
		}
		return dropdown;
	}

}
