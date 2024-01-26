package com.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.model.Users;

public interface UserRepo extends JpaRepository<Users, String> {


	@Query("select v from Users v where v.username = :username")
	public Users getUserByUsername(@Param("username") String username);
	
	@Query("select v from Users v where v.email = :email")
	public Users getUsersByEmail(@Param("email") String email);
	
	
	
	
	
}
