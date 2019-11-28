package com.everis.springboot.app.models.documents;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "profesor")
public class Teacher extends Person {
	
	@Id
	private String idTeacher;

	public String getidTeacher() {
		return idTeacher;
	}

	public void setidTeacher(String idTeacher) {
		this.idTeacher = idTeacher;
	}
	
	
}
