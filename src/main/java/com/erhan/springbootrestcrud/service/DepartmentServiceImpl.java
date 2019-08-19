package com.erhan.springbootrestcrud.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.erhan.springbootrestcrud.dao.DepartmentDAO;
import com.erhan.springbootrestcrud.model.Department;

@Service
@Transactional
public class DepartmentServiceImpl implements DepartmentService {

	@Autowired
	DepartmentDAO departmentDAO;
	
	@Override
	public void create(Department department) {
		departmentDAO.save(department);
	}

	@Override
	public void update(Department department) {
		departmentDAO.save(department);
	}

	@Override
	public void remove(Department department) {
		departmentDAO.delete(department);
	}

	@Override
	public void removeById(Long id) {
		departmentDAO.deleteById(id);
	}

	@Override
	public Department findById(Long id) {
		return departmentDAO.findById(id).orElse(null);
	}

	@Override
	public List<Department> findAll() {
		return departmentDAO.findAll();
	}

	@Override
	public Department findByDepartmentName(String name) {
		return departmentDAO.findFirstByDepartmentName(name);
	}

	@Override
	public List<Department> findAllPaginated(int page, int pageSize) {
		return departmentDAO.findAll(PageRequest.of(page, pageSize)).getContent();
	}
}
