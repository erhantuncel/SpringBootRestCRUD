package com.erhan.springbootrestcrud.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.erhan.springbootrestcrud.dao.DepartmentDAO;
import com.erhan.springbootrestcrud.dao.StaffDAO;
import com.erhan.springbootrestcrud.model.Department;
import com.erhan.springbootrestcrud.model.Staff;

import javassist.NotFoundException;

@Service
@Transactional
public class StaffServiceImpl implements StaffService {
	
	@Autowired
	private StaffDAO staffDAO;
	
	@Autowired
	private DepartmentDAO departmentDAO;
	
	@Override
	public void create(Staff staff) {
		staffDAO.save(staff);
	}

	@Override
	public void createWithDepartmentId(Staff staff, Long departmentId) throws NotFoundException {
		Department department = departmentDAO.findById(departmentId).orElse(null);
		if(department == null) {
			throw new NotFoundException("Department with id = " + departmentId + " not found!");
		}
		staff.setDepartment(department);
		staffDAO.save(staff);
	}

	@Override
	public void update(Staff staff) {
		staffDAO.save(staff);
	}

	@Override
	public void updateWithDepartmentId(Long staffId, Long departmentId, Staff staff) 
			throws NotFoundException, IllegalArgumentException {
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
		staffFromDb.setPhone(staff.getPhone());
		staffFromDb.setEmail(staff.getEmail());
		staffFromDb.setImage(staff.getImage());
		staffDAO.save(staffFromDb);
	}

	@Override
	public void remove(Staff staff) {
		staffDAO.delete(staff);
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
	public Staff findById(Long id) throws NotFoundException {
		Staff staff = staffDAO.findById(id).orElse(null);
		if(staff == null) {
			throw new NotFoundException("Staff with id = " + id + " not found!");
		}
		return staff;
	}

	@Override
	public List<Staff> findAll() {
		return staffDAO.findAll();
	}

	@Override
	public List<Staff> findAllPaginated(int page, int pageSize) throws NotFoundException {
		List<Staff> staffList = staffDAO.findAll(PageRequest.of(page, pageSize)).getContent();
		if(staffList.size() <= 0) {
			throw new NotFoundException("Staff list not found"); 
		}
		return staffList;
	}

	@Override
	public List<Staff> findByFirstName(String firstName) throws NotFoundException {
		List<Staff> staffList = staffDAO.findByFirstName(firstName);
		if(staffList.size() <= 0) {
			throw new NotFoundException("Any staff for first name: " + firstName + " not found"); 
		}
		return staffList;
	}

	@Override
	public List<Staff> findByLastName(String lastName) throws NotFoundException {
		List<Staff> staffList = staffDAO.findByLastName(lastName);
		if(staffList.size() <= 0) {
			throw new NotFoundException("Any staff for last name: " + lastName + " not found");
		}
		return staffList;
	}

	@Override
	public List<Staff> findByPhone(String phone) throws NotFoundException {
		List<Staff> staffList = staffDAO.findByPhone(phone);
		if(staffList.size() <= 0) {
			throw new NotFoundException("Any staff for phone: " + phone + " not found");
		}
		return staffList;
	}

	@Override
	public List<Staff> findByEmail(String email) throws NotFoundException {
		List<Staff> staffList = staffDAO.findByEmail(email);
		if(staffList.size() <= 0) {
			throw new NotFoundException("Any staff for email: " + email + " not found");
		}
		return staffList;
	}

	@Override
	public List<Staff> findByCreateDate(Date createDate) throws NotFoundException {
		List<Staff> staffList = staffDAO.findByCreateDate(createDate);
		if(staffList.size() <= 0) {
			throw new NotFoundException("Any staff for create date: " + createDate + " not found");
		}
		return staffList;
	}

	@Override
	public List<Staff> findByDepartment(Department department) throws NotFoundException {
		List<Staff> staffList = staffDAO.findByDepartment(department);
		if(staffList.size() <= 0) {
			throw new NotFoundException("Any staff for department : " + department.getDepartmentName() + " not found");
		}
		return staffList;
	}

	@Override
	public List<Staff> findByDepartmentId(Long id) throws NotFoundException {
		List<Staff> staffList = staffDAO.findByDepartment_Id(id);
		if(staffList.size() <= 0) {
			throw new NotFoundException("Any staff for department id : " + id + " not found");
		}
		return staffList;
	}

	@Override
	public List<Staff> findByDepartmentIdPaginated(Long id, int page, int pageSize) throws NotFoundException {
		List<Staff> staffList = staffDAO.findByDepartment_Id(id, PageRequest.of(page, pageSize));
		if(staffList.size() <= 0) {
			throw new NotFoundException("Staff list not found");
		}
		return staffList;
	}

	@Override
	public Staff findByIdAndDepartmentId(Long staffId, Long departmentId) throws NotFoundException {
		List<Staff> staffList = staffDAO.findByIdAndDepartment_Id(staffId, departmentId);
		if(staffList.size() <= 0) {
			throw new NotFoundException("Staff with Id = " + staffId + " not found!");
		}
		return staffList.get(0);
	}

	@Override
	public List<Staff> findByFirstNameAndDepartmentId(String firstName, Long departmentId) throws NotFoundException {
		List<Staff> staffList = staffDAO.findByFirstNameAndDepartment_Id(firstName, departmentId);
		if(staffList.size() <= 0) {
			throw new NotFoundException("Staff with first name = " + firstName + " not found!");
		}
		return staffList;
	}

	@Override
	public List<Staff> findByFirstNameAndDepartmentIdPaginated(String firstName, Long departmentId, int page,
			int pageSize) throws NotFoundException {
		List<Staff> staffList = staffDAO.findByFirstNameAndDepartment_Id(firstName, departmentId, PageRequest.of(page, pageSize));
		if(staffList.size() <= 0) {
			throw new NotFoundException("Staff list not found!");
		}
		return staffList;
	}

	@Override
	public List<Staff> findByLastNameAndDepartmentId(String lastName, Long departmentId) throws NotFoundException {
		List<Staff> staffList = staffDAO.findByLastNameAndDepartment_Id(lastName, departmentId);
		if(staffList.size() <= 0) {
			throw new NotFoundException("Staff with last name = " + lastName + " not found!");
		}
		return staffList;
	}

	@Override
	public List<Staff> findByLastNameAndDepartmentIdPaginated(String lastName, Long departmentId, int page,
			int pageSize) throws NotFoundException {
		List<Staff> staffList = staffDAO.findByLastNameAndDepartment_Id(lastName, departmentId, PageRequest.of(page, pageSize));
		if(staffList.size() <= 0) {
			throw new NotFoundException("Staff list not found!");
		}
		return staffList;
	}

	@Override
	public List<Staff> findByPhoneAndDepartmentId(String phone, Long departmentId) throws NotFoundException {
		List<Staff> staffList = staffDAO.findByPhoneAndDepartment_Id(phone, departmentId);
		if(staffList.size() <= 0) {
			throw new NotFoundException("Staff with phone number = " + phone + " not found!");
		}
		return staffList;
	}

	@Override
	public List<Staff> findByEmailAndDepartmentId(String email, Long departmentId) throws NotFoundException {
		List<Staff> staffList = staffDAO.findByEmailAndDepartment_Id(email, departmentId);
		if(staffList.size() <= 0) {
			throw new NotFoundException("Staff with email = " + email + " not found!");
		}
		return staffList;
	}

	@Override
	public List<Staff> findByCreateDateAndDepartmentId(Date createDate, Long departmentId) throws NotFoundException {
		List<Staff> staffList = staffDAO.findByCreateDateAndDepartment_Id(createDate, departmentId);
		if(staffList.size() <= 0) {
			SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
			throw new NotFoundException("Staff with registered time = " + df.format(createDate) + " not found!");
		}
		return staffList;
	}

	@Override
	public List<Staff> findByDepartmentIdAndQueryParameters(Long departmentId, Map<String, String> queryParameters) 
			throws NotFoundException, IllegalArgumentException, ParseException {
		Set<String> keySet = queryParameters.keySet();
		Integer page = null;
		Integer pageSize = null;
		switch (queryParameters.size()) {
		case 1:
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
			if(keySet.contains("registeredTime")) {
				SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.forLanguageTag("tr"));
				return findByCreateDateAndDepartmentId(df.parse(queryParameters.get("registeredTime")), departmentId);
			}
			throw new IllegalArgumentException("Wrong query filter");

		case 2 :
			if(!keySet.contains("page") || !keySet.contains("size")) {
				throw new IllegalArgumentException("Wrong query filter!");
			}
			page = Integer.valueOf(queryParameters.get("page"));
			pageSize = Integer.valueOf(queryParameters.get("size"));
			return findByDepartmentIdPaginated(departmentId, page, pageSize);
		case 3 :
			if(keySet.contains("page") && keySet.contains("size")) {
				page = Integer.valueOf(queryParameters.get("page"));
				pageSize = Integer.valueOf(queryParameters.get("size"));
				if(keySet.contains("firstName")) {
					return findByFirstNameAndDepartmentIdPaginated(queryParameters.get("firstName"), departmentId, 
							page, pageSize);
				} 
				if(keySet.contains("lastName")) {
					return findByLastNameAndDepartmentIdPaginated(queryParameters.get("lastName"), departmentId,
							page, pageSize);
				}
				throw new IllegalArgumentException("Wrong query filter");
			} else {				
				throw new IllegalArgumentException("Too much query filter!");
			}
			
		default :
			throw new IllegalArgumentException("Too much query parameters!");
		}
	}

	
}
