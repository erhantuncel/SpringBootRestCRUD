package com.erhan.springbootrestcrud.exception;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import lombok.extern.slf4j.Slf4j;

@RestController
@ControllerAdvice
@Slf4j
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler({Exception.class})
	public final ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) {
		log.info("Exception message = " + ex.getMessage());
		log.debug("Exception trace = " + ex.getStackTrace());
		List<String> details = new ArrayList<String>();
		details.add(ex.getMessage());
		String type = ex.getClass().getSimpleName();
		String cause = ex.getCause().getClass().getSimpleName();
		ApiErrorResponse apiErrorResponse = new ApiErrorResponse(new Date(), "Server Error", type, cause, details);
		return new ResponseEntity<Object>(apiErrorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		log.info("Validation error - Count = " + ex.getBindingResult().getErrorCount());
		List<String> details = new ArrayList<>();
		for(ObjectError error : ex.getBindingResult().getAllErrors()) {
			for(Object arg : error.getArguments()) {
				log.debug("Object = " + error.getObjectName() + " Argument = " + arg.toString());
			}
			details.add(error.getDefaultMessage());
		}
		String type = ex.getClass().getSimpleName();
		String cause = ex.getCause().getClass().getSimpleName();
		ApiErrorResponse errorResponse = new ApiErrorResponse(new Date(), "Validation Error", type, cause, details);
		return new ResponseEntity<Object>(errorResponse, HttpStatus.BAD_REQUEST);
	}
	
	
}
