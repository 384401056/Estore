<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
</head>
<body>
	<h1>My Estore 订单列表</h1><hr>
	<c:forEach items="${requestScope.list }" var="olf">
		<table>
			<tr>
				<td>订单号：</td>
				<td>${olf.id }</td>
			</tr>
			<tr>
				<td>用户名称：</td>
				<td>${olf.userName }</td>
			</tr>
			<tr>
				<td>订单金额：</td>
				<td>${olf.money }元</td>
			</tr>
			<tr>
				<td>支付状态</td>
				<td>
				<font color="red">
				<c:if test="${olf.paystate ==0 }">
					未支付
					<a href="${pageContext.request.contextPath }/delOrderServlet?id=${olf.id}">删除订单</a>
				</c:if>
				</font>
				<font color="blue">
				<c:if test="${olf.paystate ==1 }">
					已支付
				</c:if>
				</font>
				</td>
			</tr>
			<tr>
				<td>收货地址：</td>
				<td>${olf.receiverinfo }</td>
			</tr>
			<tr>
				<td>下单时间：</td>
				<td>${olf.ordertime }</td>
			</tr>
		</table>
		
		<table width="100%" border="1">
			<tr>
				<td>商品名称</td>
				<td>商品各类</td>
				<td>购买数量</td>
				<td>商品单价</td>
				<td>总金额</td>
			</tr>
			<c:forEach items="${olf.prodMap}" var="entry">
				<tr>
					<td>${entry.key.name}</td>
					<td>${entry.key.category}</td>
					<td>${entry.value}</td>
					<td>${entry.key.price}</td>
					<td>${entry.key.price * entry.value}</td>
				</tr>
			</c:forEach>
			<tr>
				<td colspan="5" align="right">合计金额：${olf.money}元</td>
			</tr>
		</table>
		
		<hr>
	</c:forEach>
</body>
</html>