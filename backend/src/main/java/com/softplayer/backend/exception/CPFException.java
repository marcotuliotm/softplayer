package com.softplayer.backend.exception;

public class CPFException extends BusinessException {

	public CPFException(String cpf) {
		super("cpf", "{com.softplayer.backend.exception.CPF.message}",  String.format("Esse CPF: %s já existe!", cpf));
	}
}
