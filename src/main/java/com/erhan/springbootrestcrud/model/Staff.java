package com.erhan.springbootrestcrud.model;

import java.util.Arrays;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Type;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "staffs")
@NoArgsConstructor
@Data
@EqualsAndHashCode(exclude = "department")
public class Staff {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotEmpty
	@NotNull
	@Size(max = 40)
	@Column(name = "FIRST_NAME")
	private String firstName;
	
	@NotEmpty
	@NotNull
	@Size(max = 40)
	@Column(name = "LAST_NAME")
	private String lastName;
	
	@NotEmpty
	@NotNull
	@Size(max = 10)
	@Pattern(regexp="[0-9]{10}", message = "{validation.phone.size.ten}")
	@Column(name = "PHONE")
	private String phone;
	
	@Email
	@Size(max = 40)
	@Column(name = "EMAIL")
	private String email;
	
	@Lob
	@Type(type = "org.hibernate.type.BinaryType")
	@Column(name = "image")
	private byte[] image;
	
	@Temporal(TemporalType.TIMESTAMP)
	@NotNull
	@Column(name = "CREATE_DATE")
	private Date createDate;
	
	
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
	@JoinColumn(name = "department_id")
	private Department department;


	public Staff(@NotEmpty @NotNull @Size(max = 40) String firstName,
			@NotEmpty @NotNull @Size(max = 40) String lastName,
			@NotEmpty @NotNull @Size(max = 10) @Pattern(regexp = "[0-9]{10}", message = "{validation.phone.size.ten}") String phone,
			@Email @Size(max = 40) String email, @NotNull Date createDate, Department department) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.phone = phone;
		this.email = email;
		this.createDate = createDate;
		this.department = department;
	}

	@Override
	public String toString() {
		return "Staff [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", phone=" + phone
				+ ", email=" + email + ", image=" + Arrays.toString(image) + ", createDate=" + createDate + "]";
	}	
}
