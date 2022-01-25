package com.shop.admin.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.shop.common.entity.Role;
import com.shop.common.entity.User;

@Service
public class UserService {
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private RoleRepository roleRepo;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	// show list all Users
	public List<User> listAll(){
		return (List<User>) userRepo.findAll();
	}
	
	// show list all Roles
	public List<Role> listRoles(){
		return (List<Role>) roleRepo.findAll();
	}
	
	// create a new User
	public void save(User user) {
		encodePassword(user);
		userRepo.save(user);
	}
	
	// encode password
	private void encodePassword(User user) {
		String encodePassword = passwordEncoder.encode(user.getPassword());
		user.setPassword(encodePassword);
	}
	
	public boolean isEmailUnique(String email) {
		User userByEmail = userRepo.getUserByEmail(email);
		
		return userByEmail == null;
	}
}
