package com.erhan.springbootrestcrud.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.erhan.springbootrestcrud.model.Department;

public interface DepartmentDAO extends JpaRepository<Department, Long> {

	public Department findFirstByDepartmentName(String name);
}
