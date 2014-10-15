<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
</head>
<body>
    <h1>My Estore 商品列表</h1><hr>
    <table width="100%" style="text-align: center;">
	    <c:forEach items="${requestScope.list}" var="prod">
	    	<tr>
	    		<td width="20%"><a href="${pageContext.request.contextPath }/prodInfoServlet?id=${prod.id}"><img src="${pageContext.request.contextPath }/ImgServlet?imgurl=${prod.imgurls}"/></a></td>
	    		<td width="40%" align="left">
		    		商品名称：${prod.name }<br>
		    		价格：${prod.price }<br>
		    		商品种类：${prod.category }<br>
		    		库存量：${prod.pnum }<br>
	    		</td>
	    	</tr>
	    	<tr>
	    		<td colspan=2><hr></td>
	    	</tr>
	    </c:forEach>
    </table>
</body>
</html>