package com.blueice.service;


import com.blueice.domain.User;


public interface UserService {

	/**
	 * 用户注册
	 * @param user 封装了用户数据的Bean.
	 */
	void registUser(User user);

	/**
	 * 用户激活
	 * @param activeCode 激活码
	 */
	User activeUser(String activeCode);

	/**
	 * 根据用户名密码查找用户
	 * @param username
	 * @param password
	 */
	User getUserByNameAndPsw(String username, String password);
	
}
