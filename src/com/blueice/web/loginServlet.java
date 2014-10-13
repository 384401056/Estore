package com.blueice.web;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.blueice.domain.User;
import com.blueice.factory.BasicFactory;
import com.blueice.service.UserService;
import com.blueice.utils.MD5Utils;

public class loginServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		UserService service = BasicFactory.getFactory().getInstance(UserService.class);
		
		//1.获取用户名和密码。
		String userName = request.getParameter("username");
		String password = MD5Utils.md5(request.getParameter("password"));
		
		//2.调用Service中的根据用户名和密码查找用户。
		User user = service.getUserByNameAndPsw(userName,password);
		
		if(user==null){
			request.setAttribute("msg", "用户名密码不正确。");
			request.getRequestDispatcher("/login.jsp").forward(request, response);
			return;
		}
		
		//3.检查用户激活状态。 
		
		if(user.getState()==0){
			request.setAttribute("msg", "用户还未激活。");
			request.getRequestDispatcher("/login.jsp").forward(request, response);
			return;
		}
		
		
		//4.登陆用户并且重定向到主页.
		request.getSession().setAttribute("user", user);
		response.sendRedirect(request.getContextPath()+"/index.jsp");
		
		
	}

	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		this.doGet(request, response);
	}

}
