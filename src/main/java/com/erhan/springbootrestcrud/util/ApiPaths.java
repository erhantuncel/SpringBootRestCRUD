package com.erhan.springbootrestcrud.util;

public final class ApiPaths {

	private static final String BASE_PATH = "/rest";
	
	public static final class PathForDepartmentController {
		public static final String departments = BASE_PATH + "/departments";
	}
	
	public static final class PathForStaffController {
		public static final String staffs = BASE_PATH + "/departments/{departmentId}/staffs";
	}
}
