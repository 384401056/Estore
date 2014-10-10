package com.blueice.dao;

import com.blueice.domain.User;

public interface UserDao {

	/**
	 * 根据用户名查找用户
	 * @param userName 用户名 
	 * @return 查找到的用户
	 */
	User findUserByName(String userName);

	
	/**
	 * 添加用户
	 * @param user 用户Bean.
	 */
	void addUser(User user);
	
}
