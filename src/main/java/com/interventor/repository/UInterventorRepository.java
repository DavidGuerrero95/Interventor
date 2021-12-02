package com.interventor.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;

import com.interventor.models.UsuariosInterventor;

public interface UInterventorRepository extends MongoRepository<UsuariosInterventor, String>{

	@RestResource(path = "find-user")
	public UsuariosInterventor findByUsername(@Param("username") String username);
	
	@RestResource(path = "exist-user")
	public Boolean existsByUsername(@Param("username") String username);
}
