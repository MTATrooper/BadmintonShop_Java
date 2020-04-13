package com.shop.Admin.DAO;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.shop.models.Bill;
import com.shop.models.Product;
import com.shop.models.Statistic;

import HibernateUtil.HibernateUtil;

public class HomeDAO {
	public HomeDAO()
	{
		super();
	}
	public Integer getRevenueMonth(Integer month)
	{
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = null;
		Integer revenue = 0;
		try {
			tx = session.beginTransaction();
			Calendar today = Calendar.getInstance();
			today.setTime(new Date());
			Integer y = today.get(Calendar.YEAR);
			List<Integer> lstSta = (List<Integer>) session.createSQLQuery("select Revenue from Statistic where Month(StatisticDate) = :month and Year(StatisticDate) = :year")
						.setInteger("month", month).setInteger("year", y).list();
			
			for(Integer s : lstSta)
			{
				revenue += s;
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
		return revenue;
	}
	public Integer getBenefitMonth(Integer month)
	{
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = null;
		Integer revenue = 0;
		try {
			tx = session.beginTransaction();
			Calendar today = Calendar.getInstance();
			today.setTime(new Date());
			Integer y = today.get(Calendar.YEAR);
			List<Integer> lstSta = (List<Integer>) session.createSQLQuery("select Benefit from Statistic where Month(StatisticDate) = :month and Year(StatisticDate) = :year")
						.setInteger("month", month).setInteger("year", y).list();
			
			for(Integer s : lstSta)
			{
				revenue += s;
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
		return revenue;
	}
	public List<Product> sanphambanchay(String begin, String end)
	{
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = null;
		List<Product> lstpro = new ArrayList<Product>();
		try {
			//SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			//String beginstring = format.format(begin);
			//String endstring = format.format(end);
			tx = session.beginTransaction();
			//Lay tat ca cac san pham co trong khoang thoi gian do
			List<Integer> proid = (List<Integer>) session.createSQLQuery("select distinct ProductID from Statistic where StatisticDate >= :begin and StatisticDate <= :end")
									.setString("begin", begin).setString("end", end).list();
			//Tinh so luong cac san pham tren
			List<Integer> amount_pro = new ArrayList<Integer>();
			List<Integer> amount = new ArrayList<Integer>();
			for (int i = 0; i<= proid.size()-1; i++)
			{
				Integer x = (Integer) session.createSQLQuery("select sum(amount) from Statistic where StatisticDate >= :begin and StatisticDate <= :end and ProductID = :proid")
						.setString("begin", begin).setString("end", end).setInteger("proid", proid.get(i)).list().get(0);
				amount.add(x);
				amount_pro.add(x);
			}
			Collections.sort(amount);
			//Lay cac san pham co so ban lon nhat
			for (int i = amount.size()-1; i>=0; i--)
			{
				for(int j = 0; j<=amount_pro.size()-1; j++)
				{
					if(amount_pro.get(j).equals(amount.get(i)))
					{
						Product pro = session.get(Product.class, proid.get(j));
						if(!lstpro.contains(pro)) lstpro.add(pro);
						if(lstpro.size() == 10)
						{
							tx.commit();
							session.close();
							return lstpro;
						}
							
					}
				}
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
		return lstpro;
	}
	public Integer sobanra(Product pro, String begin, String end)
	{
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = null;
		Integer amount = 0;
		try {
			tx = session.beginTransaction();
			amount = (Integer) session.createSQLQuery("select sum(amount) from Statistic where StatisticDate >= :begin and StatisticDate <= :end and ProductID = :proid")
					.setString("begin", begin).setString("end", end).setInteger("proid", pro.getId()).list().get(0);
			
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
		return amount;
	}
	public Integer doanhthu(Product pro, String begin, String end)
	{
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = null;
		Integer amount = 0;
		try {
			tx = session.beginTransaction();
			amount = (Integer) session.createSQLQuery("select sum(Revenue) from Statistic where StatisticDate >= :begin and StatisticDate <= :end and ProductID = :proid")
					.setString("begin", begin).setString("end", end).setInteger("proid", pro.getId()).list().get(0);
			
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
		return amount;
	}
	public Integer sohoadon(Product pro, String begin, String end)
	{
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = null;
		Integer amount = 0;
		try {
			tx = session.beginTransaction();
			amount = (Integer) session.createSQLQuery("select count(b.ID) from Bill b inner join BillDetail bd on b.ID = bd.BillID "
					+ "where b.Date >=:begin and b.Date <= :end and bd.ProductID = :proid")
					.setString("begin", begin).setString("end", end).setInteger("proid", pro.getId()).list().get(0);
			
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
		return amount;
	}
}

