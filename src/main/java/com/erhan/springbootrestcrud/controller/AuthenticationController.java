package com.erhan.springbootrestcrud.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.erhan.springbootrestcrud.dao.UserDAO;
import com.erhan.springbootrestcrud.dto.LoginRequestDTO;
import com.erhan.springbootrestcrud.dto.TokenResponseDTO;
import com.erhan.springbootrestcrud.model.User;
import com.erhan.springbootrestcrud.security.JwtTokenUtil;
import com.erhan.springbootrestcrud.util.ApiPaths;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(ApiPaths.PathForAuthenticationController.token)
public class AuthenticationController {
	
	
	@Autowired
	AuthenticationManager authenticationManager;
	
	@Autowired
	UserDAO userDAO;
	
	@Autowired
	JwtTokenUtil jwtTokenUtil;
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<TokenResponseDTO> login(@RequestBody LoginRequestDTO request) throws AuthenticationException {
		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUserName(), request.getPassword()));
		User user = userDAO.findByUserName(request.getUserName());
		String token = jwtTokenUtil.generateToken(user);
		return ResponseEntity.ok(new TokenResponseDTO(user.getUserName(), token));
	}
}
