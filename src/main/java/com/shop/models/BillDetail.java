package com.shop.models;
// Generated Jun 20, 2019, 3:42:52 PM by Hibernate Tools 4.3.5.Final

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * BillDetail generated by hbm2java
 */
@Entity
@Table(name = "BillDetail", schema = "dbo", catalog = "ShopBadminton_Online")
public class BillDetail implements java.io.Serializable {

	private Integer id;
	private Bill bill;
	private Product product;
	private int count;
	private int price;
	private Set<ExportProduct> exportProducts = new HashSet<ExportProduct>(0);

	public BillDetail() {
	}

	public BillDetail(Bill bill, Product product, int count, int price) {
		this.bill = bill;
		this.product = product;
		this.count = count;
		this.price = price;
	}

	public BillDetail(Bill bill, Product product, int count, int price, Set<ExportProduct> exportProducts) {
		this.bill = bill;
		this.product = product;
		this.count = count;
		this.price = price;
		this.exportProducts = exportProducts;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "ID", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "BillID", nullable = false)
	public Bill getBill() {
		return this.bill;
	}

	public void setBill(Bill bill) {
		this.bill = bill;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ProductID", nullable = false)
	public Product getProduct() {
		return this.product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	@Column(name = "Count", nullable = false)
	public int getCount() {
		return this.count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	@Column(name = "Price", nullable = false)
	public int getPrice() {
		return this.price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "billDetail")
	public Set<ExportProduct> getExportProducts() {
		return this.exportProducts;
	}

	public void setExportProducts(Set<ExportProduct> exportProducts) {
		this.exportProducts = exportProducts;
	}

}
