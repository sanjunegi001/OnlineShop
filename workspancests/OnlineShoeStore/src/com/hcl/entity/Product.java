package com.hcl.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.mysql.jdbc.Blob;
/*
 * This is the javadoc comment for the class. 
 * This class is entity class for Product
 * @version 1.01 22/03/2016
*/
@Entity
@Table(name="PRODUCT")
public class Product {

	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	@Column(name="PRODUCT_ID", length=10)
	private String productId;
	@Column(name="PRODUCT_NAME", length=10)
	private String productName;
	@Column(name="PRODUCT_BRAND", length=30)
	private String productBrand;
	@Column(name="PRODUCT_PICTURE",length=5)
	private String productPicture;
	@Column(name="PRODUCT_STOCK",length=3)
	private Integer productStock;
	@Column(name="PRODUCT_PRICE",length=4)
	private Double productPrice;
	@Column(name="PRODUCT_CATEGORY")
	private String productCategory;
	@Column(name="PRODUCT_DESCRIPTION",length=100)
	private String productDescription;
	
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getProductCategory() {
		return productCategory;
	}
	public void setProductCategory(String productCategory) {
		this.productCategory = productCategory;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getProductBrand() {
		return productBrand;
	}
	public void setProductBrand(String productBrand) {
		this.productBrand = productBrand;
	}
	public String getProductPicture() {
		return productPicture;
	}
	public void setProductPicture(String productPicture) {
		this.productPicture = productPicture;
	}
	public Integer getProductStock() {
		return productStock;
	}
	public void setProductStock(Integer productStock) {
		this.productStock = productStock;
	}
	public Double getProductPrice() {
		return productPrice;
	}
	public void setProductPrice(Double productPrice) {
		this.productPrice = productPrice;
	}
	public String getProductDescription() {
		return productDescription;
	}
	public void setProductDescription(String productDescription) {
		this.productDescription = productDescription;
	}
	
	
	
}
