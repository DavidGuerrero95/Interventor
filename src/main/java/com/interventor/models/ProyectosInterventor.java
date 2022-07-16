package com.interventor.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "proyectosInterventor")
@Data
@NoArgsConstructor
public class ProyectosInterventor {

	@Id
	@JsonIgnore
	private String id;

	@Indexed(unique = true)
	private Integer idProyecto;

	public ProyectosInterventor(Integer idProyecto) {
		super();
		this.idProyecto = idProyecto;
	}

}
