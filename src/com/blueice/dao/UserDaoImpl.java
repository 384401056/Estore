package com.blueice.dao;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import com.blueice.domain.User;
import com.blueice.utils.DaoUtils;

public class UserDaoImpl implements UserDao {

	@Override
	public User findUserByName(String userName) {
		
		String sql = "SELECT * FROM users WHERE username = ?";
		
		try {
			
			QueryRunner runner = new QueryRunner(DaoUtils.getDataSource());
			return runner.query(sql, new BeanHandler<User>(User.class), userName);
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public void addUser(User user) {
		
		String sql = "INSERT INTO users VALUES(null,?,?,?,?,?,?,?,null)";
		
		try{
			
			QueryRunner runner = new QueryRunner(DaoUtils.getDataSource());
			runner.update(sql,user.getUsername(),user.getPassword(),user.getNickname(),user.getEmail(),user.getRole(),user.getState(),user.getActivecode());
			
		}catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
		
	}

}
