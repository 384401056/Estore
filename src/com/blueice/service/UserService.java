package com.blueice.service;


import com.blueice.domain.User;


public interface UserService {

	/**
	 * 用户注册
	 * @param user 封装了用户数据的Bean.
	 */
	void registUser(User user);

	
	
}
