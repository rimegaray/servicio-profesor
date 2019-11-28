package com.everis.springboot.app.controllers;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.everis.springboot.app.models.documents.Person;
import com.everis.springboot.app.models.documents.Teacher;
import com.everis.springboot.app.models.service.TeacherService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/teacher")
public class TeacherController {

	@Autowired
	private TeacherService service;
	
	@GetMapping
	public Flux<Teacher> findAll(){
		return service.findAllTeachers();
	}
	
	@GetMapping("/{id}")
	public Mono<Teacher> findById(@PathVariable String id){
		return service.findByIdTeacher(id);
	}
	
	public Flux<Teacher> alterFindByDateRange(String dateIni, String dateEnd){
		
		Teacher st = new Teacher();
		st.setFullName("No encontrado");
		st.setDateOfBirth(new Date());
		st.setGender("No encontrado");
		st.setTypeDocument("No encontrado");
		st.setNumberDocument("No encontrado");
		return Flux.just(st);
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Mono<Teacher> create(@RequestBody Teacher stud){
		return Mono.just(stud).flatMap(p ->{
			return service.savePerson(p);
		}).map(p-> {
			Teacher student = new Teacher();
			student.setId(p.getId());
			student.setFullName(p.getFullName());
			student.setGender(p.getGender());
			student.setDateOfBirth(p.getDateOfBirth());
			student.setTypeDocument(p.getTypeDocument());
			student.setNumberDocument(p.getNumberDocument());
			return student;
		})	.flatMap(s->service.saveTeacher(s));
	}
	
	@PutMapping("/{id}")
	public Mono<Person> update(@RequestBody Teacher student, @PathVariable String id){
		
		return service.updateTeacher(student, id)
				.flatMap(s ->
				service.findByIdPerson(s.getId()).flatMap(p-> {
					p.setDateOfBirth(s.getDateOfBirth());
					p.setFullName(s.getFullName());
					p.setGender(s.getGender());
					p.setTypeDocument(s.getTypeDocument());
					p.setNumberDocument(s.getNumberDocument());
					return service.savePerson(p);
				})
			);
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public Mono<Void> delete(@PathVariable String id){
		return service.findByIdTeacher(id).flatMap(s->service.deleteTeacher(s).and(service.deletePerson(s.getId())));
	}
	
	@GetMapping("/findFamily/{idStudent}")
	public Flux<Person> findFamily(@PathVariable String idStudent){
		
		return service.findByIdTeacher(idStudent).flatMapMany(s->service.findFamily(s.getId()));
	}
	
	@PutMapping("/addRelative/{id}/{nameMember}")
	public Mono<Person> addRelative(@PathVariable String id, @PathVariable String nameMember){
		return service.findByIdTeacher(id).flatMap(s->service.addRelative(s.getId(), nameMember));
	}
	
	@GetMapping("/dateRange/{dateInit}/{dateEnd}")
	public Flux<Teacher> findByDateRange(@PathVariable String dateInit, @PathVariable String dateEnd){
		return service.findByDateRange(dateInit, dateEnd).flatMap(p->{
			return service.findTeacherByIdPerson(p.getId());
		});
	}
	
}
