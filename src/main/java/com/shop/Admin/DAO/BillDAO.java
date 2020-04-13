package com.shop.Admin.DAO;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.shop.models.Bill;
import com.shop.models.BillDetail;
import com.shop.models.ExportProduct;
import com.shop.models.Product;
import com.shop.models.Statistic;

import HibernateUtil.HibernateUtil;

public class BillDAO {
	public BillDAO()
	{
		super();
	}
	public List<Bill> getListAllBill() {
		Session session = HibernateUtil.getSessionFactory().openSession();
		List<?> lst = new ArrayList<Bill>();
        try{
            
               session.beginTransaction();
               lst = session.createCriteria(Bill.class).list();
               session.getTransaction().commit();
        }
        catch(Exception e){
          e.printStackTrace();
          session.getTransaction().rollback();
        }
            session.close();
            
        return (List<Bill>)lst;
	}
	public List<Bill> getListWaitProgressBill()
	{
		Session session = HibernateUtil.getSessionFactory().openSession();
		List<?> lst = new ArrayList<Bill>();
        try{
            
               session.beginTransaction();
               lst = session.createQuery("from Bill where status = :status").setString("status", "Đang chờ xử lý").list();
               session.getTransaction().commit();
        }
        catch(Exception e){
          e.printStackTrace();
          session.getTransaction().rollback();
        }
            session.close();
            
        return (List<Bill>)lst;
	}
	public List<Bill> getListProgressedBill()
	{
		Session session = HibernateUtil.getSessionFactory().openSession();
		List<?> lst = new ArrayList<Bill>();
        try{
            
               session.beginTransaction();
               lst = session.createQuery("from Bill where status = :status").setString("status", "Đã xử lý").list();
               session.getTransaction().commit();
        }
        catch(Exception e){
          e.printStackTrace();
          session.getTransaction().rollback();
        }
            session.close();
            
        return (List<Bill>)lst;
	}
	public List<Bill> getListReceivedBill()
	{
		Session session = HibernateUtil.getSessionFactory().openSession();
		List<?> lst = new ArrayList<Bill>();
        try{
            
               session.beginTransaction();
               lst = session.createQuery("from Bill where status = :status").setString("status", "Đã nhận").list();
               session.getTransaction().commit();
        }
        catch(Exception e){
          e.printStackTrace();
          session.getTransaction().rollback();
        }
            session.close();
            
        return (List<Bill>)lst;
	}
	public List<Bill> getListDestroyedBill()
	{
		Session session = HibernateUtil.getSessionFactory().openSession();
		List<?> lst = new ArrayList<Bill>();
        try{
               session.beginTransaction();
               lst = session.createQuery("from Bill where status = :status").setString("status", "Đã hủy").list();
               session.getTransaction().commit();
        }
        catch(Exception e){
          e.printStackTrace();
          session.getTransaction().rollback();
        }
            session.close();
            
        return (List<Bill>)lst;
	}

	public Bill getBillById(Integer id)
	{
		Session session = HibernateUtil.getSessionFactory().openSession();
		Bill pro = session.get(Bill.class, id);
		session.close();
		return pro;
	}
	public List<BillDetail> getListBillDetail(Integer id)
	{
		Session session = HibernateUtil.getSessionFactory().openSession();
		List<?> lst = new ArrayList<BillDetail>();
        try{
               session.beginTransaction();
               lst = session.createQuery("from BillDetail bd inner join fetch bd.bill b where b.id = :id").setInteger("id", id).list();
               session.getTransaction().commit();
        }
        catch(Exception e){
          e.printStackTrace();
          session.getTransaction().rollback();
        }
            session.close();
            
        return (List<BillDetail>)lst;
	}
	public void updateBill(Bill bill)
	{
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			Bill bi = session.get(Bill.class, bill.getId());
			bi.setStatus(bill.getStatus());
			session.save(bi);
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
	public void updateBillToReceived(Bill bill)
	{
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			//Lay tat ca chi tiet don hang
			List<BillDetail> listDetail = getListBillDetail(bill.getId());
			for (BillDetail b : listDetail)
			{
				//Voi moi dong don hang lay tat ca cac Export cua dong don hang
				List<ExportProduct> ex = (List<ExportProduct>) session.createQuery("from ExportProduct e inner join fetch e.billDetail b where b.id = :id")
										.setInteger("id", b.getId()).list();
				Integer revenue = 0, benefit = 0;
				//Voi moi Export tinh doanh thu va loi nhuan
				for(ExportProduct e : ex)
				{
					revenue += e.getCount() * e.getPriceExport();
					benefit += e.getCount() * (e.getPriceExport()-e.getPriceImport());
				}
				//Luu vao CSDL
				Statistic s = new Statistic();
				s.setProduct(b.getProduct());
				s.setStatisticDate(bill.getDate());
				s.setRevenue(revenue);
				s.setBenefit(benefit);
				s.setAmount(b.getCount());
				session.save(s);
			}
			//session.save(bi);
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
