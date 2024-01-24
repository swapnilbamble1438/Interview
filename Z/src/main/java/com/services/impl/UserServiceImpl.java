package com.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.stereotype.Service;

import com.model.Users;
import com.repositories.UserRepo;
import com.services.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepo userRepo;
	
	

	@Override
	public Users createUser(Users user) {

	
		Users savedUser = userRepo.save(user);
		
		return savedUser;
	
	}



	

	@Override
	public Users getUserById(Integer userId) {

		Users user = userRepo.findById(userId).get();	
		
		return user;
	}



	@Override
	public List<Users> getAllUsers() {

	List<Users>	users = userRepo.findAll();
	
	
	
		return users;
	}



	@Override
	public void deleteUser(Integer userId) {


	
		userRepo.deleteById(userId);
	}


}
