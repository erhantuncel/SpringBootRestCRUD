package com.erhan.springbootrestcrud;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.erhan.springbootrestcrud.dto.DepartmentDTO;
import com.erhan.springbootrestcrud.model.Department;
import com.erhan.springbootrestcrud.util.DepartmentStaffListToStaffCountConverter;

@SpringBootApplication
public class SpringBootRestCrudApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootRestCrudApplication.class, args);
	}

	@Bean
	public ModelMapper getModelMapper() {
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		modelMapper.typeMap(Department.class, DepartmentDTO.class)
				   .addMappings(new PropertyMap<Department, DepartmentDTO>() {
			@Override
			protected void configure() {
				using(new DepartmentStaffListToStaffCountConverter()).map(source.getStaffList(), destination.getStaffCount());
			}
		});
		return modelMapper;
	}
}
