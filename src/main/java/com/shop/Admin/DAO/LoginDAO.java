package com.shop.Admin.DAO;


import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.shop.models.Account;

import HibernateUtil.HibernateUtil;

public class LoginDAO {
	public LoginDAO() {
		super();
	}
	public static boolean validate(String user, String password) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = null;
		boolean results = true;
		try {
			tx = session.beginTransaction();
			List<Account> acc = (List<Account>) session.createQuery("from Account where username = :user and password = :pass and permission = 1")
					.setString("user", user).setString("pass", password).list();
			if(acc.size() == 0)
			{
				results = false;
			}
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
	public void changepass(String username, String newpass)
	{
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			Account acc = (Account) session.createQuery("from Account where username = :user and permission = 1")
					.setString("user", username).list().get(0);
			acc.setPassword(newpass);
			session.save(acc);
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
