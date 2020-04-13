package com.shop.Admin;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ValueChangeEvent;

import com.shop.Admin.DAO.ImportDAO;
import com.shop.Admin.DAO.ProductDAO;
import com.shop.models.ImportProduct;
import com.shop.models.Product;

@ManagedBean(name="importBean")
@SessionScoped
public class ImportBean implements Serializable {
	
	private ImportDAO impDAO = new ImportDAO();
	private List<ImportProduct> listImport;
	private ImportProduct newimport = new ImportProduct();
	private Integer idpro;
	private Integer idproducer=1;
	private Integer idcat=1;
	private Date date;
	private String dateString;
	public String getDateString() {
		return dateString;
	}
	public void setDateString(String dateString) {
		this.dateString = dateString;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public ImportDAO getImpDAO() {
		return impDAO;
	}
	public void setImpDAO(ImportDAO impDAO) {
		this.impDAO = impDAO;
	}
	public List<ImportProduct> getListImport() {
		return listImport;
	}
	public void setListImport(List<ImportProduct> listImport) {
		this.listImport = listImport;
	}
	public ImportProduct getNewimport() {
		return newimport;
	}
	public void setNewimport(ImportProduct newimport) {
		this.newimport = newimport;
	}
	public Integer getIdpro() {
		return idpro;
	}
	public void setIdpro(Integer idpro) {
		this.idpro = idpro;
	}
	public Integer getIdproducer() {
		return idproducer;
	}
	public void setIdproducer(Integer idproducer) {
		this.idproducer = idproducer;
	}
	public Integer getIdcat() {
		return idcat;
	}
	public void setIdcat(Integer idcat) {
		this.idcat = idcat;
	}
	public List<ImportProduct> getImportProducts()
	{
		return impDAO.getListImportProduct();
	}
	public String addImport(ImportProduct imp)
	{
		imp.setProduct(new ProductDAO().getProductbyID(idpro));
		imp.setSoldNumber(0);
		imp.setImportDate(new Date());
		impDAO.addImportProduct(imp);
		return "index.xhtml";
	}
	public Integer tong(ImportProduct imp)
	{
		return imp.getPrice()*imp.getQuantity();
	}
	public Integer total(Integer price, Integer count)
	{
		return price*count;
	}
	public void updateQuantity(ValueChangeEvent event)
	{
		newimport.setQuantity((Integer)event.getNewValue()); 
	}
	public void updatePrice(ValueChangeEvent event)
	{
		newimport.setPrice((Integer)event.getNewValue()); 
	}
	public Map<String, Integer> getDropdownProduct() {
		List<Product> products = new ProductDAO().getListProduct(idproducer,idcat);
		Map<String, Integer> dropdown = new HashMap<String, Integer>();
		for(Product p : products)
		{
			dropdown.put(p.getName(), p.getId());
		}
		return dropdown;
	}
	public List<ImportProduct> getImportProductbyDate(String dateString)
	{
		//setDate(date);
		Date date1 = new Date();
		try {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			date1 = format.parse(dateString);
		}
		catch (Exception e) {
			// TODO: handle exception
		}

		return impDAO.getImportProductbyDate(date1);
	}
	public Integer totalImport(String dateString)
	{
		List<ImportProduct> lst = getImportProductbyDate(dateString);
		Integer total = 0;
		for (ImportProduct i : lst)
		{
			total += i.getPrice()*i.getQuantity();
		}
		return total;
	}
	public String detail()
	{
		return "detail.xhtml";
	}
}
