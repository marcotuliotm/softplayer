package com.softplayer.backend.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.br.CPF;

import com.softplayer.backend.dto.Gender;
import com.softplayer.backend.dto.PersonDTO;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Person {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Setter
	private Long id;

	@NotBlank
	@Size(max = 150)
	private String name;

	@NotNull
	private LocalDate birth;

	@Enumerated(EnumType.STRING)
	private Gender gender;

	@Size(max = 100)
	private String placeOfBirth;

	@Size(max = 100)
	private String nationality;

	@CPF
	@NotBlank
	@Column(unique = true)
	private String cpf;

	@Email
	@Size(max = 100)
	private String email;

	@NotNull
	private LocalDateTime createdAt;

	@NotNull
	private LocalDateTime updatedAt;

	@PrePersist
	private void prePersist() {
		createdAt = LocalDateTime.now();
		updatedAt = createdAt;
	}

	@PreUpdate
	private void preUpdate() {
		updatedAt = LocalDateTime.now();
	}

	public void update(PersonDTO personDTO) {
		birth = personDTO.getBirth();
		name = personDTO.getName();
		email = personDTO.getEmail();
		cpf = personDTO.getCpf();
		nationality = personDTO.getNationality();
		nationality = personDTO.getNationality();
		placeOfBirth = personDTO.getPlaceOfBirth();
		gender = personDTO.getGender();
	}
}
