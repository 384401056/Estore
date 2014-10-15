<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script type="text/javascript">
	function changeNum(id,obj,oldnum){
	  			if(!/^[1-9]\d*$/.test(obj.value)){
	  				alert("购买数量必须为正整数!");
	  				obj.value=oldnum;
	  				return;
	  			}
	  			window.location.href="/Estore/ChangeCartServlet?id="+id+"&buynum="+obj.value;
	  		}
</script>
</head>
<body>
	<h1>${sessionScope.user.username} 的购物车</h1><hr>

	<c:if test="${ empty sessionScope.carMap }">
		<a href="${pageContext.request.contextPath}/prodListServlet">购物车没有商品，点击返回商品列表</a>
	</c:if>
	
	<c:if test="${ not empty sessionScope.carMap }">
	
		<div align="right">
			<a href="${pageContext.request.contextPath}/prodListServlet">继续购物</a>
			<a href="${pageContext.request.contextPath}/clearCarServlet">请空购物车</a>
		</div>
	
		<table width="100%" style="text-align: center;" border="1">
			<tr>
				<td>商品图片</td>
				<td>商品名称</td>
				<td>商品类型</td>
				<td>商品单价</td>
				<td>购买数量</td>
				<td>库存量</td>
				<td>总价</td>
				<td>删除</td>
			</tr>
			
			<c:set var="money" value="0" /><!-- 定义一个用于计算总金额的域变量 -->
	
			<c:forEach items="${sessionScope.carMap}" var="entry">
				<tr>
					<td><img src="${pageContext.request.contextPath }/ImgServlet?imgurl=${entry.key.imgurls}" /></td>
					<td>${entry.key.name}</td>
					<td>${entry.key.category}</td>
					<td>${entry.key.price}元</td>
					<td><input type="text" style="width: 30px" value="${entry.value}" onchange="changeNum('${entry.key.id}',this,${entry.value})">件</td>
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
					<td>
						<a href="${pageContext.request.contextPath }/delCarServlet?id=${entry.key.id}">删除</a>
					</td>
				</tr>
				<c:set var="money" value="${money + entry.key.price * entry.value }" />
			</c:forEach>		
		</table>
		
		<div align="right">
			<font color="red" size="10">总价：${money }元</font>
		</div>
	</c:if>
	
</body>
</html>