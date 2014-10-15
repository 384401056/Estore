package com.blueice.service;

import java.util.List;

import com.blueice.domain.Product;

public interface ProductService {

	/**
	 * 添加商品
	 * @param product 商品Bean.
	 */
	void addProduct(Product product);

	/**
	 * 查找所有商品。
	 * @return
	 */
	List<Product> findAllProd();

	
	/**
	 * 根据id查找商品
	 * @param prodId
	 * @return
	 */
	Product findProdById(String prodId);

}
