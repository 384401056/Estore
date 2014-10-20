package com.blueice.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.dbutils.DbUtils;

import com.blueice.domain.Order;
import com.blueice.domain.OrderItem;
import com.blueice.domain.Product;
import com.blueice.domain.User;
import com.blueice.factory.BasicFactory;
import com.blueice.service.OrderService;
import com.blueice.utils.DaoUtils;

public class addOrderServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		OrderService service = BasicFactory.getFactory().getService(OrderService.class);

		Order order = new Order();

		try {
			// 1.通过提参数获取收货地址。
			BeanUtils.populate(order, request.getParameterMap());
			
			// 2.生成订单编号。
			order.setId(UUID.randomUUID().toString());
			
			// 3.生成订单状态。
			order.setPaystate(0);

			// 4.获取用户id 
			User user = (User) request.getSession().getAttribute("user");
			order.setUser_id(user.getId());
			
			// 5.获取定单总金额。以及定单项list
			double money = 0;
			List<OrderItem> list = new ArrayList<OrderItem>();
			
			Map<Product, Integer> carMap = (Map<Product, Integer>) request.getSession().getAttribute("carMap");
			
			for(Map.Entry<Product, Integer> entry:carMap.entrySet()) {
				
				money += entry.getKey().getPrice() * entry.getValue();
				
				OrderItem item = new OrderItem();
				item.setOrder_id(order.getId());
				item.setProduct_id(entry.getKey().getId());
				item.setBuynum(entry.getValue());
				list.add(item);
			}
			
			order.setMoney(money);
			order.setList(list);
			
			// 6.调用service方法增加订单。
			service.addOrder(order);
			
			// 7.清空购物车。
			carMap.clear();
			
			// 8.重定向到主页。
			response.getWriter().write("订单生成成功，3秒回到主页...");
			response.setHeader("Refresh", "3;url="+request.getContextPath()+"/index.jsp");
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doGet(request, response);
	}

}
