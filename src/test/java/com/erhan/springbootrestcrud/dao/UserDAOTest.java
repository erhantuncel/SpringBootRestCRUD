package com.erhan.springbootrestcrud.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import com.erhan.springbootrestcrud.model.User;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserDAOTest {

	public static final Logger logger = LoggerFactory.getLogger(UserDAOTest.class);
	
	@Autowired
	UserDAO userDAO;
	
	@Test
	@Sql(scripts = {"classpath:test.sql"})
	public void testFindByUserName() {
		logger.info("testFindByUserName is started.");
		
		User findByUserName = userDAO.findByUserName("admin");
		assertNotNull(findByUserName);
		assertEquals(findByUserName.getUserName(), "admin");
		assertNotNull(findByUserName.getPassword());
		
		logger.info("testFindByUserName is successful.");
	}
}
