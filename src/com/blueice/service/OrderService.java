package com.blueice.service;

import java.util.List;

import com.blueice.annotation.Tran;
import com.blueice.domain.Order;
import com.blueice.domain.OrderListForm;

public interface OrderService extends Service {

	/**
	 * 添加订单. 
	 * @param order 订单Bean
	 */
	@Tran //使用事务管理。
	void addOrder(Order order);

	/**
	 * 通过用户id,获取订单列表。
	 * @param id 用户id
	 * @return List<OrderListForm>
	 */
	List<OrderListForm> findOrders(int id);

	/**
	 * 删除未支付订单。
	 * @param order_id 订单id
	 */
	@Tran //要进行事务管理。
	void delOrderById(String order_id);

}
