package com.services;

import java.util.List;

import com.model.Users;

public interface UserService {

	
	Users  createUser(Users user);
	
	
	Users getUserById(Integer userId);
	
	List<Users> getAllUsers();
	
	void deleteUser(Integer userId);
	
	
	
	
}
