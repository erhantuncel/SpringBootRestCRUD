package com.erhan.springbootrestcrud.service;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.hibernate.hql.internal.ast.InvalidPathException;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import com.erhan.springbootrestcrud.dto.DepartmentDTO;
import com.erhan.springbootrestcrud.dto.StaffDTO;
import com.erhan.springbootrestcrud.model.PageForClient;

import javassist.NotFoundException;

public interface StaffService {

	public StaffDTO create(StaffDTO staff);
	public StaffDTO createWithDepartmentId(StaffDTO staff, Long departmentId, MultipartFile imageFile) throws NotFoundException, InvalidPathException, IOException;
	public StaffDTO update(StaffDTO staff);
	public StaffDTO updateWithDepartmentId(Long staffId, Long departmentId, StaffDTO staff, MultipartFile imageFile) throws NotFoundException, IllegalArgumentException, InvalidPathException, IOException;
	public void remove(StaffDTO staff);
	public void removeWithDepartmentId(Long staffId, Long departmentId) throws NotFoundException, IllegalArgumentException;
	public StaffDTO findById(Long id) throws NotFoundException;
	public List<StaffDTO> findAll();
	public PageForClient<StaffDTO> findAllPaginated(Pageable pageable) throws NotFoundException;
	public List<StaffDTO> findByFirstName(String firstName) throws NotFoundException;
	public List<StaffDTO> findByLastName(String lastName) throws NotFoundException;
	public List<StaffDTO> findByPhone(String phone) throws NotFoundException;
	public List<StaffDTO> findByEmail(String email) throws NotFoundException;
	public List<StaffDTO> findByCreateDate(Date createDate) throws NotFoundException;
	public List<StaffDTO> findByDepartment(DepartmentDTO department) throws NotFoundException;
	public List<StaffDTO> findByDepartmentId(Long id) throws NotFoundException;
	public PageForClient<StaffDTO> findByDepartmentIdPaginated(Long id, Pageable pageable) throws NotFoundException;
	public StaffDTO findByIdAndDepartmentId(Long staffId, Long departmentId) throws NotFoundException;
	public List<StaffDTO> findByFirstNameAndDepartmentId(String firstName, Long departmentId) throws NotFoundException;
	public PageForClient<StaffDTO> findByFirstNameAndDepartmentIdPaginated(String firstName, Long departmentId, Pageable pageable) throws NotFoundException;
	public List<StaffDTO> findByLastNameAndDepartmentId(String lastName, Long departmentId) throws NotFoundException;
	public PageForClient<StaffDTO> findByLastNameAndDepartmentIdPaginated(String lastName, Long departmentId, Pageable pageable) throws NotFoundException;
	public List<StaffDTO> findByPhoneAndDepartmentId(String phone, Long departmentId)  throws NotFoundException;
	public List<StaffDTO> findByEmailAndDepartmentId(String email, Long departmentId) throws NotFoundException;
	public List<StaffDTO> findByCreateDateAndDepartmentId(Date createDate, Long departmentId) throws NotFoundException;
	public List<StaffDTO> findByDepartmentIdAndQueryParameters(Long departmentId, Map<String, String> queryParameters) throws NotFoundException, IllegalArgumentException, ParseException;
	public PageForClient<StaffDTO> findByDepartmentIdAndQueryParametersPaginated(Long departmentId, Map<String, String> queryParameters, Pageable pageable) throws NotFoundException, IllegalArgumentException, ParseException;
}
