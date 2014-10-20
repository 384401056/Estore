package com.blueice.utils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.SQLException;
import javax.sql.DataSource;
import org.apache.commons.dbutils.DbUtils;
import com.mchange.v2.c3p0.ComboPooledDataSource;

/**
 * 事务管理类
 * 
 * 使用ThreadLocal变量来实现事务的回滚。ThreadLocal变量会存储在线程中的map中，只允许当前线程去访问。
 * 所以当不同的线程访问Connection对象时，访问都是自已经线程中对象。互不干扰。
 * 
 */
public class TransactionManager {
	
	private TransactionManager(){
		
	}
	
	//数据源。
	private static DataSource source = new ComboPooledDataSource();
	
	//线程本地变量，用于保存Connection.
	private static ThreadLocal<Connection> conn_local = new ThreadLocal<Connection>();
	
	//线程本地变量，用来手动关闭连接。(在runner.updat()等方法调用后，会自动关闭连接。所以要让Connection的Close（）方法失效。）
	private static ThreadLocal<Connection> realConn_local = new ThreadLocal<Connection>();
	
	//事务开始的标志变量.
	private static ThreadLocal<Boolean> flag_local = new ThreadLocal<Boolean>(){
		protected Boolean initialValue() {
			return false;
		};
	};

	
	/**
	 * 开启事件管理。
	 * @throws SQLException 
	 */
	public static void startTran() throws SQLException{
		flag_local.set(true);
		final Connection conn = source.getConnection();
		conn.setAutoCommit(false);
		
		realConn_local.set(conn);//为了以后能手动关闭数据连接，将它存入线程本地变量中。
		
		//为了让Connection不会被 runner.update()等方法自动关闭，所以要改造conn的Close()方法。
		Connection proxyConn = (Connection) Proxy.newProxyInstance(conn.getClass().getClassLoader(), conn.getClass().getInterfaces(), new InvocationHandler() {
			@Override
			public Object invoke(Object proxy, Method method, Object[] args)
					throws Throwable {
				if("close".equals(method.getName())){ //close()此处注意大小写。
					return null;
				}else{
					return method.invoke(conn, args);
				}
			}
		});
		
		conn_local.set(proxyConn); //存入线程本地变量，改造后的Connection.

	}
	
	public static DataSource getSource(){
		//如果事务被开启。
		if(flag_local.get()){//--如果开启过事务,则返回改造的DataSource,改造为每次调用getConnection都返回同一个开启过事务的Conn
			
			return (DataSource) Proxy.newProxyInstance(source.getClass()
					.getClassLoader(), source.getClass().getInterfaces(),
					new InvocationHandler() {
						@Override
						public Object invoke(Object proxy, Method method,
								Object[] args) throws Throwable {
							if ("getConnection".equals(method.getName())) {
								return conn_local.get();
							} else {
								return method.invoke(source, args);
							}
						}
					});
			
			
		}else{//--没有开启过事务,返回普通的数据源
			return source;
		}
	}
	
	/**
	 * 提交事务。
	 * @throws SQLException 
	 */
	public static void commit(){
		DbUtils.commitAndCloseQuietly(conn_local.get());
	}
	
	/**
	 * 回滚事务。
	 */
	public static void rollback(){
		DbUtils.rollbackAndCloseQuietly(conn_local.get());
	}
	
	/**
	 * 清除Thread 中map.
	 */
	public static void release(){
		try {
			DbUtils.closeQuietly(realConn_local.get());//--手动关闭连接。之前连接是没有关闭的在release的时候真正的关闭连接

			realConn_local.remove();
			conn_local.remove();
			flag_local.remove();
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
}











