package com.everis.springboot.app.models.service;

import com.everis.springboot.app.models.documents.Person;
import com.everis.springboot.app.models.documents.Teacher;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface TeacherService {

	public Flux<Person> findAllPersons();

	public Mono<Person> findByIdPerson(String id);
	
	public Flux<Person> findFamily(String id);

	public Mono<Void> deletePerson(String id);
	
	public Mono<Person> addRelative(String id, String nameMember);

	public Mono<Person> savePerson(Person persona);
	
	public Mono<Person> updatePerson(Person persona, String id);
	
	public Flux<Person> findByDateRange(String date1, String date2);
	
	

	public Mono<Teacher> findTeacherByIdPerson(String idPerson);
	
	public Flux<Teacher> findAllTeachers();

	public Mono<Teacher> findByIdTeacher(String id);

	public Mono<Void> deleteTeacher(Teacher teacher);

	public Mono<Teacher> saveTeacher(Teacher teacher);
	
	public Mono<Teacher> updateTeacher(Teacher teacher, String id);
}
