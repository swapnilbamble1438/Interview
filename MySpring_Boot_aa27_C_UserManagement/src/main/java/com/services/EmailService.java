package com.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.model.EmailRequest;

@Service
public class EmailService {
	
	@Autowired
	private JavaMailSender javaMailSender;
	
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
	
	public boolean sendEmail(EmailRequest email)
	{
		
		 boolean f =false;

		  Random randomGenerator = new Random();
		int randomindex = randomGenerator.nextInt(otpList.size());
		
		String message = otpList.get(randomindex);		
		
		try {
				
				MimeMessage mmessage = javaMailSender.createMimeMessage();
				MimeMessageHelper mhelper = new MimeMessageHelper(mmessage);
				
				mhelper.setFrom("User_Management");
				mhelper.setTo(email.getEmail());
				mhelper.setSubject("Your OTP");
				mhelper.setText(message);
				
				javaMailSender.send(mmessage);

		
		System.out.println("Email send successfully.....");
		f= true;
		
		}catch (Exception e) {
			e.printStackTrace();
		}
		return f;
		
	}
	

	

}
