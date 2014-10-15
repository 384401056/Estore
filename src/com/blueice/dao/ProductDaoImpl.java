package com.blueice.dao;

import java.util.List;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import com.blueice.domain.Product;
import com.blueice.utils.DaoUtils;

public class ProductDaoImpl implements ProductDao {

	
	@Override
	public void addProduct(Product product) {
		String sql = "INSERT INTO products VALUES(?,?,?,?,?,?,?)";
		try {
			
			QueryRunner runner = new QueryRunner(DaoUtils.getDataSource());
			runner.update(sql, 
					product.getId(),
					product.getName(),
					product.getPrice(),
					product.getCategory(),
					product.getPnum(),
					product.getImgurl(),
					product.getDescription()
					);
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
	}

	
	@Override
	public List<Product> findAllProd() {
		
		String sql = "SELECT * FROM products";
		try {
			
			QueryRunner runner = new QueryRunner(DaoUtils.getDataSource());
			return runner.query(sql,new BeanListHandler<Product>(Product.class));
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	
	@Override
	public Product findProdById(String id) {
		String sql = "SELECT * FROM products WHERE id = ?";
		try {
			
			QueryRunner runner = new QueryRunner(DaoUtils.getDataSource());
			return runner.query(sql,new BeanHandler<Product>(Product.class),id);
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}


}
