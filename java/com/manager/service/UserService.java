package com.manager.service;

import com.manager.bean.User;
//
public interface UserService {
	
	public User findbyid(int id);
	
	public User findbyname(String username);
}
