package com.blueice.filter;

import java.io.IOException;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;


/**
 * 全站乱码过滤器。
 */
public class EncodeFilter implements Filter {

	private FilterConfig config = null;
	private ServletContext context = null;
	private String encodeName = null;
	
	
	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {

		//--响应乱码的解决-----。
		response.setCharacterEncoding(encodeName);//通知服务器使用哪种类型的编码。
		response.setContentType("text/html;charset="+this.encodeName); //通知浏览器用什么编码。 
		
		//--请求参数乱码解决，利用装饰者模式，改变request对象和获取请求参数相关的方法，从而解决请求参数乱码问题。
		chain.doFilter(new MyHttpServletRequest((HttpServletRequest)request), response);
		
	}

	/**
	 * 初始化方法。
	 */
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

		this.config = filterConfig;
		this.context = filterConfig.getServletContext();
		this.encodeName = context.getInitParameter("Encode"); //获得全站参数配置中的 Encode变量的值。
		
	}
	
	
	private class MyHttpServletRequest extends HttpServletRequestWrapper{
		
		private HttpServletRequest request = null;
		private Boolean isNotEncode = true; //防止重复进行编码的标志符。
		
		/**
		 * 构造函数。
		 *  
		 */
		public MyHttpServletRequest(HttpServletRequest request) {
			super(request);
			this.request = request;
		}
		
		
		//=============重写request要调用到的三种取参数值的方法。===================================
		
		@Override
		public Map<String, String[]> getParameterMap() {
			try {

				if(this.request.getMethod().equalsIgnoreCase("POST")){ //如果参数提交方式为POST
					
					this.request.setCharacterEncoding(encodeName);
					return this.request.getParameterMap();
					
				}else if(this.request.getMethod().equalsIgnoreCase("GET")){ //如果参数提交方式为GET
					
					
					Map<String, String[]> map = this.request.getParameterMap(); //获取原先请求的参数Map.
					
					if(isNotEncode){  //如果还没经过编码，则执行。否则直接返回。
						for(Map.Entry<String, String[]> entry:map.entrySet()){
							
							String[] vs = entry.getValue();
							
							for(int i=0;i<vs.length;i++){
								
								vs[i] = new String(vs[i].getBytes("iso8859-1"),encodeName); //使用encodeName对map中的每一个值进行重新编码。
								
							}
						}
						
						isNotEncode = false;
					}
					
					return map; //返回重新编码后的map.
					
					 
				}else{ //如果参数提交方式为其它几种。
					return this.request.getParameterMap();
				}
				
				
			} catch (Exception e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		
		}


		@Override
		public String[] getParameterValues(String name) {
			return this.getParameterMap().get(name);  //使用getParameterMap()方法来重编码。
		}
		
		
		@Override
		public String getParameter(String name) {
			
			//如果参数为空，则返回空，否则返回getParameterValues的第0个值。
			return this.getParameterValues(name) == null?null:this.getParameterValues(name)[0];
			
		}
		
	}

	
	
}






















