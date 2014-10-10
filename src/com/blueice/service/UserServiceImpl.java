package com.blueice.service;

import java.util.UUID;

import com.blueice.dao.UserDao;
import com.blueice.domain.User;
import com.blueice.factory.BasicFactory;

public class UserServiceImpl implements UserService {

	
	UserDao dao = BasicFactory.getFactory().getInstance(UserDao.class);
	
	@Override
	public void registUser(User user) {
		//1.调用dao中的方法校验用户名是否已经存在。
		
		if(dao.findUserByName(user.getUsername())!=null){
			
		}
		
		//2.调用dao中的方法添加用户。
		user.setRole("user");
		user.setState(0);
		user.setActivecode(UUID.randomUUID().toString());
		dao.addUser(user);

		//3.发送激活邮件。
		
	}

}
