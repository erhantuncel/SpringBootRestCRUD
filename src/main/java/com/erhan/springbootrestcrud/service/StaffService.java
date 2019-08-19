package com.erhan.springbootrestcrud.service;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.erhan.springbootrestcrud.model.Department;
import com.erhan.springbootrestcrud.model.Staff;

import javassist.NotFoundException;

public interface StaffService {

	public void create(Staff staff);
	public void createWithDepartmentId(Staff staff, Long departmentId) throws NotFoundException;
	public void update(Staff staff);
	public void updateWithDepartmentId(Long staffId, Long departmentId, Staff staff) throws NotFoundException, IllegalArgumentException;
	public void remove(Staff staff);
	public void removeWithDepartmentId(Long staffId, Long departmentId) throws NotFoundException, IllegalArgumentException;
	public Staff findById(Long id) throws NotFoundException;
	public List<Staff> findAll();
	public List<Staff> findAllPaginated(int page, int pageSize) throws NotFoundException;
	public List<Staff> findByFirstName(String firstName) throws NotFoundException;
	public List<Staff> findByLastName(String lastName) throws NotFoundException;
	public List<Staff> findByPhone(String phone) throws NotFoundException;
	public List<Staff> findByEmail(String email) throws NotFoundException;
	public List<Staff> findByCreateDate(Date createDate) throws NotFoundException;
	public List<Staff> findByDepartment(Department department) throws NotFoundException;
	public List<Staff> findByDepartmentId(Long id) throws NotFoundException;
	public List<Staff> findByDepartmentIdPaginated(Long id, int page, int pageSize) throws NotFoundException;
	public Staff findByIdAndDepartmentId(Long staffId, Long departmentId) throws NotFoundException;
	public List<Staff> findByFirstNameAndDepartmentId(String firstName, Long departmentId) throws NotFoundException;
	public List<Staff> findByFirstNameAndDepartmentIdPaginated(String firstName, Long departmentId, int page, int pageSize) throws NotFoundException;
	public List<Staff> findByLastNameAndDepartmentId(String lastName, Long departmentId) throws NotFoundException;
	public List<Staff> findByLastNameAndDepartmentIdPaginated(String lastName, Long departmentId, int page, int pageSize) throws NotFoundException;
	public List<Staff> findByPhoneAndDepartmentId(String phone, Long departmentId)  throws NotFoundException;
	public List<Staff> findByEmailAndDepartmentId(String email, Long departmentId) throws NotFoundException;
	public List<Staff> findByCreateDateAndDepartmentId(Date createDate, Long departmentId) throws NotFoundException;
	public List<Staff> findByDepartmentIdAndQueryParameters(Long departmentId, Map<String, String> queryParameters) throws NotFoundException, IllegalArgumentException, ParseException;
}
