package com.blueice.factory;

import java.io.FileReader;
import java.util.Properties;

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
	 * 
	 * 根据方法的参数值，来确定是返回Custdao还是Custservice.
	 * 再根据配置文件，来确定是返回哪种dao和service
	 * @param clazz
	 * @return
	 */

	public <T> T getInstance(Class<T> clazz){
		
		try {
			
			String className = clazz.getName();
			
			return (T)Class.forName(prop.getProperty(className)).newInstance();

		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}

	}
	
	
	
	/**
	 * 通过配置文件中的设置，获取CustDao的对象
	 * @return
	 */
	public CustDao getDao(){
		
		try {
			
			String clazz = prop.getProperty("CustDao");
			return (CustDao)Class.forName(clazz).newInstance();
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}

	}
	
	
	/**
	 * 通过配置文件中的设置，获取CustService的对象
	 * @return
	 */
	public CustService getService(){
		
		try {
			
			String clazz = prop.getProperty("CustService");
			return (CustService)Class.forName(clazz).newInstance();
			

		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
	}
}


















