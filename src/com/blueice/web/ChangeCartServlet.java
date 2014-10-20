package com.blueice.web;

import java.io.IOException;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.blueice.domain.Product;
import com.blueice.factory.BasicFactory;
import com.blueice.service.ProductService;

public class ChangeCartServlet extends HttpServlet {


	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		ProductService service = BasicFactory.getFactory().getService(ProductService.class);
		
		//1.获取商品id
		String prodId = request.getParameter("id");
		int buyNum = Integer.parseInt(request.getParameter("buynum"));
		
		//2.通过service中的方法查找出商品。
		Product prod = service.findProdById(prodId);
		
		//3.从Session中的carMap中删除此商品
		Map<Product, Integer> carMap = (Map<Product, Integer>) request.getSession().getAttribute("carMap");
		
		carMap.put(prod, buyNum);
		
		//4.重定向到购物车。
		response.sendRedirect(request.getContextPath()+"/car.jsp");
		
		
	}

	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		this.doGet(request, response);
	}

}
