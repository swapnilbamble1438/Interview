package com.controllers;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.model.Users;
import com.repositories.UserRepo;
import com.services.UserService;

@RestController
@RequestMapping("/user")
public class UserController {
	
	

	@Autowired
	private UserService userService;
	
	@Autowired
	private UserRepo userRepo;
	
	@PostMapping("/create")   //@Valid is used for activating working of validator
	public String createUser(@Valid @RequestBody Users user)
	{
		
		
		user.setRole("ROLE_NORMAL");
		
		
			
			Users createUserDto = userService.createUser(user);
		String	msg = "User created successfully..";
		
		return msg;
		
	}

	@DeleteMapping("/delete/{userId}")
	public void deleteUser(@PathVariable("userId") Integer uid)
	{
		userService.deleteUser(uid);
		
	}
	
	@GetMapping("/getall")
	public  List<Users> getAllUsers()
	{
		
		return userService.getAllUsers();
	}
	
	@GetMapping("/{userId}")
	public  Users getSingleUser(@PathVariable("userId") Integer uId)
	{
		
		return userService.getUserById(uId);
	}
	
	
	@GetMapping("/name/{username}")
	public  Users getUserbyname(@PathVariable("username") String username)
	{
		
		return userRepo.getUsersByUsername(username);
	}
	
	
}
