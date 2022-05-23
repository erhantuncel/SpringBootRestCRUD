package com.erhan.springbootrestcrud.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.erhan.springbootrestcrud.model.Department;
import com.erhan.springbootrestcrud.model.Staff;

public interface StaffDAO extends JpaRepository<Staff, Long>{

	public List<Staff> findByFirstName(String firstName);
	public List<Staff> findByLastName(String lastName);
	public List<Staff> findByPhone(String phone);
	public List<Staff> findByEmail(String email);
	public List<Staff> findByCreateDate(Date createDate);
	public List<Staff> findByDepartment(Department department);
	public List<Staff> findByDepartment_Id(Long id);
	public Page<Staff> findByDepartment_Id(Long id, Pageable pageable);
	public List<Staff> findByIdAndDepartment_Id(Long staffId, Long departmentId);
	public List<Staff> findByFirstNameAndDepartment_Id(String firstName, Long departmentId);
	public Page<Staff> findByFirstNameAndDepartment_Id(String firstName, Long departmentId, Pageable pageable);
	public List<Staff> findByLastNameAndDepartment_Id(String lastName, Long departmentId);
	public Page<Staff> findByLastNameAndDepartment_Id(String lastName, Long departmentId, Pageable pageable);
	public List<Staff> findByPhoneAndDepartment_Id(String phone, Long departmentId);
	public List<Staff> findByEmailAndDepartment_Id(String email, Long departmentId);
	public List<Staff> findByCreateDateAndDepartment_Id(Date createDate, Long departmentId);
}
