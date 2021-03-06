package com.interventor.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "usuariosInterventor")
@Data
@NoArgsConstructor
public class UsuariosInterventor {

	@Id
	@JsonIgnore
	private String id;

	@Indexed(unique = true)
	private String username;

	public UsuariosInterventor(String username) {
		super();
		this.username = username;
	}
}
