package com.blueice.utils;

import java.sql.Connection;
import javax.sql.DataSource;
import com.mchange.v2.c3p0.ComboPooledDataSource;

public class DaoUtils {
	
	//将数据源设置成静态的，这样每个线程都在一个数据源中进行操作。
	private static ComboPooledDataSource source = new ComboPooledDataSource();
	
	private DaoUtils() {
		
	}
	
	public static DataSource getDataSource(){
		return source;
	}
	
	public static Connection getConnection(){
		try {
			
			return source.getConnection();
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

}
