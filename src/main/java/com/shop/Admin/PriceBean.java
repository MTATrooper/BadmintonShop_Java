package com.shop.Admin;

import java.io.Serializable;
import java.text.NumberFormat;
import java.util.Date;
import java.util.Locale;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import com.shop.Admin.DAO.PriceDAO;

@ManagedBean(name="priceBean")
@SessionScoped
public class PriceBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public PriceDAO priceDAO = new PriceDAO();
	private Date begindate;
	private Date enddate;
	
	public Date getBegindate() {
		return begindate;
	}

	public void setBegindate(Date begindate) {
		this.begindate = begindate;
	}

	public Date getEnddate() {
		return enddate;
	}

	public void setEnddate(Date enddate) {
		this.enddate = enddate;
	}

	public String getPriceByIdPro(Integer id) {
		Integer price  = priceDAO.getPriceByIdPro(id);
		Locale localeVN = new Locale("vi", "VN");
	    NumberFormat currencyVN = NumberFormat.getCurrencyInstance(localeVN);
	    String str1 = currencyVN.format(price);
		//return priceDAO.getPriceByIdPro(id);
		return str1;
	}
}
