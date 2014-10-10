package com.blueice.web;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;

import com.blueice.domain.User;
import com.blueice.factory.BasicFactory;
import com.blueice.service.UserService;

public class registServlet extends HttpServlet {


	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		
		UserService Service = BasicFactory.getFactory().getInstance(UserService.class);
		
		try {
		
			//1.校验验证码
			String valistr1 = request.getParameter("valistr");
			String valistr2 = (String) request.getSession().getAttribute("valistr");
			
			if(!valistr1.equals(valistr2)){
				request.setAttribute("msg", "验证码不正确。");
				request.getRequestDispatcher("/regist.jsp").forward(request, response);
				return;
			}
			
			
		    //2.封装数据校验数据。
			User user = new User();
			BeanUtils.populate(user, request.getParameterMap());
			
			//3.调用Service添加用户。
			Service.registUser(user);
			
			//4.重定向到主页。
			response.getWriter().write("注册成功，请到邮箱中激活...3秒后回到主页。");
			response.setHeader("Refresh", "3;url="+request.getContextPath()+"/index.jsp");
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
		
		
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		this.doGet(request, response);
	}

}
