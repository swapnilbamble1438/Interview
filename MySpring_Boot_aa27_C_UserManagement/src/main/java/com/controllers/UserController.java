package com.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.exceptions.CustomException;
import com.model.Users;
import com.repositories.UserRepo;
import com.services.UserService;

@RestController
@RequestMapping("/user")
public class UserController {
	
private static List<String> otpList = new ArrayList<>();
	
	static {
		otpList.add("1234");
		otpList.add("2345");
		otpList.add("3456");
		otpList.add("4567");
		otpList.add("5678");
		otpList.add("6789");
		otpList.add("7891");
		otpList.add("8912");
		otpList.add("9123");
		otpList.add("9876");
		otpList.add("8765");
		otpList.add("7654");
		otpList.add("6543");
		otpList.add("5432");
		otpList.add("4321");
		otpList.add("3219");	
		
	}
	
	

	@Autowired
	private UserService userService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private UserRepo userRepo;
	
	
	

	
//======== create User =======================================================================================================	
	/*
	 * select request type POST 
	 * Type url  http://localhost:8080/user/create
	 * select Body 
	 * select raw and JSON and write below code
		
		{
		    "username":"Your username",
		    "password":"Your password",
		    "email":"Your EmailId"
		}
		
	 * And Click Send	
	
	*/
	@PostMapping("/create")   //@Valid is used for activating working of validator
	public String createUser( @RequestBody Users user)
	{
		
		if(userRepo.getUserByUsername(user.getUsername()) != null)
		{
			throw new CustomException("Failed to register User, Same Username already exists");

		}
		if(userRepo.getUsersByEmail(user.getEmail()) != null)
		{
			throw new CustomException("Failed to register User, Same EmailId already exists");

		}
		
			 userService.createUser(user);
			
		return "User created successfully..";
		
	}

	
//======= delete User by id/username ==================================================================================================
	/*
	 * select request type DELETE
	 * Type url  http://localhost:8080/user/delete/Write your username here	
	 * And Click Send
	*/
	
	@DeleteMapping("/delete/{username}")
	public String deleteUser(@PathVariable("username") String username)
	{
		userService.deleteUser(username);
		
		return "User Deleted Successfully...";
		
	}
	
//========== getall Users ====================================================================================================
	/*
	 * select request type GET
	 * Type url  http://localhost:8080/user/getall
	 * select Body
	 * select form-data
	 * In Key Column write "pageNo" and its Integer value in Value Column
	 * Again In Key Column in below row(below pageNo) write "pageSize" and its Integer value in Value Column
	 * And Click Send
	
	*/
	
	@GetMapping("/getall") // try adding this after url:    ?pageNo=1&pageSize=2
	public  List<Users> getAllUsers(@RequestParam(value = "pageNo",defaultValue="0",required = false) Integer pageNo,
			@RequestParam(value = "pageSize",defaultValue = "1", required = false) Integer pageSize)
	{
		System.out.println("pageNo : " + pageNo);
		System.out.println("pageSize : " + pageSize);
		
		return userService.getAllUsers(pageNo,pageSize);
	}
	
//============ get User by id/username ================================================================================	
	/*
	 * select request type GET 
	 * Type url  http://localhost:8080/user/Write your username here
	 * And Click Send	
	*/
	
	@GetMapping("/{username}")
	public  Users getSingleUser(@PathVariable("username") String username)
	{
		
		return userService.getUserById(username);
	}
	
//====== check login ====================================================================================================
	/*
	 This is restricted url which is only accessible after login.
	 so you have to try to login and check if the url is accessible or not
	 if not then credentials you entered are wrong.
	
	 * select request type GET 
	 * Type url  http://localhost:8080/user/checklogin
	 * select Authorization
	 * In Type select Basic Auth
	 * Type your Username and Password
	 * And Click Send
	
	*/
	
	@GetMapping("/checklogin")
	public String UserPage()
	{
		return "This Page is only accessible to loggedIn User";
	}	
	
	
//========== verify otp ===================================================================================================
	/*
	 * select request type POST 
	 * Type url  http://localhost:8080/user/delete/Write otp here	
	 * And Click Send
	*/
	
	
	@PostMapping("/verifyotp/{otp}")   //@Valid is used for activating working of validator
	public String verifyOtp(@PathVariable("otp") String otp,HttpSession session)
	{
		try {
			
		
			
			String msg="Something went wrong !";
			
			if(otpList.contains(otp))
			{
			
				msg="Otp is verified successfully! Now you can update your password..";
				
				session.setAttribute("otp", otp);
				
				System.out.println("otp: "+ session.getAttribute("otp").toString());
								
			}
			else {
				msg = "Failed to verfity Otp! Otp is wrong..";
			}
			
			return msg;
		
		}catch (Exception e) {
			return "Something went Wrong !!";
		}	
		
		
	}
	
//=========== update Password =================================================================================================
	/*
	 * select request type PUT
	 * Type url  http://localhost:8080/user/updatepassword
	 * select Body
	 * select form-data
	 * In Key Column write "newPassword" and its String value in Value Column
	 * And Click Send
	
	*/
	@PutMapping("/updatepassword")   
	public String updatePassword(@RequestParam("newPassword")  String newPassword,HttpSession session)
	{
		
		String msg="Something went wrong !";
		try {
			

					
			String email = session.getAttribute("email").toString();
			String otp = session.getAttribute("otp").toString();
						
			System.out.println("otp: " + otp);
			System.out.println("email:" + email);
								
			if(otpList.contains(otp))
			{
			
				if(userRepo.getUsersByEmail(email) != null)
				{
					Users user= userRepo.getUsersByEmail(email);
					user.setPassword(passwordEncoder.encode(newPassword));
					  userRepo.save(user);

						msg = "User password successfully updated";		
						
						session.invalidate();
				}
				else {		
					msg="Failed to Update User Password, No username is specified";
				}
			}
			else {
				msg = "Failed to update Password Otp is expired";
			}
			
			return msg;
		
		}catch (Exception e) {
			return msg;
		}	
		
		
	}

//=========================================================================================================================

	
	
}
