package com.chaitanya.auth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.chaitanya.auth.entity.ApplicationUser;
import com.chaitanya.auth.repo.UserRepository;

import static java.util.Collections.emptyList;

import java.util.Collection;
import java.util.Collections;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	@Autowired
    private UserRepository applicationUserRepository;

    public UserDetailsServiceImpl(UserRepository applicationUserRepository) {
        this.applicationUserRepository = applicationUserRepository;
    }

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        ApplicationUser applicationUser = applicationUserRepository.findByUserName(username);
        if (applicationUser == null) {
            throw new UsernameNotFoundException(username);
        }
        return new User(applicationUser.getUserName(), applicationUser.getPassword(),null);
    }
}