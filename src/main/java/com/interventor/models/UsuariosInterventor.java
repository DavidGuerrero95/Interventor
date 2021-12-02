package com.interventor.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "usuariosInterventor")
public class UsuariosInterventor {

	@Id
	private String id;

	@Indexed(unique = true)
	private String username;

	public UsuariosInterventor() {
	}

	public UsuariosInterventor(String username) {
		super();
		this.username = username;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNombre() {
		return username;
	}

	public void setNombre(String username) {
		this.username = username;
	}

}
