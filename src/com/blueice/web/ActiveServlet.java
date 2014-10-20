package com.blueice.web;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.blueice.domain.User;
import com.blueice.factory.BasicFactory;
import com.blueice.service.UserService;

public class ActiveServlet extends HttpServlet {


	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		UserService service = BasicFactory.getFactory().getService(UserService.class);
		
		//1.获取激活码。
		String activeCode = request.getParameter("activecode");
		
		//2.调用activeUser方法激活用户。
		User user = service.activeUser(activeCode);
		
		//3.登陆用户。
		request.getSession().setAttribute("user", user);

		//4.返回主页。
		response.getWriter().write("激活成功...3秒后回到主页。");
		response.setHeader("Refresh", "3;url="+request.getContextPath()+"/index.jsp");
		
	}

	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		this.doGet(request, response);
	}

}
