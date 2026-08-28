package com.springboot.scm.services;

import java.util.List;
import java.util.Optional;

import com.springboot.scm.entities.User;

public interface UserService {
	
	User saveUser(User user);
	
	Optional<User> getUserById(String id);
	
	User getUserByEmail(String email);
	
	Optional<User> updateUser(User user);
	
	void deleteUser(String id);
	
	boolean isUserExist(String userId);
	
	
	boolean isUserExistByEmail(String email);
	
	List<User> getAllUsers();

}
