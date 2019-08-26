package com.erhan.springbootrestcrud.controller;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;
import javax.validation.Validator;

import org.hibernate.hql.internal.ast.InvalidPathException;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.erhan.springbootrestcrud.dto.StaffDTO;
import com.erhan.springbootrestcrud.model.PageForClient;
import com.erhan.springbootrestcrud.service.StaffService;
import com.erhan.springbootrestcrud.util.ApiPaths;

import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(ApiPaths.PathForStaffController.staffs)
@Slf4j
public class StaffController {

	private final StaffService staffService;
	
	public StaffController(StaffService staffService, Validator validator) {
		this.staffService = staffService;
	}

	@GetMapping
	public ResponseEntity<List<StaffDTO>> getAllStaffs(
			@PathVariable("departmentId") Long departmentId, 
			@RequestParam Map<String, String> queryParameters) throws IllegalArgumentException, NotFoundException, ParseException {
		log.info("getAllStaffs method is invoked.");
		List<StaffDTO> staffList = null;
		if(!queryParameters.isEmpty()) {
			staffList = staffService.findByDepartmentIdAndQueryParameters(departmentId, queryParameters);
		} else {
			staffList = staffService.findByDepartmentId(departmentId);
		}
		return ResponseEntity.ok(staffList);
	}
	
	@GetMapping("/pagination")
	public ResponseEntity<PageForClient<StaffDTO>> getAllStaffsWithQueryParameterAndPagination(
			@PathVariable("departmentId") Long departmentId,
			@RequestParam Map<String, String> queryParameters, 
			Pageable pageable) throws NotFoundException, IllegalArgumentException, ParseException {
		log.info("getAllStaffsWithPagination method is invoked.");
		PageForClient<StaffDTO> staffListPaginated = staffService.findByDepartmentIdAndQueryParametersPaginated(
				departmentId, queryParameters, pageable);
		
		return ResponseEntity.ok(staffListPaginated);
	}
	
	@GetMapping("/{staffId}")
	public ResponseEntity<StaffDTO> getByStaffId(@PathVariable("departmentId") Long departmentId, @PathVariable("staffId") Long staffId) throws NotFoundException {
		log.info("getByStaffId method is invoked.");
		StaffDTO staffDTO = staffService.findByIdAndDepartmentId(staffId, departmentId);
		return ResponseEntity.ok(staffDTO);
	}
	
	@PostMapping
	public ResponseEntity<StaffDTO> createStaff(@PathVariable("departmentId") Long departmentId, 
												@Valid @RequestPart(value = "staff", required = true) StaffDTO staff,
												@RequestPart(value = "image", required = false) MultipartFile imageFile) 
														throws InvalidPathException, NotFoundException, IOException {
		log.info("createStaff method is invoked.");
		StaffDTO createWithDepartmentId = staffService.createWithDepartmentId(staff, departmentId, imageFile);
		return ResponseEntity.ok(createWithDepartmentId);
	}
	
	@PutMapping("{staffId}")
	public ResponseEntity<StaffDTO> updateStaff(@PathVariable("departmentId") Long departmentId, 
												@PathVariable("staffId") Long staffId,
												@RequestPart("staff") StaffDTO staff,
												@RequestPart(value = "image", required = false) MultipartFile imageFile) throws InvalidPathException, IllegalArgumentException, NotFoundException, IOException {
		log.info("updateStaff method is invoked.");
		staff.setId(staffId);
		StaffDTO updateWithDepartmentId = staffService.updateWithDepartmentId(staffId, departmentId, staff, imageFile);
		return ResponseEntity.ok(updateWithDepartmentId);
	}
	
	@DeleteMapping("/{staffId}")
	public ResponseEntity<StaffDTO> deleteStaff(@PathVariable("departmentId") Long departmentId, 
												@PathVariable("staffId") Long staffId) throws IllegalArgumentException, NotFoundException {
		log.info("deleteStaff method is invoked.");
		staffService.removeWithDepartmentId(staffId, departmentId);
		return ResponseEntity.ok().build();
	}
}
