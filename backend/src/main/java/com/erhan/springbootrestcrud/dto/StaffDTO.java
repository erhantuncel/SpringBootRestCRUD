package com.erhan.springbootrestcrud.dto;

import java.util.Date;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "Staff Data Transfer Object")
public class StaffDTO {

	@ApiModelProperty(value = "Id", required = true)
	private Long id;
	
	@NotEmpty
	@Size(max = 40)
	@ApiModelProperty(value = "First Name", required = true)
	private String firstName;
	
	@NotNull
	@Size(max = 40)
	@ApiModelProperty(value = "Last Name", required = true)
	private String lastName;

	@NotNull
	@Size(max = 10)
	@Pattern(regexp="[0-9]{10}", message = "Phone must be 10 numbers.")
	@ApiModelProperty(value = "Phone", required = true)
	private String phone;
	
	@Email
	@ApiModelProperty(value = "E-mail", required = false)
	private String email;
	
	@ApiModelProperty(value = "Image", required = false)
	private byte[] image;
	
	@JsonFormat(shape = Shape.STRING, pattern = "dd.MM.yyyy HH:mm:ss", timezone="Europe/Istanbul")
	@ApiModelProperty(value = "Department", required = true)
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
