package com.blueice.dao;

import java.util.List;

import com.blueice.domain.Product;

public interface ProductDao {

	/**
	 * 添加商品到数据库。
	 * @param product
	 */
	void addProduct(Product product);

	/**
	 * 查找所有商品.
	 * @return
	 */
	List<Product> findAllProd();

	/**
	 * 根据id查找商品。
	 * @param id  商品id
	 * @return    商品Bean.
	 */
	Product findProdById(String id);

}
