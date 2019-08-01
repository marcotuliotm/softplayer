package com.softplayer.backend.dto;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BusinessExceptionDTO {
	private HttpStatus status;
	private String propertyPath;
	private String messageTemplate;
	private String message;
}
