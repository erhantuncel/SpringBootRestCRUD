package com.erhan.springbootrestcrud.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "departments")
@NoArgsConstructor
@Data
@EqualsAndHashCode
public class Department {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@Column(name = "department_name")
	@NotEmpty
	@NotNull
	@Size(max = 100)
	private String departmentName;

	@JsonIgnore
	@OneToMany(mappedBy = "department", fetch = FetchType.EAGER, 
			   cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<Staff> staffList = new HashSet<>();
	
	
	public Department(@NotEmpty @NotNull @Size(max = 100) String departmentName) {
		super();
		this.departmentName = departmentName;
	}
	
	@Override
	public String toString() {
		return "Department [id=" + id + ", departmentName=" + departmentName + "]";
	}
}
