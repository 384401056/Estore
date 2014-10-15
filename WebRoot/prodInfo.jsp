<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
  </head>
  
  <body>
    <font color="blue"><h1>${prod.name }</h1><hr></font>
    <table>
    	<tr>
	    	<td width="40%"> <img src="${pageContext.request.contextPath }/ImgServlet?imgurl=${prod.imgurl}"> </td>
	    	<td width="50%" align="left">
	    		商品名称：${prod.name }<br>
	    		价格：${prod.price }<br>
	    		商品种类：${prod.category }<br>
	    		库存量：${prod.pnum }<br>
	    		<a href="${pageContext.request.contextPath }/addCarServlet?id=${prod.id}"><img alt="" src="${pageContext.request.contextPath }/img/buy.bmp"/></a>
		    </td>
    	</tr>
    	<tr height="30"></tr>
    	<tr>
    		<td height="50%">
		    	${prod.description}
		    </td>
    	</tr>
    </table>
  </body>
</html>
