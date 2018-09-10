package com.example.jpetstore.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.example.jpetstore.domain.Product;

public interface ProductDao {

	List<Product> getProductListByCategory(String categoryId) throws DataAccessException;

	Product getProduct(String productId) throws DataAccessException;

	//�߰�
	List<String> productNameList(String categoryId) throws DataAccessException;
	
	public String getProductIdByName(String name) throws DataAccessException;

}
