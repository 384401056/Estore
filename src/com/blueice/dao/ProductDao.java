package com.blueice.dao;

import java.sql.SQLException;
import java.util.List;

import com.blueice.domain.Product;

public interface ProductDao extends Dao {

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

	/**
	 * 扣除商品数据
	 * @param product_id 商品id
	 * @param buynum  商品数量
	 * @throws SQLException 
	 */
	void delProdNum(String product_id, int buynum) throws SQLException;

	/**
	 * 加回商品数据
	 * @param product_id 商品id
	 * @param buynum   商品数量
	 */
	void addProdNum(String product_id, int buynum);

}
