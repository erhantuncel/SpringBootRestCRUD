package com.erhan.springbootrestcrud.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.erhan.springbootrestcrud.dto.DepartmentDTO;
import com.erhan.springbootrestcrud.model.Department;
import com.erhan.springbootrestcrud.model.PageForClient;
import com.erhan.springbootrestcrud.service.DepartmentService;
import com.erhan.springbootrestcrud.util.ApiPaths;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(ApiPaths.PathForDepartmentController.departments)
@Slf4j
public class DepartmentController {
	
	private final DepartmentService departmentService;
	
	public DepartmentController(DepartmentService departmentService) {
		this.departmentService = departmentService;
	}
	
	@GetMapping
	public ResponseEntity<List<DepartmentDTO>> getAllDepartments(
			@RequestParam(value = "departmentName", required = false) String departmentName, 
			Pageable pageable) {
		log.info("getAllDepartments method is invoked");
		List<DepartmentDTO> allDepartments = null;
		if(departmentName != null) {
			allDepartments = Arrays.asList(departmentService.findByDepartmentName(departmentName));			
		} else {
			allDepartments = departmentService.findAll();			
		}
		return ResponseEntity.ok(allDepartments);
	}
	
	@GetMapping("/pagination")
	public ResponseEntity<PageForClient<DepartmentDTO>> getAllDepartmentsWithPaginated(Pageable pageable) {
		log.info("getAllDepartmentsWithPaginated method is invoked");
		PageForClient<DepartmentDTO> pageForContent = departmentService.findAllPaginated(pageable);
		return ResponseEntity.ok(pageForContent);
	}
	
	@GetMapping("/{departmentId}")
	public ResponseEntity<DepartmentDTO> getDepartmentById(@PathVariable(value = "departmentId", required = true)Long id) {
		log.info("getDepartmentById method is invoked.");
		DepartmentDTO department = departmentService.findById(id);
		return ResponseEntity.ok(department);
	}
	
	@PostMapping
	public ResponseEntity<DepartmentDTO> createDepartment(@RequestBody DepartmentDTO department) {
		log.info("createDepartment method is invoked.");
		DepartmentDTO departmentDTO = departmentService.create(department);
		return ResponseEntity.ok(departmentDTO);
	}
	
	@PutMapping("/{departmentId}")
	public ResponseEntity<DepartmentDTO> updateDepartment(@PathVariable("departmentId") Long departmentId, @RequestBody DepartmentDTO department) {
		log.info("updateDepartment method is invoked.");
		department.setId(departmentId);
		departmentService.update(department);
		return ResponseEntity.ok(department);
	}
	
	@DeleteMapping("/{departmentId}")
	public ResponseEntity<Department> deleteDepartment(@PathVariable("departmentId") Long departmentId) {
		log.info("deleteDepartment method is invoked.");
		departmentService.removeById(departmentId);
		return ResponseEntity.ok().build();
	}
}
