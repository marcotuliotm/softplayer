package com.softplayer.backend.dto;

import org.springframework.http.HttpStatus;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BusinessException {
	private HttpStatus status;
	private String propertyPath;
	private String messageTemplate;
	private String message;
}
