<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1>${sessionScope.user.username}生成订单</h1><hr>
	<form action="${pageContext.request.contextPath}/addOrderServlet" method="POST">
		<table>
			<tr>
				<td>收货地址：</td>
				<td><textarea rows="6" cols="40" name="receiverinfo"></textarea></td>
			</tr>
			<tr>
				<td>支付方式：</td>
				<td><input type="radio" name="typex" checked="checked"/>在线支付</td>
			</tr>
			<tr>
				<td colspan="2" align="center"><input type="submit" value = "生成订单" /></td>
			</tr>
		</table>
	</form>
	<hr>
	<h3>购物清单</h3><hr>
	<table width="100%" style="text-align: center;" border="1">
			<tr>
				<td>商品图片</td>
				<td>商品名称</td>
				<td>商品类型</td>
				<td>商品单价</td>
				<td>购买数量</td>
				<td>库存量</td>
				<td>总价</td>
			</tr>
			
			<c:set var="money" value="0" /><!-- 定义一个用于计算总金额的域变量 -->
	
			<c:forEach items="${sessionScope.carMap}" var="entry">
				<tr>
					<td><img src="${pageContext.request.contextPath }/ImgServlet?imgurl=${entry.key.imgurls}" width="50%"/></td>
					<td>${entry.key.name}</td>
					<td>${entry.key.category}</td>
					<td>${entry.key.price}元</td>
					<td>${entry.value}件</td>
					<td>
						<c:if test="${entry.value<=entry.key.pnum }">
							<font color="blue">有货</font>
						</c:if>
						<c:if test="${entry.value>entry.key.pnum }">
							<font color="red">无货</font>
						</c:if>
					</td>
					<td>
						${entry.key.price * entry.value}元
					</td>
				</tr>
				<c:set var="money" value="${money + entry.key.price * entry.value }" />
			</c:forEach>		
		</table>
		
		<div align="right">
			<font color="red" size="7">总价：${money }元</font>
		</div>
</body>
</html>