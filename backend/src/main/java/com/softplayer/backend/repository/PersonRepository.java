package com.softplayer.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.softplayer.backend.domain.Person;

public interface PersonRepository extends JpaRepository<Person, Long> {
}
