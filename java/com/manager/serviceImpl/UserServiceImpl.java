package com.manager.serviceImpl;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.manager.bean.User;
import com.manager.dao.UserMapper;
import com.manager.service.UserService;

@Service
@Transactional
public class UserServiceImpl implements UserService {

@Autowired
private UserMapper umapper;
	public User findbyid(int id) {
		// TODO Auto-generated method stub
		return umapper.selectByPrimaryKey(id);
	}
	public User findbyname(String username) {
		// TODO Auto-generated method stub
		return umapper.selectByname(username);
	}



}
