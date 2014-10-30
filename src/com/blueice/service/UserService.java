package com.blueice.service;


import com.blueice.annotation.Tran;
import com.blueice.domain.User;


public interface UserService extends Service {

	/**
	 * 用户注册
	 * @param user 封装了用户数据的Bean.
	 */
	@Tran //事务管理
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

	/**
	 * 检查用户名是否存在。
	 * @param username
	 * @return
	 */
	boolean hasUser(String username);
	
}
