package com.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler({CustomException.class})  // these exception is working from created class
	public ResponseEntity<String> exceptionHandler(CustomException ex)
	{
		String message = ex.getMessage();
		
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body(message);
		
	}
	

}
