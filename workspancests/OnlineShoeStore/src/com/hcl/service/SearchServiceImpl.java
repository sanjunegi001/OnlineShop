package com.hcl.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hcl.dao.SearchDAO;
import com.hcl.model.BrandTO;
import com.hcl.model.ProductTO;
/*
 * This is the javadoc comment for the class. 
 * This class  for searchService
 * @version 1.01 22/03/2016
*/
@Service
public class SearchServiceImpl implements SearchService {

	@Autowired
	private SearchDAO searchDAO;
	
	
	public SearchDAO getSearchDAO() {
		return searchDAO;
	}

	public void setSearchDAO(SearchDAO searchDAO) {
		this.searchDAO = searchDAO;
	}

	@Override
	@Transactional
	public List<ProductTO> getAllImage() {
		// TODO Auto-generated method stub
		return searchDAO.getAllImage();
	}

	@Override
	@Transactional
	public List<ProductTO> getParticularProduct(String productBrand, Double productPrice,String productCategory) {
		// TODO Auto-generated method stub
		return searchDAO.getParticularProduct(productBrand, productPrice,productCategory);
	}

	@Override
	@Transactional
	public List<BrandTO> getAllBrand() {
		// TODO Auto-generated method stub
		return searchDAO.getAllBrand();
	}

	@Override
	@Transactional
	public ProductTO getProductById(String productId) {
		// TODO Auto-generated method stub
		return searchDAO.getProductById(productId);
	}

	@Override
	@Transactional
	public List<ProductTO> getProductByName(String productName) {
		// TODO Auto-generated method stub
		return searchDAO.getProductByName(productName);
	}

	@Override
	@Transactional
	public List<ProductTO> getMenProduct() {
		// TODO Auto-generated method stub
		return searchDAO.getMenProduct();
	}

	@Override
	@Transactional
	public List<ProductTO> getWomenProduct() {
		// TODO Auto-generated method stub
		return searchDAO.getWomenProduct();
	}

}
