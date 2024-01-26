package com.services;

import java.util.List;

import com.model.Users;

public interface UserService {

	
	Users  createUser(Users user);
	
	
	Users getUserById(String username);
	
	List<Users> getAllUsers(Integer pageNo,Integer pageSize);
	
	void deleteUser(String username);
	
	Users updatePassword(Users user);
	
	
}
