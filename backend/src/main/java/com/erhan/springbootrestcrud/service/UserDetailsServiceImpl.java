package com.erhan.springbootrestcrud.service;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.erhan.springbootrestcrud.dao.UserDAO;
import com.erhan.springbootrestcrud.model.User;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	UserDAO userDAO;
	
	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		User user = userDAO.findByUserName(userName);
		if(user == null) {
			throw new UsernameNotFoundException("Invalid username or password");
		}
		return new org.springframework.security.core.userdetails.User(
				user.getUserName(), 
				user.getPassword(), 
				Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN")));
	}

}
