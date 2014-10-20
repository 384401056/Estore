package com.blueice.service;

import java.util.List;
import java.util.UUID;

import com.blueice.dao.ProductDao;
import com.blueice.domain.Product;
import com.blueice.factory.BasicFactory;

public class ProductServiceImpl implements ProductService {

	
	ProductDao dao = BasicFactory.getFactory().getDao(ProductDao.class);
	
	@Override
	public void addProduct(Product product) {
		
		//生成商品的id.
		product.setId(UUID.randomUUID().toString());
		
		//添加商品。
		dao.addProduct(product);
		
	}

	@Override
	public List<Product> findAllProd() {
		return dao.findAllProd();
	}

	@Override
	public Product findProdById(String prodId) {
		return dao.findProdById(prodId);
	}

}
