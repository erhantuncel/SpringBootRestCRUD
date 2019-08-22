package com.erhan.springbootrestcrud.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StaffDTO {

	private Long id;
	private String firstName;
	private String lastName;
	private String phone;
	private String email;
	private byte[] image;
	private Date createDate;
	private DepartmentDTO department;
	
	public StaffDTO(String firstName, String lastName, String phone, String email, Date createDate,
			DepartmentDTO department) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.phone = phone;
		this.email = email;
		this.createDate = createDate;
		this.department = department;
	}
	
	
}
