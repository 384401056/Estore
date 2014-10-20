package com.blueice.dao;

import java.sql.SQLException;
import java.util.List;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import com.blueice.domain.Product;
import com.blueice.utils.DaoUtils;
import com.blueice.utils.TransactionManager;

public class ProductDaoImpl implements ProductDao {

	
	@Override
	public void addProduct(Product product) {
		String sql = "INSERT INTO products VALUES(?,?,?,?,?,?,?)";
		try {
			
			QueryRunner runner = new QueryRunner(TransactionManager.getSource());
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
			
			QueryRunner runner = new QueryRunner(TransactionManager.getSource());
			return runner.query(sql,new BeanListHandler<Product>(Product.class));
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	
	@Override
	public Product findProdById(String id) {
		String sql = "SELECT * FROM products WHERE id = ?";
		try{
			QueryRunner runner = new QueryRunner(TransactionManager.getSource());
			return runner.query(sql,new BeanHandler<Product>(Product.class),id);
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}


	@Override
	public void delProdNum(String product_id, int buynum) throws SQLException {
		String sql = "UPDATE products set pnum = pnum-? WHERE id = ? and pnum-? >= 0";
		QueryRunner runner = new QueryRunner(TransactionManager.getSource());
		int count = runner.update(sql, buynum,product_id,buynum);
		
		if(count<=0){
			throw new SQLException("库存不足...请选择其它商品。");
		}
	}


	@Override
	public void addProdNum(String product_id, int buynum) {
		try {
			
		String sql = "UPDATE products set pnum = pnum+? WHERE id = ?";
		QueryRunner runner = new QueryRunner(TransactionManager.getSource());
		int count = runner.update(sql, buynum,product_id);
		}catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}

	}


}
