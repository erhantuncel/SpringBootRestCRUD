package com.erhan.springbootrestcrud.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import com.erhan.springbootrestcrud.model.Department;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DepartmentDAOTest {

	public static final Logger logger = LoggerFactory.getLogger(DepartmentDAOTest.class);
	
	@Autowired
	DepartmentDAO departmentDAO;
	
	@Test
	@Sql(scripts = {"classpath:test.sql"})
	public void testSave() {
		logger.info("testSave is started.");
		
		Department departmentSatis = new Department("Satış");
		departmentDAO.save(departmentSatis);
		departmentDAO.flush();
		
		assertNotNull(departmentSatis);
		assertEquals(departmentSatis.getId(), Long.valueOf("4"));
		
		logger.info("testSave is successful.");
	}
	
	@Test
	@Sql(scripts = {"classpath:test.sql"})
	public void testFindAll() {
		logger.info("testFindAll is started.");
		
		List<Department> allDepartments = departmentDAO.findAll();
		
		assertNotNull(allDepartments);
		assertEquals(allDepartments.size(), 3);
		
		logger.info("testFindAll is successful.");
	}
	
	@Test
	@Sql(scripts = {"classpath:test.sql"})
	public void testFindById() {
		logger.info("testFindById is started.");
		
		Department deparmentARGE = departmentDAO.findById(2L).orElse(null);
		
		assertNotNull(deparmentARGE);
		assertEquals(deparmentARGE.getId(), Long.valueOf("2"));
		assertEquals(deparmentARGE.getDepartmentName(), "AR-GE");
		
		logger.info("testFindById is successful.");
	}
	
	@Test
	@Sql(scripts = {"classpath:test.sql"})
	public void testDelete() {
		logger.info("testDelete is started.");
		
		Department deparmentARGE = departmentDAO.findById(2L).orElse(null);
		assertNotNull(deparmentARGE);
		departmentDAO.delete(deparmentARGE);
		departmentDAO.flush();
		
		List<Department> allDeparment = departmentDAO.findAll();
		assertNotNull(allDeparment);
		assertEquals(allDeparment.size(), 2);
		
		logger.info("testDelete is successful.");
	}
	
	@Test
	@Sql(scripts = {"classpath:test.sql"})
	public void testFindFirstByDepartmentName() {
		logger.info("testFindFirstByDepartmentName is started.");
		
		Department departmentUretim = departmentDAO.findFirstByDepartmentName("Üretim");
		
		assertNotNull(departmentUretim);
		assertEquals(departmentUretim.getId(), Long.valueOf("1"));
		assertEquals(departmentUretim.getDepartmentName(), "Üretim");
		
		logger.info("testFindFirstByDepartmentName is successful.");
	}
}
