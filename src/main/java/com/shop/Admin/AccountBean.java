package com.shop.Admin;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import com.shop.Admin.DAO.AccountDAO;
import com.shop.models.Account;

@ManagedBean(name="accountBean")
@SessionScoped
public class AccountBean implements Serializable {
	private AccountDAO accDAO = new AccountDAO();
	private List<Account> listAccount = new ArrayList<Account>();
	private Account newAccount = new Account();
	private Account currentAccount = new Account();
	public AccountDAO getAccDAO() {
		return accDAO;
	}
	public void setAccDAO(AccountDAO accDAO) {
		this.accDAO = accDAO;
	}
	public List<Account> getListAccount() {
		return listAccount;
	}
	public void setListAccount(List<Account> listAccount) {
		this.listAccount = listAccount;
	}
	public Account getNewAccount() {
		return newAccount;
	}
	public void setNewAccount(Account newAccount) {
		this.newAccount = newAccount;
	}
	public Account getCurrentAccount() {
		return currentAccount;
	}
	public void setCurrentAccount(Account currentAccount) {
		this.currentAccount = currentAccount;
	}
	public List<Account> getAccounts()
	{
		listAccount = accDAO.getListAccount();
		return listAccount;
	}
	public String addAccount(Account acc)
	{
		accDAO.addAccount(acc);
		return "index.xhtml";
	}
	public String changeAccount(Integer id)
	{
		Account acc =  accDAO.getAccountbyID(id);
		currentAccount = acc;
		return "edit.xhtml?faces-redirect=true&includeViewParams=true";
	}
	public String detail(Integer id)
	{
		changeAccount(id);
		return "detail.xhtml?faces-redirect=true&includeViewParams=true";
	}
	public void update(Integer id, Integer active)
	{
		Account acc = accDAO.getAccountbyID(id);
		acc.setActive(active);
		accDAO.updateAccount(acc);
	}
}
