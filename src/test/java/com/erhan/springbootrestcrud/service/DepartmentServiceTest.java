package com.erhan.springbootrestcrud.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.erhan.springbootrestcrud.dao.DepartmentDAO;
import com.erhan.springbootrestcrud.model.Department;

@RunWith(MockitoJUnitRunner.class)
public class DepartmentServiceTest {

private static final Logger logger = LoggerFactory.getLogger(DepartmentServiceTest.class);
	
	@Mock
	DepartmentDAO mockDepartmentDAO;
	
	@Mock
	Page<Department> mockPageDepartment;
	
	@InjectMocks
	DepartmentServiceImpl departmentService;
	
	private Department department;
	
	@Before
	public void setUp() {
		department = new Department("Üretim");
	}
	
	@Test
	public void testCreate() {
		logger.info("testCreate is started.");
		
		departmentService.create(department);
		
		verify(mockDepartmentDAO, times(1)).save(any(Department.class));
		
		logger.info("testCreate is successful.");
	}
	
	@Test
	public void testUpdate() {
		logger.info("testUpdate is started.");
		
		departmentService.update(department);
		
		verify(mockDepartmentDAO, times(1)).save(any(Department.class));
		
		logger.info("testUpdate is successful.");
	}
	
	@Test
	public void testRemove() {
		logger.info("testRemove is started.");
		
		departmentService.remove(department);
		
		verify(mockDepartmentDAO, times(1)).delete(any(Department.class));
		
		logger.info("testRemove is successful.");
	}
	
	@Test
	public void testRemoveById() {
		logger.info("testRemoveById is started.");
		
		departmentService.removeById(anyLong());
		
		verify(mockDepartmentDAO, times(1)).deleteById(anyLong());
		
		logger.info("testRemoveById is successful.");
	}
	
	@Test
	public void testFindById() {
		logger.info("testFindById is started.");
		
		when(mockDepartmentDAO.findById(2L)).thenReturn(Optional.of(department));
		
		Department findById = departmentService.findById(2L);
		assertNotNull(findById);
		assertEquals(findById.getDepartmentName(), department.getDepartmentName());
		
		verify(mockDepartmentDAO, times(1)).findById(anyLong());
		
		logger.info("testFindById is successful.");
	}
	
	@Test
	public void testFindAll() {
		logger.info("testFindAll is started.");
		
		List<Department> departmentList = new ArrayList<Department>();
		departmentList.add(new Department("Üretim"));
		departmentList.add(new Department("Finans"));
		
		when(mockDepartmentDAO.findAll()).thenReturn(departmentList);
		
		List<Department> findAll = departmentService.findAll();
		assertNotNull(findAll);
		assertEquals(findAll.size(), 2);
		assertEquals(findAll.get(0).getDepartmentName(), "Üretim");
		
		verify(mockDepartmentDAO, times(1)).findAll();
		
		logger.info("testFindAll is successful.");
	}
	
	@Test
	public void testFindByDepartmentName() {
		logger.info("testFindByDepartmentName is started.");
				
		when(mockDepartmentDAO.findFirstByDepartmentName(anyString())).thenReturn(department);
		
		Department findByDepartmentName = departmentService.findByDepartmentName("Üretim");
		assertNotNull(findByDepartmentName);
		assertEquals(findByDepartmentName.getDepartmentName(), "Üretim");
		
		verify(mockDepartmentDAO, times(1)).findFirstByDepartmentName(anyString());
		
		logger.info("testFindByDepartmentName is successful.");
	}
	
	@Test
	public void testAllPaginated() {
		logger.info("testAllPaginated is started.");
		
		List<Department> departmentList = new ArrayList<Department>();
		departmentList.add(new Department("Üretim"));
		departmentList.add(new Department("Finans"));;
		
		when(mockDepartmentDAO.findAll(any(Pageable.class))).thenReturn(mockPageDepartment);
		when(mockPageDepartment.getContent()).thenReturn(departmentList);
		
		List<Department> allDepartmentsPaginated = departmentService.findAllPaginated(0, 2);
		assertNotNull(allDepartmentsPaginated);
		assertEquals(allDepartmentsPaginated.get(0).getDepartmentName(), "Üretim");
		
		verify(mockDepartmentDAO, times(1)).findAll(any(Pageable.class));
		verify(mockPageDepartment, times(1)).getContent();
		
		
		logger.info("testAllPaginated is successful.");
	}
}
