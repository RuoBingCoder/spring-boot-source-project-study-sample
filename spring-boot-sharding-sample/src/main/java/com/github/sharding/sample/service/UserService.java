package com.github.sharding.sample.service;


import com.github.sharding.sample.po.User;

import java.util.List;

public interface UserService {

	List<User> list();
	
	Long add(User user);
	
	User findById(Long id);
	
	User findByName(String name);
	
}
