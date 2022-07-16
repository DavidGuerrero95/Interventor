package com.interventor.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "respuestasInterventor")
@Data
@NoArgsConstructor
public class RespuestasInterventor {

	@Id
	@JsonIgnore
	private String id;

	private String username;
	private Integer idProyecto;
	private Integer formulario;

	public RespuestasInterventor(String username, Integer idProyecto, Integer formulario) {
		super();
		this.username = username;
		this.idProyecto = idProyecto;
		this.formulario = formulario;
	}
	
}
