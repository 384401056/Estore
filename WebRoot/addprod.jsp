<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Insert title here</title>
</head>
<body>
	<div align="center">
		<h1>My Estore 添加商品</h1><hr>
		<form action="${pageContext.request.contextPath}/addProdServlet" method="POST" enctype="multipart/form-data">
			<table>
			
				<tr>
	  				<td>商品名称:</td>
	  				<td><input type="text" name="name" value="${param.username }"/></td>
	  				<td id="username_msg"></td>
	  			</tr>
	  			
	  			<tr>
	  				<td>商品价格:</td>
	  				<td><input type="text" name="price" value="${param.username }"/></td>
	  				<td id="username_msg"></td>
	  			</tr>
	  			
	  			<tr>
	  				<td>商品类别:</td>
	  				<td><select name="category">
	  					<option value="电子数码">电子数码</option>
	  					<option value="家用电器">家用电器</option>
	  					<option value="百货日用">百货日用</option>
	  					<option value="图书音像">图书音像</option>
	  					<option value="精品音像">精品音像</option>
	  				</select></td>
	  				<td id="username_msg"></td>
	  			</tr>
	  			
	  			<tr>
	  				<td>库存数量:</td>
	  				<td><input type="text" name="pnum" value="${param.username }"/></td>
	  				<td id="username_msg"></td>
	  			</tr>
	  			
	  			<tr>
	  				<td>商品图片:</td>
	  				<td><input type="file" name="file1"/></td>
	  				<td id="username_msg"></td>
	  			</tr>
	  			
	  			<tr>
	  				<td>描述信息:</td>
	  				<td><textarea name="description" rows="6" cols="40"></textarea></td>
	  			</tr>
	  			
	  			<tr>
					<td colspan="2" align="center"><input type="submit" value="添加"/></td>
				</tr>
	  			
			</table>
		
		</form>
		
	</div>
</body>
</html>