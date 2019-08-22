package com.erhan.springbootrestcrud.service;

import java.util.Arrays;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.erhan.springbootrestcrud.dao.DepartmentDAO;
import com.erhan.springbootrestcrud.dto.DepartmentDTO;
import com.erhan.springbootrestcrud.model.Department;
import com.erhan.springbootrestcrud.model.PageForClient;

@Service
@Transactional
public class DepartmentServiceImpl implements DepartmentService {

	@Autowired
	DepartmentDAO departmentDAO;
	
	@Autowired
	ModelMapper modelMapper;
	
	@Override
	public DepartmentDTO create(DepartmentDTO department) {
		Department departmentEntity = modelMapper.map(department, Department.class);
		departmentDAO.save(departmentEntity);
		department.setId(departmentEntity.getId());
		return department;
	}

	@Override
	public DepartmentDTO update(DepartmentDTO department) {
		Department departmentEntity = modelMapper.map(department, Department.class);
		departmentDAO.save(departmentEntity);
		return modelMapper.map(departmentEntity, DepartmentDTO.class);
	}

	@Override
	public void remove(DepartmentDTO department) {
		Department departmentEntity = modelMapper.map(department, Department.class);
		departmentDAO.delete(departmentEntity);
	}

	@Override
	public void removeById(Long id) {
		departmentDAO.deleteById(id);
	}

	@Override
	public DepartmentDTO findById(Long id) {
		Department departmentEntity = departmentDAO.findById(id).orElse(null);
		DepartmentDTO departmentDTO = modelMapper.map(departmentEntity, DepartmentDTO.class);
		return departmentDTO;
	}

	@Override
	public List<DepartmentDTO> findAll() {
		List<Department> departmentList = departmentDAO.findAll();
		return Arrays.asList(modelMapper.map(departmentList, DepartmentDTO[].class));
	}

	@Override
	public DepartmentDTO findByDepartmentName(String name) {
		Department departmentEntity = departmentDAO.findFirstByDepartmentName(name);
		return modelMapper.map(departmentEntity, DepartmentDTO.class);
	}

	@Override
	public PageForClient<DepartmentDTO> findAllPaginated(Pageable pageable) {
		Page<Department> pageDepartment = departmentDAO.findAll(pageable);
		PageForClient<DepartmentDTO> pageForClient = new PageForClient<DepartmentDTO>();
		List<DepartmentDTO> departmentDTOList = Arrays.asList(modelMapper.map(pageDepartment.getContent(), DepartmentDTO[].class));
		pageForClient.setFields(pageDepartment, departmentDTOList);
		return pageForClient;
	}
}
