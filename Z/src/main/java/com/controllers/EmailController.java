package com.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.model.EmailRequest;
import com.services.EmailService;

@RestController
public class EmailController {
	
	@Autowired
	private EmailService emailService;
	
	
	@RequestMapping("/welcome")
	public String welcome()
	{
		return "Hello this is my Email API";
	}

	
	/*
	 select Post and type url  http://localhost:8080/sendemail
	 and int body select raw and JSON and paste 
	 below code
	{
    "to":"swapnilbamble4@gmail.com",
    "subject":"Hello Self",
    "message":"Hii Self How are you doing"
}
	*/
	// api to send email
	@RequestMapping(value="/forgotpassword",method = RequestMethod.POST)
	public ResponseEntity<?> sendEmail(@RequestBody EmailRequest request)
	{
		
		boolean result = emailService.sendEmail(request);
		if(result) {
			return ResponseEntity.ok("Email is sent successfully..");
		}
		else {
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Email not sent..!");
		}
	}
	
}
