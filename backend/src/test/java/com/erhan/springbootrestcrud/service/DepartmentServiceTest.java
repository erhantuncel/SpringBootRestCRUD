package com.erhan.springbootrestcrud.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.erhan.springbootrestcrud.dao.DepartmentDAO;
import com.erhan.springbootrestcrud.dto.DepartmentDTO;
import com.erhan.springbootrestcrud.model.Department;
import com.erhan.springbootrestcrud.model.PageForClient;


@RunWith(MockitoJUnitRunner.class)
public class DepartmentServiceTest {

private static final Logger logger = LoggerFactory.getLogger(DepartmentServiceTest.class);
	
	@Mock
	DepartmentDAO mockDepartmentDAO;
	
	@Mock
	Page<Department> mockPageDepartment;
	
	@Mock
	ModelMapper mockModelMapper;
	
	@InjectMocks
	DepartmentServiceImpl departmentService;
	
	private DepartmentDTO departmentDTO;
	private Department department;
	private DepartmentDTO[] departmentDTOArray;
	
	@Before
	public void setUp() {
		department = new Department("Üretim");
		departmentDTO = new DepartmentDTO();
		departmentDTO.setDepartmentName("Üretim");
		departmentDTOArray = new DepartmentDTO[] {new DepartmentDTO("Üretim"), 
				  new DepartmentDTO("Finans")};
	}
	
	@Test
	public void testCreate() {
		logger.info("testCreate is started.");
		
		when(mockModelMapper.map(departmentDTO, Department.class)).thenReturn(department);
		
		DepartmentDTO departmentDTOFromDB = departmentService.create(departmentDTO);
		assertEquals(departmentDTOFromDB, departmentDTO);
		
		verify(mockDepartmentDAO, times(1)).save(any(Department.class));
		
		logger.info("testCreate is successful.");
	}
	
	@Test
	public void testUpdate() {
		logger.info("testUpdate is started.");
		
		when(mockModelMapper.map(departmentDTO, Department.class)).thenReturn(department);
		when(mockModelMapper.map(department, DepartmentDTO.class)).thenReturn(departmentDTO);
		
		DepartmentDTO departmentDTOUpdated = departmentService.update(departmentDTO);
		assertEquals(departmentDTOUpdated, departmentDTO);
		
		verify(mockDepartmentDAO, times(1)).save(any(Department.class));
		
		logger.info("testUpdate is successful.");
	}
	
	@Test
	public void testRemove() {
		logger.info("testRemove is started.");
		
		when(mockModelMapper.map(departmentDTO, Department.class)).thenReturn(department);
		
		departmentService.remove(departmentDTO);
		
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
		when(mockModelMapper.map(department, DepartmentDTO.class)).thenReturn(departmentDTO);
		
		DepartmentDTO findById = departmentService.findById(2L);
		assertNotNull(findById);
		assertEquals(findById.getDepartmentName(), departmentDTO.getDepartmentName());
		
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
		when(mockModelMapper.map(departmentList, DepartmentDTO[].class)).thenReturn(departmentDTOArray);
		
		List<DepartmentDTO> findAll = departmentService.findAll();
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
		when(mockModelMapper.map(department, DepartmentDTO.class)).thenReturn(departmentDTO);
		
		DepartmentDTO findByDepartmentName = departmentService.findByDepartmentName("Üretim");
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
		departmentList.add(new Department("Finans"));
		
		when(mockDepartmentDAO.findAll(any(Pageable.class))).thenReturn(mockPageDepartment);
		when(mockPageDepartment.getContent()).thenReturn(departmentList);	
		when(mockModelMapper.map(departmentList, DepartmentDTO[].class)).thenReturn(departmentDTOArray);
		
		PageForClient<DepartmentDTO> pageForClient = departmentService.findAllPaginated(PageRequest.of(0, 2));
		assertNotNull(pageForClient);
		assertEquals(pageForClient.getContent().size(), 2);
		assertEquals(pageForClient.getContent().get(0).getDepartmentName(), "Üretim");
		
		verify(mockDepartmentDAO, times(1)).findAll(any(Pageable.class));
		verify(mockPageDepartment, times(1)).getContent();
		
		
		logger.info("testAllPaginated is successful.");
	}
}
