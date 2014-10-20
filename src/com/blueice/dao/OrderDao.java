package com.blueice.dao;

import java.sql.SQLException;
import java.util.List;

import com.blueice.domain.Order;
import com.blueice.domain.OrderItem;

public interface OrderDao extends Dao {

	/**
	 * 添加订单到order表中
	 * @param order Bean
	 * @throws SQLException 
	 */
	void addOrder(Order order) throws SQLException;

	/**
	 * 添加订单项到orderItem表中。
	 * @param item orderItem Bean
	 * @throws SQLException 
	 */
	void addOrderItem(OrderItem item) throws SQLException;

	
	/**
	 * 根据用户id查找order。
	 * @param id 用户
	 * @return
	 */
	List<Order> findOrderByUserId(int id);

	/**
	 * 根据订单id查找所有订单项。
	 * @param id 订单id
	 * @return
	 */
	List<OrderItem> findOrderItems(String id);

	/**
	 * 删除订单项。
	 * @param order_id 订单id
	 */
	void delOrderItemById(String order_id);

	/**
	 * 删除订单。
	 * @param order_id
	 */
	void delOrderById(String order_id);

}
