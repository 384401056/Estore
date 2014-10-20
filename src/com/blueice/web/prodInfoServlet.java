package com.blueice.web;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.blueice.domain.Product;
import com.blueice.factory.BasicFactory;
import com.blueice.service.ProductService;

public class prodInfoServlet extends HttpServlet {


	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ProductService service = BasicFactory.getFactory().getService(ProductService.class);
		
		//1.获取商品id.
		String prodId = request.getParameter("id");
		
		//2.调用 service 中的方法查找出商品。
		Product prod = service.findProdById(prodId);

		if(prod==null){
			throw new RuntimeException("商品没有找到。");
		}
		
		//3.请求转发向商品信息页面。
		request.setAttribute("prod", prod);
		request.getRequestDispatcher("/prodInfo.jsp").forward(request, response);
		
	}

	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		this.doGet(request, response);
	}

}
