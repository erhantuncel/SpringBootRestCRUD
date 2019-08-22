package com.erhan.springbootrestcrud.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.erhan.springbootrestcrud.dto.DepartmentDTO;
import com.erhan.springbootrestcrud.model.PageForClient;

public interface DepartmentService {

	public DepartmentDTO create(DepartmentDTO department);
	public DepartmentDTO update(DepartmentDTO department);
	public void remove(DepartmentDTO department);
	public void removeById(Long id);
	public DepartmentDTO findById(Long id);
	public List<DepartmentDTO> findAll();
	public DepartmentDTO findByDepartmentName(String name);
	public PageForClient<DepartmentDTO> findAllPaginated(Pageable pageable);
}
