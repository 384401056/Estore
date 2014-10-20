package com.blueice.dao;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import com.blueice.domain.User;
import com.blueice.utils.DaoUtils;
import com.blueice.utils.TransactionManager;

public class UserDaoImpl implements UserDao {

	@Override
	public void addUser(User user) {
		try{
			String sql = "INSERT INTO users VALUES(null,?,?,?,?,?,?,?,null)";
			QueryRunner runner = new QueryRunner(TransactionManager.getSource());
			runner.update(sql,user.getUsername(),user.getPassword(),user.getNickname(),user.getEmail(),user.getRole(),user.getState(),user.getActivecode());
			
		}catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}

	}

	@Override
	public User findUserByName(String userName) {
		try {
			String sqlStr = "SELECT * FROM users WHERE username = ?";
			QueryRunner runner = new QueryRunner(TransactionManager.getSource());
			return runner.query(sqlStr, new BeanHandler<User>(User.class), userName);
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public User findUserByActivecode(String activecode) {
		try {
			String sqlStr = "SELECT * FROM users WHERE activecode = ?";
			QueryRunner runner = new QueryRunner(TransactionManager.getSource());
			return runner.query(sqlStr, new BeanHandler<User>(User.class), activecode);
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public void delUser(int id) {
		try{
			String sql = "delete from users where id = ?";
			QueryRunner runner = new QueryRunner(TransactionManager.getSource());
			runner.update(sql,id);
		}catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}	
	}

	@Override
	public void updateState(int id, int state) {
		String sql = "update users set state = ? where id=?";
		try{
			QueryRunner runner = new QueryRunner(TransactionManager.getSource());
			runner.update(sql,state,id);
		}catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}		
	}

	@Override
	public User findUserByNameAndPsw(String username, String password) {
		try{
			String sql = "select * from users where username = ? and password = ?";
			QueryRunner runner = new QueryRunner(TransactionManager.getSource());
			return runner.query(sql, new BeanHandler<User>(User.class) ,username ,password);
		}catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public User findUserById(int id) {
		try{
			String sql = "select * from users where id = ?";
			QueryRunner runner = new QueryRunner(TransactionManager.getSource());
			return runner.query(sql, new BeanHandler<User>(User.class) ,id);
		}catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
}
