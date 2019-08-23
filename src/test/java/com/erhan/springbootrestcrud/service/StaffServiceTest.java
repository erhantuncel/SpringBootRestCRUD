package com.erhan.springbootrestcrud.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

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

import com.erhan.springbootrestcrud.dao.DepartmentDAO;
import com.erhan.springbootrestcrud.dao.StaffDAO;
import com.erhan.springbootrestcrud.dto.DepartmentDTO;
import com.erhan.springbootrestcrud.dto.StaffDTO;
import com.erhan.springbootrestcrud.model.Department;
import com.erhan.springbootrestcrud.model.PageForClient;
import com.erhan.springbootrestcrud.model.Staff;

import javassist.NotFoundException;

@RunWith(MockitoJUnitRunner.class)
public class StaffServiceTest {

	public static final Logger logger = LoggerFactory.getLogger(StaffServiceTest.class);
	
	@Mock
	StaffDAO mockStaffDAO;
	
	@Mock
	DepartmentDAO mockDepartmentDAO;
	
	@Mock
	Page<Staff> mockPageStaff;
	
	@Mock
	ModelMapper mockModelMapper;
	
	@InjectMocks
	StaffServiceImpl staffService;
	
	private Staff staff;
	private StaffDTO staffDTO;
	private Department department;
	private DepartmentDTO departmentDTO;
	private List<Staff> staffList;
	private List<StaffDTO> staffDTOList;
	private StaffDTO[] staffDTOArray;
	
	@Before
	public void setUp() {
		department = new Department("Üretim");
		department.setId(1L);
		departmentDTO = new DepartmentDTO("Üretim");
		departmentDTO.setId(1L);
		
		Date dateForFirstStaff = new Date();
		staff = new Staff("Mehmet", "ÇALIŞKAN", "1231231231", "mehmet@abc.com", dateForFirstStaff, department);
		staff.setId(1L);
		staffDTO = new StaffDTO("Mehmet", "ÇALIŞKAN", "1231231231", "mehmet@abc.com", dateForFirstStaff, departmentDTO);
		staffDTO.setId(1L);
		
		staffList = new ArrayList<Staff>();
		staffDTOList = new ArrayList<StaffDTO>();
		
		staffList.add(staff);
		staffDTOList.add(staffDTO);
		Date dateForSecondStaff = new Date();
		Staff staff2 = new Staff("Ahmet", "TEMBEL", "9879879879", "ahmet@abc.com", dateForSecondStaff, department);
		staff2.setId(2L);
		StaffDTO staffDTO2 = new StaffDTO("Ahmet", "TEMBEL", "9879879879", "ahmet@abc.com", dateForSecondStaff, departmentDTO);
		staff2.setId(2L);
		staffList.add(staff2);
		staffDTOList.add(staffDTO2);
		
		department.getStaffList().add(staff);
		
		staffDTOArray = new StaffDTO[] {
				new StaffDTO("Mehmet", "ÇALIŞKAN", "1231231231", "mehmet@abc.com", dateForFirstStaff, departmentDTO),
				new StaffDTO("Ahmet", "TEMBEL", "9879879879", "ahmet@abc.com", dateForSecondStaff, departmentDTO)
		};
	}
	
	@Test
	public void testCreate() {
		logger.info("testCreate is started.");
		
		when(mockModelMapper.map(staffDTO, Staff.class)).thenReturn(staff);
		
		StaffDTO staffDTOFromDB = staffService.create(staffDTO);
		assertEquals(staffDTOFromDB, staffDTO);
		
		verify(mockStaffDAO, times(1)).save(any(Staff.class));
		
		logger.info("testCreate is successful.");
	}
	
	@Test
	public void testCreateWithDepartmentId() throws NotFoundException {
		logger.info("testCreateWithDepartmentId is started.");
		
		when(mockDepartmentDAO.findById(anyLong())).thenReturn(Optional.of(department));
		when(mockModelMapper.map(department, DepartmentDTO.class)).thenReturn(departmentDTO);
		when(mockModelMapper.map(staffDTO, Staff.class)).thenReturn(staff);
		when(mockModelMapper.map(staff, StaffDTO.class)).thenReturn(staffDTO);
		
		staffService.createWithDepartmentId(staffDTO, 2L);
		assertEquals(staff.getDepartment(), department);
		
		verify(mockDepartmentDAO, times(1)).findById(anyLong());
		verify(mockStaffDAO, times(1)).save(any(Staff.class));
		verify(mockModelMapper, times(1)).map(department, DepartmentDTO.class);
		verify(mockModelMapper, times(1)).map(staffDTO, Staff.class);
		verify(mockModelMapper, times(1)).map(staff, StaffDTO.class);
		
		logger.info("testCreateWithDepartmentId is successful.");
	}
	
	@Test(expected = NotFoundException.class)
	public void testCreateWithDepartmentIdWithException() throws NotFoundException {
		logger.info("testCreateWithDepartmentIdWithException is started.");
		when(mockDepartmentDAO.findById(anyLong())).thenReturn(Optional.empty());
		
		staffService.createWithDepartmentId(staffDTO, 2L);
		
		verify(mockDepartmentDAO, times(1)).findById(anyLong());
		
		logger.info("testCreateWithDepartmentIdWithException is successful.");
	}
	
	@Test
	public void testUpdate() {
		logger.info("testUpdate is started.");
		
		when(mockModelMapper.map(staffDTO, Staff.class)).thenReturn(staff);
		when(mockModelMapper.map(staff, StaffDTO.class)).thenReturn(staffDTO);
		
		staffService.update(staffDTO);
		
		verify(mockStaffDAO, times(1)).save(any(Staff.class));
		verify(mockModelMapper, times(1)).map(staffDTO, Staff.class);
		verify(mockModelMapper, times(1)).map(staff, StaffDTO.class);
		
		logger.info("testUpdate is successful.");
	}
	
	@Test
	public void testUpdateWithDepartmentId() throws IllegalArgumentException, NotFoundException {
		logger.info("testUpdateWithDepartmentId is started.");
		
		Staff staffUpdate = new Staff("Mehmet", "ÇALIŞKAN", "4564564566", "mehmet2@abc.com", new Date(), department);
		StaffDTO staffUpdateDTO = new StaffDTO("Mehmet", "ÇALIŞKAN", "4564564566", "mehmet2@abc.com", new Date(), departmentDTO);
		
		when(mockDepartmentDAO.findById(anyLong())).thenReturn(Optional.of(department));
		when(mockStaffDAO.findById(anyLong())).thenReturn(Optional.of(staff));
		when(mockModelMapper.map(staffUpdateDTO, Staff.class)).thenReturn(staffUpdate);
		when(mockModelMapper.map(staff, StaffDTO.class)).thenReturn(staffDTO);
		
		staffService.updateWithDepartmentId(1L, 1L, staffUpdateDTO);
		
		verify(mockDepartmentDAO, times(1)).findById(anyLong());
		verify(mockStaffDAO, times(1)).findById(anyLong());
		verify(mockModelMapper, times(1)).map(staffUpdateDTO, Staff.class);
		verify(mockModelMapper, times(1)).map(staff, StaffDTO.class);
		
		assertEquals(staff.getPhone(), staffUpdate.getPhone());
		assertEquals(staff.getEmail(), staffUpdate.getEmail());
		
		logger.info("testUpdateWithDepartmentId is successful.");
	}
	
	@Test(expected = NotFoundException.class)
	public void testUpdateWithDepartmentIdWithNotFoundDepartment() throws IllegalArgumentException, NotFoundException {
		logger.info("testUpdateWithDepartmentIdWithNotFoundDepartment is started.");
		
		when(mockDepartmentDAO.findById(anyLong())).thenReturn(Optional.empty());
		
		StaffDTO staffUpdate = new StaffDTO("Mehmet", "ÇALIŞKAN", "4564564566", "mehmet2@abc.com", new Date(), departmentDTO);
		staffService.updateWithDepartmentId(1L, 1L, staffUpdate);
		
		verify(mockDepartmentDAO, times(1)).findById(anyLong());
		
		logger.info("testUpdateWithDepartmentIdWithNotFoundDepartment is successful.");
	}
	
	@Test(expected = NotFoundException.class)
	public void testUpdateWithDepartmentIdWithNotFoundStaff() throws IllegalArgumentException, NotFoundException {
		logger.info("testUpdateWithDepartmentIdWithNotFoundStaff is started.");
		
		when(mockDepartmentDAO.findById(anyLong())).thenReturn(Optional.of(department));
		when(mockStaffDAO.findById(anyLong())).thenReturn(Optional.empty());
		
		StaffDTO staffUpdate = new StaffDTO("Mehmet", "ÇALIŞKAN", "4564564566", "mehmet2@abc.com", new Date(), departmentDTO);
		staffService.updateWithDepartmentId(1L, 1L, staffUpdate);
		
		verify(mockDepartmentDAO, times(1)).findById(anyLong());
		verify(mockStaffDAO, times(1)).findById(anyLong());
		
		logger.info("testUpdateWithDepartmentIdWithNotFoundStaff is successful.");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testUpdateWithDepartmentIdWithIllegalArgumentException() throws IllegalArgumentException, NotFoundException {
		logger.info("testUpdateWithDepartmentId is started.");
		
		Staff unknownStaff = new Staff("Mehmet", "ÇALIŞKAN", "1231231231", "mehmet2@abc.com", new Date(), department);
		
		when(mockDepartmentDAO.findById(anyLong())).thenReturn(Optional.of(department));
		when(mockStaffDAO.findById(anyLong())).thenReturn(Optional.of(unknownStaff));
		
		StaffDTO staffUpdate = new StaffDTO("Mehmet", "ÇALIŞKAN", "4564564566", "mehmet2@abc.com", new Date(), departmentDTO);
		staffService.updateWithDepartmentId(1L, 1L, staffUpdate);
		
		verify(mockDepartmentDAO, times(1)).findById(anyLong());
		verify(mockStaffDAO, times(1)).findById(anyLong());
		
		logger.info("testUpdateWithDepartmentId is successful.");
	}
	
	@Test
	public void testRemove() {
		logger.info("testRemove is started.");
		
		when(mockModelMapper.map(staffDTO, Staff.class)).thenReturn(staff);
		
		staffService.remove(staffDTO);
		
		verify(mockStaffDAO, times(1)).delete(any(Staff.class));
		verify(mockModelMapper, times(1)).map(staffDTO, Staff.class);
		
		logger.info("testRemove is successful.");
	}
	
	@Test
	public void testRemoveWithDepartmentId() throws IllegalArgumentException, NotFoundException {
		logger.info("testRemoveWithDepartmentId is started.");
		
		when(mockDepartmentDAO.findById(anyLong())).thenReturn(Optional.of(department));
		when(mockStaffDAO.findById(anyLong())).thenReturn(Optional.of(staff));
		
		staffService.removeWithDepartmentId(1L, 1L);
		
		verify(mockDepartmentDAO, times(1)).findById(anyLong());
		verify(mockStaffDAO, times(1)).findById(anyLong());
		verify(mockDepartmentDAO, times(1)).save(any(Department.class));
		
		logger.info("testRemoveWithDepartmentId is successful.");
	}
	
	@Test(expected = NotFoundException.class)
	public void testRemoveWithDepartmentIdWithNotFoundDepartment() throws IllegalArgumentException, NotFoundException {
		logger.info("testRemoveWithDepartmentIdWithNotFoundDepartment is started.");
		
		when(mockDepartmentDAO.findById(anyLong())).thenReturn(Optional.empty());
		
		staffService.removeWithDepartmentId(1L, 1L);
		
		verify(mockDepartmentDAO, times(1)).findById(anyLong());
		
		logger.info("testRemoveWithDepartmentIdWithNotFoundDepartment is successful.");
	}

	@Test(expected = NotFoundException.class)
	public void testRemoveWithDepartmentIdWithNotFoundStaff() throws IllegalArgumentException, NotFoundException {
		logger.info("testRemoveWithDepartmentIdWithNotFoundStaff is started.");
		
		when(mockDepartmentDAO.findById(anyLong())).thenReturn(Optional.of(department));
		when(mockStaffDAO.findById(anyLong())).thenReturn(Optional.empty());
		
		staffService.removeWithDepartmentId(1L, 1L);
		
		verify(mockDepartmentDAO, times(1)).findById(anyLong());
		verify(mockStaffDAO, times(1)).findById(anyLong());
		verify(mockDepartmentDAO, times(1)).save(any(Department.class));
		
		logger.info("testRemoveWithDepartmentIdWithNotFoundStaff is successful.");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testRemoveWithDepartmentIdWithBadRequest() throws IllegalArgumentException, NotFoundException {
		logger.info("testRemoveWithDepartmentIdWithBadRequest is started.");
		
		Staff unknownStaff = new Staff("Mehmet", "ÇALIŞKAN", "1231231231", "mehmet2@abc.com", new Date(), department);
		
		when(mockDepartmentDAO.findById(anyLong())).thenReturn(Optional.of(department));
		when(mockStaffDAO.findById(anyLong())).thenReturn(Optional.of(unknownStaff));
		
		staffService.removeWithDepartmentId(1L, 1L);
		
		verify(mockDepartmentDAO, times(1)).findById(anyLong());
		verify(mockStaffDAO, times(1)).findById(anyLong());
		verify(mockDepartmentDAO, times(1)).save(any(Department.class));
		
		logger.info("testRemoveWithDepartmentIdWithBadRequest is successful.");
	}
	
	@Test
	public void testFindById() throws NotFoundException {
		logger.info("testFindById is started.");
		
		when(mockStaffDAO.findById(2L)).thenReturn(Optional.of(staff));
		when(mockModelMapper.map(staff, StaffDTO.class)).thenReturn(staffDTO);
		
		StaffDTO findById = staffService.findById(2L);
		makeAssertionOverStaffList(findById, staffDTOList.get(0));
		
		verify(mockStaffDAO, times(1)).findById(anyLong());
		verify(mockModelMapper, times(1)).map(staff, StaffDTO.class);
		
		logger.info("testFindById is successful.");
	}
	
	@Test(expected = NotFoundException.class)
	public void testFindByIdWithException() throws NotFoundException {
		logger.info("testFindByIdWithException is started.");
		
		when(mockStaffDAO.findById(2L)).thenReturn(Optional.ofNullable(null));
		
		staffService.findById(2L);
		
		logger.info("testFindByIdWithException is successful.");
	}
	
	@Test
	public void testFindAll() {
		logger.info("testFindAll is started.");
		
		when(mockStaffDAO.findAll()).thenReturn(staffList);
		when(mockModelMapper.map(staffList, StaffDTO[].class)).thenReturn(staffDTOArray);
		
		List<StaffDTO> allStaffs = staffService.findAll();
		makeAssertionOverStaffList(allStaffs.get(0), staffDTOList.get(0));
		
		verify(mockStaffDAO, times(1)).findAll();
		
		logger.info("testFindAll is successful.");
	}
	
	@Test
	public void testFindAllPaginated() throws NotFoundException {
		logger.info("testFindAllPaginated is started.");
		
		when(mockStaffDAO.findAll(any(Pageable.class))).thenReturn(mockPageStaff);
		when(mockPageStaff.isEmpty()).thenReturn(false);
		when(mockPageStaff.getContent()).thenReturn(staffList);
		when(mockModelMapper.map(staffList, StaffDTO[].class)).thenReturn(staffDTOArray);
		
		PageForClient<StaffDTO> allStaffs = staffService.findAllPaginated(PageRequest.of(1, 2));
		makeAssertionOverStaffList(allStaffs.getContent().get(0), staffDTOList.get(0));
		
		verify(mockStaffDAO, times(1)).findAll(any(Pageable.class));
		verify(mockPageStaff, times(1)).isEmpty();
		verify(mockPageStaff, times(1)).getContent();
		verify(mockModelMapper, times(1)).map(staffList, StaffDTO[].class);
		
		logger.info("testFindAllPaginated is successful.");
	}
	
	@Test(expected = NotFoundException.class)
	public void testFindAllPaginatedWithException() throws NotFoundException {
		logger.info("testFindAllPaginatedWithException is started.");
		
		when(mockStaffDAO.findAll(any(Pageable.class))).thenReturn(mockPageStaff);
		when(mockPageStaff.isEmpty()).thenReturn(true);
		
		staffService.findAllPaginated(PageRequest.of(1, 2));
		
		verify(mockStaffDAO, times(1)).findAll(any(Pageable.class));
		verify(mockPageStaff, times(1)).isEmpty();
		
		logger.info("testFindAllPaginatedWithException is successful.");
	}
	
	@Test
	public void testFindByFirstName() throws NotFoundException {
		logger.info("testFindByFirstName is started.");
		
		when(mockStaffDAO.findByFirstName(anyString())).thenReturn(staffList);
		when(mockModelMapper.map(staffList, StaffDTO[].class)).thenReturn(staffDTOArray);
		
		List<StaffDTO> findByFirstName = staffService.findByFirstName("Ahmet");
		makeAssertionOverStaffList(findByFirstName.get(0), staffDTOList.get(0));
		
		verify(mockStaffDAO, times(1)).findByFirstName(anyString());
		verify(mockModelMapper, times(1)).map(staffList, StaffDTO[].class);
		
		logger.info("testFindByFirstName is successful.");
	}
	
	@Test(expected = NotFoundException.class)
	public void testFindByFirstNameWithNotFoundException() throws NotFoundException {
		logger.info("testFindByFirstName is started.");
		
		when(mockStaffDAO.findByFirstName(anyString())).thenReturn(new ArrayList<>());
		
		staffService.findByFirstName("Sibel");
		
		verify(mockStaffDAO, times(1)).findByFirstName(anyString());
		
		logger.info("testFindByFirstName is successful.");
	}
	
	@Test
	public void testFindByLastName() throws NotFoundException {
		logger.info("testFindByLastName is started.");
		
		when(mockStaffDAO.findByLastName(anyString())).thenReturn(staffList);
		when(mockModelMapper.map(staffList, StaffDTO[].class)).thenReturn(staffDTOArray);
		
		List<StaffDTO> findByLastName = staffService.findByLastName("TEMBEL");
		makeAssertionOverStaffList(findByLastName.get(0), staffDTOList.get(0));
		
		verify(mockStaffDAO, times(1)).findByLastName(anyString());
		verify(mockModelMapper, times(1)).map(staffList, StaffDTO[].class);
		
		logger.info("testFindByLastName is successful.");
	}
	
	@Test(expected = NotFoundException.class)
	public void testFindByLastNameWithNotFoundException() throws NotFoundException {
		logger.info("testFindByLastName is started.");
		
		when(mockStaffDAO.findByLastName(anyString())).thenReturn(new ArrayList<>());
		
		staffService.findByLastName("HIZLI");
		
		verify(mockStaffDAO, times(1)).findByLastName(anyString());
		
		logger.info("testFindByLastName is successful.");
	}
	
	@Test
	public void testFindByPhone() throws NotFoundException {
		logger.info("testFindByPhone is started.");
		
		when(mockStaffDAO.findByPhone(anyString())).thenReturn(staffList);
		when(mockModelMapper.map(staffList, StaffDTO[].class)).thenReturn(staffDTOArray);
		
		List<StaffDTO> findByPhone = staffService.findByPhone("9879879879");
		makeAssertionOverStaffList(findByPhone.get(0), staffDTOList.get(0));
		
		verify(mockStaffDAO, times(1)).findByPhone(anyString());
		verify(mockModelMapper, times(1)).map(staffList, StaffDTO[].class);
		
		logger.info("testFindByPhone is successful.");
	}
	
	@Test(expected = NotFoundException.class)
	public void testFindByPhoneWithNotFoundException() throws NotFoundException {
		logger.info("testFindByPhone is started.");
		
		when(mockStaffDAO.findByPhone(anyString())).thenReturn(new ArrayList<>());
		
		staffService.findByPhone("9879879879");
		
		verify(mockStaffDAO, times(1)).findByPhone(anyString());
		
		logger.info("testFindByPhone is successful.");
	}
	
	@Test
	public void testFindByEmail() throws NotFoundException {
		logger.info("testFindByEmail is started.");
		
		when(mockStaffDAO.findByEmail(anyString())).thenReturn(staffList);
		when(mockModelMapper.map(staffList, StaffDTO[].class)).thenReturn(staffDTOArray);
		
		List<StaffDTO> findByEmail = staffService.findByEmail("ahmet@abc.com");
		makeAssertionOverStaffList(findByEmail.get(0), staffDTOList.get(0));
		
		verify(mockStaffDAO, times(1)).findByEmail(anyString());
		verify(mockModelMapper, times(1)).map(staffList, StaffDTO[].class);
		
		logger.info("testFindByEmail is successful.");
	}
	
	@Test(expected = NotFoundException.class)
	public void testFindByEmailWithNotFoundException() throws NotFoundException {
		logger.info("testFindByEmail is started.");
		
		when(mockStaffDAO.findByEmail(anyString())).thenReturn(new ArrayList<>());
		
		staffService.findByEmail("ahmet@abc.com");
		
		verify(mockStaffDAO, times(1)).findByEmail(anyString());
		
		logger.info("testFindByEmail is successful.");
	}
	
	@Test
	public void testFindByCreateDate() throws NotFoundException {
		logger.info("testFindByRegisteredTime is started.");
		
		when(mockStaffDAO.findByCreateDate(any(Date.class))).thenReturn(staffList);
		when(mockModelMapper.map(staffList, StaffDTO[].class)).thenReturn(staffDTOArray);
		
		List<StaffDTO> findByRegisteredTime = staffService.findByCreateDate(staffList.get(0).getCreateDate());
		
		makeAssertionOverStaffList(findByRegisteredTime.get(0), staffDTOList.get(0));
		verify(mockStaffDAO, times(1)).findByCreateDate(any(Date.class));
		verify(mockModelMapper, times(1)).map(staffList, StaffDTO[].class);
		
		logger.info("testFindByRegisteredTime is successful.");
	}
	
	@Test(expected = NotFoundException.class)
	public void testFindByCreateDateWithNotFoundException() throws NotFoundException {
		logger.info("testFindByRegisteredTime is started.");
		
		when(mockStaffDAO.findByCreateDate(any(Date.class))).thenReturn(new ArrayList<>());
		
		staffService.findByCreateDate(staffList.get(0).getCreateDate());
		
		verify(mockStaffDAO, times(1)).findByCreateDate(any(Date.class));
		
		logger.info("testFindByRegisteredTime is successful.");
	}
	
	@Test
	public void testFindByDepartment() throws NotFoundException {
		logger.info("testFindByDepartment is started.");
		
		when(mockStaffDAO.findByDepartment(any(Department.class))).thenReturn(staffList);
		when(mockModelMapper.map(departmentDTO, Department.class)).thenReturn(department);
		when(mockModelMapper.map(staffList, StaffDTO[].class)).thenReturn(staffDTOArray);
		
		List<StaffDTO> findByDepartment = staffService.findByDepartment(staffDTOList.get(0).getDepartment());
		
		makeAssertionOverStaffList(findByDepartment.get(0), staffDTOList.get(0));
		verify(mockStaffDAO, times(1)).findByDepartment(any(Department.class));
		verify(mockModelMapper, times(1)).map(departmentDTO, Department.class);
		verify(mockModelMapper, times(1)).map(staffList, StaffDTO[].class);
		
		logger.info("testFindByDepartment is successful.");
	}
	
	@Test(expected = NotFoundException.class)
	public void testFindByDepartmentWithNotFoundException() throws NotFoundException {
		logger.info("testFindByDepartment is started.");
		
		when(mockModelMapper.map(departmentDTO, Department.class)).thenReturn(department);
		when(mockStaffDAO.findByDepartment(any(Department.class))).thenReturn(new ArrayList<>());
		
		staffService.findByDepartment(staffDTOList.get(0).getDepartment());
		
		verify(mockStaffDAO, times(1)).findByDepartment(any(Department.class));
		
		logger.info("testFindByDepartment is successful.");
	}
	
	@Test
	public void testFindByDepartmentId() throws NotFoundException {
		logger.info("testFindByDepartmentId is started.");
		
		when(mockStaffDAO.findByDepartment_Id(any(Long.class))).thenReturn(staffList);
		when(mockModelMapper.map(staffList, StaffDTO[].class)).thenReturn(staffDTOArray);
		
		List<StaffDTO> findByDepartment_Id = staffService.findByDepartmentId(staffList.get(0).getDepartment().getId());
		
		makeAssertionOverStaffList(findByDepartment_Id.get(0), staffDTOList.get(0));
		verify(mockStaffDAO, times(1)).findByDepartment_Id(any(Long.class));
		verify(mockModelMapper, times(1)).map(staffList, StaffDTO[].class);
		logger.info("testFindByDepartmentId is successful.");
	}
	
	@Test(expected = NotFoundException.class)
	public void testFindByDepartmentIdWithNotFoundException() throws NotFoundException {
		logger.info("testFindByDepartmentId is started.");
		
		when(mockStaffDAO.findByDepartment_Id(any(Long.class))).thenReturn(new ArrayList<>());
		
		staffService.findByDepartmentId(3L);
		
		verify(mockStaffDAO, times(1)).findByDepartment_Id(any(Long.class));
		
		logger.info("testFindByDepartmentId is successful.");
	}
	
	@Test
	public void testFindByDepartmentIdPaginated() throws NotFoundException {
		logger.info("testFindByDepartmentIdPaginated is started.");
		
		when(mockStaffDAO.findByDepartment_Id(anyLong(), any(Pageable.class))).thenReturn(mockPageStaff);
		when(mockPageStaff.isEmpty()).thenReturn(false);
		when(mockPageStaff.getContent()).thenReturn(staffList);
		when(mockModelMapper.map(staffList, StaffDTO[].class)).thenReturn(staffDTOArray);
		
		PageForClient<StaffDTO> findByDepartment_IdPaginated = staffService
				.findByDepartmentIdPaginated(staffList.get(0).getDepartment().getId(), PageRequest.of(0, 2));
		
		makeAssertionOverStaffList(findByDepartment_IdPaginated.getContent().get(0), staffDTOList.get(0));
		verify(mockStaffDAO, times(1)).findByDepartment_Id(any(Long.class), any(Pageable.class));
		verify(mockPageStaff, times(1)).isEmpty();
		verify(mockPageStaff, times(1)).getContent();
		verify(mockModelMapper, times(1)).map(staffList, StaffDTO[].class);
		
		logger.info("testFindByDepartmentIdPaginated is successful.");
	}
	
	@Test(expected = NotFoundException.class)
	public void testFindByDepartmentIdPaginatedWithNotFoundException() throws NotFoundException {
		logger.info("testFindByDepartmentIdPaginated is started.");
		
		when(mockStaffDAO.findByDepartment_Id(anyLong(), any(Pageable.class))).thenReturn(mockPageStaff);
		when(mockPageStaff.isEmpty()).thenReturn(true);
		
		staffService.findByDepartmentIdPaginated(staffList.get(0).getDepartment().getId(), PageRequest.of(0, 2));
		
		verify(mockStaffDAO, times(1)).findByDepartment_Id(any(Long.class), any(Pageable.class));
		verify(mockPageStaff, times(1)).isEmpty();
		
		logger.info("testFindByDepartmentIdPaginated is successful.");
	}
	
	@Test
	public void testFindByIdAndDepartmentId() throws NotFoundException {
		logger.info("testFindByIdAndDepartmentId is started.");
		
		when(mockStaffDAO.findByIdAndDepartment_Id(anyLong(), anyLong())).thenReturn(staffList);
		when(mockModelMapper.map(staff, StaffDTO.class)).thenReturn(staffDTO);
		
		StaffDTO findByIdAndDepartmentId = staffService.findByIdAndDepartmentId(2L, 3L);
		makeAssertionOverStaffList(findByIdAndDepartmentId, staffDTOList.get(0));
		verify(mockStaffDAO, times(1)).findByIdAndDepartment_Id(anyLong(), anyLong());
		verify(mockModelMapper, times(1)).map(staff, StaffDTO.class);
		
		logger.info("testFindByIdAndDepartmentId is successful.");
	}
	
	@Test(expected = NotFoundException.class)
	public void testFindByIdAndDepartmentIdWithNotFoundException() throws NotFoundException {
		logger.info("testFindByIdAndDepartmentId is started.");
		
		when(mockStaffDAO.findByIdAndDepartment_Id(anyLong(), anyLong())).thenReturn(new ArrayList<>());
		
		staffService.findByIdAndDepartmentId(2L, 3L);
		
		logger.info("testFindByIdAndDepartmentId is successful.");
	}
	
	@Test
	public void testFindByFirstNameAndDepartmentId() throws NotFoundException {
		logger.info("testFindByFirstNameAndDepartmentId is started.");
		
		when(mockStaffDAO.findByFirstNameAndDepartment_Id(any(String.class), anyLong())).thenReturn(staffList);
		when(mockModelMapper.map(staffList, StaffDTO[].class)).thenReturn(staffDTOArray);
		
		List<StaffDTO> findByFirstNameAndDepartmentId = staffService.findByFirstNameAndDepartmentId("Ahmet", 2L);
		makeAssertionOverStaffList(findByFirstNameAndDepartmentId.get(0), staffDTOList.get(0));
		
		verify(mockStaffDAO, times(1)).findByFirstNameAndDepartment_Id(any(String.class), anyLong());
		verify(mockModelMapper, times(1)).map(staffList, StaffDTO[].class);
		
		logger.info("testFindByFirstNameAndDepartmentId is successful.");
	}
	
	@Test(expected = NotFoundException.class)
	public void testFindByFirstNameAndDepartmentIdWithNotFoundException() throws NotFoundException {
		logger.info("testFindByFirstNameAndDepartmentId is started.");
		
		when(mockStaffDAO.findByFirstNameAndDepartment_Id(any(String.class), anyLong())).thenReturn(new ArrayList<>());
		
		staffService.findByFirstNameAndDepartmentId("Ahmet", 2L);
		
		logger.info("testFindByFirstNameAndDepartmentId is successful.");
	}
	
	@Test
	public void testFindByFirstNameAndDepartmentIdPaginated() throws NotFoundException {
		logger.info("testFindByFirstNameAndDepartmentIdPaginated is started.");
		
		when(mockStaffDAO.findByFirstNameAndDepartment_Id(any(String.class), anyLong(), any(Pageable.class))).thenReturn(mockPageStaff);
		when(mockPageStaff.getContent()).thenReturn(staffList);
		when(mockPageStaff.isEmpty()).thenReturn(false);
		when(mockModelMapper.map(staffList, StaffDTO[].class)).thenReturn(staffDTOArray);
		
		PageForClient<StaffDTO> findByFirstNameAndDepartmentIdPaginated = staffService.findByFirstNameAndDepartmentIdPaginated("Ahmet", 2L, PageRequest.of(0, 2));
		makeAssertionOverStaffList(findByFirstNameAndDepartmentIdPaginated.getContent().get(0), staffDTOList.get(0));
		verify(mockStaffDAO, times(1)).findByFirstNameAndDepartment_Id(any(String.class), anyLong(), any(Pageable.class));
		verify(mockPageStaff, times(1)).isEmpty();
		verify(mockModelMapper, times(1)).map(staffList, StaffDTO[].class);
		
		logger.info("testFindByFirstNameAndDepartmentIdPaginated is successful.");
	}
	
	@Test(expected = NotFoundException.class)
	public void testFindByFirstNameAndDepartmentIdPaginatedWithNotFoundException() throws NotFoundException {
		logger.info("testFindByFirstNameAndDepartmentIdPaginated is started.");
		
		when(mockStaffDAO.findByFirstNameAndDepartment_Id(any(String.class), anyLong(), any(Pageable.class))).thenReturn(mockPageStaff);
		when(mockPageStaff.isEmpty()).thenReturn(true);
		
		staffService.findByFirstNameAndDepartmentIdPaginated("Ahmet", 2L, PageRequest.of(0, 2));
		
		verify(mockStaffDAO, times(1)).findByFirstNameAndDepartment_Id(any(String.class), anyLong(), any(Pageable.class));
		verify(mockPageStaff, times(1)).isEmpty();
		
		logger.info("testFindByFirstNameAndDepartmentIdPaginated is successful.");
	}
	
	@Test
	public void testFindByLastNameAndDepartmentId() throws NotFoundException {
		logger.info("testFindByLastNameAndDepartmentId is started.");
		
		when(mockStaffDAO.findByLastNameAndDepartment_Id(any(String.class), anyLong())).thenReturn(staffList);
		when(mockModelMapper.map(staffList, StaffDTO[].class)).thenReturn(staffDTOArray);
		
		List<StaffDTO> findByLastNameAndDepartmentId = staffService.findByLastNameAndDepartmentId("ÇALIŞKAN", 2L);
		makeAssertionOverStaffList(findByLastNameAndDepartmentId.get(0), staffDTOList.get(0));
		
		verify(mockStaffDAO, times(1)).findByLastNameAndDepartment_Id(any(String.class), anyLong());
		verify(mockModelMapper, times(1)).map(staffList, StaffDTO[].class);
		
		logger.info("testFindByLastNameAndDepartmentId is successful.");
	}
	
	@Test(expected = NotFoundException.class)
	public void testFindByLastNameAndDepartmentIdWithNotFoundException() throws NotFoundException {
		logger.info("testFindByLastNameAndDepartmentId is started.");
		
		when(mockStaffDAO.findByLastNameAndDepartment_Id(any(String.class), anyLong())).thenReturn(new ArrayList<>());
		
		staffService.findByLastNameAndDepartmentId("ÇALIŞKAN", 2L);
		verify(mockStaffDAO, times(1)).findByLastNameAndDepartment_Id(any(String.class), anyLong());
		
		logger.info("testFindByLastNameAndDepartmentId is successful.");
	}
	
	@Test
	public void testFindByLastNameAndDepartmentIdPaginated() throws NotFoundException {
		logger.info("testFindByLastNameAndDepartmentIdPaginated is started.");
		
		when(mockStaffDAO.findByLastNameAndDepartment_Id(any(String.class), anyLong(), any(Pageable.class))).thenReturn(mockPageStaff);
		when(mockPageStaff.isEmpty()).thenReturn(false);
		when(mockPageStaff.getContent()).thenReturn(staffList);
		when(mockModelMapper.map(staffList, StaffDTO[].class)).thenReturn(staffDTOArray);
		
		PageForClient<StaffDTO> findByLastNameAndDepartmentIdPaginated = staffService.findByLastNameAndDepartmentIdPaginated("TEMBEL", 2L, PageRequest.of(0, 2));
		makeAssertionOverStaffList(findByLastNameAndDepartmentIdPaginated.getContent().get(0), staffDTOList.get(0));
		
		verify(mockStaffDAO, times(1)).findByLastNameAndDepartment_Id(any(String.class), anyLong(), any(Pageable.class));
		verify(mockPageStaff, times(1)).isEmpty();
		verify(mockModelMapper, times(1)).map(staffList, StaffDTO[].class);
		logger.info("testFindByLastNameAndDepartmentIdPaginated is successful.");
	}
	
	@Test(expected = NotFoundException.class)
	public void testFindByLastNameAndDepartmentIdPaginatedWithNotFoundException() throws NotFoundException {
		logger.info("testFindByLastNameAndDepartmentIdPaginated is started.");
		
		when(mockStaffDAO.findByLastNameAndDepartment_Id(any(String.class), anyLong(), any(Pageable.class))).thenReturn(mockPageStaff);
		when(mockPageStaff.isEmpty()).thenReturn(true);
		
		staffService.findByLastNameAndDepartmentIdPaginated("TEMBEL", 2L, PageRequest.of(0, 2));
		
		verify(mockStaffDAO, times(1)).findByLastNameAndDepartment_Id(any(String.class), anyLong(), any(Pageable.class));
		verify(mockPageStaff, times(1)).isEmpty();
		
		logger.info("testFindByLastNameAndDepartmentIdPaginated is successful.");
	}
	
	@Test
	public void testFindByPhoneAndDepartmentId() throws NotFoundException {
		logger.info("testFindByPhoneAndDepartmentId is started.");
		
		when(mockStaffDAO.findByPhoneAndDepartment_Id(any(String.class), anyLong())).thenReturn(staffList);
		when(mockModelMapper.map(staffList, StaffDTO[].class)).thenReturn(staffDTOArray);
		
		List<StaffDTO> findByPhoneAndDepartmentId = staffService.findByPhoneAndDepartmentId("1231231231", 2L);
		makeAssertionOverStaffList(findByPhoneAndDepartmentId.get(0), staffDTOList.get(0));
		
		verify(mockStaffDAO, times(1)).findByPhoneAndDepartment_Id(any(String.class), anyLong());
		verify(mockModelMapper, times(1)).map(staffList, StaffDTO[].class);
		
		logger.info("testFindByPhoneAndDepartmentId is successful.");
	}
	
	@Test(expected = NotFoundException.class)
	public void testFindByPhoneAndDepartmentIdWithNotFoundException() throws NotFoundException {
		logger.info("testFindByPhoneAndDepartmentId is started.");
		
		when(mockStaffDAO.findByPhoneAndDepartment_Id(any(String.class), anyLong())).thenReturn(new ArrayList<>());
		
		staffService.findByPhoneAndDepartmentId("1231231231", 2L);
		verify(mockStaffDAO, times(1)).findByPhoneAndDepartment_Id(any(String.class), anyLong());
		
		logger.info("testFindByPhoneAndDepartmentId is successful.");
	}
	
	@Test
	public void testFindByEmailAndDepartmentId() throws NotFoundException {
		logger.info("testFindByEmailAndDepartmentId is started.");
		
		when(mockStaffDAO.findByEmailAndDepartment_Id(any(String.class), anyLong())).thenReturn(staffList);
		when(mockModelMapper.map(staffList, StaffDTO[].class)).thenReturn(staffDTOArray);
		
		List<StaffDTO> findByEmailAndDepartmentId = staffService.findByEmailAndDepartmentId("mehmet@abc.com", 2L);
		makeAssertionOverStaffList(findByEmailAndDepartmentId.get(0), staffDTOList.get(0));
		
		verify(mockStaffDAO, times(1)).findByEmailAndDepartment_Id(any(String.class), anyLong());
		verify(mockModelMapper, times(1)).map(staffList, StaffDTO[].class);
		
		logger.info("testFindByEmailAndDepartmentId is successful.");
	}
	
	@Test(expected = NotFoundException.class)
	public void testFindByEmailAndDepartmentIdWithNotFoundException() throws NotFoundException {
		logger.info("testFindByEmailAndDepartmentId is started.");
		
		when(mockStaffDAO.findByEmailAndDepartment_Id(any(String.class), anyLong())).thenReturn(new ArrayList<>());
		
		staffService.findByEmailAndDepartmentId("mehmet@abc.com", 2L);
		verify(mockStaffDAO, times(1)).findByEmailAndDepartment_Id(any(String.class), anyLong());
		
		logger.info("testFindByEmailAndDepartmentId is successful.");
	}
	
	@Test
	public void testFindByCreateDateAndDepartmentId() throws NotFoundException {
		logger.info("testFindByCreateDateAndDepartmentId is started.");
		
		when(mockStaffDAO.findByCreateDateAndDepartment_Id(any(Date.class), anyLong())).thenReturn(staffList);
		when(mockModelMapper.map(staffList, StaffDTO[].class)).thenReturn(staffDTOArray);
		
		List<StaffDTO> findByRegisteredTimeAndDepartmentId = staffService
				.findByCreateDateAndDepartmentId(staffList.get(0).getCreateDate(), 2L);
		makeAssertionOverStaffList(findByRegisteredTimeAndDepartmentId.get(0), staffDTOList.get(0));
		
		verify(mockStaffDAO, times(1)).findByCreateDateAndDepartment_Id(any(Date.class), anyLong());
		verify(mockModelMapper, times(1)).map(staffList, StaffDTO[].class);
		
		logger.info("testFindByCreateDateAndDepartmentId is successful.");
	}
	
	@Test(expected = NotFoundException.class)
	public void testFindByCreateDateAndDepartmentIdWithNotFoundException() throws NotFoundException {
		logger.info("testFindByCreateDateAndDepartmentId is started.");
		
		when(mockStaffDAO.findByCreateDateAndDepartment_Id(any(Date.class), anyLong())).thenReturn(new ArrayList<>());
		
		staffService.findByCreateDateAndDepartmentId(staffList.get(0).getCreateDate(), 2L);
		verify(mockStaffDAO, times(1)).findByCreateDateAndDepartment_Id(any(Date.class), anyLong());
		
		logger.info("testFindByCreateDateAndDepartmentId is successful.");
	}
	
	private void makeAssertionOverStaffList(StaffDTO actualStaff, StaffDTO staffForCheck) {
		assertNotNull(actualStaff);
		assertEquals(actualStaff.getFirstName(), staffForCheck.getFirstName());
		assertEquals(actualStaff.getLastName(), staffForCheck.getLastName());
		assertEquals(actualStaff.getPhone(), staffForCheck.getPhone());
		assertEquals(actualStaff.getEmail(), staffForCheck.getEmail());
		assertEquals(actualStaff.getCreateDate(), staffForCheck.getCreateDate());
	}
}
