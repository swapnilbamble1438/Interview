package com.controllers;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.model.EmailRequest;
import com.services.EmailService;

@RestController
public class EmailController {
	
	@Autowired
	private EmailService emailService;
	
	
	
//====== send otp by email / reset password ============================================================================================
	/*
	 * select request type POST 
	 * Type url  http://localhost:8080/resetpassword
	 * Select Body 
	 * select raw and JSON and write below code
			
		{
	    "email":"Your email id"
		}
	
	* And Click Send	
	
	*/

	@PostMapping("/resetpassword")
	public ResponseEntity<?> sendEmail(@RequestBody EmailRequest request,HttpSession session)
	{

		
		session.setAttribute("email", request.getEmail());
		

		boolean result = emailService.sendEmail(request);
		if(result) {
			return ResponseEntity.ok("Otp is sent successfully..");
		}
		else {
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to send Otp..!");
		}
	}
	
//============================================================================================================================	
	
}
