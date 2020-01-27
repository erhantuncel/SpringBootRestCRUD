package com.erhan.springbootrestcrud.dto;


import java.util.Set;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.erhan.springbootrestcrud.model.Staff;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "Department Data Transfer Object")
public class DepartmentDTO {

	@ApiModelProperty(value = "Id", required = true)
	private Long id;
	
	@NotEmpty
	@Size(max = 100)
	@ApiModelProperty(value = "Department Name", required = true)
	private String departmentName;
	
	private Set<Staff> staffList;	
	
	public DepartmentDTO(String departmentName) {
		this.departmentName = departmentName;
	}
}
