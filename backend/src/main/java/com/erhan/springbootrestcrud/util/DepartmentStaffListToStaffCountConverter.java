package com.erhan.springbootrestcrud.util;

import java.util.Set;

import org.modelmapper.AbstractConverter;

import com.erhan.springbootrestcrud.model.Staff;


public class DepartmentStaffListToStaffCountConverter extends AbstractConverter<Set<Staff>, Integer> {

	@Override
	protected Integer convert(Set<Staff> staffList) {
		if(staffList != null) {
			return staffList.size();
		} else {
			return 0;
		}
	}
}
