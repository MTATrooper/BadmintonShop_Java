package com.shop.Admin;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;


import com.shop.Admin.DAO.HomeDAO;
import com.shop.models.Product;

@ManagedBean(name="homeBean")
@SessionScoped
public class HomeBean implements Serializable {
	private HomeDAO homeDAO = new HomeDAO();
	private String begin = "2019-01-01";
	private String end = "2019-01-01";
	
	public HomeDAO getHomeDAO() {
		return homeDAO;
	}
	
	public void setHomeDAO(HomeDAO homeDAO) {
		this.homeDAO = homeDAO;
	}
	
	public String getBegin() {
		return begin;
	}
	
	public void setBegin(String begin) {
		this.begin = begin;
	}
	
	public String getEnd() {
		return end;
	}
	
	public void setEnd(String end) {
		this.end = end;
	}
	
	public Integer getRevenueMonth(Integer month)
	{
		return homeDAO.getRevenueMonth(month);
	}
	
	public Integer getBenefitMonth(Integer month)
	{
		return homeDAO.getBenefitMonth(month);
	}
	
	public List<Product> sanphambanchay()
	{
		return homeDAO.sanphambanchay(begin, end);
	}
	
	public Integer sobanra(Product pro)
	{
		return homeDAO.sobanra(pro, begin, end);
	}
	
	public Integer doanhthu(Product pro)
	{
		return homeDAO.doanhthu(pro, begin, end);
	}
	
	public Integer sohoadon(Product pro)
	{
		return homeDAO.sohoadon(pro, begin, end);
	}
}
