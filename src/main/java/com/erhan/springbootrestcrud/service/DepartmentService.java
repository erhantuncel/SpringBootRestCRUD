package com.erhan.springbootrestcrud.service;

import java.util.List;

import com.erhan.springbootrestcrud.model.Department;

public interface DepartmentService {

	public void create(Department department);
	public void update(Department department);
	public void remove(Department department);
	public void removeById(Long id);
	public Department findById(Long id);
	public List<Department> findAll();
	public Department findByDepartmentName(String name);
	public List<Department> findAllPaginated(int page, int pageSize);
}
