<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Insert title here</title>
</head>

<script type="text/javascript" src="./js/jquery-1.6.1.js"></script>
<script type="text/javascript">
	function checkData(){
  			var price = document.getElementsByName("price")[0].value;
  			if(isNaN(price)){
  				alert("单价必须是数字!");
  				document.getElementsByName("price")[0].value = "";
  				return false;
  			}else if(price<=0){
	  			alert("单价必须大于0!")
	  			document.getElementsByName("price")[0].value = "";
	  			return false;
  			}else{
  				return true;
  			}
  		}
		
	function subFunc(){
		$("#bar1").css("display","block");
		//每隔10毫秒触发一次ref事件。
		window.setInterval(ref, 10);
	}
	
	function ref(){
		$.post("${pageContext.request.contextPath}/UploadMsg",function(data){
			var json = eval("("+data+")");
			$("#bar2").css("width",json.per+"%"); //注意此处要加%号。因为width值要实现百分比。
			$("#progSpeed").text("速度："+json.speed+"KB/S 剩余时间："+json.ltime+"S");
		});
	}
	
</script>
<body>
	<div align="center">
		<h1>My Estore 添加商品</h1><hr>
		<form action="${pageContext.request.contextPath}/addProdServlet" method="POST" enctype="multipart/form-data" onsubmit="return checkData()">
			<table>
			
				<tr>
	  				<td>商品名称:</td>
	  				<td><input type="text" name="name" }"/></td>
	  			</tr>
	  			
	  			<tr>
	  				<td>商品价格:</td>
	  				<td><input type="text" name="price" }"/></td>
	  			</tr>
	  			
	  			<tr>
	  				<td>商品类别:</td>
	  				<td><select name="category">
	  					<option value="电子数码">电子数码</option>
	  					<option value="家用电器">家用电器</option>
	  					<option value="百货日用">百货日用</option>
	  					<option value="图书音像">图书音像</option>
	  					<option value="精品音像">精品音像</option>
	  					</select>
	  				</td> 
	  			</tr>
	  			
	  			<tr>
	  				<td>库存数量:</td>
	  				<td><input type="text" name="pnum"/></td>
	  			</tr>
	  			
	  			<tr>
	  				<td>商品图片:</td>
	  				<td><input type="file" name="file1"/>
	  					<div id="bar1" style="width: 200px;height: 20px;border: 1px solid green ;display:none">
	  						<div id="bar2" style="width:0%;height:20px;background-color: green"></div>
	  					</div>
	  					<div id="progSpeed"></div>
	  				</td>
	  			</tr>
	  			
	  			<tr>
	  				<td>描述信息:</td>
	  				<td><textarea name="description" rows="6" cols="40"></textarea></td>
	  			</tr>
	  			
	  			<tr>
					<td colspan="2" align="center"><input type="submit" value="添加" onclick="subFunc()"/></td>
				</tr>
	  			
			</table>
		
		</form>
		
	</div>
</body>
</html>