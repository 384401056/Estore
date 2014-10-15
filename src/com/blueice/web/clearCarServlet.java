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

public class clearCarServlet extends HttpServlet {


	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		//1.请空Session中的carMap.
		Map<Product, Integer> carMap = (Map<Product, Integer>) request.getSession().getAttribute("carMap");
		carMap.clear();
		
		//2.重定向到购物车。
		response.sendRedirect(request.getContextPath()+"/car.jsp");
		
		
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		this.doGet(request, response);
	}

}
