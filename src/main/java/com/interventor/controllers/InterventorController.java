package com.interventor.controllers;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.interventor.clients.ProyectosFeignClient;
import com.interventor.clients.UsersFeignClient;
import com.interventor.models.ProyectosInterventor;
import com.interventor.models.UsuariosInterventor;
import com.interventor.repository.PInterventorRepository;
import com.interventor.repository.UInterventorRepository;

@RestController
public class InterventorController {

	private final Logger logger = LoggerFactory.getLogger(InterventorController.class);

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

	@GetMapping("/interventor/listarUsuarios")
	@ResponseStatus(code = HttpStatus.OK)
	public List<UsuariosInterventor> verUsuarios() {
		return uRepository.findAll();
	}
	
	@GetMapping("/interventor/listarProyectos")
	@ResponseStatus(code = HttpStatus.OK)
	public List<ProyectosInterventor> verProyectos() {
		return pRepository.findAll();
	}

	@PostMapping("/interventor/usuariosEliminar")
	@ResponseStatus(code = HttpStatus.CREATED)
	public Boolean peticionEliminarUsuarios(@RequestParam("username") String username) throws IOException {
		if (!uRepository.existsByUsername(username)) {
			UsuariosInterventor uInterventor = new UsuariosInterventor(username);
			uRepository.save(uInterventor);
			return true;
		} else if (uRepository.existsByUsername(username))
			return false;
		throw new IOException("Error en la creacion");
	}
	
	@PutMapping("/interventor/eliminar/peticion/usuario/")
	@ResponseStatus(code = HttpStatus.OK)
	public Boolean eliminarPeticionUsuarios(@RequestParam("username") String username) throws IOException {
		if(uRepository.existsByUsername(username)) {
			UsuariosInterventor uInterventor = uRepository.findByUsername(username);
			uRepository.delete(uInterventor);
		} else if (!uRepository.existsByUsername(username))
			return false;
		throw new IOException("Error en la eliminacion de peticion usuario");
	}

	@PostMapping("/interventor/proyectosEliminar")
	@ResponseStatus(code = HttpStatus.CREATED)
	public Boolean peticionEliminarProyectos(@RequestParam("nombre") String nombre) throws IOException {
		if (!pRepository.existsByNombre(nombre)) {
			ProyectosInterventor pInterventor = new ProyectosInterventor(nombre);
			pRepository.save(pInterventor);
			return true;
		} else if (uRepository.existsByUsername(nombre))
			return false;
		throw new IOException("Error en la creacion");
	}
	
	@PutMapping("/interventor/eliminar/peticion/proyecto/")
	@ResponseStatus(code = HttpStatus.OK)
	public Boolean eliminarPeticionProyecto(@RequestParam("nombre") String nombre) throws IOException {
		if(pRepository.existsByNombre(nombre)) {
			ProyectosInterventor pInterventor = pRepository.findByNombre(nombre);
			pRepository.delete(pInterventor);
		} else if (!pRepository.existsByNombre(nombre))
			return false;
		throw new IOException("Error en la eliminacion de peticion proyecto");
	}

	@DeleteMapping("/interventor/eliminarUsuarioDefinitivamente/")
	public ResponseEntity<?> eliminarUsuario(@RequestParam("username") String username) {
		UsuariosInterventor uInter = uRepository.findByUsername(username);
		uRepository.delete(uInter);
		if (cbFactory.create("interventor").run(() -> uClient.eliminarUsuario(username), e -> errorConexion(e))) {
			return ResponseEntity.ok("Usuario Eliminado Correctamente");
		}
		return ResponseEntity.badRequest().body("Error en la eliminacion del usuario");
	}

	@DeleteMapping("/interventor/eliminarProyectoDefinitivamente/")
	public ResponseEntity<?> eliminarProyectos(@RequestParam("nombre") String nombre) {
		ProyectosInterventor pInter = pRepository.findByNombre(nombre);
		pRepository.delete(pInter);
		if (cbFactory.create("interventor").run(() -> pClient.eliminarProyectos(nombre), e -> errorConexion(e))) {
			return ResponseEntity.ok("Proyecto Eliminado Correctamente");
		}
		return ResponseEntity.badRequest().body("Error en la eliminacion del usuario");
	}

	public Boolean errorConexion(Throwable e) {
		logger.info(e.getMessage());
		return false;
	}
}
