package com.erhan.springbootrestcrud.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.erhan.springbootrestcrud.model.User;

public interface UserDAO extends JpaRepository<User, Long> {
	public User findByUserName(String userName);
}
