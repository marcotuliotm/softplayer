package com.softplayer.backend.controller;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.ResponseEntity.noContent;
import static org.springframework.http.ResponseEntity.ok;
import static org.springframework.http.ResponseEntity.status;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.softplayer.backend.dto.PersonDTO;
import com.softplayer.backend.service.PersonService;

@RestController
@RequestMapping(value = "persons", produces = MediaType.APPLICATION_JSON_VALUE)
public class PersonController {

	@Autowired
	private PersonService service;

	@PostMapping
	public ResponseEntity<PersonDTO> create(@RequestBody PersonDTO personDTO) {
		return status(CREATED)
				.body(service.create(personDTO));
	}

	@GetMapping
	public ResponseEntity<List<PersonDTO>> getAll() {
		return ok(service.findAll());
	}

	@GetMapping("/{filter}")
	public ResponseEntity<List<PersonDTO>> findByNameOrEmail(@PathVariable("filter") String filter) {
		return ok(service.findByNameOrEmail(filter));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> remove(@PathVariable("id") long id) {
		service.remove(id);
		return noContent().build();
	}

	@PutMapping("/{id}")
	public ResponseEntity<PersonDTO> update(@PathVariable("id") long id, @RequestBody PersonDTO personDTO) {
		personDTO.setId(id);
		return ok(service.update(personDTO));
	}
}
