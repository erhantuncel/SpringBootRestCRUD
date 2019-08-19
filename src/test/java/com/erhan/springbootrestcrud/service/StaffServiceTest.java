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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

import com.erhan.springbootrestcrud.dao.DepartmentDAO;
import com.erhan.springbootrestcrud.dao.StaffDAO;
import com.erhan.springbootrestcrud.model.Department;
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
	
	@InjectMocks
	StaffServiceImpl staffService;
	
	private Staff staff;
	private Department department;
	private List<Staff> staffList;
	
	@Before
	public void setUp() {
		department = new Department("Üretim");
		department.setId(1L);
		staff = new Staff("Mehmet", "ÇALIŞKAN", "1231231231", "mehmet@abc.com", new Date(), department);
		staff.setId(1L);
		staffList = new ArrayList<Staff>();
		staffList.add(staff);
		Staff staff2 = new Staff("Ahmet", "TEMBEL", "9879879879", "ahmet@abc.com", new Date(), department);
		staff2.setId(2L);
		staffList.add(staff2);
		department.getStaffList().add(staff);
	}
	
	@Test
	public void testCreate() {
		logger.info("testCreate is started.");
		
		staffService.create(staff);
		
		verify(mockStaffDAO, times(1)).save(any(Staff.class));
		
		logger.info("testCreate is successful.");
	}
	
	@Test
	public void testCreateWithDepartmentId() throws NotFoundException {
		logger.info("testCreateWithDepartmentId is started.");
		
		when(mockDepartmentDAO.findById(anyLong())).thenReturn(Optional.of(department));
		
		staffService.createWithDepartmentId(staff, 2L);
		assertEquals(staff.getDepartment(), department);
		
		verify(mockDepartmentDAO, times(1)).findById(anyLong());
		verify(mockStaffDAO, times(1)).save(any(Staff.class));
		
		logger.info("testCreateWithDepartmentId is successful.");
	}
	
	@Test(expected = NotFoundException.class)
	public void testCreateWithDepartmentIdWithException() throws NotFoundException {
		logger.info("testCreateWithDepartmentIdWithException is started.");
		when(mockDepartmentDAO.findById(anyLong())).thenReturn(Optional.empty());
		
		staffService.createWithDepartmentId(staff, 2L);
		
		verify(mockDepartmentDAO, times(1)).findById(anyLong());
		
		logger.info("testCreateWithDepartmentIdWithException is successful.");
	}
	
	@Test
	public void testUpdate() {
		logger.info("testUpdate is started.");
		
		staffService.update(staff);
		
		verify(mockStaffDAO, times(1)).save(any(Staff.class));
		
		logger.info("testUpdate is successful.");
	}
	
	@Test
	public void testUpdateWithDepartmentId() throws IllegalArgumentException, NotFoundException {
		logger.info("testUpdateWithDepartmentId is started.");
		
		when(mockDepartmentDAO.findById(anyLong())).thenReturn(Optional.of(department));
		when(mockStaffDAO.findById(anyLong())).thenReturn(Optional.of(staff));
		
		Staff staffUpdate = new Staff("Mehmet", "ÇALIŞKAN", "4564564566", "mehmet2@abc.com", new Date(), department);
		staffService.updateWithDepartmentId(1L, 1L, staffUpdate);
		
		verify(mockDepartmentDAO, times(1)).findById(anyLong());
		verify(mockStaffDAO, times(1)).findById(anyLong());
		assertEquals(staff.getPhone(), staffUpdate.getPhone());
		assertEquals(staff.getEmail(), staffUpdate.getEmail());
		
		logger.info("testUpdateWithDepartmentId is successful.");
	}
	
	@Test(expected = NotFoundException.class)
	public void testUpdateWithDepartmentIdWithNotFoundDepartment() throws IllegalArgumentException, NotFoundException {
		logger.info("testUpdateWithDepartmentIdWithNotFoundDepartment is started.");
		
		when(mockDepartmentDAO.findById(anyLong())).thenReturn(Optional.empty());
		when(mockStaffDAO.findById(anyLong())).thenReturn(Optional.of(staff));
		
		Staff staffUpdate = new Staff("Mehmet", "ÇALIŞKAN", "4564564566", "mehmet2@abc.com", new Date(), department);
		staffService.updateWithDepartmentId(1L, 1L, staffUpdate);
		
		verify(mockDepartmentDAO, times(1)).findById(anyLong());
		
		logger.info("testUpdateWithDepartmentIdWithNotFoundDepartment is successful.");
	}
	
	@Test(expected = NotFoundException.class)
	public void testUpdateWithDepartmentIdWithNotFoundStaff() throws IllegalArgumentException, NotFoundException {
		logger.info("testUpdateWithDepartmentIdWithNotFoundStaff is started.");
		
		when(mockDepartmentDAO.findById(anyLong())).thenReturn(Optional.of(department));
		when(mockStaffDAO.findById(anyLong())).thenReturn(Optional.empty());
		
		Staff staffUpdate = new Staff("Mehmet", "ÇALIŞKAN", "4564564566", "mehmet2@abc.com", new Date(), department);
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
		
		Staff staffUpdate = new Staff("Mehmet", "ÇALIŞKAN", "4564564566", "mehmet2@abc.com", new Date(), department);
		staffService.updateWithDepartmentId(1L, 1L, staffUpdate);
		
		verify(mockDepartmentDAO, times(1)).findById(anyLong());
		verify(mockStaffDAO, times(1)).findById(anyLong());
		
		logger.info("testUpdateWithDepartmentId is successful.");
	}
	
	@Test
	public void testRemove() {
		logger.info("testRemove is started.");
		
		staffService.remove(staff);
		
		verify(mockStaffDAO, times(1)).delete(any(Staff.class));
		
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
		when(mockStaffDAO.findById(anyLong())).thenReturn(Optional.of(staff));
		
		staffService.removeWithDepartmentId(1L, 1L);
		
		verify(mockDepartmentDAO, times(1)).findById(anyLong());
		verify(mockStaffDAO, times(1)).findById(anyLong());
		verify(mockDepartmentDAO, times(1)).save(any(Department.class));
		
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
		
		Staff findById = staffService.findById(2L);
		makeAssertionOverStaffList(findById, staffList.get(0));
		
		verify(mockStaffDAO, times(1)).findById(anyLong());
		
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
		
		List<Staff> allStaffs = staffService.findAll();
		makeAssertionOverStaffList(allStaffs.get(0), staffList.get(0));
		
		verify(mockStaffDAO, times(1)).findAll();
		
		logger.info("testFindAll is successful.");
	}
	
	@Test
	public void testFindAllPaginated() throws NotFoundException {
		logger.info("testFindAllPaginated is started.");
		
		when(mockStaffDAO.findAll(any(Pageable.class))).thenReturn(mockPageStaff);
		when(mockPageStaff.getContent()).thenReturn(staffList);
		
		List<Staff> allStaffs = staffService.findAllPaginated(1, 2);
		makeAssertionOverStaffList(allStaffs.get(0), staffList.get(0));
		
		verify(mockStaffDAO, times(1)).findAll(any(Pageable.class));
		verify(mockPageStaff, times(1)).getContent();
		
		logger.info("testFindAllPaginated is successful.");
	}
	
	@Test(expected = NotFoundException.class)
	public void testFindAllPaginatedWithException() throws NotFoundException {
		logger.info("testFindAllPaginatedWithException is started.");
		
		when(mockStaffDAO.findAll(any(Pageable.class))).thenReturn(mockPageStaff);
		when(mockPageStaff.getContent()).thenReturn(new ArrayList<Staff>());
		
		staffService.findAllPaginated(1, 2);
		
		logger.info("testFindAllPaginatedWithException is successful.");
	}
	
	@Test
	public void testFindByFirstName() throws NotFoundException {
		logger.info("testFindByFirstName is started.");
		
		when(mockStaffDAO.findByFirstName(anyString())).thenReturn(staffList);
		
		List<Staff> findByFirstName = staffService.findByFirstName("Ahmet");
		makeAssertionOverStaffList(findByFirstName.get(0), staffList.get(0));
		
		verify(mockStaffDAO, times(1)).findByFirstName(anyString());
		
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
		
		List<Staff> findByLastName = staffService.findByLastName("TEMBEL");
		makeAssertionOverStaffList(findByLastName.get(0), staffList.get(0));
		
		verify(mockStaffDAO, times(1)).findByLastName(anyString());
		
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
		
		List<Staff> findByPhone = staffService.findByPhone("9879879879");
		makeAssertionOverStaffList(findByPhone.get(0), staffList.get(0));
		
		verify(mockStaffDAO, times(1)).findByPhone(anyString());
		
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
		
		List<Staff> findByEmail = staffService.findByEmail("ahmet@abc.com");
		makeAssertionOverStaffList(findByEmail.get(0), staffList.get(0));
		
		verify(mockStaffDAO, times(1)).findByEmail(anyString());
		
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
		
		List<Staff> findByRegisteredTime = staffService.findByCreateDate(staffList.get(0).getCreateDate());
		
		makeAssertionOverStaffList(findByRegisteredTime.get(0), staffList.get(0));
		verify(mockStaffDAO, times(1)).findByCreateDate(any(Date.class));
		
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
		
		List<Staff> findByDepartment = staffService.findByDepartment(staffList.get(0).getDepartment());
		
		makeAssertionOverStaffList(findByDepartment.get(0), staffList.get(0));
		verify(mockStaffDAO, times(1)).findByDepartment(any(Department.class));
		
		logger.info("testFindByDepartment is successful.");
	}
	
	@Test(expected = NotFoundException.class)
	public void testFindByDepartmentWithNotFoundException() throws NotFoundException {
		logger.info("testFindByDepartment is started.");
		
		when(mockStaffDAO.findByDepartment(any(Department.class))).thenReturn(new ArrayList<>());
		
		staffService.findByDepartment(staffList.get(0).getDepartment());
		
		verify(mockStaffDAO, times(1)).findByDepartment(any(Department.class));
		
		logger.info("testFindByDepartment is successful.");
	}
	
	@Test
	public void testFindByDepartmentId() throws NotFoundException {
		logger.info("testFindByDepartmentId is started.");
		
		when(mockStaffDAO.findByDepartment_Id(any(Long.class))).thenReturn(staffList);
		
		List<Staff> findByDepartment_Id = staffService.findByDepartmentId(staffList.get(0).getDepartment().getId());
		
		makeAssertionOverStaffList(findByDepartment_Id.get(0), staffList.get(0));
		verify(mockStaffDAO, times(1)).findByDepartment_Id(any(Long.class));
		
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
		
		when(mockStaffDAO.findByDepartment_Id(anyLong(), any(Pageable.class))).thenReturn(staffList);
		
		List<Staff> findByDepartment_IdPaginated = staffService
				.findByDepartmentIdPaginated(staffList.get(0).getDepartment().getId(), 0, 2);
		
		makeAssertionOverStaffList(findByDepartment_IdPaginated.get(0), staffList.get(0));
		verify(mockStaffDAO, times(1)).findByDepartment_Id(any(Long.class), any(Pageable.class));
		
		logger.info("testFindByDepartmentIdPaginated is successful.");
	}
	
	@Test(expected = NotFoundException.class)
	public void testFindByDepartmentIdPaginatedWithNotFoundException() throws NotFoundException {
		logger.info("testFindByDepartmentIdPaginated is started.");
		
		when(mockStaffDAO.findByDepartment_Id(anyLong(), any(Pageable.class))).thenReturn(new ArrayList<>());
		
		staffService.findByDepartmentIdPaginated(staffList.get(0).getDepartment().getId(), 0, 2);
		
		verify(mockStaffDAO, times(1)).findByDepartment_Id(any(Long.class), any(Pageable.class));
		
		logger.info("testFindByDepartmentIdPaginated is successful.");
	}
	
	@Test
	public void testFindByIdAndDepartmentId() throws NotFoundException {
		logger.info("testFindByIdAndDepartmentId is started.");
		
		when(mockStaffDAO.findByIdAndDepartment_Id(anyLong(), anyLong())).thenReturn(staffList);
		
		Staff findByIdAndDepartmentId = staffService.findByIdAndDepartmentId(2L, 3L);
		makeAssertionOverStaffList(findByIdAndDepartmentId, staffList.get(0));
		verify(mockStaffDAO, times(1)).findByIdAndDepartment_Id(anyLong(), anyLong());
		
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
		
		List<Staff> findByFirstNameAndDepartmentId = staffService.findByFirstNameAndDepartmentId("Ahmet", 2L);
		makeAssertionOverStaffList(findByFirstNameAndDepartmentId.get(0), staffList.get(0));
		verify(mockStaffDAO, times(1)).findByFirstNameAndDepartment_Id(any(String.class), anyLong());
		
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
		
		when(mockStaffDAO.findByFirstNameAndDepartment_Id(any(String.class), anyLong(), any(Pageable.class))).thenReturn(staffList);
		
		List<Staff> findByFirstNameAndDepartmentIdPaginated = staffService.findByFirstNameAndDepartmentIdPaginated("Ahmet", 2L, 0, 2);
		makeAssertionOverStaffList(findByFirstNameAndDepartmentIdPaginated.get(0), staffList.get(0));
		verify(mockStaffDAO, times(1)).findByFirstNameAndDepartment_Id(any(String.class), anyLong(), any(Pageable.class));
		
		logger.info("testFindByFirstNameAndDepartmentIdPaginated is successful.");
	}
	
	@Test(expected = NotFoundException.class)
	public void testFindByFirstNameAndDepartmentIdPaginatedWithNotFoundException() throws NotFoundException {
		logger.info("testFindByFirstNameAndDepartmentIdPaginated is started.");
		
		when(mockStaffDAO.findByFirstNameAndDepartment_Id(any(String.class), anyLong(), any(Pageable.class))).thenReturn(new ArrayList<>());
		
		staffService.findByFirstNameAndDepartmentIdPaginated("Ahmet", 2L, 0, 2);
		verify(mockStaffDAO, times(1)).findByFirstNameAndDepartment_Id(any(String.class), anyLong(), any(Pageable.class));
		
		logger.info("testFindByFirstNameAndDepartmentIdPaginated is successful.");
	}
	
	@Test
	public void testFindByLastNameAndDepartmentId() throws NotFoundException {
		logger.info("testFindByLastNameAndDepartmentId is started.");
		
		when(mockStaffDAO.findByLastNameAndDepartment_Id(any(String.class), anyLong())).thenReturn(staffList);
		
		List<Staff> findByLastNameAndDepartmentId = staffService.findByLastNameAndDepartmentId("ÇALIŞKAN", 2L);
		makeAssertionOverStaffList(findByLastNameAndDepartmentId.get(0), staffList.get(0));
		verify(mockStaffDAO, times(1)).findByLastNameAndDepartment_Id(any(String.class), anyLong());
		
		logger.info("testFindByLastNameAndDepartmentId is successful.");
	}
	
	@Test(expected = NotFoundException.class)
	public void testFindByLastNameAndDepartmentIdWithNotFoundException() throws NotFoundException {
		logger.info("testFindByLastNameAndDepartmentId is started.");
		
		when(mockStaffDAO.findByLastNameAndDepartment_Id(any(String.class), anyLong())).thenReturn(staffList);
		
		staffService.findByLastNameAndDepartmentId("ÇALIŞKAN", 2L);
		verify(mockStaffDAO, times(1)).findByLastNameAndDepartment_Id(any(String.class), anyLong());
		
		logger.info("testFindByLastNameAndDepartmentId is successful.");
	}
	
	@Test
	public void testFindByLastNameAndDepartmentIdPaginated() throws NotFoundException {
		logger.info("testFindByLastNameAndDepartmentIdPaginated is started.");
		
		when(mockStaffDAO.findByLastNameAndDepartment_Id(any(String.class), anyLong(), any(Pageable.class))).thenReturn(staffList);
		
		List<Staff> findByLastNameAndDepartmentIdPaginated = staffService.findByLastNameAndDepartmentIdPaginated("TEMBEL", 2L, 0, 2);
		makeAssertionOverStaffList(findByLastNameAndDepartmentIdPaginated.get(0), staffList.get(0));
		verify(mockStaffDAO, times(1)).findByLastNameAndDepartment_Id(any(String.class), anyLong(), any(Pageable.class));
		
		logger.info("testFindByLastNameAndDepartmentIdPaginated is successful.");
	}
	
	@Test(expected = NotFoundException.class)
	public void testFindByLastNameAndDepartmentIdPaginatedWithNotFoundException() throws NotFoundException {
		logger.info("testFindByLastNameAndDepartmentIdPaginated is started.");
		
		when(mockStaffDAO.findByLastNameAndDepartment_Id(any(String.class), anyLong(), any(Pageable.class))).thenReturn(new ArrayList<>());
		
		staffService.findByLastNameAndDepartmentIdPaginated("TEMBEL", 2L, 0, 2);
		verify(mockStaffDAO, times(1)).findByLastNameAndDepartment_Id(any(String.class), anyLong(), any(Pageable.class));
		
		logger.info("testFindByLastNameAndDepartmentIdPaginated is successful.");
	}
	
	@Test
	public void testFindByPhoneAndDepartmentId() throws NotFoundException {
		logger.info("testFindByPhoneAndDepartmentId is started.");
		
		when(mockStaffDAO.findByPhoneAndDepartment_Id(any(String.class), anyLong())).thenReturn(staffList);
		
		List<Staff> findByPhoneAndDepartmentId = staffService.findByPhoneAndDepartmentId("1231231231", 2L);
		makeAssertionOverStaffList(findByPhoneAndDepartmentId.get(0), staffList.get(0));
		verify(mockStaffDAO, times(1)).findByPhoneAndDepartment_Id(any(String.class), anyLong());
		
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
		
		List<Staff> findByEmailAndDepartmentId = staffService.findByEmailAndDepartmentId("mehmet@abc.com", 2L);
		makeAssertionOverStaffList(findByEmailAndDepartmentId.get(0), staffList.get(0));
		verify(mockStaffDAO, times(1)).findByEmailAndDepartment_Id(any(String.class), anyLong());
		
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
		
		List<Staff> findByRegisteredTimeAndDepartmentId = staffService
				.findByCreateDateAndDepartmentId(staffList.get(0).getCreateDate(), 2L);
		makeAssertionOverStaffList(findByRegisteredTimeAndDepartmentId.get(0), staffList.get(0));
		verify(mockStaffDAO, times(1)).findByCreateDateAndDepartment_Id(any(Date.class), anyLong());
		
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
	
	private void makeAssertionOverStaffList(Staff actualStaff, Staff staffForCheck) {
		assertNotNull(actualStaff);
		assertEquals(actualStaff.getFirstName(), staffForCheck.getFirstName());
		assertEquals(actualStaff.getLastName(), staffForCheck.getLastName());
		assertEquals(actualStaff.getPhone(), staffForCheck.getPhone());
		assertEquals(actualStaff.getEmail(), staffForCheck.getEmail());
		assertEquals(actualStaff.getCreateDate(), staffForCheck.getCreateDate());
	}
}
