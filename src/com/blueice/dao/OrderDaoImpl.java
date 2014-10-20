package com.blueice.dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.blueice.domain.Order;
import com.blueice.domain.OrderItem;
import com.blueice.utils.TransactionManager;
import com.sun.org.apache.bcel.internal.generic.NEW;

public class OrderDaoImpl implements OrderDao {

	@Override
	public void addOrder(Order order) throws SQLException {

		String sql = "INSERT INTO orders VALUES(?,?,?,?,null,?)";
		QueryRunner runner = new QueryRunner(TransactionManager.getSource());
		runner.update(sql, order.getId(), order.getMoney(),order.getReceiverinfo(), order.getPaystate(),order.getUser_id());

	}

	@Override
	public void addOrderItem(OrderItem item) throws SQLException {

		String sql = "INSERT INTO orderitem VALUES(?,?,?)";
		QueryRunner runner = new QueryRunner(TransactionManager.getSource());
		runner.update(sql, item.getOrder_id(), item.getProduct_id(),item.getBuynum());

	}

	@Override
	public List<Order> findOrderByUserId(int id) {
		String sql = "SELECT * FROM orders WHERE user_id = ?";
		
		try {
			
			QueryRunner runner = new QueryRunner(TransactionManager.getSource());
			return runner.query(sql,new BeanListHandler<Order>(Order.class),id);
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public List<OrderItem> findOrderItems(String id) {
		
		String sql = "SELECT * FROM orderitem WHERE order_id = ?";
		try {
			
			QueryRunner runner = new QueryRunner(TransactionManager.getSource());
			return runner.query(sql,new BeanListHandler<OrderItem>(OrderItem.class),id);
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public void delOrderItemById(String order_id) {
		String sql = "DELETE FROM orderitem WHERE order_id = ?";
		try {
			
			QueryRunner runner = new QueryRunner(TransactionManager.getSource());
			runner.update(sql,order_id);
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public void delOrderById(String order_id) {
		String sql = "DELETE FROM orders WHERE id = ?";
		try {
			
			QueryRunner runner = new QueryRunner(TransactionManager.getSource());
			runner.update(sql,order_id);
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

}
