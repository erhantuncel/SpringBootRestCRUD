package com.erhan.springbootrestcrud.service;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import org.hibernate.hql.internal.ast.InvalidPathException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.erhan.springbootrestcrud.dao.DepartmentDAO;
import com.erhan.springbootrestcrud.dao.StaffDAO;
import com.erhan.springbootrestcrud.dto.DepartmentDTO;
import com.erhan.springbootrestcrud.dto.StaffDTO;
import com.erhan.springbootrestcrud.model.Department;
import com.erhan.springbootrestcrud.model.PageForClient;
import com.erhan.springbootrestcrud.model.Staff;

import javassist.NotFoundException;

@Service
@Transactional
public class StaffServiceImpl implements StaffService {
	
	@Autowired
	private StaffDAO staffDAO;
	
	@Autowired
	private DepartmentDAO departmentDAO;
	
	@Autowired
	ModelMapper modelMapper;
	
	@Override
	public StaffDTO create(StaffDTO staff) {
		Staff staffEntity = modelMapper.map(staff, Staff.class);
		staffDAO.save(staffEntity);
		staff.setId(staffEntity.getId());
		return staff;
	}

	@Override
	public StaffDTO createWithDepartmentId(StaffDTO staff, Long departmentId, MultipartFile imageFile) throws NotFoundException, InvalidPathException, IOException {
		if(imageFile != null) {
			String fileName = StringUtils.cleanPath(imageFile.getName());
			if(fileName.contains("..")) {
				throw new InvalidPathException("File path " + fileName +" is invalid ");
			}			
			staff.setImage(imageFile.getBytes());
		}
		Department department = departmentDAO.findById(departmentId).orElse(null);
		if(department == null) {
			throw new NotFoundException("Department with id = " + departmentId + " not found!");
		}
		staff.setDepartment(modelMapper.map(department, DepartmentDTO.class));
		Staff staffEntity = modelMapper.map(staff, Staff.class);
		staffEntity.setCreateDate(new Date());
		staffDAO.save(staffEntity);
		return modelMapper.map(staffEntity, StaffDTO.class);
	}

	@Override
	public StaffDTO update(StaffDTO staff) {
		Staff staffEntity = modelMapper.map(staff, Staff.class);
		staffDAO.save(staffEntity);
		return modelMapper.map(staffEntity, StaffDTO.class);
	}

	@Override
	public StaffDTO updateWithDepartmentId(Long staffId, Long departmentId, StaffDTO staff, MultipartFile imageFile) 
			throws NotFoundException, IllegalArgumentException, InvalidPathException, IOException {
		if(imageFile != null) {
			String fileName = StringUtils.cleanPath(imageFile.getName());
			if(fileName.contains("..")) {
				throw new InvalidPathException("File path " + fileName +" is invalid ");
			}			
			staff.setImage(imageFile.getBytes());
		} else {
			staff.setImage(null);
		}
		Department department = departmentDAO.findById(departmentId).orElse(null);
		if(department == null) {
			throw new NotFoundException("Department with id = " + departmentId + "  not found!");
		}
		Staff staffFromDb = staffDAO.findById(staffId).orElse(null);
		if(staffFromDb == null) {
			throw new NotFoundException("Staff with id = " + staffId + " not found!");
		}
		
		if(!department.getStaffList().contains(staffFromDb)) {
			throw new IllegalArgumentException("Department with id = " + departmentId + 
					" does not have staff with id = " + staffId);
		}
		
		Staff staffEntity = modelMapper.map(staff, Staff.class);
		staffFromDb.setPhone(staffEntity.getPhone());
		staffFromDb.setEmail(staffEntity.getEmail());
		staffFromDb.setImage(staffEntity.getImage());
		staffDAO.save(staffFromDb);
		return modelMapper.map(staffFromDb, StaffDTO.class);
	}

	@Override
	public void remove(StaffDTO staff) {
		Staff staffEntity = modelMapper.map(staff, Staff.class);
		staffDAO.delete(staffEntity);
	}

	@Override
	public void removeWithDepartmentId(Long staffId, Long departmentId) throws NotFoundException, IllegalArgumentException {
		Department department = departmentDAO.findById(departmentId).orElse(null);
		if(department == null) {
			throw new NotFoundException("Department with id = " + departmentId + "  not found!");
		}
		Staff staffFromDb = staffDAO.findById(staffId).orElse(null);
		if(staffFromDb == null) {
			throw new NotFoundException("Staff with id = " + staffId + " not found!");
		}
		if(!department.getStaffList().contains(staffFromDb)) {
			throw new IllegalArgumentException("Department with id = " + departmentId + 
					" does not have staff with id = " + staffId);
		}
		department.getStaffList().remove(staffFromDb);
		departmentDAO.save(department);
		staffDAO.delete(staffFromDb);
	}

	@Override
	public StaffDTO findById(Long id) throws NotFoundException {
		Staff staff = staffDAO.findById(id).orElse(null);
		if(staff == null) {
			throw new NotFoundException("Staff with id = " + id + " not found!");
		}
		return modelMapper.map(staff, StaffDTO.class);
	}

	@Override
	public List<StaffDTO> findAll() {
		List<Staff> allStaff = staffDAO.findAll();
		return Arrays.asList(modelMapper.map(allStaff, StaffDTO[].class));
	}

	@Override
	public PageForClient<StaffDTO> findAllPaginated(Pageable pageable) throws NotFoundException {
		Page<Staff> pageStaff = staffDAO.findAll(pageable);
		if(pageStaff.isEmpty()) {
			throw new NotFoundException("Staff list not found"); 
		}
		PageForClient<StaffDTO> pageForClient = new PageForClient<StaffDTO>();
		StaffDTO[] staffDTOArray = modelMapper.map(pageStaff.getContent(), StaffDTO[].class);
		List<StaffDTO> staffDTOList = Arrays.asList(staffDTOArray);
		pageForClient.setFields(pageStaff, staffDTOList);
		return pageForClient;
	}

	@Override
	public List<StaffDTO> findByFirstName(String firstName) throws NotFoundException {
		List<Staff> staffList = staffDAO.findByFirstName(firstName);
		if(staffList.size() <= 0) {
			throw new NotFoundException("Any staff for first name: " + firstName + " not found"); 
		}
		return Arrays.asList(modelMapper.map(staffList, StaffDTO[].class));
	}

	@Override
	public List<StaffDTO> findByLastName(String lastName) throws NotFoundException {
		List<Staff> staffList = staffDAO.findByLastName(lastName);
		if(staffList.size() <= 0) {
			throw new NotFoundException("Any staff for last name: " + lastName + " not found");
		}
		return Arrays.asList(modelMapper.map(staffList, StaffDTO[].class));
	}

	@Override
	public List<StaffDTO> findByPhone(String phone) throws NotFoundException {
		List<Staff> staffList = staffDAO.findByPhone(phone);
		if(staffList.size() <= 0) {
			throw new NotFoundException("Any staff for phone: " + phone + " not found");
		}
		return Arrays.asList(modelMapper.map(staffList, StaffDTO[].class));
	}

	@Override
	public List<StaffDTO> findByEmail(String email) throws NotFoundException {
		List<Staff> staffList = staffDAO.findByEmail(email);
		if(staffList.size() <= 0) {
			throw new NotFoundException("Any staff for email: " + email + " not found");
		}
		return Arrays.asList(modelMapper.map(staffList, StaffDTO[].class));
	}

	@Override
	public List<StaffDTO> findByCreateDate(Date createDate) throws NotFoundException {
		List<Staff> staffList = staffDAO.findByCreateDate(createDate);
		if(staffList.size() <= 0) {
			throw new NotFoundException("Any staff for create date: " + createDate + " not found");
		}
		return Arrays.asList(modelMapper.map(staffList, StaffDTO[].class));
	}

	@Override
	public List<StaffDTO> findByDepartment(DepartmentDTO department) throws NotFoundException {
		Department departmentEntity = modelMapper.map(department, Department.class);
		List<Staff> staffList = staffDAO.findByDepartment(departmentEntity);
		if(staffList.size() <= 0) {
			throw new NotFoundException("Any staff for department : " + department.getDepartmentName() + " not found");
		}
		return Arrays.asList(modelMapper.map(staffList, StaffDTO[].class));
	}

	@Override
	public List<StaffDTO> findByDepartmentId(Long id) throws NotFoundException {
		List<Staff> staffList = staffDAO.findByDepartment_Id(id);
		if(staffList.size() <= 0) {
			throw new NotFoundException("Any staff for department id : " + id + " not found");
		}
		return Arrays.asList(modelMapper.map(staffList, StaffDTO[].class));
	}

	@Override
	public PageForClient<StaffDTO> findByDepartmentIdPaginated(Long id, Pageable pageable) throws NotFoundException {
		Page<Staff> pageStaff = staffDAO.findByDepartment_Id(id, pageable);
		if(pageStaff.isEmpty()) {
			throw new NotFoundException("Staff list not found");
		}
		PageForClient<StaffDTO> pageForClient = new PageForClient<StaffDTO>();
		StaffDTO[] staffDTOArray = modelMapper.map(pageStaff.getContent(), StaffDTO[].class);
		pageForClient.setFields(pageStaff, Arrays.asList(staffDTOArray));
		return pageForClient;
	}

	@Override
	public StaffDTO findByIdAndDepartmentId(Long staffId, Long departmentId) throws NotFoundException {
		List<Staff> staffList = staffDAO.findByIdAndDepartment_Id(staffId, departmentId);
		if(staffList.size() <= 0) {
			throw new NotFoundException("Staff with Id = " + staffId + " for Department with id = " + departmentId + " not found!");
		}
		return modelMapper.map(staffList.get(0), StaffDTO.class);
	}

	@Override
	public List<StaffDTO> findByFirstNameAndDepartmentId(String firstName, Long departmentId) throws NotFoundException {
		List<Staff> staffList = staffDAO.findByFirstNameAndDepartment_Id(firstName, departmentId);
		if(staffList.size() <= 0) {
			throw new NotFoundException("Staff with first name = " + firstName + " not found!");
		}
		return Arrays.asList(modelMapper.map(staffList, StaffDTO[].class));
	}

	@Override
	public PageForClient<StaffDTO> findByFirstNameAndDepartmentIdPaginated(String firstName, Long departmentId, 
			Pageable pageable) throws NotFoundException {
		Page<Staff> pageStaff = staffDAO.findByFirstNameAndDepartment_Id(firstName, departmentId, pageable);
		if(pageStaff.isEmpty()) {
			throw new NotFoundException("Staff list not found!");
		}
		PageForClient<StaffDTO> pageForClient = new PageForClient<StaffDTO>();
		StaffDTO[] staffDTOArray = modelMapper.map(pageStaff.getContent(), StaffDTO[].class);
		pageForClient.setFields(pageStaff, Arrays.asList(staffDTOArray));
		return pageForClient;
	}

	@Override
	public List<StaffDTO> findByLastNameAndDepartmentId(String lastName, Long departmentId) throws NotFoundException {
		List<Staff> staffList = staffDAO.findByLastNameAndDepartment_Id(lastName, departmentId);
		if(staffList.size() <= 0) {
			throw new NotFoundException("Staff with last name = " + lastName + " not found!");
		}
		return Arrays.asList(modelMapper.map(staffList, StaffDTO[].class));
	}

	@Override
	public PageForClient<StaffDTO> findByLastNameAndDepartmentIdPaginated(String lastName, Long departmentId, 
			Pageable pageable) throws NotFoundException {
		Page<Staff> pageStaff = staffDAO.findByLastNameAndDepartment_Id(lastName, departmentId, pageable);
		if(pageStaff.isEmpty()) {
			throw new NotFoundException("Staff list not found!");
		}
		PageForClient<StaffDTO> pageForClient = new PageForClient<StaffDTO>();
		StaffDTO[] staffDTOArray = modelMapper.map(pageStaff.getContent(), StaffDTO[].class);
		pageForClient.setFields(pageStaff, Arrays.asList(staffDTOArray));
		return pageForClient;
	}

	@Override
	public List<StaffDTO> findByPhoneAndDepartmentId(String phone, Long departmentId) throws NotFoundException {
		List<Staff> staffList = staffDAO.findByPhoneAndDepartment_Id(phone, departmentId);
		if(staffList.size() <= 0) {
			throw new NotFoundException("Staff with phone number = " + phone + " not found!");
		}
		return Arrays.asList(modelMapper.map(staffList, StaffDTO[].class));
	}

	@Override
	public List<StaffDTO> findByEmailAndDepartmentId(String email, Long departmentId) throws NotFoundException {
		List<Staff> staffList = staffDAO.findByEmailAndDepartment_Id(email, departmentId);
		if(staffList.size() <= 0) {
			throw new NotFoundException("Staff with email = " + email + " not found!");
		}
		return Arrays.asList(modelMapper.map(staffList, StaffDTO[].class));
	}

	@Override
	public List<StaffDTO> findByCreateDateAndDepartmentId(Date createDate, Long departmentId) throws NotFoundException {
		List<Staff> staffList = staffDAO.findByCreateDateAndDepartment_Id(createDate, departmentId);
		if(staffList.size() <= 0) {
			SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
			throw new NotFoundException("Staff with registered time = " + df.format(createDate) + " not found!");
		}
		return Arrays.asList(modelMapper.map(staffList, StaffDTO[].class));
	}

	@Override
	public List<StaffDTO> findByDepartmentIdAndQueryParameters(Long departmentId, Map<String, String> queryParameters) 
			throws NotFoundException, IllegalArgumentException, ParseException {
		Set<String> keySet = queryParameters.keySet();
		if(keySet.size() == 1) {			
			if(keySet.contains("firstName")) {
				return findByFirstNameAndDepartmentId(queryParameters.get("firstName"), departmentId);
			} 
			if(keySet.contains("lastName")) {
				return findByLastNameAndDepartmentId(queryParameters.get("lastName"), departmentId);
			}
			if(keySet.contains("phone")) {
				return findByPhoneAndDepartmentId(queryParameters.get("phone"), departmentId);
			}
			if(keySet.contains("email")) {
				return findByEmailAndDepartmentId(queryParameters.get("email"), departmentId);
			}
			if(keySet.contains("createDate")) {
				SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.forLanguageTag("tr"));
				return findByCreateDateAndDepartmentId(df.parse(queryParameters.get("createDate")), departmentId);
			}
		}
		throw new IllegalArgumentException("Wrong query filter");		
	}
	
	@Override
	public PageForClient<StaffDTO> findByDepartmentIdAndQueryParametersPaginated(Long departmentId, Map<String, String> queryParameters, Pageable pageable) 
			throws NotFoundException, IllegalArgumentException, ParseException {
		Set<String> keySet = queryParameters.keySet();
		if(keySet.contains("firstName")) {
			return findByFirstNameAndDepartmentIdPaginated(queryParameters.get("firstName"), departmentId, pageable);
		} else if(keySet.contains("lastName")) {
			return findByLastNameAndDepartmentIdPaginated(queryParameters.get("lastName"), departmentId, pageable);
		} else {
			return findByDepartmentIdPaginated(departmentId, pageable);
		}
	}

	
}
