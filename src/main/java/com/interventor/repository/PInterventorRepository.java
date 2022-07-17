package com.interventor.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;

import com.interventor.models.ProyectosInterventor;

public interface PInterventorRepository extends MongoRepository<ProyectosInterventor, String> {

	@RestResource(path = "find-user")
	public ProyectosInterventor findByIdProyecto(@Param("idProyecto") Integer idProyecto);

	@RestResource(path = "exist-user")
	public Boolean existsByIdProyecto(@Param("idProyecto") Integer idProyecto);

	@RestResource(path = "delete-user")
	public Boolean deleteByIdProyecto(@Param("idProyecto") Integer idProyecto);
}
