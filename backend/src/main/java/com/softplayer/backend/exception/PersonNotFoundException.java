package com.softplayer.backend.exception;

import lombok.Getter;

@Getter
public class PersonNotFoundException  extends BusinessException {

	public PersonNotFoundException() {
		super("person", "{com.softplayer.backend.exception.person.message}", "Pesso nao está cadastrada!");
	}
}
