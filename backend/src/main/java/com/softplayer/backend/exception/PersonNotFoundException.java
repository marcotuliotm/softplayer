package com.softplayer.backend.exception;

import javax.validation.ValidationException;

import lombok.Getter;

@Getter
public class CPFException extends ValidationException {
	private String propertyPath;
	private String messageTemplate;
	private String message;

	public CPFException(String cpf) {
		this.propertyPath = "cpf";
		this.messageTemplate = "{com.softplayer.backend.exception.CPF.message}";
		this.message = String.format("Esse CPF: %s já existe!", cpf);
	}
}
