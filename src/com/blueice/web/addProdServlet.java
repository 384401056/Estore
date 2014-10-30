package com.blueice.web;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.ProgressListener;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.blueice.domain.Product;
import com.blueice.domain.ProgressMsg;
import com.blueice.factory.BasicFactory;
import com.blueice.service.ProductService;
import com.blueice.utils.IOUtils;
import com.blueice.utils.PicUtils;

public class addProdServlet extends HttpServlet {


	public void doGet(final HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		
		ProductService service = BasicFactory.getFactory().getService(ProductService.class);
		
		/*1.上传图片。-------*/
		
		//上传文件夹和缓存文件夹路径。
		String uploadPath = this.getServletContext().getRealPath("WEB-INF/Upload"); //硬盘路径。
		String imgurl = "/WEB-INF/Upload"; //用来存入数据库的路径，这个路径不能用服务硬盘路径。
		String temp = this.getServletContext().getRealPath("WEB-INF/temp");
		String encode = this.getServletContext().getInitParameter("Encode");
		
		//用来存储普通上传项的参数。
		Map<String,String> paraMap = new HashMap<String, String>(); 
		
		DiskFileItemFactory factory = new DiskFileItemFactory();
		factory.setSizeThreshold(1024*1024);    //设置缓冲区大小。
		factory.setRepository(new File(temp)); //设置缓存文件夹。
		
		
		ServletFileUpload fileUpload = new ServletFileUpload(factory);
		fileUpload.setHeaderEncoding(encode);     //文件名中文乱码处理。
		fileUpload.setFileSizeMax(1024*1024*10);  //单个上传文件的大小限制
		fileUpload.setSizeMax(1024*1024*100);		//总的上传大小限制
		
		//文件上传监听器。
		fileUpload.setProgressListener(new ProgressListener() {
			
			Long beginTime = System.currentTimeMillis();
			@Override
			public void update(long bytesRead, long contentLength, int items) {
				
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
				ProgressMsg progressMsg = new ProgressMsg();
				
				BigDecimal br = new BigDecimal(bytesRead).divide(new BigDecimal(1024),2,BigDecimal.ROUND_HALF_UP);
				BigDecimal cl = new BigDecimal(contentLength).divide(new BigDecimal(1024),2,BigDecimal.ROUND_HALF_UP);
				System.out.print("当前读取的是第"+items+"个上传项,总大小"+cl+"KB,已经读取"+br+"KB");
				//剩余字节数
				BigDecimal ll = cl.subtract(br);
				System.out.print("剩余"+ll+"KB");
				//上传百分比
				BigDecimal per = br.multiply(new BigDecimal(100)).divide(cl,2,BigDecimal.ROUND_HALF_UP);
				System.out.print("已经完成"+per+"%");
				
				progressMsg.setPer(per.toString());
				
				
				//上传用时
				Long nowTime = System.currentTimeMillis();
				Long useTime = (nowTime - beginTime)/1000;
				System.out.print("已经用时"+useTime+"秒");
				//上传速度
				BigDecimal speed = new BigDecimal(0);
				if(useTime!=0){
					speed = br.divide(new BigDecimal(useTime),2,BigDecimal.ROUND_HALF_UP);
				}
				System.out.print("上传速度为"+speed+"KB/S");
				
				progressMsg.setSpeed(speed.toString());
				
				//大致剩余时间
				BigDecimal ltime = new BigDecimal(0);
				if(!speed.equals(new BigDecimal(0))){
					ltime = ll.divide(speed,0,BigDecimal.ROUND_HALF_UP);
				}
				System.out.print("大致剩余时间为"+ltime+"秒");
				
				progressMsg.setLtime(ltime.toString());
				
				request.getSession().setAttribute("progressMsg",progressMsg); //将进度条需要的数据定入Session域中。
				
				System.out.println();

			}
		});
		
		try {
			
			List<FileItem> list = fileUpload.parseRequest(request);//得到文件上传表单提交过来的的数据列表。
			
			//判断是否使用正确的表单。
			if(!fileUpload.isMultipartContent(request)){
				throw new RuntimeException("请使用正确的表单进行上传.");
			}
			
			//遍历数据列表。
			for(FileItem item :list){
				if(item.isFormField()){ 
					
					//如果是普通上传项,则存入map。
					String name = item.getFieldName();
					String value = item.getString(encode); //注册这里要加入encode的值，解决乱码。
					
					paraMap.put(name, value);
					
				}else{
					
					//如果是文件上传项
					String realName = item.getName();
					
					/*item.getName()会获取路径全名，所以要将真正的文件名提取出来。*/
					realName = realName.substring(realName.lastIndexOf("\\")+1);
					
					String uuidName = UUID.randomUUID().toString()+"_"+realName;
					

					/*--对上传文件的位置进行分目录存储。*/
					String hash = Integer.toHexString(uuidName.hashCode());//将这个uuidName的哈希值转为16进制的字符串。
							
					for(char c :hash.toCharArray()){
						
						uploadPath+="/"+c;
						imgurl+="/"+c;
					}
					
					imgurl+="/"+uuidName; //存入数据库的图片路径要包含文件名。
					paraMap.put("imgurl", imgurl);
					
					
					File uploFile = new File(uploadPath);
					
					if(!uploFile.exists())
						uploFile.mkdirs(); //如果文件夹不存在，则创建文件夹。
					
					InputStream in = item.getInputStream();  //获取文件输入流。
					OutputStream out = new FileOutputStream(new File(uploadPath,uuidName)); //创建文件输出流，指向建立好的文件夹路径uploadPath.和文件名.
					
					//对接流，和关闭流。
					IOUtils.In2Out(in, out);
					IOUtils.close(in, out);
					
					item.delete();  //删除缓存文件。
					
					
					
					//生成缩略图,此工具类会在原图片的路径下生成一个缩略图。在原文件名的基础上加一个"_s".
					PicUtils picUtils = new PicUtils(this.getServletContext().getRealPath(imgurl));
					picUtils.resizeByHeight(150); //按高度等比例压缩。

				}
			}
			
			//将提交的数据放入Bean中。但是此时还缺少id的值。
			Product product = new Product();
			BeanUtils.populate(product, paraMap);
			
			/*2.调用Service中的方法添加商品--*/
			service.addProduct(product);
			

			/*3.添加成功，返回主页--*/
			response.getWriter().write("商品添加成功，3秒回到主页...");
			response.setHeader("Refresh", "3;url="+request.getContextPath()+"/index.jsp");
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
		
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doGet(request, response);
	}

}
