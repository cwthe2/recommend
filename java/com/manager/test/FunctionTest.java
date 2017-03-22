package com.manager.test;



import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


import com.manager.service.UserService;




public class FunctionTest {
	static ApplicationContext cxt = new ClassPathXmlApplicationContext("spring-mybatis.xml");
	
	@Autowired
	private UserService admin;
	@Test
	public void get(){
		admin  = (UserService) cxt.getBean("userServiceImpl");
		System.out.println("admin"+admin.findbyid(1));
		
	}
}
