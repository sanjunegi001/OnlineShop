package com.hcl.service;

import java.util.List;

import com.hcl.model.BrandTO;
import com.hcl.model.ProductTO;
/*
 * This is the javadoc comment for the interface. 
 * This interface  for searchService
 * @version 1.01 22/03/2016
*/
public interface SearchService {
	public List<ProductTO> getAllImage();
	public List<ProductTO> getParticularProduct(String productBrand, Double productPrice,String productCategory);
	public List<BrandTO> getAllBrand();
	public ProductTO getProductById(String productId);
	public List<ProductTO> getProductByName(String productName);
	public List<ProductTO> getMenProduct();
	public List<ProductTO> getWomenProduct();
}
