package com.erhan.springbootrestcrud.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentDTO {

	private Long id;
	private String departmentName;
	
	public DepartmentDTO(String departmentName) {
		this.departmentName = departmentName;
	}
}
