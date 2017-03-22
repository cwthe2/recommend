package com.manager.controller;

//test11

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.manager.bean.User;
import com.manager.service.UserService;
import com.manager.untils.Md5Utils;

@Controller
@RequestMapping("/admin")
public class LoginController {
	@Autowired
	private UserService userservice;


	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(HttpSession httpsession, HttpServletRequest request, User userInput) {
		System.out.println("login"+userInput);
		
		User userInTable = this.userservice.findbyname(userInput.getUsername());
		if (userInTable == null) {
			return "index";
		} else {
			if (userInTable.getPassword().equals(Md5Utils.md5(userInput.getPassword()))) {
				httpsession.setAttribute("username", userInput.getUsername());
				httpsession.setAttribute("userid", userInput.getId());
				return "recommendIndex";
			}else{
				return "index";
			}
		}
		
	}

}
