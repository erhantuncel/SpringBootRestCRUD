package com.erhan.springbootrestcrud.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@EqualsAndHashCode
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@Column(name = "user_name")
	@NotEmpty
	@Size(max = 100)
	private String userName;
	
	@Column(name = "password")
	@NotEmpty
	@Size(max = 200)
	private String password;

	public User(@NotEmpty @Size(max = 100) String userName, @NotEmpty @Size(max = 200) String password) {
		super();
		this.userName = userName;
		this.password = password;
	}
}
