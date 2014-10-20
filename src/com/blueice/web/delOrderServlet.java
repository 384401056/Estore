package com.blueice.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.blueice.domain.OrderItem;
import com.blueice.factory.BasicFactory;
import com.blueice.service.OrderService;

public class delOrderServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		OrderService service = BasicFactory.getFactory().getService(OrderService.class);
		
		//1.获取订单id。
		String order_id = request.getParameter("id");

		//2.调用Service中的方法删除订单。
		service.delOrderById(order_id);
		
		//3.返回订单页面。
		response.getWriter().write("订单删除成功，3秒回到订单列表...");
		response.setHeader("Refresh", "3;url="+request.getContextPath()+"/orderListServlet");
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doGet(request, response);
	}

}
