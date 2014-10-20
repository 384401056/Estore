package com.blueice.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.blueice.domain.OrderListForm;
import com.blueice.domain.User;
import com.blueice.factory.BasicFactory;
import com.blueice.service.OrderService;
import com.blueice.service.ProductService;

public class orderListServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		OrderService orderService = BasicFactory.getFactory().getService(OrderService.class);
		
		//1.获取用户id
		User user = (User) request.getSession().getAttribute("user");
		int id = user.getId();
		
		//2.通过用户id得到Order列表。
		List<OrderListForm> list = orderService.findOrders(id);
		
		//3.请求转发到orderList.jsp
		request.setAttribute("list", list);
		request.getRequestDispatcher("/orderList.jsp").forward(request, response);
	
	}

	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		this.doGet(request, response);
	}

}
