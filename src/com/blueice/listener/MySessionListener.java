package com.blueice.listener;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import com.blueice.domain.Product;

public class MySessionListener implements HttpSessionListener {

	@Override
	public void sessionCreated(HttpSessionEvent se) {
		//创建一个session变量，用来存储购物车中的商品。
		Map<Product, Integer> map = new LinkedHashMap<Product, Integer>();
		se.getSession().setAttribute("carMap", map);

	}

	@Override
	public void sessionDestroyed(HttpSessionEvent se) {
		// TODO Auto-generated method stub

	}

}
