package com.interventor.controllers;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.interventor.clients.ProyectosFeignClient;
import com.interventor.clients.UsersFeignClient;
import com.interventor.models.ProyectosInterventor;
import com.interventor.models.UsuariosInterventor;
import com.interventor.repository.PInterventorRepository;
import com.interventor.repository.UInterventorRepository;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class InterventorController {

	@SuppressWarnings("rawtypes")
	@Autowired
	private CircuitBreakerFactory cbFactory;

	@Autowired
	PInterventorRepository pRepository;

	@Autowired
	UInterventorRepository uRepository;

	@Autowired
	UsersFeignClient uClient;

	@Autowired
	ProyectosFeignClient pClient;

//  ****************************	USUARIOS 	***********************************  //

	// LISTAR USUARIOS
	@GetMapping("/interventor/listarUsuarios")
	@ResponseStatus(code = HttpStatus.OK)
	public List<UsuariosInterventor> verUsuarios() {
		return uRepository.findAll();
	}

	// CREAR PETICION
	@PostMapping("/interventor/usuariosEliminar")
	public Boolean peticionEliminarUsuarios(@RequestParam("username") String username) throws IOException {
		if (!uRepository.existsByUsername(username)) {
			UsuariosInterventor uInterventor = new UsuariosInterventor(username);
			uRepository.save(uInterventor);
			return true;
		} else if (uRepository.existsByUsername(username))
			return false;
		throw new IOException("Error en la creacion");
	}

	// ELIMINAR PETICION
	@PutMapping("/interventor/eliminar/peticion/usuario/")
	@ResponseStatus(code = HttpStatus.OK)
	public Boolean eliminarPeticionUsuarios(@RequestParam("username") String username) throws IOException {
		if (uRepository.existsByUsername(username)) {
			UsuariosInterventor uInterventor = uRepository.findByUsername(username);
			uRepository.delete(uInterventor);
		} else if (!uRepository.existsByUsername(username))
			return false;
		throw new IOException("Error en la eliminacion de peticion usuario");
	}

	@DeleteMapping("/interventor/eliminarUsuarioDefinitivamente/")
	public Boolean eliminarUsuario(@RequestParam("username") String username) {
		UsuariosInterventor uInter = uRepository.findByUsername(username);
		uRepository.delete(uInter);
		if (cbFactory.create("interventor").run(() -> uClient.eliminarUsuario(username),
				e -> errorConexionUsuarios(e))) {
			return true;
		}
		throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El servicio usuarios no disponible");
	}

//  ****************************	PROYECTOS 	***********************************  //

	// LISTAR PROYECTOS
	@GetMapping("/interventor/listarProyectos")
	@ResponseStatus(code = HttpStatus.OK)
	public List<ProyectosInterventor> verProyectos() {
		return pRepository.findAll();
	}

	// CREAER PETICION
	@PostMapping("/interventor/proyectosEliminar/")
	@ResponseStatus(code = HttpStatus.CREATED)
	public Boolean peticionEliminarProyectos(@RequestParam("codigoProyecto") Integer codigoProyecto)
			throws IOException {
		if (!pRepository.existsByIdProyecto(codigoProyecto)) {
			ProyectosInterventor pInterventor = new ProyectosInterventor(codigoProyecto);
			pRepository.save(pInterventor);
			return true;
		} else
			return false;
	}

	@PutMapping("/interventor/eliminar/peticion/proyecto/")
	@ResponseStatus(code = HttpStatus.OK)
	public Boolean eliminarPeticionProyecto(@RequestParam("codigoProyecto") Integer codigoProyecto) throws IOException {
		if (pRepository.existsByIdProyecto(codigoProyecto)) {
			ProyectosInterventor pInterventor = pRepository.findByIdProyecto(codigoProyecto);
			pRepository.delete(pInterventor);
		} else
			return false;
		throw new IOException("Error en la eliminacion de peticion proyecto");
	}

	@DeleteMapping("/interventor/eliminarProyectoDefinitivamente/")
	public Boolean eliminarProyectos(@RequestParam("codigoProyecto") Integer codigoProyecto) {
		ProyectosInterventor pInter = pRepository.findByIdProyecto(codigoProyecto);
		pRepository.delete(pInter);
		if (cbFactory.create("interventor").run(() -> pClient.eliminarProyectos(codigoProyecto),
				e -> errorConexionProyectos(e))) {
			return true;
		}
		throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El servicio usuarios no disponible");
	}

//  ****************************	FUNCIONES TOLERANCIA A FALLOS	***********************************  //

	public Boolean errorConexionUsuarios(Throwable e) {
		log.info(e.getMessage());
		return false;
	}

	public Boolean errorConexionProyectos(Throwable e) {
		log.info(e.getMessage());
		return false;
	}
}
