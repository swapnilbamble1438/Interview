package com.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.exceptions.CustomException;
import com.model.Users;
import com.repositories.UserRepo;
import com.services.UserService;

@Service
public class CustomUserDetailsService implements UserDetailsService  {

	@Autowired
	private UserRepo userRepo;
	
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		//loading user from database by username
		Users user = this.userRepo.findById(username)
				.orElseThrow(()-> new CustomException("Username or Password is wrong !"));
		
		
		
		return new MyUserDetails(user);
	}

}
