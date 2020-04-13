package com.shop.Admin;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ValueChangeEvent;

import com.shop.Admin.DAO.BillDAO;
import com.shop.models.Bill;
import com.shop.models.BillDetail;

@ManagedBean(name="billBean")
@SessionScoped
public class BillBean implements Serializable {
	private BillDAO billDAO = new BillDAO();
	private Bill bill = new Bill();
	private String status = "";
	private List<Bill> list = new ArrayList<Bill>();
	public BillDAO getBillDAO() {
		return billDAO;
	}
	public void setBillDAO(BillDAO billDAO) {
		this.billDAO = billDAO;
	}
	public Bill getBill() {
		return bill;
	}
	public void setBill(Bill bill) {
		this.bill = bill;
	}

	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public List<Bill> getList() {
		return list;
	}
	public void setList(List<Bill> list) {
		this.list = list;
	}
	public List<Bill> getListWaitProgressBill()
	{
		return billDAO.getListWaitProgressBill();
	}
	public List<Bill> getListProgressedBill()
	{
		return billDAO.getListProgressedBill();
	}
	public List<Bill> getListReceivedBill()
	{
		return billDAO.getListReceivedBill();
	}
	public List<Bill> getListDestroyedBill()
	{
		return billDAO.getListDestroyedBill();
	}
	public void changeBill(Bill bi)
	{
		if(list.isEmpty()) list.add(bi);
		else
		{
			int index = 0;
			for (Bill i : list)
			{
				if(i.getId() == bi.getId())
				{
					list.remove(index);
					return;
				}
				index++;
			}
			list.add(bi);
		}
	}
	public void updateStatus(ValueChangeEvent event)
	{
		status = event.getNewValue().toString();
	}
	public void update()
	{
		for (Bill b : list)
		{
			b.setStatus(status);
			billDAO.updateBill(b);
			if(status.equals("Đã nhận"))
				billDAO.updateBillToReceived(b);
		}
		list = new ArrayList<Bill>();
		//status = "";
	}
	public String detail(Integer id)
	{
		bill = billDAO.getBillById(id);
		return "detail.xhtml";
	}
	public List<BillDetail> getListBillDetail(Integer id)
	{
		return billDAO.getListBillDetail(id);
	}
	public Integer thanhtien(Integer price, Integer count)
	{
		return price*count;
	}
}
