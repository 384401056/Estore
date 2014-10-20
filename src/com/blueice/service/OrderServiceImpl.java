package com.blueice.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;

import com.blueice.dao.OrderDao;
import com.blueice.dao.ProductDao;
import com.blueice.dao.UserDao;
import com.blueice.domain.Order;
import com.blueice.domain.OrderItem;
import com.blueice.domain.OrderListForm;
import com.blueice.domain.Product;
import com.blueice.domain.User;
import com.blueice.factory.BasicFactory;

public class OrderServiceImpl implements OrderService {

	OrderDao orderDao = BasicFactory.getFactory().getDao(OrderDao.class);
	ProductDao productDao = BasicFactory.getFactory().getDao(ProductDao.class);
	UserDao userDao = BasicFactory.getFactory().getDao(UserDao.class);

	@Override
	public void addOrder(Order order) {

		try {

			// 1.添加订单
			orderDao.addOrder(order);

			// 2.添加订单项，扣除对应商品的数量
			for (OrderItem item : order.getList()) {

				orderDao.addOrderItem(item);

				productDao.delProdNum(item.getProduct_id(), item.getBuynum());
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public List<OrderListForm> findOrders(int id) {

		List<OrderListForm> orderListForms = new ArrayList<OrderListForm>();

		try {
			List<Order> orderList = orderDao.findOrderByUserId(id);

			for (Order order : orderList) {

				OrderListForm olf = new OrderListForm();
				BeanUtils.copyProperties(olf, order);
				
				User user = userDao.findUserById(id);
				
				olf.setUserName(user.getUsername());
				
				Map<Product, Integer> map = new HashMap<Product, Integer>();
				
				List<OrderItem> orderItems = orderDao.findOrderItems(olf.getId());
				
				for(OrderItem item:orderItems){
					
					Product prod = productDao.findProdById(item.getProduct_id());
					map.put(prod, item.getBuynum());
					
				}
				
				olf.setProdMap(map);
				
				orderListForms.add(olf);
			}
			
			return orderListForms;
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}

	}

	@Override
	public void delOrderById(String order_id) {
		
		OrderDao orderDao = BasicFactory.getFactory().getDao(OrderDao.class);
		ProductDao prodDao = BasicFactory.getFactory().getDao(ProductDao.class);
		
		//1.根据id获取订单项。
		List<OrderItem> list = orderDao.findOrderItems(order_id);
		
		//2.根据订单项获得商品，并加回购买数据量。
		for(OrderItem item:list){
			
			prodDao.addProdNum(item.getProduct_id(),item.getBuynum());
		}
		
		
		//3.删除订单项。
		orderDao.delOrderItemById(order_id);
		
		//4.删除订单。
		orderDao.delOrderById(order_id);
		
	}

}
































