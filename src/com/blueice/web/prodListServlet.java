package com.blueice.web;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.blueice.domain.Product;
import com.blueice.factory.BasicFactory;
import com.blueice.service.ProductService;

public class prodListServlet extends HttpServlet {


	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		ProductService service = BasicFactory.getFactory().getService(ProductService.class);
		
		List<Product> list  = new ArrayList<Product>();
		
		//1.获取商品列表。
		list = service.findAllProd();
		
		//2.将列表存入request域中。
		request.setAttribute("list", list);
		
		//3.请求转发到商品列表页面。
		request.getRequestDispatcher("/prodList.jsp").forward(request, response);
		
	}

	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		this.doGet(request, response);
	}

}
