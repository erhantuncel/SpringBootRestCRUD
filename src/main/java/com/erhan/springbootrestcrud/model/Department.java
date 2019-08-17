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


@Entity
@Table(name = "departments")
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
	
	public Department() {
	
	}

	public Department(Long id, @NotEmpty @NotNull @Size(max = 100) String departmentName, Set<Staff> staffList) {
		super();
		this.id = id;
		this.departmentName = departmentName;
		this.staffList = staffList;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public Set<Staff> getStaffList() {
		return staffList;
	}

	public void setStaffList(Set<Staff> staffList) {
		this.staffList = staffList;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((departmentName == null) ? 0 : departmentName.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((staffList == null) ? 0 : staffList.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Department other = (Department) obj;
		if (departmentName == null) {
			if (other.departmentName != null)
				return false;
		} else if (!departmentName.equals(other.departmentName))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (staffList == null) {
			if (other.staffList != null)
				return false;
		} else if (!staffList.equals(other.staffList))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Department [id=" + id + ", departmentName=" + departmentName + "]";
	}
}
