<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
  </head>
  <body>
  	<div align="center">
  
	    <h1>My Estore 注册用户</h1><hr>
	    
	    <form action="${pageContext.request.contextPath}/registServlet" method="POST">
			<table>
				<tr>
					<td>用户名：</td>
					<td><input type="text" name="username"/></td>
				</tr>
				
				<tr>
					<td>密码：</td>
					<td><input type="password" name="password"/></td>
				</tr>
				<tr>
				
				<tr>
					<td>密码确认：</td>
					<td><input type="password" name="password2"/></td>
				</tr>
				
				<tr>
					<td>昵称：</td>
					<td><input type="text" name="nickname"/></td>
				</tr>
				
				<tr>
					<td>E-mail：</td>
					<td><input type="text" name="email"/></td>
				</tr>
				
				<tr>
					<td colspan="2" align="center"><input type="submit" value="注册" /></td>
				</tr>
			</table>
		</form>
    </div>
  </body>
</html>
