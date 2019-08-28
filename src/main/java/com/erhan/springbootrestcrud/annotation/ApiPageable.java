package com.erhan.springbootrestcrud.annotation;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;

@Retention(RUNTIME)
@Target({ TYPE, METHOD, ANNOTATION_TYPE })
@ApiImplicitParams({
	@ApiImplicitParam(name = "page", dataType = "integer", paramType = "query", 
			value = "Results page you want to retrive.", defaultValue = "0"),
	@ApiImplicitParam(name = "size", dataType = "integer", paramType = "query",
			value = "Number of records per page.", defaultValue = "5"),
	@ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query",
			value = "Sorting criteria in the format: property(asc|desc). " +
					"Default sort order is ascending. " + "Multiple sort criteria are supported.")})
public @interface ApiPageable {

}
