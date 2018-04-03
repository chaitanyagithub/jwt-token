package com.chaitanya.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.chaitanya.auth.entity.ApplicationUser;
import com.chaitanya.auth.repo.UserRepository;

public class UserController {
	
	private UserRepository userRepo;
	
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	public UserController(UserRepository userRepo,BCryptPasswordEncoder bCryptPasswordEncoder) {
		this.userRepo = userRepo;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}
	
	@PostMapping("/sign-up")
	public void signUp(@RequestBody ApplicationUser user) {
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		userRepo.save(user);
	}

}
