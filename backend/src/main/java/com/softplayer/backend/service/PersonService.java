package com.softplayer.backend.service;

import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.toList;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.softplayer.backend.domain.Person;
import com.softplayer.backend.dto.PersonDTO;
import com.softplayer.backend.exception.CPFException;
import com.softplayer.backend.exception.PersonNotFoundException;
import com.softplayer.backend.repository.PersonRepository;

@Service
public class PersonService {

	@Autowired
	private PersonRepository repository;

	@Transactional
	public PersonDTO create(PersonDTO personDTO) {
		requireNonNull(personDTO);
		if (repository.existsByCpf(personDTO.getCpf())) {
			throw new CPFException(personDTO.getCpf());
		}
		final Person person = buildPerson(personDTO);
		return buildDTO(repository.save(person));
	}

	public List<PersonDTO> findAll() {
		return repository.findAll()
				.stream()
				.map(this::buildDTO)
				.collect(toList());
	}

	public List<PersonDTO> findByNameOrEmail(String filter) {
		return repository.findByNameStartsWithOrEmailStartsWith(filter, filter)
				.stream()
				.map(this::buildDTO)
				.collect(toList());
	}

	@Transactional
	public void remove(Long id) {
		repository.deleteById(id);
	}

	private Person buildPerson(PersonDTO personDTO) {
		return Person.builder()
				.id(personDTO.getId())
				.birth(personDTO.getBirth())
				.name(personDTO.getName())
				.email(personDTO.getEmail())
				.cpf(personDTO.getCpf())
				.nationality(personDTO.getNationality())
				.nationality(personDTO.getNationality())
				.placeOfBirth(personDTO.getPlaceOfBirth())
				.gender(personDTO.getGender())
				.build();
	}

	private PersonDTO buildDTO(Person person) {
		return PersonDTO.builder()
				.id(person.getId())
				.birth(person.getBirth())
				.name(person.getName())
				.email(person.getEmail())
				.cpf(person.getCpf())
				.nationality(person.getNationality())
				.nationality(person.getNationality())
				.placeOfBirth(person.getPlaceOfBirth())
				.gender(person.getGender())
				.updatedAt(person.getUpdatedAt())
				.createdAt(person.getCreatedAt())
				.build();
	}

	@Transactional
	public PersonDTO update(PersonDTO personDTO) {
		requireNonNull(personDTO);

		final Person person = repository.findById(personDTO.getId())
				.orElseThrow(PersonNotFoundException::new);

		if (!person.getCpf().equals(personDTO.getCpf()) && repository.existsByCpf(personDTO.getCpf())) {
			throw new CPFException(personDTO.getCpf());
		}

		person.update(personDTO);

		return buildDTO(repository.save(person));
	}
}
