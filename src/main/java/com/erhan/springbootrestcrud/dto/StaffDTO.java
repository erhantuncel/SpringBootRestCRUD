package com.erhan.springbootrestcrud.dto;

import java.util.Date;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StaffDTO {

	private Long id;
	
	@NotEmpty
	@Size(max = 40)
	private String firstName;
	
	@NotNull
	@Size(max = 40)
	private String lastName;

	@NotNull
	@Size(max = 10)
	@Pattern(regexp="[0-9]{10}", message = "Phone must be 10 numbers.")
	private String phone;
	
	@Email
	private String email;
	
	private byte[] image;
	
	@JsonFormat(shape = Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss", timezone="Europe/Istanbul")
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
