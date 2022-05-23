package com.erhan.springbootrestcrud.controller;

import java.util.Arrays;
import java.util.List;

import javax.validation.Valid;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.erhan.springbootrestcrud.annotation.ApiPageable;
import com.erhan.springbootrestcrud.dto.DepartmentDTO;
import com.erhan.springbootrestcrud.model.PageForClient;
import com.erhan.springbootrestcrud.service.DepartmentService;
import com.erhan.springbootrestcrud.util.ApiPaths;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.SwaggerDefinition;
import io.swagger.annotations.Tag;
import lombok.extern.slf4j.Slf4j;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping(ApiPaths.PathForDepartmentController.departments)
@Slf4j
@Api(value = ApiPaths.PathForDepartmentController.departments, tags = {"Department Resource"})
@SwaggerDefinition(tags = {
		@Tag(name = "Department Resource", description = "Departments APIs")
})
@CrossOrigin
public class DepartmentController {
	
	private final DepartmentService departmentService;
	
	public DepartmentController(DepartmentService departmentService) {
		this.departmentService = departmentService;
	}
	
	@GetMapping
	@ApiOperation(value = "Get all departments operation.", response = DepartmentDTO.class)
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
	@ApiOperation(value = "Get all departments with pagination operation.", response = PageForClient.class)
	@ApiPageable
	public ResponseEntity<PageForClient<DepartmentDTO>> getAllDepartmentsWithPagination(@ApiIgnore Pageable pageable) {
		log.info("getAllDepartmentsWithPaginated method is invoked");
		PageForClient<DepartmentDTO> pageForContent = departmentService.findAllPaginated(pageable);
		return ResponseEntity.ok(pageForContent);
	}
	
	@GetMapping("/{departmentId}")
	@ApiOperation(value = "Get department by Id operation.", response = DepartmentDTO.class)
	public ResponseEntity<DepartmentDTO> getDepartmentById(@PathVariable(value = "departmentId", required = true)Long id) {
		log.info("getDepartmentById method is invoked.");
		DepartmentDTO department = departmentService.findById(id);
		return ResponseEntity.ok(department);
	}
	
	@PostMapping
	@ApiOperation(value = "Create department operation.", response = DepartmentDTO.class)
	public ResponseEntity<DepartmentDTO> createDepartment(@Valid @RequestBody DepartmentDTO department) {
		log.info("createDepartment method is invoked.");
		DepartmentDTO departmentDTO = departmentService.create(department);
		return ResponseEntity.ok(departmentDTO);
	}
	
	@PutMapping("/{departmentId}")
	@ApiOperation(value = "Update department operation.", response = DepartmentDTO.class)
	public ResponseEntity<DepartmentDTO> updateDepartment(@PathVariable("departmentId") Long departmentId, @RequestBody DepartmentDTO department) {
		log.info("updateDepartment method is invoked.");
		department.setId(departmentId);
		departmentService.update(department);
		return ResponseEntity.ok(department);
	}
	
	@DeleteMapping("/{departmentId}")
	@ApiOperation(value = "Delete department operation.")
	public ResponseEntity<?> deleteDepartment(@PathVariable("departmentId") Long departmentId) {
		log.info("deleteDepartment method is invoked.");
		departmentService.removeById(departmentId);
		return ResponseEntity.ok().build();
	}
}
