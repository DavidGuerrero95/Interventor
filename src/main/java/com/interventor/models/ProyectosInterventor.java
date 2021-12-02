package com.interventor.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "proyectosInterventor")
public class ProyectosInterventor {

	@Id
	private String id;

	@Indexed(unique = true)
	private String nombre;

	public ProyectosInterventor() {
	}

	public ProyectosInterventor(String nombre) {
		super();
		this.nombre = nombre;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

}
