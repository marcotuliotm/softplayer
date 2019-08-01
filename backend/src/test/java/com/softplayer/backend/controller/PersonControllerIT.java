package com.softplayer.backend.controller;

import static java.util.Arrays.stream;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.softplayer.backend.BackendApplicationTests;
import com.softplayer.backend.domain.Person;
import com.softplayer.backend.dto.BusinessExceptionDTO;
import com.softplayer.backend.dto.Gender;
import com.softplayer.backend.dto.PersonDTO;
import com.softplayer.backend.repository.PersonRepository;


public class PersonControllerIT extends BackendApplicationTests {

	private static final String CPF = "747.696.561-22";
	private static final String CPF2 = "421.275.434-75";
	private static final String CPF3 = "922.549.523-40";

	private static final String NOT_BLANK_MESSAGE_KEY = "{javax.validation.constraints.NotBlank.message}";
	private static final String EMAIL_MESSAGE_KEY = "{javax.validation.constraints.Email.message}";
	private static final String CPF_MESSAGE_KEY = "{org.hibernate.validator.constraints.br.CPF.message}";
	private static final String CPF_UNIQUE_MESSAGE_KEY = "{com.softplayer.backend.exception.CPF.message}";

	private static final String CPF_FIELD = "cpf";
	private static final String EMAIL_FIELD = "email";
	private static final String NAME_FIELD = "name";


	@LocalServerPort
	private int port;

	@Autowired
	private PersonRepository repository;

	private PersonDTO expectFull;

	private final TestRestTemplate REST_TEMPLATE = new TestRestTemplate();

	@Before
	public void before() {
		expectFull = PersonDTO.builder()
				.birth(LocalDate.now())
				.cpf(CPF)
				.gender(Gender.MALE)
				.name("Zé flores")
				.nationality("Brasil")
				.placeOfBirth("Blumenau")
				.email("ddd@hhh.com")
				.build();
		repository.deleteAll();
	}

	@Test
	public void create() {
		ResponseEntity<PersonDTO> responseEntity = REST_TEMPLATE.postForEntity(createURLWithPort(), expectFull, PersonDTO.class);
		assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
		assertPersonDTO(expectFull, responseEntity);

		final PersonDTO expectMin = PersonDTO.builder()
				.birth(LocalDate.now())
				.cpf(CPF2)
				.gender(Gender.FEMALE)
				.name("Maria flores")
				.build();

		responseEntity = REST_TEMPLATE.postForEntity(createURLWithPort(), expectMin, PersonDTO.class);
		assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
		assertPersonDTO(expectMin, responseEntity);
	}

	@Test
	public void createInvalid() {
		expectFull.setCpf(null);
		expectFull.setEmail("formatoerrado");
		expectFull.setName(null);

		ResponseEntity<BusinessExceptionDTO[]> responseEntity = REST_TEMPLATE.postForEntity(createURLWithPort(), expectFull, BusinessExceptionDTO[].class);

		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, responseEntity.getStatusCode());
		assertNotNull(responseEntity.getBody());

		Map<String, List<BusinessExceptionDTO>> businessMap = stream(responseEntity.getBody())
				.collect(Collectors.groupingBy(BusinessExceptionDTO::getPropertyPath));
		assertEquals(3, businessMap.entrySet().size());

		assertInvalidField(businessMap, CPF_FIELD, NOT_BLANK_MESSAGE_KEY);
		assertInvalidField(businessMap, EMAIL_FIELD, EMAIL_MESSAGE_KEY);
		assertInvalidField(businessMap, NAME_FIELD, NOT_BLANK_MESSAGE_KEY);

		expectFull.setCpf("444.888.888-40");
		expectFull.setEmail("sure@sure.com");
		expectFull.setName("Sure");

		responseEntity = REST_TEMPLATE.postForEntity(createURLWithPort(), expectFull, BusinessExceptionDTO[].class);

		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, responseEntity.getStatusCode());
		assertNotNull(responseEntity.getBody());

		businessMap = stream(responseEntity.getBody())
				.collect(Collectors.groupingBy(BusinessExceptionDTO::getPropertyPath));
		assertEquals(1, businessMap.entrySet().size());

		assertInvalidField(businessMap, CPF_FIELD, CPF_MESSAGE_KEY);

		expectFull.setCpf(CPF);
		REST_TEMPLATE.postForEntity(createURLWithPort(), expectFull, PersonDTO.class);

		responseEntity = REST_TEMPLATE.postForEntity(createURLWithPort(), expectFull, BusinessExceptionDTO[].class);

		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, responseEntity.getStatusCode());
		assertNotNull(responseEntity.getBody());

		businessMap = stream(responseEntity.getBody())
				.collect(Collectors.groupingBy(BusinessExceptionDTO::getPropertyPath));
		assertEquals(1, businessMap.entrySet().size());

		assertInvalidField(businessMap, CPF_FIELD, CPF_UNIQUE_MESSAGE_KEY);
	}

	@Test
	public void delete() {
		Long id = repository.save(Person.builder()
				.birth(LocalDate.now())
				.cpf(CPF)
				.gender(Gender.MALE)
				.name("Zé flores")
				.nationality("Brasil")
				.placeOfBirth("Brusque")
				.email("ddd@hhh.com")
				.build()).getId();
		assertEquals(1L, repository.count());
		REST_TEMPLATE.delete(createURLWithPort("/"+ id));
		assertEquals(0L, repository.count());
	}

	@Test
	public void update() {
		final Person person = Person.builder()
				.birth(LocalDate.now())
				.cpf(CPF)
				.gender(Gender.MALE)
				.name("Zé flores")
				.nationality("Brasil")
				.placeOfBirth("Brusque")
				.email("ddd@hhh.com")
				.build();
		Long id = repository.save(person).getId();
		assertEquals(1L, repository.count());
		REST_TEMPLATE.put(createURLWithPort("/" + id), expectFull, PersonDTO.class);
		assertEquals(1L, repository.count());
		Optional<Person> find = repository.findById(id);
		assertTrue(find.isPresent());
		Person update = find.get();
		assertEquals(expectFull.getCpf(), update.getCpf());
		assertEquals(expectFull.getPlaceOfBirth(), update.getPlaceOfBirth());

		expectFull.setPlaceOfBirth("Indaial");
		expectFull.setCpf(CPF3);
		REST_TEMPLATE.put(createURLWithPort("/" + id), expectFull, PersonDTO.class);
		find = repository.findById(id);
		assertTrue(find.isPresent());
		update = find.get();
		assertEquals(expectFull.getPlaceOfBirth(), update.getPlaceOfBirth());

		expectFull.setPlaceOfBirth("Gaspar");
		REST_TEMPLATE.put(createURLWithPort("/" + -1), expectFull, PersonDTO.class);
		find = repository.findById(id);
		assertTrue(find.isPresent());
		update = find.get();
		assertNotEquals(expectFull.getPlaceOfBirth(), update.getPlaceOfBirth());

		final Person person2 = Person.builder()
				.birth(LocalDate.now())
				.cpf(CPF2)
				.gender(Gender.FEMALE)
				.name("Maria flores")
				.nationality("Brasil")
				.placeOfBirth("Itajai")
				.email("maria@hhh.com")
				.build();
		id = repository.save(person2).getId();
		final PersonDTO dto = PersonDTO.builder()
				.birth(LocalDate.now())
				.cpf(CPF3)
				.gender(Gender.FEMALE)
				.name("Maria flores")
				.nationality("Brasil")
				.placeOfBirth("Pomerode")
				.email("maria@hhh.com")
				.build();
		REST_TEMPLATE.put(createURLWithPort("/" + id), dto, PersonDTO.class);
		final Optional<Person> notUpdateFind = repository.findById(id);
		assertTrue(notUpdateFind.isPresent());
		final Person notUpdate = find.get();
		assertNotEquals(dto.getPlaceOfBirth(), notUpdate.getPlaceOfBirth());
		assertEquals(2L, repository.count());
	}

	@Test
	public void findByNameOrEmail() {
		repository.save(Person.builder()
				.birth(LocalDate.now())
				.cpf(CPF)
				.gender(Gender.MALE)
				.name("Zé flores")
				.nationality("Brasil")
				.placeOfBirth("Brusque")
				.email("ddd@hhh.com")
				.build());
		repository.save(Person.builder()
				.birth(LocalDate.now())
				.cpf(CPF2)
				.gender(Gender.FEMALE)
				.name("Maria flores")
				.nationality("Brasil")
				.placeOfBirth("Brusque")
				.email("maria@maa.com")
				.build());
		final ResponseEntity<PersonDTO[]> responseEntity = REST_TEMPLATE.getForEntity(createURLWithPort("/maria"), PersonDTO[].class);
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		final PersonDTO[] body = responseEntity.getBody();
		assertNotNull(body);
		assertEquals(1, body.length);
		assertEquals(CPF2, body[0].getCpf());
	}

	@Test
	public void getAll() {
		repository.save(Person.builder()
				.birth(LocalDate.now())
				.cpf(CPF)
				.gender(Gender.MALE)
				.name("Zé flores")
				.nationality("Brasil")
				.placeOfBirth("Brusque")
				.email("ddd@hhh.com")
				.build());
		repository.save(Person.builder()
				.birth(LocalDate.now())
				.cpf(CPF2)
				.gender(Gender.FEMALE)
				.name("Maria flores")
				.nationality("Brasil")
				.placeOfBirth("Brusque")
				.email("maria@maa.com")
				.build());
		final ResponseEntity<PersonDTO[]> responseEntity = REST_TEMPLATE.getForEntity(createURLWithPort(), PersonDTO[].class);
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		final PersonDTO[] body = responseEntity.getBody();
		assertNotNull(body);
		assertEquals(2, body.length);
	}


	private void assertInvalidField(Map<String, List<BusinessExceptionDTO>> map, String field, String messageTemplate) {
		final List<BusinessExceptionDTO> exceptions = map.get(field);
		assertNotNull(exceptions);
		assertEquals(1, exceptions.size());
		exceptions.forEach(businessExceptionDTO ->
				assertEquals(messageTemplate, businessExceptionDTO.getMessageTemplate()));
	}

	private void assertPersonDTO(PersonDTO expect, ResponseEntity<PersonDTO> responseEntity) {
		PersonDTO actual = responseEntity.getBody();
		assertNotNull(actual);
		expect.setId(actual.getId());
		expect.setUpdatedAt(actual.getUpdatedAt());
		expect.setCreatedAt(actual.getCreatedAt());
		assertEquals(expect, actual);
	}

	private String createURLWithPort(String uri) {
		return  createURLWithPort() + uri;
	}

	private String createURLWithPort() {
		return "http://localhost:" + port + "/persons";
	}
}

