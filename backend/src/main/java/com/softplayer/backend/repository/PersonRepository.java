package com.softplayer.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.softplayer.backend.domain.Person;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {
	boolean existsByCpf(String cpf);
	List<Person> findByNameStartsWithOrEmailStartsWith(String name, String email);
}
