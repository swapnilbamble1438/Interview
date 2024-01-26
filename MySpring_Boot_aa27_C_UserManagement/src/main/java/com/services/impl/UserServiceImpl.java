package com.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.exceptions.CustomException;
import com.model.Users;
import com.repositories.UserRepo;
import com.services.UserService;


@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
// ======== create User ==========================================================================================
	@Override
	public Users createUser(Users user) {
		
		user.setRole("ROLE_NORMAL");
		user.setPassword(passwordEncoder.encode(user.getPassword()));

	
		Users savedUser = userRepo.save(user);
		
		return savedUser;
	
	}



	
//=========== get User By username/id =============================================================================
	
	@Override
	public Users getUserById(String username) {

		Users user = userRepo.findById(username)
				.orElseThrow(()-> new CustomException("User not found with id/username: "+ username));

		
		return user;
	}


//============== get all Users ===================================================================================
	
	@Override
	public List<Users> getAllUsers(Integer pageNo,Integer pageSize) {

	
	Pageable pageable = PageRequest.of(pageNo, pageSize);

	Page<Users> pageUser = userRepo.findAll(pageable);
	 List<Users> users = pageUser.getContent();

	
		return users;
	}

//============== delete User by username/id ========================================================================

	@Override
	public void deleteUser(String username) {


		Users user = userRepo.findById(username)
				.orElseThrow(()-> new CustomException("User not found with id/username: "+ username));

		
	
		userRepo.delete(user);
	}




//============== update User password ========================================================================
	
	@Override
	public Users updatePassword(Users user) {
		
		user.setRole("ROLE_NORMAL");
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		
	
		Users savedUser = userRepo.save(user);
		
		return savedUser;
	
	}
//=============================================================================================================
	
	

}
