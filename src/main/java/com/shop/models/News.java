package com.shop.models;
// Generated Jun 20, 2019, 3:42:52 PM by Hibernate Tools 4.3.5.Final

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * News generated by hbm2java
 */
@Entity
@Table(name = "News", schema = "dbo", catalog = "ShopBadminton_Online")
public class News implements java.io.Serializable {

	private Integer id;
	private Product product;
	private String title;
	private String content;
	private Date createdDate;
	private int isPublic;

	public News() {
	}

	public News(Product product, String title, String content, Date createdDate, int isPublic) {
		this.product = product;
		this.title = title;
		this.content = content;
		this.createdDate = createdDate;
		this.isPublic = isPublic;
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
	@JoinColumn(name = "ProductID", nullable = false)
	public Product getProduct() {
		return this.product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	@Column(name = "Title", nullable = false)
	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Column(name = "Content", nullable = false)
	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "CreatedDate", nullable = false, length = 10)
	public Date getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	@Column(name = "IsPublic", nullable = false)
	public int getIsPublic() {
		return this.isPublic;
	}

	public void setIsPublic(int isPublic) {
		this.isPublic = isPublic;
	}

}
