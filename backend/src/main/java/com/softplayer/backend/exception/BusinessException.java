package com.softplayer.backend.exception;

import javax.validation.ValidationException;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public abstract class BusinessException extends ValidationException {
	private String propertyPath;
	private String messageTemplate;
	private String message;
}
