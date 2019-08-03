package com.softplayer.backend.controller;

import static java.util.Collections.singleton;
import static org.springframework.http.HttpStatus.SERVICE_UNAVAILABLE;
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;
import static org.springframework.http.ResponseEntity.status;

import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolationException;

import org.springframework.http.ResponseEntity;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.softplayer.backend.dto.BusinessExceptionDTO;
import com.softplayer.backend.exception.BusinessException;

import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class ControllerAdvice {

	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<Set<BusinessExceptionDTO>> handleServerError(final ConstraintViolationException cve) {
		final Set<BusinessExceptionDTO> violations = cve.getConstraintViolations()
				.stream()
				.map(constraintViolation ->
						new BusinessExceptionDTO(UNPROCESSABLE_ENTITY, constraintViolation.getPropertyPath().toString(), constraintViolation.getMessageTemplate(), constraintViolation.getMessage()))
				.collect(Collectors.toSet());
		return status(UNPROCESSABLE_ENTITY)
				.body(violations);
	}

	@ExceptionHandler(BusinessException.class)
	public ResponseEntity<Set<BusinessExceptionDTO>> handleServerError(final BusinessException businessException) {
				return status(UNPROCESSABLE_ENTITY)
				.body(singleton(new BusinessExceptionDTO(UNPROCESSABLE_ENTITY, businessException.getPropertyPath(), businessException.getMessageTemplate(), businessException.getMessage())));
	}

	@ExceptionHandler(TransactionSystemException.class)
	public ResponseEntity<Set<BusinessExceptionDTO>> handleServerError(final TransactionSystemException businessException) {
		if(businessException.getCause().getCause() instanceof ConstraintViolationException) {
			return handleServerError((ConstraintViolationException) businessException.getCause().getCause());
		}
		return status(SERVICE_UNAVAILABLE)
				.body(singleton(new BusinessExceptionDTO(SERVICE_UNAVAILABLE, "server", "server.error", "unexpected")));
	}
}
