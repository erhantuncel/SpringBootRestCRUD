package com.erhan.springbootrestcrud.dto;


import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentDTO {

	private Long id;
	
	@NotEmpty
	@Size(max = 100)
	private String departmentName;
	
	public DepartmentDTO(String departmentName) {
		this.departmentName = departmentName;
	}
}
