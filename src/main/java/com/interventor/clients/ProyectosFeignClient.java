package com.interventor.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "app-proyectos")
public interface ProyectosFeignClient {

	@DeleteMapping("/proyectos/eliminar/{codigoProyecto}")
	public Boolean eliminarProyectos(@PathVariable("codigoProyecto") Integer codigoProyecto);

}
