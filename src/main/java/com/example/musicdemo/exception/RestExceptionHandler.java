package com.example.musicdemo.exception;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.RestClientException;

import com.example.musicdemo.model.ErrorModel;

@RestControllerAdvice
public class RestExceptionHandler
{
	private final static Logger log = LoggerFactory.getLogger(RestExceptionHandler.class);
	
	@ExceptionHandler(RestClientException.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public ErrorModel handleRestClientException(RestClientException ex)
	{
		return new ErrorModel.ErrorDtoBuilder()
				.withTitle("WebHook Communication Error")
				.withStatus(500)
				.withDetail(ex.getMessage())
				.build();
	}

	@ExceptionHandler(ResourceExistsException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ErrorModel handleResourceExistsException(ResourceExistsException ex)
	{
		return new ErrorModel.ErrorDtoBuilder()
				.withTitle("Save Error")
				.withStatus(400)
				.withDetail(ex.getMessage())
				.build();
	}
	
	@ExceptionHandler(ResourceNotFoundException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ErrorModel handleResourceNotFoundException(ResourceNotFoundException ex)
	{
		return new ErrorModel.ErrorDtoBuilder()
				.withTitle("Retrieval Error")
				.withStatus(400)
				.withDetail(ex.getMessage())
				.build();
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ErrorModel handleMethodArgumentNotValidException(MethodArgumentNotValidException ex)
	{
		List<String> errorMessages = new ArrayList<>();
		
		ex.getFieldErrors().forEach(field -> {
			errorMessages.add(field.getField() +  ": " + field.getDefaultMessage());
		});
		
		return new ErrorModel.ErrorDtoBuilder()
				.withTitle("Validation Error")
				.withStatus(400)
				.withDetail(String.join(", ", errorMessages.toArray(new String[errorMessages.size()])))
				.build();
	}
	
	@ExceptionHandler(Throwable.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public ErrorModel handleThrowable(Throwable th)
	{
		log.error("Got exception", th);
		
		return new ErrorModel.ErrorDtoBuilder()
				.withTitle("Server Error")
				.withStatus(500)
				.withDetail("An unexpected error occurred. Check log for details")
				.build();
	}
}
