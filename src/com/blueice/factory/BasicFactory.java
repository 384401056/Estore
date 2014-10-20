package com.blueice.factory;

import java.io.FileReader;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Properties;

import com.blueice.annotation.Tran;
import com.blueice.dao.Dao;
import com.blueice.service.Service;
import com.blueice.utils.TransactionManager;

public class BasicFactory {
	
	private static BasicFactory factory = new BasicFactory();
	private static Properties prop = null;
	
	static{

		try {
			
			prop = new Properties();
			prop.load(new FileReader(BasicFactory.class.getClassLoader().getResource("config.properties").getPath()));
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
	}
	
	private BasicFactory() {

	}
	
	
	public static BasicFactory getFactory(){
		return factory;
	}
	
	
	
	/**
	 * 这个方法可以可返回dao,也可返回service.
	 * 根据方法的参数值，来确定是返回Custdao还是Custservice.
	 * 再根据配置文件，来确定是返回哪种dao和service
	 * 
	 * 第一个<T>是定义一个泛型，后面的T 函数是返回值类型。
	 * @param clazz
	 * @return
	 */
//	@SuppressWarnings("unchecked")
//	public <T> T getInstance(Class<T> clazz){
//		
//		try {
//			
//			String className = clazz.getSimpleName();
//			String implName = prop.getProperty(className);
//			return (T)Class.forName(implName).newInstance();
//
//		} catch (Exception e) {
//			e.printStackTrace();
//			throw new RuntimeException(e);
//		}
//
//	}
	
	/**
	 * 获取dao的方法
	 */
	public <T extends Dao> T getDao(Class<T> clazz){
		try{
			String infName = clazz.getSimpleName();
			String implName = prop.getProperty(infName);
			return (T) Class.forName(implName).newInstance();
		}catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 获取Service的方法
	 */
	@SuppressWarnings("unchecked")
	public <T extends Service> T getService(Class<T> clazz){
		try{
			//--根据配置文件创建具体的Service
			String infName = clazz.getSimpleName();
			String implName = prop.getProperty(infName);
			
			final T service = (T) Class.forName(implName).newInstance();
			
			//--为了实现AOP(面向切面),生成service代理,根据注解确定在Service方法执行之前和之后做一些操作,比如:事务管理/记录日志/细粒度权限控制....
			T proxyService =  (T) Proxy.newProxyInstance(service.getClass().getClassLoader(), service.getClass().getInterfaces()
				 , new InvocationHandler(){
				
				    //根据注解控制事务
					@Override
					public Object invoke(Object proxy, Method method,Object[] args) throws Throwable {
						
						if(method.isAnnotationPresent(Tran.class)){//如果有注解,则管理事务:
							try{
								TransactionManager.startTran();//--开启事务
								
								Object obj = method.invoke(service, args);//--真正执行方法
								
								TransactionManager.commit();//--提交事务
								return obj;
							}catch (InvocationTargetException e) {
								TransactionManager.rollback();//--回滚事务
								throw new RuntimeException(e.getTargetException());
							} catch (Exception e) {
								TransactionManager.rollback();//--回滚事务
								throw new RuntimeException(e);
							}finally{
								TransactionManager.release();//--释放资源
							}
						}else{//如果没有注解,则不管理事务,直接执行方法
							return method.invoke(service, args);
						}
					}
			 });
			 
			return proxyService;
			
		}catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

}



















