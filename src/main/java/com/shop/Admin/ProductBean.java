package com.shop.Admin;


import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.http.Part;


import com.shop.Admin.DAO.CategoryDAO;
import com.shop.Admin.DAO.PriceDAO;
import com.shop.Admin.DAO.ProducerDAO;
import com.shop.Admin.DAO.ProductDAO;
import com.shop.models.Category;
import com.shop.models.Price;
import com.shop.models.Producer;
import com.shop.models.Product;


@ManagedBean(name="productBean")
@SessionScoped
public class ProductBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private ProductDAO productService = new ProductDAO();
	private Product new_product = new Product();
	private Product current_product;
	private List<Product> listProduct;
	private Integer idpro;
	private Integer idcat;
	private Part file;
	private Integer price;
	private String begin="";
	private String end="";
	
	public ProductDAO getProductService() {
		return productService;
	}
	public void setProductService(ProductDAO productService) {
		this.productService = productService;
	}
	public Product getNew_product() {
		return new_product;
	}
	public void setNew_product(Product new_product) {
		this.new_product = new_product;
	}
	public Product getCurrent_product() {
		return current_product;
	}
	public void setCurrent_product(Product current_product) {
		this.current_product = current_product;
	}
	public List<Product> getListProduct() {
		return listProduct;
	}
	public void setListProduct(List<Product> listProduct) {
		this.listProduct = listProduct;
	}
	public Integer getIdpro() {
		return idpro;
	}
	public void setIdpro(Integer idpro) {
		this.idpro = idpro;
	}
	public Integer getIdcat() {
		return idcat;
	}
	public void setIdcat(Integer idcat) {
		this.idcat = idcat;
	}

	public Part getFile() {
		return file;
	}
	public void setFile(Part file) {
		this.file = file;
	}
	
	public Integer getPrice() {
		return price;
	}
	public void setPrice(Integer price) {
		this.price = price;
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
	public List<Product> getProducts()
	{
		listProduct = getProductService().getListProduct();
		return listProduct;
	}
	public List<Product> getListStop()
	{
		return productService.getListStop();
	}
	public List<Product> getListOut()
	{
		return productService.getListOut();
	}
	public String changeCurrentProduct(Integer id)
	{
		Product pro = productService.getProductbyID(id);
		this.current_product = pro;
		idpro = pro.getProducer().getId();
		idcat = pro.getCategory().getId();
		//file = 
		return "edit.xhtml?faces-redirect=true&includeViewParams=true";
	}
	public String Detail(Integer id)
	{
		this.current_product = getProductService().getProductbyID(id);
		return "detail.xhtml?faces-redirect=true&includeViewParams=true";
	}
	public String addProduct()
	{
		ProducerDAO proDAO = new ProducerDAO();
		Producer pro = proDAO.getProducerbyID(idpro);
		getNew_product().setProducer(pro);
		CategoryDAO catDAO =  new CategoryDAO();
		Category cat = catDAO.getCategorybyID(idcat);
		getNew_product().setCategory(cat);
		//upload file
		if(file !=null)
		{
			try {
				InputStream inputStream = file.getInputStream();
				FacesContext context = FacesContext.getCurrentInstance();
				ServletContext servletContext = (ServletContext) context.getExternalContext().getContext();
				String path = servletContext.getRealPath("");
				String workingpath = "E:\\JAVA\\Project\\ShopBadminton_Online\\src\\main\\webapp";
				File outputFile = new File(path + "\\resources\\Data\\images\\" + file.getSubmittedFileName());
				File outputFile2 = new File(workingpath + "\\resources\\Data\\images\\" + file.getSubmittedFileName());
				if(!outputFile.exists())
				{
					OutputStream outputStream = new FileOutputStream(outputFile);
					OutputStream outputStream2 = new FileOutputStream(outputFile2);
					byte[] buffer = new byte[1024];
					int bytesRead = 0;
					while ((bytesRead = inputStream.read(buffer)) != -1) 
					{
						outputStream.write(buffer, 0, bytesRead);
						outputStream2.write(buffer, 0, bytesRead);
					}
					if (outputStream != null) {
						outputStream.close();
					}
					if (outputStream2 != null) {
						outputStream2.close();
					}
				}
				
				if (inputStream != null) {
					inputStream.close();
				}
			}
			catch (Exception e) {
			}
			getNew_product().setImage("/Data/images/" + file.getSubmittedFileName());
		}
		
		getNew_product().setQuantity(0);
		productService.addProduct(new_product);
		// add Price
		try {
			//convert String to Date
			if(!price.equals(""))
			{
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
				Date begindate,enddate;
				if(!begin.equals("")) begindate = format.parse(begin);
				else begindate = null;
				if(!end.equals("")) enddate = format.parse(end);
				else enddate=null;
				//
				Price p = new Price(price,begindate,enddate);
				p.setProduct(new_product);
				addPrice(p);
			}
			
		}
		catch (Exception e) {
		}
		
		setNew_product(new Product());
		setFile(null);
		return "index.xhtml";
	}
	public void addPrice(Price price)
	{
		PriceDAO priDAO = new PriceDAO();
		priDAO.addPrice(price);
	}
	public String update(Product product)
	{
		//product.setActive(2);
		product.setCategory(new CategoryDAO().getCategorybyID(idcat));
		product.setProducer(new ProducerDAO().getProducerbyID(idpro));
		if(file != null)
		{
			try {
				InputStream inputStream = file.getInputStream();
				FacesContext context = FacesContext.getCurrentInstance();
				ServletContext servletContext = (ServletContext) context.getExternalContext().getContext();
				String path = servletContext.getRealPath("");
				String workingpath = "E:\\JAVA\\Project\\ShopBadminton_Online\\src\\main\\webapp";
				File outputFile = new File(path + "\\resources\\Data\\images\\" + file.getSubmittedFileName());
				File outputFile2 = new File(workingpath + "\\resources\\Data\\images\\" + file.getSubmittedFileName());
				if(!outputFile.exists())
				{
					OutputStream outputStream = new FileOutputStream(outputFile);
					OutputStream outputStream2 = new FileOutputStream(outputFile2);
					byte[] buffer = new byte[1024];
					int bytesRead = 0;
					while ((bytesRead = inputStream.read(buffer)) != -1) 
					{
						outputStream.write(buffer, 0, bytesRead);
						outputStream2.write(buffer, 0, bytesRead);
					}
					if (outputStream != null) {
						outputStream.close();
					}
					if (outputStream2 != null) {
						outputStream2.close();
					}
				}
				
				if (inputStream != null) {
					inputStream.close();
				}
			}
			catch (Exception e) {
			}
			product.setImage("/Data/images/" + file.getSubmittedFileName());
		}
		
		productService.updateProduct(product);
		setFile(null);
		return "index.xhtml?faces-redirect=true"; 
	}
	public void delete(Product product)
	{
		changeCurrentProduct(product.getId());
		current_product.setActive(2);
		update(current_product);
	}
	public void resumse(Product product)
	{
		changeCurrentProduct(product.getId());
		current_product.setActive(1);
		update(current_product);
	}
	public Map<String, Integer> getDropdownProduct()
	{
		return productService.getDropdownProduct();
	}
}
