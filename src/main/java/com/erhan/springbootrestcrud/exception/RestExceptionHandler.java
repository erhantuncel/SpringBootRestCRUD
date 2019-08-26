package com.erhan.springbootrestcrud.exception;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
		ApiErrorResponse apiErrorResponse = new ApiErrorResponse(new Date(), ex.getMessage());
		return new ResponseEntity<Object>(apiErrorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
