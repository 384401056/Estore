package com.blueice.service;

import java.util.Properties;
import java.util.UUID;

import javax.mail.Message;
import javax.mail.Message.RecipientType;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.blueice.dao.UserDao;
import com.blueice.domain.User;
import com.blueice.factory.BasicFactory;

public class UserServiceImpl implements UserService {

	
	UserDao dao = BasicFactory.getFactory().getInstance(UserDao.class);
	
	@Override
	public void registUser(User user) {
		//1.调用dao中的方法校验用户名是否已经存在。
		User testUser = new User();
		testUser = dao.findUserByName(user.getUsername());
		
		if(testUser!=null){
			throw new RuntimeException("用户已经存在。");
		}
		
		
		user.setRole("user");
		user.setState(0);
		user.setActivecode(UUID.randomUUID().toString());
		

		//3.发送激活邮件。导入activation.jar和mail.jar
		try {
		
			//发送邮件前的配置.
			Properties prop = new Properties();
			prop.setProperty("mail.transport.protocol", "smtp");
			prop.setProperty("mail.smtp.host", "smtp.qq.com");
			prop.setProperty("mail.smtp.auth", "true");
			prop.setProperty("mail.debug", "true");
			
			//创建邮件发送的会话。
			Session session = Session.getInstance(prop);
			
			//创建邮件
			Message msg = new MimeMessage(session);
			msg.setFrom(new InternetAddress("384401056@qq.com"));                        //发件人
			msg.setRecipient(RecipientType.TO, new InternetAddress(user.getEmail()));  //收件人
			msg.setSubject(user.getUsername()+"，您好！这是来自Estore的用户注册激活邮件");       //邮件标题
			msg.setText(user.getUsername()+",请点击如下连接，激活用户。http://localhost/Estore/ActiveServlet?activecode="+user.getActivecode());//邮件正文。

			//发送邮件
			Transport transport = session.getTransport();
			transport.connect("384401056@qq.com","king1029");
			transport.sendMessage(msg, msg.getAllRecipients());
			
			//2.调用dao中的方法添加用户。
			dao.addUser(user);
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		

		
	}

	@Override
	public User activeUser(String activecode) {
		
		//1.调用dao根据激活码查找用户
		User user = dao.findUserByActivecode(activecode);
		
		//2.如果找不到提示激活码无效
		if(user == null){
			throw new RuntimeException("激活码不正确!!!!");
		}
		//3.如果用户已经激活过,提示不要重复激活
		if(user.getState() == 1){
			throw new RuntimeException("此用户已经激活过!不要重复激活!!");
		}
		//4.如果没激活但是激活码已经超时,则提示,并删除此用户
		if(System.currentTimeMillis() - user.getUpdatetime().getTime() >1000*3600*24){
			dao.delUser(user.getId());
			throw new RuntimeException("激活码已经超时,请重新注册并在24小时内激活!");
		}
		//5.调用dao中修改用户激活状态的方法
		dao.updateState(user.getId(),1);
		user.setState(1);
	
		return user;
	}
	
	@Override
	public User getUserByNameAndPsw(String username, String password) {
		return dao.findUserByNameAndPsw(username,password);
	}

}















