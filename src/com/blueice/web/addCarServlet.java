package com.blueice.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.blueice.domain.Product;
import com.blueice.factory.BasicFactory;
import com.blueice.service.ProductService;

public class addCarServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ProductService service = BasicFactory.getFactory().getInstance(
				ProductService.class);

		// 1.获取商品id.
		String prodId = request.getParameter("id");

		// 2.调用service中的方法获取商品Bean.
		Product prod = service.findProdById(prodId);

		if (prod == null) {
			throw new RuntimeException("没有找到商品.");
		} else {
			Map<Product, Integer> carMap = (Map<Product, Integer>) request.getSession().getAttribute("carMap");
			
			/*
			 * 判断carMap中是否已经有些商品，如果有则购买数在原来基础上加1.
			 * 由于Map的containsKey（）方法是通地对象的Hash值来判断，两个对象是否为同一个对象，所以在Product类中要重载HashCode和equals方法。让这两个方法以商品id来区分不同对象。
			 * 否则，即使是同一个商品也会但作两个商品处理。
			 */
			carMap.put(prod, carMap.containsKey(prod)?carMap.get(prod)+1:1);
		}
		
		//3.重定向到购物车页面进行展示。
		response.sendRedirect(request.getContextPath()+"/car.jsp");
		
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		this.doGet(request, response);
	}

}
