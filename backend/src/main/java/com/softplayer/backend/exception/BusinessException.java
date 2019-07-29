package com.softplayer.backend.exception;

import javax.validation.ValidationException;

import lombok.Getter;

@Getter
public class PersonNotFoundException extends ValidationException {
	private String propertyPath;
	private String messageTemplate;
	private String message;

	public PersonNotFoundException(String cpf) {
		this.propertyPath = "person";
		this.messageTemplate = "{com.softplayer.backend.exception.person.message}";
		this.message = String.format("A pessoa que possui o CPF: %s nao está cadastrada!", cpf);
	}
}
