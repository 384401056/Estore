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

public class ImgServlet extends HttpServlet {


	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		ProductService service = BasicFactory.getFactory().getService(ProductService.class);
		
//		//1.获取商品图片地址.
//		String prodId = request.getParameter("id");
//		
//		//2.调用service的查找方法，找出该Id的商品
//		Product prod = service.findProdById(prodId);
//		
//		//3.获取商品缩略图.输出图片.
//		String imgurls = prod.getImgurl().substring(0, prod.getImgurl().lastIndexOf("."))
//				+"_s"
//				+prod.getImgurl().substring(prod.getImgurl().lastIndexOf("."));
//		
//		request.getRequestDispatcher(imgurls).forward(request,response);
		
		
		//1.获取商品图片地址.输出图片.
		String imgurl = request.getParameter("imgurl");
		request.getRequestDispatcher(imgurl).forward(request,response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		this.doGet(request, response);
	}

}
