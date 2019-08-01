package com.softplayer.backend.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PersonDTO {

	private Long id;
	private String name;
	private LocalDate birth;
	private Gender gender;
	private String placeOfBirth;
	private String nationality;
	private String cpf;
	private String email;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
}
