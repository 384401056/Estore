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
	 * 根据方法的参数值，来确定是返回Custdao还是Custservice.
	 * 再根据配置文件，来确定是返回哪种dao和service
	 * 
	 * 第一个<T>是定义一个泛型，后面的T 函数是返回值类型。
	 * @param clazz
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T> T getInstance(Class<T> clazz){
		
		try {
			
			String className = clazz.getSimpleName();
			String implName = prop.getProperty(className);
			return (T)Class.forName(implName).newInstance();

		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}

	}

}


















