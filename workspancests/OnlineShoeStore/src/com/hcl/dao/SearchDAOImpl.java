package com.hcl.dao;

import java.util.ArrayList;
import java.util.List;








import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hcl.entity.Brand;
import com.hcl.entity.Product;
import com.hcl.model.BrandTO;
import com.hcl.model.ProductTO;
/*
 * This is the javadoc comment for the class. 
 * This class demonstrate the  search  DAO impl
 * @version 1.01 22/03/2016
*/
@Repository
public class SearchDAOImpl implements SearchDAO {

	@Autowired
	private SessionFactory sessionFactory;
	
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	@Override
	public List<ProductTO> getAllImage() {
		List<ProductTO> product=new ArrayList<ProductTO>();
		List<Product> pr=sessionFactory.getCurrentSession().createQuery("from Product p").list();
		for (Product product2 : pr) {
			ProductTO prt=new ProductTO();
			prt.setProductId(product2.getProductId());
			prt.setProductName(product2.getProductName());
			prt.setProductBrand(product2.getProductBrand());
			prt.setProductDescription(product2.getProductDescription());
			prt.setProductPicture(product2.getProductPicture());
			prt.setProductPrice(product2.getProductPrice());
			prt.setProductCategory(product2.getProductCategory());
			prt.setProductStock(product2.getProductStock());
			product.add(prt);
		}
		return product;
	}
	@Override
	public List<ProductTO> getParticularProduct(String productBrand, Double productPrice,String productCategory) {
		List<ProductTO> product=new ArrayList<ProductTO>();
		Query q=sessionFactory.getCurrentSession().createQuery(" from Product  where productBrand= :productBrand and productPrice>= :productPrice and productCategory= :productCategory");
		q.setParameter("productBrand", productBrand);
		q.setParameter("productPrice", productPrice);
		q.setParameter("productCategory", productCategory);
		List<Product> pr=q.list();
		for (Product product2 : pr) {
			ProductTO prt=new ProductTO();
			prt.setProductId(product2.getProductId());
			prt.setProductName(product2.getProductName());
			prt.setProductBrand(product2.getProductBrand());
			prt.setProductDescription(product2.getProductDescription());
			prt.setProductPicture(product2.getProductPicture());
			prt.setProductPrice(product2.getProductPrice());
			prt.setProductCategory(product2.getProductCategory());
			prt.setProductStock(product2.getProductStock());
			product.add(prt);
		}
		return product;
	}
	@Override
	public List<BrandTO> getAllBrand() {
		List<Brand> blist=sessionFactory.getCurrentSession().createQuery("from Brand").list();
		List<BrandTO> blistTO=new ArrayList<BrandTO>();
		for (Brand brand : blist) {
			BrandTO brandTO= new BrandTO();
			brandTO.setBrandId(brand.getBrandId());
			brandTO.setBrandName(brand.getBrandName());
			blistTO.add(brandTO);
		}
		return blistTO;
	}
	@Override
	public ProductTO getProductById(String productId) {
		Product product=(Product) sessionFactory.getCurrentSession().get(Product.class, productId);
		ProductTO productTO =new ProductTO();
		System.out.println("Id"+product.getProductId());
		productTO.setProductId(product.getProductId());
		productTO.setProductName(product.getProductName());
		productTO.setProductBrand(product.getProductBrand());
		productTO.setProductDescription(product.getProductDescription());
		productTO.setProductPicture(product.getProductPicture());
		productTO.setProductPrice(product.getProductPrice());
		productTO.setProductCategory(product.getProductCategory());
		productTO.setProductStock(product.getProductStock());
		return productTO ;
	}
	@Override
	public List<ProductTO> getProductByName(String productBrand) {
		List<ProductTO> productList=new ArrayList<ProductTO>();
		Query q=sessionFactory.getCurrentSession().createQuery("from Product  where productBrand= :productBrand");
		q.setParameter("productBrand", productBrand);
		List<Product> pr=q.list();
		for(Product product : pr){
		ProductTO productTO =new ProductTO();
		productTO.setProductId(product.getProductId());
		productTO.setProductName(product.getProductName());
		productTO.setProductBrand(product.getProductBrand());
		productTO.setProductDescription(product.getProductDescription());
		productTO.setProductPicture(product.getProductPicture());
		productTO.setProductPrice(product.getProductPrice());
		productTO.setProductCategory(product.getProductCategory());
		productTO.setProductStock(product.getProductStock());
		productList.add(productTO);
		}
		return productList ;
	}
	@Override
	public List<ProductTO> getMenProduct() {
		List<ProductTO> productList=new ArrayList<ProductTO>();
		Query q=sessionFactory.getCurrentSession().createQuery("from Product  where productCategory='M'");
		List<Product> pr=q.list();
		for(Product product : pr){
		ProductTO productTO =new ProductTO();
		productTO.setProductId(product.getProductId());
		productTO.setProductName(product.getProductName());
		productTO.setProductBrand(product.getProductBrand());
		productTO.setProductDescription(product.getProductDescription());
		productTO.setProductPicture(product.getProductPicture());
		productTO.setProductPrice(product.getProductPrice());
		productTO.setProductCategory(product.getProductCategory());
		productTO.setProductStock(product.getProductStock());
		productList.add(productTO);
		}
		return productList ;
	}
	@Override
	public List<ProductTO> getWomenProduct() {
		List<ProductTO> productList=new ArrayList<ProductTO>();
		Query q=sessionFactory.getCurrentSession().createQuery("from Product  where productCategory='F'");
		List<Product> pr=q.list();
		for(Product product : pr){
		ProductTO productTO =new ProductTO();
		productTO.setProductId(product.getProductId());
		productTO.setProductName(product.getProductName());
		productTO.setProductBrand(product.getProductBrand());
		productTO.setProductDescription(product.getProductDescription());
		productTO.setProductPicture(product.getProductPicture());
		productTO.setProductPrice(product.getProductPrice());
		productTO.setProductCategory(product.getProductCategory());
		productTO.setProductStock(product.getProductStock());
		productList.add(productTO);
		}
		return productList ;
	}

}
