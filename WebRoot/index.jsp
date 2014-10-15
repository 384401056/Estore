<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <body>
  
	<div align="center">
		<h1>My Estore</h1><hr>
		
		<c:if test="${sessionScope.user==null}">
			欢迎，游客！
			
			<a href="${pageContext.request.contextPath}/regist.jsp">注册</a>
	    	<a href="${pageContext.request.contextPath}/login.jsp">登陆</a>
	    	
    	</c:if> 

	    <c:if test="${sessionScope.user!=null}">
	    	欢迎回来！${sessionScope.user.username}<br>
	    	<a href="${pageContext.request.contextPath}/addprod.jsp">添加商品</a>
	    	<a href="${pageContext.request.contextPath}/prodListServlet">商品列表</a>
	    	<a href="${pageContext.request.contextPath}/car.jsp">我的购物车</a>
	    	<a href="${pageContext.request.contextPath}/logoutServlet">注销</a>
	    </c:if>
	</div>
  </body>
</html>
