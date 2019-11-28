package com.everis.springboot.app.models.dao;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.everis.springboot.app.models.documents.Teacher;

public interface TeacherDao extends ReactiveMongoRepository<Teacher, String>{

}
