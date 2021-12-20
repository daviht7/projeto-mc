package com.daviholanda.cursomc.resources.exception;

import javax.servlet.http.HttpServletRequest;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.daviholanda.cursomc.service.exceptions.AuthorizationException;
import com.daviholanda.cursomc.service.exceptions.DataIntegrityException;
import com.daviholanda.cursomc.service.exceptions.FileException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.daviholanda.cursomc.service.exceptions.ObjectNotFoundException;

@ControllerAdvice
public class ResourceExceptionHandler {

	@ExceptionHandler(ObjectNotFoundException.class)
	public ResponseEntity<StandardError> objectNotFound(ObjectNotFoundException e, HttpServletRequest request) {
		HttpStatus status = HttpStatus.NOT_FOUND;
		StandardError err = new StandardError(status.value(), e.getMessage(), System.currentTimeMillis());
		return ResponseEntity.status(status).body(err);
	}

	@ExceptionHandler(DataIntegrityException.class)
	public ResponseEntity<StandardError> dataIntegrity(DataIntegrityException e, HttpServletRequest request) {
		HttpStatus status = HttpStatus.BAD_REQUEST;
		StandardError err = new StandardError(status.value(), e.getMessage(), System.currentTimeMillis());
		return ResponseEntity.status(status).body(err);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<StandardError> validation(MethodArgumentNotValidException e, HttpServletRequest request) {
		HttpStatus status = HttpStatus.BAD_REQUEST;
		ValidationError err = new ValidationError(status.value(),"Erro de validação", System.currentTimeMillis());

		for(FieldError x : e.getBindingResult().getFieldErrors()) {
			err.addError(x.getField(),x.getDefaultMessage());
		}

		return ResponseEntity.status(status).body(err);
	}

	@ExceptionHandler(AuthorizationException.class)
	public ResponseEntity<StandardError> authorizationException(AuthorizationException e, HttpServletRequest request) {
		HttpStatus status = HttpStatus.FORBIDDEN;
		StandardError err = new StandardError(status.value(), e.getMessage(), System.currentTimeMillis());
		return ResponseEntity.status(status).body(err);
	}

	@ExceptionHandler(FileException.class)
	public ResponseEntity<StandardError> fileException(FileException e, HttpServletRequest request) {
		HttpStatus status = HttpStatus.BAD_REQUEST;
		StandardError err = new StandardError(status.value(), e.getMessage(), System.currentTimeMillis());
		return ResponseEntity.status(status).body(err);
	}

	@ExceptionHandler(AmazonServiceException.class)
	public ResponseEntity<StandardError> amazonServiceException(AmazonServiceException e, HttpServletRequest request) {
		HttpStatus status = HttpStatus.valueOf(e.getErrorCode());
		StandardError err = new StandardError(status.value(), e.getMessage(), System.currentTimeMillis());
		return ResponseEntity.status(status).body(err);
	}

	@ExceptionHandler(AmazonClientException.class)
	public ResponseEntity<StandardError> amazonClientException(AmazonClientException e, HttpServletRequest request) {
		HttpStatus status = HttpStatus.BAD_REQUEST;
		StandardError err = new StandardError(status.value(), e.getMessage(), System.currentTimeMillis());
		return ResponseEntity.status(status).body(err);
	}

	@ExceptionHandler(AmazonS3Exception.class)
	public ResponseEntity<StandardError> amazonS3Exception(AmazonS3Exception e, HttpServletRequest request) {
		HttpStatus status = HttpStatus.BAD_REQUEST;
		StandardError err = new StandardError(status.value(), e.getMessage(), System.currentTimeMillis());
		return ResponseEntity.status(status).body(err);
	}

}
